package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.RemedyComparator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SortNatural;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A Remedy.
 */
@Entity
@JsonPropertyOrder({"id", "plantIngredients"})
@Table(name = "remedy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "remedy")
public class Remedy implements Serializable, Comparable<Remedy> {

    private final static Comparator<Remedy> COMPARATOR = new RemedyComparator();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnoreProperties(value = {"remedy"})
    @OneToMany(mappedBy = "remedy", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @SortNatural
    private SortedSet<PlantIngredient> plantIngredients = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SortedSet<PlantIngredient> getPlantIngredients() {
        return plantIngredients;
    }

    public void setPlantIngredients(SortedSet<PlantIngredient> plantIngredients) {
        this.plantIngredients = plantIngredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Remedy remedy = (Remedy) o;

        if (id == null && remedy.id == null)
            return EqualsBuilder.reflectionEquals(this, remedy);

        if ( ! Objects.equals(id, remedy.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Remedy{" +
            "id=" + id +
            '}';
    }

    @Override
    public int compareTo(@NotNull Remedy o) {
        return COMPARATOR.compare(this, o);
    }
}
