package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.domain.PubSpecies;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.annotations.XlsEntityRef;
import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.SpeciesEntitiesTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import nc.ird.malariaplantdb.service.xls.validators.EmptyOrNotIfPropertyValue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * DTO for Species entity
 *
 * @author acheype
 */
@Data
@ImportDto(sheetLabel = "2 - SPECIES", importOrder = 2, outputEntityClass = PubSpecies.class,
        xlsEntityRef = {
                @XlsEntityRef(
                        dtoProperties = {"publication"},
                        xlsEntityRefType = Publication.class,
                        xlsEntityRefProperties = {"title"},
                        xlsEntityRefPropertiesLabels = {"Title"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "publication",
                        dtoPropertiesTransformer = StringNormalizer.class
                )
        })
@EmptyOrNotIfPropertyValue.List({
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
            propertyLoader = @PropertyLoader(outputProperty = "species.family", transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String family;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Species",
            propertyLoader = @PropertyLoader(outputProperty = "species.species", transformer = StringNormalizer
                .class), afterLoadingTransformer = SpeciesEntitiesTransformer.class)
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String species;

    @ImportProperty(columnLetterRef = "D", columnLabel = "Species name in publication",
            propertyLoader = @PropertyLoader(outputProperty = "speciesNameInPub", transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String speciesNameInPub;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Herbarium voucher",
            propertyLoader = @PropertyLoader(outputProperty = "isHerbariumVoucher"))
    @NotNull(message = "The cell is empty or the value invalid")
    private Boolean isHerbariumVoucher;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Herbarium",
            propertyLoader = @PropertyLoader(outputProperty = "herbarium", transformer = StringNormalizer.class))
    private String herbarium;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Country",
            propertyLoader = @PropertyLoader(outputProperty = "country", transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String country;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Continent",
            propertyLoader = @PropertyLoader(outputProperty = "continent", transformer = StringNormalizer.class))
    private String continent;

}
