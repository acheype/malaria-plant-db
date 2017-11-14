package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.domain.*;
import nc.ird.malariaplantdb.repository.*;
import nc.ird.malariaplantdb.repository.search.*;
import nc.ird.malariaplantdb.service.PublicationService;
import nc.ird.malariaplantdb.service.xls.ExcelETL;
import nc.ird.malariaplantdb.service.xls.dto.PublicationLine;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import nc.ird.malariaplantdb.service.xls.structures.CellError;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import nc.ird.malariaplantdb.service.xls.structures.ImportStatus;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for the entities importation process.
 * <p>
 * Some operations are directly exposes as a Rest Web Services via the JAX-RS specification.
 *
 * @author acheype
 */
@RestController
@RequestMapping("/api")
public class ImportResource {

    private final Logger log = LoggerFactory.getLogger(ImportResource.class);

    @Inject
    private PublicationService publiService;

    @Inject
    private PublicationRepository publicationRepo;

    @Inject
    PublicationSearchRepository publicationSearchRepo;

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    AuthorSearchRepository authorSearchRepo;

    @Inject
    private PubSpeciesRepository pubSpeciesRepo;

    @Inject
    private PubSpeciesSearchRepository pubSpeciesSearchRepo;

    @Inject
    private SpeciesRepository speciesRepo;

    @Inject
    private SpeciesSearchRepository speciesSearchRepo;

    @Inject
    private PlantIngredientRepository plantIngredientRepo;

    @Inject
    private PlantIngredientSearchRepository plantIngredientSearchRepo;

    @Inject
    private RemedyRepository remedyRepo;

    @Inject
    private RemedySearchRepository remedySearchRepo;

    @Inject
    private EthnologyRepository ethnologyRepo;

    @Inject
    private EthnologySearchRepository ethnologySearchRepo;

    @Inject
    private InVivoPharmacoRepository inVivoPharmacoRepo;

    @Inject
    private InVivoPharmacoSearchRepository inVivoPharmacoSearchRepo;

    @Inject
    private InVitroPharmacoRepository inVitroPharmacoRepo;

    @Inject
    private InVitroPharmacoSearchRepository inVitroPharmacoSearchRepo;

    @Transactional
    @RequestMapping(value = "/import",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ImportStatus importEntitiesFromXls() throws ImportException {
        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls.dto", "/xls/publi_mapping.xml");

        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/publi_test.xlsm"));

        etl.checkDataForImport(excelInput);

        checkExitingPublications(etl);

        if (etl.getImportStatus().isStatusOK()) {
            etl.startImportProcess();

            checkPublicationsWithNoReview(etl);
            if (etl.getImportStatus().isStatusOK()) {
                persistEntities(etl.getEntitiesMap());
            }
        }

        return etl.getImportStatus();
    }

    private void checkPublicationsWithNoReview(ExcelETL etl){
        ArrayList<CellError> cellErrors = new ArrayList<>();

        SheetInfo pubSheetInfo = etl.getSheetInfos().stream()
            .filter(s -> s.getOutputEntityClass().equals(Publication.class))
            .findAny()
            .orElse(null);
        int curLine = pubSheetInfo.getStartRow();

        for (Publication pub : etl.getEntitiesMap().getList(Publication.class)) {
            List<Ethnology> ethnoList = etl.getEntitiesMap().getList(Ethnology.class);
            List<InVivoPharmaco> inVivoList = etl.getEntitiesMap().getList(InVivoPharmaco.class);
            List<InVitroPharmaco> inVitroList = etl.getEntitiesMap().getList(InVitroPharmaco.class);

            if (!ethnoList.stream().filter(e -> pub == e.getPublication()).findAny().isPresent() &&
                !inVivoList.stream().filter(ivv -> pub == ivv.getPublication()).findAny().isPresent() &&
                !inVitroList.stream().filter(ivt -> pub == ivt.getPublication()).findAny().isPresent())
                cellErrors.add(
                    new CellError(
                        String.format("The publication '%s' has no review data (ethnology notes, in vivo " +
                            "pharmaco notes or in vitro pharmaco notes). Please add at least one or delete the " +
                            "publication.", pub.getTitle()),
                        pubSheetInfo.getSheetLabel(),
                        curLine,
                        pubSheetInfo.getColumnInfoByDtoProperty("title").getColumnLabel()
                    )
                );

            curLine++;
        }
        etl.getImportStatus().getBusinessErrors().addAll(0, cellErrors);
    }

    private void checkExitingPublications(ExcelETL etl){
        ArrayList<CellError> cellErrors = new ArrayList<>();

        SheetInfo pubSheetInfo = etl.getSheetInfos().stream()
            .filter(s -> s.getDtoClass().equals(PublicationLine.class))
            .findAny()
            .orElse(null);
        int curLine = pubSheetInfo.getStartRow();

        for (PublicationLine pubLine : etl.getDtosMap().getList(PublicationLine.class)) {
            if (!publicationRepo.findByTitleIgnoreCase(StringNormalizer.getInstance().transform(pubLine.getTitle()))
                    .isEmpty())
                cellErrors.add(
                    new CellError(
                        String.format("The publication '%s' already exists in the database. Please remove it if you " +
                        "need to update the information about it.", pubLine.getTitle()),
                        pubSheetInfo.getSheetLabel(),
                        curLine,
                        pubSheetInfo.getColumnInfoByDtoProperty("title").getColumnLabel()
                    )
                );

            curLine++;
        }
        etl.getImportStatus().getBusinessErrors().addAll(0, cellErrors);
    }

    private void persistEntities(ClassMap entities) {

        List<Publication> publications = entities.getList(Publication.class);
        for (Publication pub : publications) {
            // save in the publication service because of the building of the "citation" property
            publiService.save(pub);
            publicationSearchRepo.save(pub);
            for (Author author : pub.getAuthors()) {
                author.setPublication(pub);
                authorRepo.save(author);
                authorSearchRepo.save(author);
            }
        }

        List<Species> species = entities.getList(Species.class);
        species.forEach(speciesRepo::save);
        species.forEach(speciesSearchRepo::save);

        List<PubSpecies> pubSpecies = entities.getList(PubSpecies.class);
        pubSpecies.forEach(pubSpeciesRepo::save);
        pubSpecies.forEach(pubSpeciesSearchRepo::save);

        List<Remedy> remedies = entities.getList(Remedy.class);
        remedies.forEach(remedyRepo::save);
        remedies.forEach(remedySearchRepo::save);

        List<PlantIngredient> plantIngredients = entities.getList(PlantIngredient.class);
        plantIngredients.forEach(plantIngredientRepo::save);
        plantIngredients.forEach(plantIngredientSearchRepo::save);

        List<Ethnology> ethnoNotes = entities.getList(Ethnology.class);
        ethnoNotes.forEach(ethnologyRepo::save);
        ethnoNotes.forEach(ethnologySearchRepo::save);

        List<InVivoPharmaco> inVivoNotes = entities.getList(InVivoPharmaco.class);
        inVivoNotes.forEach(inVivoPharmacoRepo::save);
        inVivoNotes.forEach(inVivoPharmacoSearchRepo::save);

        List<InVitroPharmaco> inVitroNotes = entities.getList(InVitroPharmaco.class);
        inVitroNotes.forEach(inVitroPharmacoRepo::save);
        inVitroNotes.forEach(inVitroPharmacoSearchRepo::save);
    }

}
