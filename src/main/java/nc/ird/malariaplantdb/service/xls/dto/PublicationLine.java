package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import lombok.ToString;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.DbEntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.fillers.CompilersDbEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.CompilersStrTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.StrToAuthorsSet;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
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
@ToString(of = {"title", "authors", "year", "entryType"})
@ImportDto(sheetLabel = "1 - PUBLI", importOrder = 1, outputEntityClass = Publication.class,
        dbEntityRef = {
                @DbEntityRef(
                        dtoProperties = {"compilers"},
                        dtoPropertiesTransformer = CompilersStrTransformer.class,
                        filler = CompilersDbEntityRefFiller.class,
                        outputProperty = "compilers"
                )
        })
public class PublicationLine {

    @ImportProperty(columnLetterRef = "D", columnLabel = "Title",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String title;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Author(s)",
            propertyLoader = @PropertyLoader(transformer = StrToAuthorsSet.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ \\-']+),([a-zA-ZÀ-ÿ \\-\\.]+)/)*([a-zA-ZÀ-ÿ \\-']+),([a-zA-ZÀ-ÿ \\-\\.]+)$",
            message = "The authors value is not well formatted. Please enter each author name with the last name " +
                    "first, a coma (,) then the given name initials (with comas). For several authors, " +
                    "please separate each complete name by a slash (/).")
    private String authors;

    @ImportProperty(columnLetterRef = "C", columnLabel = "Year",
            propertyLoader = @PropertyLoader())
    @NotNull(message = "The cell is empty or the value invalid")
    @Range(min = 0, max = 3000, message = "The integer must be ranged between {min} and {max}")
    private Integer year;

    @ImportProperty(columnLetterRef = "A", columnLabel = "Entry Type",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    @NotEmpty(message = "The cell is empty or the value invalid")
    @Column(nullable = false, name = "entry_type")
    private String entryType;

    @ImportProperty(columnLetterRef = "E", columnLabel = "Journal",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String journal;

    @ImportProperty(columnLetterRef = "F", columnLabel = "Pages",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String pages;

    @ImportProperty(columnLetterRef = "G", columnLabel = "Volume",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String volume;

    @ImportProperty(columnLetterRef = "H", columnLabel = "Nb of volumes",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    @Column(name = "nb_of_volumes")
    private String nbOfVolumes;

    @ImportProperty(columnLetterRef = "I", columnLabel = "Number",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String number;

    @ImportProperty(columnLetterRef = "J", columnLabel = "Book title",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    @Column(name = "book_title")
    private String bookTitle;

    @ImportProperty(columnLetterRef = "K", columnLabel = "Publisher",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String publisher;

    @ImportProperty(columnLetterRef = "L", columnLabel = "Edition",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String edition;

    @ImportProperty(columnLetterRef = "M", columnLabel = "Conference name",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String conferenceName;

    @ImportProperty(columnLetterRef = "N", columnLabel = "Conference place",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String conferencePlace;

    @ImportProperty(columnLetterRef = "O", columnLabel = "University",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String university;

    @ImportProperty(columnLetterRef = "P", columnLabel = "Institution",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String institution;

    @ImportProperty(columnLetterRef = "Q", columnLabel = "DOI",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String doi;

    @ImportProperty(columnLetterRef = "R", columnLabel = "PMID",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String pmid;

    @ImportProperty(columnLetterRef = "S", columnLabel = "ISBN",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String isbn;

    @ImportProperty(columnLetterRef = "T", columnLabel = "URL",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    @URL
    private String url;

    @NotEmpty(message = "The cell is empty or the value invalid")
    @ImportProperty(columnLetterRef = "U", columnLabel = "Compiler(s) name(s)")
    @Pattern(regexp = "^(([a-zA-ZÀ-ÿ \\-']+),([a-zA-ZÀ-ÿ \\-]+)/)*([a-zA-ZÀ-ÿ \\-']+),([a-zA-ZÀ-ÿ \\-]+)$",
            message = "The compilers value is not well formatted. Please enter each compiler name with the last name " +
                    "first, a coma (,) then the given name. For several compilers, please separate each complete " +
                    "name by a slash (/).")
    private String compilers;

    @ImportProperty(columnLetterRef = "V", columnLabel = "Note from compiler(s)",
            propertyLoader = @PropertyLoader(transformer = StringNormalizer.class))
    private String compilersNotes;

}
