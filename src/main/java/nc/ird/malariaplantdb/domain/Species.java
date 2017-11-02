package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.SpeciesComparator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Species entity
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "family", "species"})
@Table(name = "species", uniqueConstraints = @UniqueConstraint(columnNames = {"family", "species"}, name =
    "uk_species_family_species"))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "species")
public class Species implements Serializable, Comparable<Species> {

    private final static Comparator<Species> COMPARATOR = new SpeciesComparator();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "family", length = 255, nullable = false)
    private String family;

    @NotNull
    @Size(max = 255)
    @Column(name = "species", length = 255, nullable = false)
    private String species;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Species species = (Species) o;

        if (id == null && species.id == null)
            return EqualsBuilder.reflectionEquals(this, species);

        if ( ! Objects.equals(id, species.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Species{" +
            "id=" + id +
            ", family='" + family + "'" +
            ", species='" + species + "'" +
            '}';
    }

    @Override
    public int compareTo(@NotNull Species o) {
        return COMPARATOR.compare(this, o);
    }
}
