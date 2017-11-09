package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.domain.PubSpecies;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.annotations.XlsEntityRef;
import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.PubSpeciesToSpecies;
import nc.ird.malariaplantdb.service.xls.transformers.RemedyEntitiesTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO for a bundle of 10 PlantIngredients
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "3 - PLANT INGREDIENTS", importOrder = 3, outputEntityClass = PlantIngredientsTemp.class,
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
                dtoProperties = {"publication", "species1"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient1.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species2"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient2.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species3"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient3.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species4"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient4.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species5"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient5.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species6"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient6.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species7"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient7.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species8"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient8.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species9"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient9.species",
                outputTransformer = PubSpeciesToSpecies.class
            ),
            @XlsEntityRef(
                dtoProperties = {"publication", "species10"},
                dtoPropertiesTransformer = StringNormalizer.class,
                xlsEntityRefType = PubSpecies.class,
                xlsEntityRefProperties = {"publication.title", "species.species"},
                xlsEntityRefPropertiesLabels = {"Publication", "Species"},
                filler = XlsEntityRefFiller.class,
                outputProperty = "plantIngredient10.species",
                outputTransformer = PubSpeciesToSpecies.class,
                afterFillingTransformer = RemedyEntitiesTransformer.class
            )
        })
//TODO Improve EmptyOrNotIfPropertyValue annotation to some ones with a criteriaProperty "not empty"
public class PlantIngredientsLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Publication (title)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String publication;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Plant ingredient #1 (species)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String species1;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Plant ingredient #1 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient1.partUsed", transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String partUsed1;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Plant ingredient #2 (species)")
    private String species2;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Plant ingredient #2 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient2.partUsed", transformer = StringNormalizer.class))
    private String partUsed2;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Plant ingredient #3 (species)")
    private String species3;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Plant ingredient #3 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient3.partUsed", transformer = StringNormalizer.class))
    private String partUsed3;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Plant ingredient #4 (species)")
    private String species4;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Plant ingredient #4 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient4.partUsed", transformer = StringNormalizer.class))
    private String partUsed4;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Plant ingredient #5 (species)")
    private String species5;

    @ImportProperty(columnLetterRef = "K", columnLabel = "Plant ingredient #5 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient5.partUsed", transformer = StringNormalizer.class))
    private String partUsed5;

    @ImportProperty(columnLetterRef = "L", columnLabel = "Plant ingredient #6 (species)")
    private String species6;

    @ImportProperty(columnLetterRef = "M", columnLabel = "Plant ingredient #6 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient6.partUsed", transformer = StringNormalizer.class))
    private String partUsed6;

    @ImportProperty(columnLetterRef = "N", columnLabel = "Plant ingredient #7 (species)")
    private String species7;

    @ImportProperty(columnLetterRef = "O", columnLabel = "Plant ingredient #7 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient7.partUsed", transformer = StringNormalizer.class))
    private String partUsed7;

    @ImportProperty(columnLetterRef = "P", columnLabel = "Plant ingredient #8 (species)")
    private String species8;

    @ImportProperty(columnLetterRef = "Q", columnLabel = "Plant ingredient #8 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient8.partUsed", transformer = StringNormalizer.class))
    private String partUsed8;

    @ImportProperty(columnLetterRef = "R", columnLabel = "Plant ingredient #9 (species)")
    private String species9;

    @ImportProperty(columnLetterRef = "S", columnLabel = "Plant ingredient #9 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient9.partUsed", transformer = StringNormalizer.class))
    private String partUsed9;

    @ImportProperty(columnLetterRef = "T", columnLabel = "Plant ingredient #10 (species)")
    private String species10;

    @ImportProperty(columnLetterRef = "U", columnLabel = "Plant ingredient #10 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "plantIngredient10.partUsed", transformer = StringNormalizer.class))
    private String partUsed10;

}
