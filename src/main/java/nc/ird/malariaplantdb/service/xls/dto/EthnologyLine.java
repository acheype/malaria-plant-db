package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.entities.Ethnology;
import nc.ird.malariaplantdb.service.xls.annotations.EntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.transformers.PlantIngredientsXlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.TrimStringTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.validators.EmptyOrNotIfPropertyValue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * DTO for Ethnology entity
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "4 - ETHNO", importOrder = 4, startRow = 2, outputEntityClass = Ethnology.class,
        entityRef = {
                @EntityRef(
                        dtoIdentifierProperties = {"publication"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"title"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "publication"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"plantIngredients"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"plantIngredient1", "plantIngredient2", "plantIngredient3",
                                "plantIngredient4", "plantIngredient5", "plantIngredient6", "plantIngredient7", "plantIngredient8",
                                "plantIngredient9", "plantIngredient10"},
                        entityRefType = PlantIngredients.class,
                        filler = PlantIngredientsXlsEntityRefFiller.class,
                        outputProperty = "plantIngredients"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"ethnoRelevancyRef"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"title"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "ethnoRelevancyRef"
                )
        })
@EmptyOrNotIfPropertyValue.List({
        @EmptyOrNotIfPropertyValue(
                message = "As 'Traditional recipe' is NO, 'Traditional recipe details' must be empty",
                criteriaProperty = "isTraditionalRecipe",
                criteriaValues = {"false"},
                testedProperty = "traditionalRecipeDetails",
                isEmpty = true
        ),
        @EmptyOrNotIfPropertyValue(
                message = "As 'Traditional recipe' is NO, 'Preparation mode in traditional recipe' must be empty",
                criteriaProperty = "isTraditionalRecipe",
                criteriaValues = {"false"},
                testedProperty = "preparationMode",
                isEmpty = true
        ),
        @EmptyOrNotIfPropertyValue(
                message = "As 'Traditional recipe' is NO, 'Administration route in traditional recipe' must be empty",
                criteriaProperty = "isTraditionalRecipe",
                criteriaValues = {"false"},
                testedProperty = "administrationRoute",
                isEmpty = true
        )}
)
public class EthnologyLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Publication (title)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String publication;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Plant ingredient(s) used")
    @NotEmpty(message = "The cell is empty or the value invalid")
    // TODO reactivate the validator just below
//    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ &\\.\\-\\(\\)]+),([a-zA-ZÀ-ÿ \\-\\.]+)/)*" +
//            "([a-zA-ZÀ-ÿ &\\.\\-\\(\\)]]+),([a-zA-ZÀ-ÿ \\-\\.]+)$",
//            message = "The plant ingredient(s) value is not well formatted. Please enter each species name first, " +
//            "a coma (,) then the part used. For several plant ingredients, please separate each plant ingredient " +
//            "by a slash (/).")
    private String plantIngredients;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Ethnopharmalogical relevancy",
            propertyLoader = @PropertyLoader(outputProperty = "ethnoRelevancy",
                    transformer = TrimStringTransformer.class))
    private String ethnoRelevancy;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Reference for ethnopharmalogical relevancy")
    private String ethnoRelevancyRef;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Treatment type",
            propertyLoader = @PropertyLoader(outputProperty = "treatmentType",
                    transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String treatmentType;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Traditional recipe",
            propertyLoader = @PropertyLoader(outputProperty = "isTraditionalRecipe"))
    @NotNull(message = "The cell is empty or the value invalid")
    private Boolean isTraditionalRecipe;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Traditional recipe details",
            propertyLoader = @PropertyLoader(outputProperty = "traditionalRecipeDetails",
                    transformer = TrimStringTransformer.class))
    private String traditionalRecipeDetails;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Preparation mode in traditional recipe",
            propertyLoader = @PropertyLoader(outputProperty = "preparationMode",
                    transformer = TrimStringTransformer.class))
    private String preparationMode;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Administration route in traditional recipe",
            propertyLoader = @PropertyLoader(outputProperty = "administrationRoute",
                    transformer = TrimStringTransformer.class))
    private String administrationRoute;

}
