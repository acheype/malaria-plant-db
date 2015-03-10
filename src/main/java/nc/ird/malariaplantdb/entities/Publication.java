package nc.ird.malariaplantdb.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nc.ird.malariaplantdb.service.json.View;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Publication entity
 *
 * Standard publication fields (based on Zotero fields and APA style).
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "citation", "title", "authors", "year", "entryType", "journal", "month", "page", "volume",
        "nbOfVolumes", "number", "bookTitle", "publisher", "edition", "university", "institution", "doi", "pmid", "isbn",
        "url", "isReviewed", "compilers", "compilersNotes", "ethnologyNotes", "inVivoPharmacoNotes",
        "inVitroPharmacoNotes", "clinicalNotes"})
@Entity
@Table(name = "publication", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}, name = "uk_title"))
@Data
@EqualsAndHashCode(of = "id")
public class Publication implements Serializable {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "publication")})

    @JsonView(View.Summary.class)
    @NotNull
    @Id
    @GeneratedValue(generator = "table-hilo-generator")
    private Long id;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false)
    private String title;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false)
    @OneToMany(mappedBy = "publication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Author> authors;

    @JsonView(View.Detailed.class)
    @NotNull
    @Min(0)
    @Max(3000)
    @Column(nullable = false)
    private Integer year;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false, name = "entry_type")
    private String entryType;

    @JsonView(View.Detailed.class)
    private String journal;

    @JsonView(View.Detailed.class)
    private String month;

    @JsonView(View.Detailed.class)
    private String page;

    @JsonView(View.Detailed.class)
    private String volume;

    @JsonView(View.Detailed.class)
    @Column(name = "nb_of_volumes")
    private String nbOfVolumes;

    @JsonView(View.Detailed.class)
    private String number;

    @JsonView(View.Detailed.class)
    @Column(name = "book_title")
    private String bookTitle;

    @JsonView(View.Detailed.class)
    private String publisher;

    @JsonView(View.Detailed.class)
    private String edition;

    @JsonView(View.Detailed.class)
    private String university;

    @JsonView(View.Detailed.class)
    private String institution;

    @JsonView(View.Summary.class)
    private String doi;

    @JsonView(View.Summary.class)
    private String pmid;

    @JsonView(View.Summary.class)
    private String isbn;

    @JsonView(View.Summary.class)
    @Type(type = "org.hibernate.type.StringClobType")
    @URL
    private String url;

    @JsonView(View.Detailed.class)
    @NotNull
    @Column(nullable = false, name = "is_reviewed")
    private Boolean isReviewed;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "publication_compilers",
            joinColumns = {@JoinColumn(name = "compiler_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "publication_id", nullable = false)})
    // TODO update with JPA way when the bug is resolved : https://hibernate.atlassian.net/browse/HHH-9436
    @ForeignKey(name = "fk_compiler_id", inverseName = "fk_publication_id")
    private List<Compiler> compilers;

    @JsonView(View.Detailed.class)
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "compilers_notes")
    private String compilersNotes;

    @JsonView(View.Summary.class)
    @NotNull
    @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ethnology> ethnologyNotes;

    @JsonView(View.Summary.class)
    @NotNull
    @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InVivoPharmaco> inVivoPharmacoNotes;

    @JsonView(View.Summary.class)
    @NotNull
    @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InVitroPharmaco> inVitroPharmacoNotes;

    @JsonView(View.Summary.class)
    @NotNull
    @OneToMany(mappedBy = "publication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InVitroPharmaco> clinicalNotes;

    /**
     * Get the publication citation in the APA style
     *
     * @return
     */
    @JsonView(View.Summary.class)
    @Transient
    public String getCitation() {
        return "";
    }
}
