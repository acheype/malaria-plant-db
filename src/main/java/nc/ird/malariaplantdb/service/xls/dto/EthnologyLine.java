package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.domain.Ethnology;
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

import javax.validation.constraints.Pattern;

/**
 * DTO for Ethnology entity
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "4 - ETHNO", importOrder = 4, startRow = 2, outputEntityClass = Ethnology.class,
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
public class EthnologyLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Publication (title)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String publication;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Plant ingredient(s) used")
    @NotEmpty(message = "The cell is empty or the value invalid")
    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ &\\.\\-\\(\\)']+),([a-zA-ZÀ-ÿ \\-]+)/)*([a-zA-ZÀ-ÿ &\\.\\-\\(\\)']+),([a-zA-ZÀ-ÿ \\-]+)$",
            message = "The plant ingredient(s) value is not well formatted. Please enter each species name first, " +
            "a coma (,) then the part used. For several plant ingredients, please separate each plant ingredient " +
            "by a slash (/).")
    private String plantIngredients;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Ethnopharmalogical relevancy",
            propertyLoader = @PropertyLoader(outputProperty = "ethnoRelevancy",
                    transformer = StringNormalizer.class))
    private String ethnoRelevancy;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Treatment type",
            propertyLoader = @PropertyLoader(outputProperty = "treatmentType",
                    transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String treatmentType;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Traditional recipe details",
            propertyLoader = @PropertyLoader(outputProperty = "traditionalRecipeDetails",
                    transformer = StringNormalizer.class))
    private String traditionalRecipeDetails;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Preparation mode in traditional recipe",
            propertyLoader = @PropertyLoader(outputProperty = "preparationMode",
                    transformer = StringNormalizer.class))
    private String preparationMode;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Administration route in traditional recipe",
            propertyLoader = @PropertyLoader(outputProperty = "administrationRoute",
                    transformer = StringNormalizer.class))
    private String administrationRoute;

}
