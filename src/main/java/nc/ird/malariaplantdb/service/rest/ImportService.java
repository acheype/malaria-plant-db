package nc.ird.malariaplantdb.service.rest;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.entities.*;
import nc.ird.malariaplantdb.entities.Compiler;
import nc.ird.malariaplantdb.repositories.PlantIngredientRepo;
import nc.ird.malariaplantdb.repositories.PublicationRepo;
import nc.ird.malariaplantdb.repositories.SpeciesRepo;
import nc.ird.malariaplantdb.service.xls.ClassMap;
import nc.ird.malariaplantdb.service.xls.ExcelETL;
import nc.ird.malariaplantdb.service.xls.ImportStatus;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredients;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
@Service
@Path("/import")
@Slf4j
public class ImportService {

    @Inject
    private PublicationRepo publiRepo;

    @Inject
    private SpeciesRepo speciesRepo;

    @Inject
    private PlantIngredientRepo plantIngredientRepo;

    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/xls")
    public ImportStatus importEntitiesFromXls() throws ImportException {
        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls.dto", "/xls/publi_mapping.xml");

        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/publi_test.xlsm"));
//        try {
        etl.startImportProcess(excelInput);
//        } catch (ImportException e) {
//            log.error("Exception raised during the XLS importation", e);
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
            for (Compiler compiler : pub.getCompilers())
                compiler.getPublications().add(pub);
            publiRepo.save(pub);
        }

        List<Species> species = entities.getList(Species.class);
        for (Species sp : species) {
            speciesRepo.save(sp);
        }
        persistBundleOfPlantsIngredients(entities.getList(PlantIngredients.class));
    }

    private void persistBundleOfPlantsIngredients(List<PlantIngredients> bundleOfPlantIngredients) {
        for (PlantIngredients plantIngredients : bundleOfPlantIngredients) {
            persistPlantIngredient(plantIngredients.getSpecies1(), plantIngredients.getPartUsed1());
            persistPlantIngredient(plantIngredients.getSpecies2(), plantIngredients.getPartUsed2());
            persistPlantIngredient(plantIngredients.getSpecies3(), plantIngredients.getPartUsed3());
            persistPlantIngredient(plantIngredients.getSpecies4(), plantIngredients.getPartUsed4());
            persistPlantIngredient(plantIngredients.getSpecies5(), plantIngredients.getPartUsed5());
            persistPlantIngredient(plantIngredients.getSpecies6(), plantIngredients.getPartUsed6());
            persistPlantIngredient(plantIngredients.getSpecies7(), plantIngredients.getPartUsed7());
            persistPlantIngredient(plantIngredients.getSpecies8(), plantIngredients.getPartUsed8());
            persistPlantIngredient(plantIngredients.getSpecies9(), plantIngredients.getPartUsed9());
            persistPlantIngredient(plantIngredients.getSpecies10(), plantIngredients.getPartUsed10());
        }
    }

    private void persistPlantIngredient(Species species, String partUsed) {
        if (species != null && partUsed != null) {
            PlantIngredient plantIngredient = new PlantIngredient();
            plantIngredient.setSpecies(species);
            plantIngredient.setPartUsed(partUsed);

            plantIngredientRepo.save(plantIngredient);
        }
    }

}
