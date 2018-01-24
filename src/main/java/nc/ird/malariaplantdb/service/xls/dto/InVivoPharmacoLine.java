package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.annotations.XlsEntityRef;
import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.PlantIngredientsStrTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.PlantIngredientsTempToRemedy;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * In vivo pharmacology entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in in vivo pharmacology
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "5 - IN VIVO PHARMACO", importOrder = 5, startRow = 2, outputEntityClass = InVivoPharmaco.class,
    xlsEntityRef = {
        @XlsEntityRef(
            dtoProperties = {"publication"},
            xlsEntityRefType = Publication.class,
            xlsEntityRefProperties = {"title"},
            xlsEntityRefPropertiesLabels = {"Title"},
            filler = XlsEntityRefFiller.class,
            outputProperty = "publication",
            dtoPropertiesTransformer = StringNormalizer.class
        ),
        @XlsEntityRef(
            dtoProperties = {"publication", "plantIngredients"},
            dtoPropertiesTransformer = PlantIngredientsStrTransformer.class,
            xlsEntityRefType = PlantIngredientsTemp.class,
            xlsEntityRefProperties = {"publication.title", "plantIngredient1.species.species",
                "plantIngredient1.partUsed", "plantIngredient2.species.species",
                "plantIngredient2.partUsed", "plantIngredient3.species.species",
                "plantIngredient3.partUsed", "plantIngredient4.species.species",
                "plantIngredient4.partUsed", "plantIngredient5.species.species",
                "plantIngredient5.partUsed", "plantIngredient6.species.species",
                "plantIngredient6.partUsed", "plantIngredient7.species.species",
                "plantIngredient7.partUsed", "plantIngredient8.species.species",
                "plantIngredient8.partUsed", "plantIngredient9.species.species",
                "plantIngredient9.partUsed", "plantIngredient10.species.species",
                "plantIngredient10.partUsed"},
            xlsEntityRefPropertiesLabels = {"Publication", "Plant ingredient #1 (species)",
                "Plant ingredient #1 (part used)","Plant ingredient #2 (species)",
                "Plant ingredient #2 (part used)", "Plant ingredient #3 (species)",
                "Plant ingredient #3 (part used)", "Plant ingredient #4 (species)",
                "Plant ingredient #4 (part used)", "Plant ingredient #5 (species)",
                "Plant ingredient #5 (part used)", "Plant ingredient #6 (species)",
                "Plant ingredient #6 (part used)", "Plant ingredient #7 (species)",
                "Plant ingredient #7 (part used)", "Plant ingredient #8 (species)",
                "Plant ingredient #8 (part used)", "Plant ingredient #9 (species)",
                "Plant ingredient #9 (part used)", "Plant ingredient #10 (species)",
                "Plant ingredient #10 (part used)"},
            filler = XlsEntityRefFiller.class,
            outputProperty = "remedy",
            outputTransformer = PlantIngredientsTempToRemedy.class
        )
    })
public class InVivoPharmacoLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Publication (title)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String publication;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Plant ingredient(s) tested")
    @NotEmpty(message = "The cell is empty or the value invalid")
    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ &\\.\\-\\(\\)']+),([a-zA-ZÀ-ÿ \\-]+)/)*([a-zA-ZÀ-ÿ &\\.\\-\\(\\)']+),([a-zA-ZÀ-ÿ \\-]+)$",
        message = "The plant ingredient(s) value is not well formatted. Please enter each species name first, " +
            "a coma (,) then the part used. For several plant ingredients, please separate each plant ingredient " +
            "by a slash (/).")
    private String plantIngredients;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Tested entity",
            propertyLoader = @PropertyLoader(outputProperty = "testedEntity",
                    transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String testedEntity;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Extraction solvent",
            propertyLoader = @PropertyLoader(outputProperty = "extractionSolvent",
                    transformer = StringNormalizer.class))
    private String extractionSolvent;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Additive product",
            propertyLoader = @PropertyLoader(outputProperty = "additiveProduct",
                    transformer = StringNormalizer.class))
    private String additiveProduct;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Compound name",
            propertyLoader = @PropertyLoader(outputProperty = "compoundName",
                    transformer = StringNormalizer.class))
    private String compoundName;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Screening test",
            propertyLoader = @PropertyLoader(outputProperty = "screeningTest",
                    transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String screeningTest;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Treatment route",
        propertyLoader = @PropertyLoader(outputProperty = "treatmentRoute",
            transformer = StringNormalizer.class))
    private String treatmentRoute;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Dose for selected result",
        propertyLoader = @PropertyLoader(outputProperty = "dose"))
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    private BigDecimal dose;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Inhibition for selected result",
        propertyLoader = @PropertyLoader(outputProperty = "inhibition"))
    @Min(value = 0)
    @Max(value = 100)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal inhibition;

    @ImportProperty(columnLetterRef = "K", columnLabel = "Survical percent for selected result",
        propertyLoader = @PropertyLoader(outputProperty = "survivalPercent"))
    @Min(value = 0)
    @Max(value = 100)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal survivalPercent;

    @ImportProperty(columnLetterRef = "L", columnLabel = "Survical time for selected result",
        propertyLoader = @PropertyLoader(outputProperty = "survivalTime"))
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal survivalTime;

    @ImportProperty(columnLetterRef = "M", columnLabel = "ED50 for selected result",
        propertyLoader = @PropertyLoader(outputProperty = "ed50"))
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    private BigDecimal ed50;

    @ImportProperty(columnLetterRef = "N", columnLabel = "LD50 for selected result",
        propertyLoader = @PropertyLoader(outputProperty = "ld50"))
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    private BigDecimal ld50;

    @ImportProperty(columnLetterRef = "O", columnLabel = "Experimental observations from compiler(s)",
        propertyLoader = @PropertyLoader(outputProperty = "compilersObservations",
            transformer = StringNormalizer.class))
    private String compilersObservations;

}
