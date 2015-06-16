package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.entities.Species;
import nc.ird.malariaplantdb.service.xls.annotations.EntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.transformers.TrimStringTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.validators.EmptyOrNotIfPropertyValue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * DTO for Species entity
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "2 - SPECIES", importOrder = 2, startRow = 2, outputEntityClass = Species.class,
        entityRef = {
                @EntityRef(
                        dtoIdentifierProperties = {"publication"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        entityRefIdentifierProperties = {"title"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "publication"
                )
        })
@EmptyOrNotIfPropertyValue.List({
        @EmptyOrNotIfPropertyValue(
                message = "As 'Herbarium voucher' is YES, 'Herbarium' must not be empty",
                criteriaProperty = "isHerbariumVoucher",
                criteriaValues = {"true"},
                testedProperty = "herbarium",
                isEmpty = false
        ),
        @EmptyOrNotIfPropertyValue(
                message = "As 'Herbarium voucher' is NO, 'Herbarium' must be empty",
                criteriaProperty = "isHerbariumVoucher",
                criteriaValues = {"false"},
                testedProperty = "herbarium",
                isEmpty = true
        )}
)
public class SpeciesLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Publication (title)")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String publication;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Family",
            propertyLoader = @PropertyLoader(outputProperty = "family", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String family;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Species",
            propertyLoader = @PropertyLoader(outputProperty = "species", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String species;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Species name in publication",
            propertyLoader = @PropertyLoader(outputProperty = "speciesNameInPub", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String speciesNameInPub;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Herbarium voucher",
            propertyLoader = @PropertyLoader(outputProperty = "isHerbariumVoucher"))
    @NotNull(message = "The cell is empty or the value invalid")
    private Boolean isHerbariumVoucher;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Herbarium",
            propertyLoader = @PropertyLoader(outputProperty = "herbarium", transformer = TrimStringTransformer.class))
    private String herbarium;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Local name",
            propertyLoader = @PropertyLoader(outputProperty = "localName", transformer = TrimStringTransformer.class))
    private String localName;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Collection site",
            propertyLoader = @PropertyLoader(outputProperty = "collectionSite", transformer = TrimStringTransformer.class))
    private String collectionSite;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Country",
            propertyLoader = @PropertyLoader(outputProperty = "country", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String country;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Continent",
            propertyLoader = @PropertyLoader(outputProperty = "continent", transformer = TrimStringTransformer.class))
    private String continent;

}
