package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.entities.PlantIngredient;
import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.transformers.TrimStringTransformer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * In vivo pharmacology entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in in vivo pharmacology
 *
 * @author acheype
 */
@Data
public class InVivoPharmacoLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Publication (title)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private Publication publication;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Plant ingredient(s) tested")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private List<PlantIngredient> plantIngredients;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Tested entity",
            propertyLoader = @PropertyLoader(outputProperty = "testedEntity",
                    transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String testedEntity;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Extraction solvent",
            propertyLoader = @PropertyLoader(outputProperty = "extractionSolvent",
                    transformer = TrimStringTransformer.class))
    private String extractionSolvent;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Additive product",
            propertyLoader = @PropertyLoader(outputProperty = "additiveProduct",
                    transformer = TrimStringTransformer.class))
    private String additiveProduct;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Compound name",
            propertyLoader = @PropertyLoader(outputProperty = "compoundName",
                    transformer = TrimStringTransformer.class))
    private String compoundName;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Screening test",
            propertyLoader = @PropertyLoader(outputProperty = "screeningTest",
                    transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String screeningTest;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Parasite",
            propertyLoader = @PropertyLoader(outputProperty = "parasite",
                    transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String parasite;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Parasite details",
            propertyLoader = @PropertyLoader(outputProperty = "parasiteDetails",
                    transformer = TrimStringTransformer.class))
    private String parasiteDetails;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Animal",
            propertyLoader = @PropertyLoader(outputProperty = "animal",
                    transformer = TrimStringTransformer.class))
    private String animal;

    private String treatmentRoute;

    private Float dose;

    @Min(0)
    @Max(100)
    private Float inhibition;

    @Min(0)
    @Max(100)
    private Float survivalPercent;

    @Column(name = "survival_time")
    private Float survivalTime;

    private Float ed50;

    @NotNull(message = "The cell is empty or the value invalid")
    private Boolean isToxicity;

    private Float ld50;

    private String compilersObservations;

}
