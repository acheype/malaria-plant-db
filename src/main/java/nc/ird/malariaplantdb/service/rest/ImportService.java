package nc.ird.malariaplantdb.service.rest;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.entities.*;
import nc.ird.malariaplantdb.entities.Compiler;
import nc.ird.malariaplantdb.repositories.EthnologyRepo;
import nc.ird.malariaplantdb.repositories.PlantIngredientRepo;
import nc.ird.malariaplantdb.repositories.PublicationRepo;
import nc.ird.malariaplantdb.repositories.SpeciesRepo;
import nc.ird.malariaplantdb.service.xls.ClassMap;
import nc.ird.malariaplantdb.service.xls.ExcelETL;
import nc.ird.malariaplantdb.service.xls.ImportStatus;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredients;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
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

    static final private String[] PLANT_INGREDIENTS_PROPERTIES = {"plantIngredient1", "plantIngredient2", "plantIngredient3",
            "plantIngredient4", "plantIngredient5", "plantIngredient6", "plantIngredient7", "plantIngredient8",
            "plantIngredient9", "plantIngredient10"};

    @Inject
    private PublicationRepo publiRepo;

    @Inject
    private SpeciesRepo speciesRepo;

    @Inject
    private PlantIngredientRepo plantIngredientRepo;

    @Inject
    private EthnologyRepo ethnologyRepo;

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
        species.forEach(speciesRepo::save);

        persistBundleOfPlantsIngredients(entities.getList(PlantIngredients.class));

        List<Ethnology> ethnoNotes = entities.getList(Ethnology.class);
        ethnoNotes.forEach(ethnologyRepo::save);
    }

    private void persistBundleOfPlantsIngredients(List<PlantIngredients> bundleOfPlantIngredients) {
        for (PlantIngredients plantIngredients : bundleOfPlantIngredients) {
            for (int i = 0; i < 10; i++) {
                try {
                    PlantIngredient plantIngredient = (PlantIngredient) PropertyUtils.getProperty(plantIngredients,
                            PLANT_INGREDIENTS_PROPERTIES[i]);
                    if (plantIngredient.getSpecies() != null && plantIngredient.getPartUsed() != null)
                        plantIngredientRepo.save(plantIngredient);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                        IllegalArgumentException e) {
                    throw new ImportRuntimeException("An unexpected error occurs during saving the entities " +
                            "in the database", e);
                }
            }
        }
    }

}
