package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.domain.*;
import nc.ird.malariaplantdb.repository.*;
import nc.ird.malariaplantdb.service.xls.ExcelETL;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import nc.ird.malariaplantdb.service.xls.structures.ImportStatus;
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
    private PublicationRepository publiRepo;

    @Inject
    private PubSpeciesRepository pubSpeciesRepo;

    @Inject
    private SpeciesRepository speciesRepo;

    @Inject
    private PlantIngredientRepository plantIngredientRepo;

    @Inject
    private RemedyRepository remedyRepo;

    @Inject
    private EthnologyRepository ethnologyRepo;

    @Transactional
    @RequestMapping(value = "/import",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ImportStatus importEntitiesFromXls() throws ImportException {
        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls.dto", "/xls/publi_mapping.xml");

        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/publi_test.xlsm"));
//        try {
        etl.startImportProcess(excelInput);
//        } catch (ImportException e) {
//            log.errors("Exception raised during the XLS importation", e);
//            return Response.status(404).build();
//        }

        if (etl.getImportStatus().isStatusOK()) {
            persistEntities(etl.getEntitiesMap());
        }

        return etl.getImportStatus();
    }

    private void persistEntities(ClassMap entities) {

        List<Publication> publications = entities.getList(Publication.class);
        for (Publication pub : publications) {
            for (Author author : pub.getAuthors())
                author.setPublication(pub);
            publiRepo.save(pub);
        }

        List<Species> species = entities.getList(Species.class);
        species.forEach(speciesRepo::save);

        List<PubSpecies> pubSpecies = entities.getList(PubSpecies.class);
        pubSpecies.forEach(pubSpeciesRepo::save);

        List<PlantIngredient> plantIngredients = entities.getList(PlantIngredient.class);
        plantIngredients.forEach(plantIngredientRepo::save);

        List<Remedy> remedies = entities.getList(Remedy.class);
        remedies.forEach(remedyRepo::save);

        List<Ethnology> ethnoNotes = entities.getList(Ethnology.class);
        ethnoNotes.forEach(ethnologyRepo::save);
    }

}
