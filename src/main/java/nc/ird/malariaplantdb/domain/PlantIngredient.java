package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.PlantIngredientComparator;
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
 * Plant ingredient entity
 * <p/>
 * Represent a part of a mix referred in a publication
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "species", "partUsed"})
@Table(name = "plant_ingredient", uniqueConstraints = @UniqueConstraint(columnNames = {"species_id", "part_used",
    "remedy_id"},
    name = "uk_plantingredient_speciesid_partused_remedy_id"))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "plantingredient")
public class PlantIngredient implements Serializable, Comparable<PlantIngredient> {

    private final static Comparator<PlantIngredient> COMPARATOR = new PlantIngredientComparator();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Species species;

    @NotNull
    @Size(max = 255)
    @Column(name = "part_used", length = 255, nullable = false)
    private String partUsed;

    @NotNull
    @ManyToOne
    private Remedy remedy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getPartUsed() {
        return partUsed;
    }

    public void setPartUsed(String partUsed) {
        this.partUsed = partUsed;
    }

    public Remedy getRemedy() {
        return remedy;
    }

    public void setRemedy(Remedy remedy) {
        this.remedy = remedy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlantIngredient plantIngredient = (PlantIngredient) o;

        return Objects.equals(id, plantIngredient.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PlantIngredient{" +
            "id=" + id +
            ", partUsed='" + partUsed + "'" +
            '}';
    }

    @Override
    public int compareTo(@NotNull PlantIngredient o) {
        return COMPARATOR.compare(this, o);
    }
}
