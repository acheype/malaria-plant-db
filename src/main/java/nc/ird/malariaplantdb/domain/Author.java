package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.AuthorComparator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Author entity
 * <p/>
 * For institutional and other single names, only the family name could be entered.
 *
 * @author : acheype
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonPropertyOrder({"id", "publication", "family", "given"})
@Table(name = "author")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "author")
public class Author implements Serializable, Comparable<Author> {

    private final static Comparator<Author> COMPARATOR = new AuthorComparator();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Publication publication;

    @NotNull
    @Size(max = 255)
    @Column(name = "family", length = 255, nullable = false)
    private String family;

    @Size(max = 255)
    @Column(name = "given", length = 255)
    private String given;

    @Min(value = 1)
    @Column(name= "position", nullable = false)
    private int position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        if (id == null && author.id == null)
            return EqualsBuilder.reflectionEquals(this, author);

        if ( ! Objects.equals(id, author.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + id +
            ", family='" + family + "'" +
            ", given='" + given + "'" +
            ", postion='" + position + "'" +
            '}';
    }

    @Override
    public int compareTo(@NotNull Author o) {
        return COMPARATOR.compare(this, o);
    }
}
