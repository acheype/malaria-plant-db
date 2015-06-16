package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.service.xls.annotations.EntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.transformers.TrimStringTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.XlsEntityRefFiller;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO for a bundle of 10 PlantIngredients
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "3 - PLANT INGREDIENTS", importOrder = 3, startRow = 2, outputEntityClass = PlantIngredients.class,
        entityRef = {
                @EntityRef(
                        dtoIdentifierProperties = {"species1"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species1"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species2"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species2"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species3"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species3"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species4"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species4"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species5"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species5"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species6"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species6"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species7"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species7"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species8"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species8"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species9"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species9"
                ),
                @EntityRef(
                        dtoIdentifierProperties = {"species10"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"species"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "species10"
                )
        })
//TODO Improve EmptyOrNotIfPropertyValue annotation to some ones with a criteriaProperty "not empty"
public class PlantIngredientsLine {

    @ImportProperty(columnLetterRef = "B", columnLabel = "Plant ingredient #1 (species)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String species1;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Plant ingredient #1 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed1", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String partUsed1;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Plant ingredient #2 (species)")
    private String species2;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Plant ingredient #2 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed2", transformer = TrimStringTransformer.class))
    private String partUsed2;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Plant ingredient #3 (species)")
    private String species3;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Plant ingredient #3 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed3", transformer = TrimStringTransformer.class))
    private String partUsed3;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Plant ingredient #4 (species)")
    private String species4;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Plant ingredient #4 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed4", transformer = TrimStringTransformer.class))
    private String partUsed4;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Plant ingredient #5 (species)")
    private String species5;

    @ImportProperty(columnLetterRef = "K", columnLabel = "Plant ingredient #5 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed5", transformer = TrimStringTransformer.class))
    private String partUsed5;

    @ImportProperty(columnLetterRef = "L", columnLabel = "Plant ingredient #6 (species)")
    private String species6;

    @ImportProperty(columnLetterRef = "M", columnLabel = "Plant ingredient #6 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed6", transformer = TrimStringTransformer.class))
    private String partUsed6;

    @ImportProperty(columnLetterRef = "N", columnLabel = "Plant ingredient #7 (species)")
    private String species7;

    @ImportProperty(columnLetterRef = "O", columnLabel = "Plant ingredient #7 (part used)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed7", transformer = TrimStringTransformer.class))
    private String partUsed7;

    @ImportProperty(columnLetterRef = "P", columnLabel = "Plant ingredient #8 (species)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed8", transformer = TrimStringTransformer.class))
    private String species8;

    @ImportProperty(columnLetterRef = "Q", columnLabel = "Plant ingredient #8 (part used)")
    private String partUsed8;

    @ImportProperty(columnLetterRef = "R", columnLabel = "Plant ingredient #9 (species)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed9", transformer = TrimStringTransformer.class))
    private String species9;

    @ImportProperty(columnLetterRef = "S", columnLabel = "Plant ingredient #9 (part used)")
    private String partUsed9;

    @ImportProperty(columnLetterRef = "T", columnLabel = "Plant ingredient #10 (species)",
            propertyLoader = @PropertyLoader(outputProperty = "partUsed10", transformer = TrimStringTransformer.class))
    private String species10;

    @ImportProperty(columnLetterRef = "U", columnLabel = "Plant ingredient #10 (part used)")
    private String partUsed10;

}
