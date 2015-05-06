package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import lombok.ToString;
import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.validation.EmptyOrNotIfPropertyValue;
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
@ImportDto(sheetLabel = "1 - PUBLI", importOrder = 1, startRow = 2, outputEntityClass = Publication.class)
@Data
@ToString(of = {"title", "authors", "year", "entryType", "isReviewed"})
@EmptyOrNotIfPropertyValue.List({
        @EmptyOrNotIfPropertyValue(
                message = "As 'Reviewed by compiler(s)' is YES, 'Compiler(s) name(s)' must not be empty",
                criteriaProperty = "isReviewed",
                criteriaValues = {"true"},
                testedProperty = "compilers",
                isEmpty = false
        ),
        @EmptyOrNotIfPropertyValue(
                message = "As 'Reviewed by compiler(s)' is YES, 'Note from compiler(s)' must not be empty",
                criteriaProperty = "isReviewed",
                criteriaValues = {"true"},
                testedProperty = "compilersNotes",
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
public class PublicationSheet {

    @ImportProperty(columnLetterRef = "D", columnLabel = "Title", outputProperty = "title")
    @NotEmpty(message="The cell is empty or the value invalid")
    private String title;

    //@ImportProperty(columnLetterRef = "B", columnLabel = "Author(s)")
    @NotEmpty(message="The cell is empty or the value invalid")
    @Pattern(regexp= "^(([a-zA-ZÀ-ÿ -]+),([a-zA-ZÀ-ÿ -.]+)/)*([a-zA-ZÀ-ÿ -]+),([a-zA-ZÀ-ÿ -.]+)$",
            message = "The authors value is not well formated. Please enter each author name with the last name " +
                    "first, a coma (,) then the given name initials (with comas). For several authors, " +
                    "please separate each complete name by a slash (/).")
    private String authors;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Year", outputProperty = "year")
    @NotNull(message="The cell is empty or the value invalid")
    @Range(min=0,max=3000, message="The integer must be ranged between {min} and {max}")
    private Integer year;

    @ImportProperty(columnLetterRef = "A", columnLabel = "Entry Type", outputProperty = "entryType")
    @NotEmpty(message="The cell is empty or the value invalid")
    @Column(nullable = false, name = "entry_type")
    private String entryType;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Journal", outputProperty = "journal")
    private String journal;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Month", outputProperty = "month")
    private String month;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Page", outputProperty = "page")
    private String page;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Volume", outputProperty = "volume")
    private String volume;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Nb of volumes", outputProperty = "nbOfVolumes")
    @Column(name = "nb_of_volumes")
    private String nbOfVolumes;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Number", outputProperty = "number")
    private String number;

    @ImportProperty(columnLetterRef = "K", columnLabel = "Book title", outputProperty = "bookTitle")
    @Column(name = "book_title")
    private String bookTitle;

    @ImportProperty(columnLetterRef = "L", columnLabel = "Publisher", outputProperty = "publisher")
    private String publisher;

    @ImportProperty(columnLetterRef = "M", columnLabel = "Edition", outputProperty = "edition")
    private String edition;

    @ImportProperty(columnLetterRef = "N", columnLabel = "University", outputProperty = "university")
    private String university;

    @ImportProperty(columnLetterRef = "O", columnLabel = "Institution", outputProperty = "institution")
    private String institution;

    @ImportProperty(columnLetterRef = "P", columnLabel = "DOI", outputProperty = "doi")
    private String doi;

    @ImportProperty(columnLetterRef = "Q", columnLabel = "PMID", outputProperty = "pmid")
    private String pmid;

    @ImportProperty(columnLetterRef = "R", columnLabel = "ISBN", outputProperty = "isbn")
    private String isbn;

    @ImportProperty(columnLetterRef = "S", columnLabel = "URL", outputProperty = "url")
    @URL
    private String url;

    @ImportProperty(columnLetterRef = "T", columnLabel = "Reviewed by compiler(s)", outputProperty = "isReviewed")
    @NotNull(message="The cell is empty or the value invalid")
    private Boolean isReviewed;

    //@ImportProperty(columnLetterRef = "U", columnLabel = "Compiler(s) name(s)")
    private String compilers;

    @ImportProperty(columnLetterRef = "V", columnLabel = "Note from compiler(s)", outputProperty = "compilersNote")
    private String compilersNotes;

}
