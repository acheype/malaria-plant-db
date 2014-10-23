package nc.ird.malariaplantdb.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Publication entity
 * <p>
 * Standard publication fields (based on JabRef). For more convenient, all are in Strings.
 *
 * @author : acheype
 */
@XmlRootElement
@Entity
@Data
@EqualsAndHashCode(of = "id")
// TODO authors can't be in the unique constraint... need to think about/update the model ?
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title"}, name = "uk_publication_title"))
public class Publication implements Serializable {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "table")})

    @Id
    @GeneratedValue(generator = "table-hilo-generator")
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    // don't work with @ForeignKey from JPA ?! -> foreignKey = @ForeignKey(name = "fk_publication_id")
    @JoinColumn(name = "publication_id", nullable = false)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // so using depreciated hibernate annotation
    @SuppressWarnings("deprecation")
    @org.hibernate.annotations.ForeignKey(name = "fk_publication_id")
    private List<Author> authors;

    @NotEmpty
    @Column(nullable = false)
    private String year;

    @NotEmpty
    @Column(nullable = false, name = "entry_type")
    private String entryType;

    private String journal;

    private String month;

    private String pages;

    private String volume;

    private String number;

    private String publisher;

    private String editor;

    private String address;

    private String edition;

    private String series;

    private String school;

    private String type;

    private String institution;

    private String doi;

    private String pmid;

    private String isbn;

    private String url;

}
