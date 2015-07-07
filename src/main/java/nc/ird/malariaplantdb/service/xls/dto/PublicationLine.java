package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import lombok.ToString;
import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.EntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.transformers.CompilersDbEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.StringToAuthorsList;
import nc.ird.malariaplantdb.service.xls.transformers.TrimStringTransformer;
import nc.ird.malariaplantdb.service.xls.validators.EmptyOrNotIfPropertyValue;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * DTO for Publication entity
 *
 * @author acheype
 */

@Data
@ToString(of = {"title", "authors", "year", "entryType", "isReviewed"})
@ImportDto(sheetLabel = "1 - PUBLI", importOrder = 1, startRow = 2, outputEntityClass = Publication.class,
        entityRef = {
                @EntityRef(
                        dtoIdentifierProperties = {"compilers"},
                        dtoIdentifierTransformer = TrimStringTransformer.class,
                        filler = CompilersDbEntityRefFiller.class,
                        outputProperty = "compilers"
                )})
@EmptyOrNotIfPropertyValue.List({
        @EmptyOrNotIfPropertyValue(
                message = "As 'Reviewed by compiler(s)' is YES, 'Compiler(s) name(s)' must not be empty",
                criteriaProperty = "isReviewed",
                criteriaValues = {"true"},
                testedProperty = "compilers",
                isEmpty = false
        ),
        @EmptyOrNotIfPropertyValue(
                message = "As 'Reviewed by compiler(s)' is NO, 'Compiler(s) name(s)' must be empty",
                criteriaProperty = "isReviewed",
                criteriaValues = {"false"},
                testedProperty = "compilers",
                isEmpty = true
        ),
        @EmptyOrNotIfPropertyValue(
                message = "As 'Reviewed by compiler(s)' is NO, 'Note from compiler(s)' must not be empty",
                criteriaProperty = "isReviewed",
                criteriaValues = {"false"},
                testedProperty = "compilersNotes",
                isEmpty = true
        )}
)
public class PublicationLine {

    @ImportProperty(columnLetterRef = "D", columnLabel = "Title",
            propertyLoader = @PropertyLoader(outputProperty = "title", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String title;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Author(s)",
            propertyLoader = @PropertyLoader(outputProperty = "authors", transformer = StringToAuthorsList.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-.]+)/)*([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-.]+)$",
            message = "The authors value is not well formatted. Please enter each author name with the last name " +
                    "first, a coma (,) then the given name initials (with comas). For several authors, " +
                    "please separate each complete name by a slash (/).")
    private String authors;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Year",
            propertyLoader = @PropertyLoader(outputProperty = "year"))
    @NotNull(message = "The cell is empty or the value invalid")
    @Range(min = 0, max = 3000, message = "The integer must be ranged between {min} and {max}")
    private Integer year;

    @ImportProperty(columnLetterRef = "A", columnLabel = "Entry Type",
            propertyLoader = @PropertyLoader(outputProperty = "entryType", transformer = TrimStringTransformer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    @Column(nullable = false, name = "entry_type")
    private String entryType;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Journal",
            propertyLoader = @PropertyLoader(outputProperty = "journal", transformer = TrimStringTransformer.class))
    private String journal;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Month",
            propertyLoader = @PropertyLoader(outputProperty = "month", transformer = TrimStringTransformer.class))
    private String month;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Page",
            propertyLoader = @PropertyLoader(outputProperty = "page", transformer = TrimStringTransformer.class))
    private String page;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Volume",
            propertyLoader = @PropertyLoader(outputProperty = "volume", transformer = TrimStringTransformer.class))
    private String volume;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Nb of volumes",
            propertyLoader = @PropertyLoader(outputProperty = "nbOfVolumes", transformer = TrimStringTransformer.class))
    @Column(name = "nb_of_volumes")
    private String nbOfVolumes;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Number",
            propertyLoader = @PropertyLoader(outputProperty = "number", transformer = TrimStringTransformer.class))
    private String number;

    @ImportProperty(columnLetterRef = "K", columnLabel = "Book title",
            propertyLoader = @PropertyLoader(outputProperty = "bookTitle", transformer = TrimStringTransformer.class))
    @Column(name = "book_title")
    private String bookTitle;

    @ImportProperty(columnLetterRef = "L", columnLabel = "Publisher",
            propertyLoader = @PropertyLoader(outputProperty = "publisher", transformer = TrimStringTransformer.class))
    private String publisher;

    @ImportProperty(columnLetterRef = "M", columnLabel = "Edition",
            propertyLoader = @PropertyLoader(outputProperty = "edition", transformer = TrimStringTransformer.class))
    private String edition;

    @ImportProperty(columnLetterRef = "N", columnLabel = "University",
            propertyLoader = @PropertyLoader(outputProperty = "university", transformer = TrimStringTransformer.class))
    private String university;

    @ImportProperty(columnLetterRef = "O", columnLabel = "Institution",
            propertyLoader = @PropertyLoader(outputProperty = "institution", transformer = TrimStringTransformer.class))
    private String institution;

    @ImportProperty(columnLetterRef = "P", columnLabel = "DOI",
            propertyLoader = @PropertyLoader(outputProperty = "doi", transformer = TrimStringTransformer.class))
    private String doi;

    @ImportProperty(columnLetterRef = "Q", columnLabel = "PMID",
            propertyLoader = @PropertyLoader(outputProperty = "pmid", transformer = TrimStringTransformer.class))
    private String pmid;

    @ImportProperty(columnLetterRef = "R", columnLabel = "ISBN",
            propertyLoader = @PropertyLoader(outputProperty = "isbn", transformer = TrimStringTransformer.class))
    private String isbn;

    @ImportProperty(columnLetterRef = "S", columnLabel = "URL",
            propertyLoader = @PropertyLoader(outputProperty = "url", transformer = TrimStringTransformer.class))
    @URL
    private String url;

    @ImportProperty(columnLetterRef = "T", columnLabel = "Reviewed by compiler(s)",
            propertyLoader = @PropertyLoader(outputProperty = "isReviewed"))
    @NotNull(message = "The cell is empty or the value invalid")
    private Boolean isReviewed;

    @ImportProperty(columnLetterRef = "U", columnLabel = "Compiler(s) name(s)")
    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-]+)/)*([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-]+)$",
            message = "The compilers value is not well formatted. Please enter each compiler name with the last name " +
                    "first, a coma (,) then the given name. For several compilers, please separate each complete " +
                    "name by a slash (/).")
    private String compilers;

    @ImportProperty(columnLetterRef = "V", columnLabel = "Note from compiler(s)",
            propertyLoader = @PropertyLoader(outputProperty = "compilersNotes", transformer =
                    TrimStringTransformer.class))
    private String compilersNotes;

}
