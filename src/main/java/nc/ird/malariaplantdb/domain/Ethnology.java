package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.EthnologyComparator;
import nc.ird.malariaplantdb.domain.util.comparator.PlantIngredientComparator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Ethnology entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in ethnology
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "publication", "plantIngredients", "ethnoRelevancy", "treatmentType",
    "traditionalRecipeDetails", "preparationMode", "administrationRoute"})
@Table(name = "ETHNOLOGY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="ethnology")
public class Ethnology implements Serializable, Comparable<Ethnology> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Publication publication;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ETHNOLOGY_PLANT_INGREDIENT",
        joinColumns = @JoinColumn(name="ethnologies_id", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="plant_ingredients_id", referencedColumnName="ID"))
    @SortNatural
    private SortedSet<PlantIngredient> plantIngredients = new TreeSet<>();

    @NotNull
    @Lob
    @Type(type="org.hibernate.type.StringClobType")
    @Column(name = "ethno_relevancy", nullable = false)
    private String ethnoRelevancy;

    @NotNull
    @Size(max = 255)
    @Column(name = "treatment_type", length = 255, nullable = false)
    private String treatmentType;

    @Lob
    @Type(type="org.hibernate.type.StringClobType")
    @Column(name = "traditional_recipe_details")
    private String traditionalRecipeDetails;

    @Size(max = 255)
    @Column(name = "preparation_mode", length = 255)
    private String preparationMode;

    @Size(max = 255)
    @Column(name = "administration_route", length = 255)
    private String administrationRoute;

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Set<PlantIngredient> getPlantIngredients() {
        return plantIngredients;
    }

    public void setPlantIngredients(SortedSet<PlantIngredient> PlantIngredients) {
        this.plantIngredients = PlantIngredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEthnoRelevancy() {
        return ethnoRelevancy;
    }

    public void setEthnoRelevancy(String ethnoRelevancy) {
        this.ethnoRelevancy = ethnoRelevancy;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTraditionalRecipeDetails() {
        return traditionalRecipeDetails;
    }

    public void setTraditionalRecipeDetails(String traditionalRecipeDetails) {
        this.traditionalRecipeDetails = traditionalRecipeDetails;
    }

    public String getPreparationMode() {
        return preparationMode;
    }

    public void setPreparationMode(String preparationMode) {
        this.preparationMode = preparationMode;
    }

    public String getAdministrationRoute() {
        return administrationRoute;
    }

    public void setAdministrationRoute(String administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ethnology ethnology = (Ethnology) o;

        if ( ! Objects.equals(id, ethnology.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ethnology{" +
                "id=" + id +
                ", ethnoRelevancy='" + ethnoRelevancy + "'" +
                ", treatmentType='" + treatmentType + "'" +
                ", traditionalRecipeDetails='" + traditionalRecipeDetails + "'" +
                ", preparationMode='" + preparationMode + "'" +
                ", administrationRoute='" + administrationRoute + "'" +
                '}';
    }

    private final static Comparator<Ethnology> COMPARATOR = new EthnologyComparator();

    @Override
    public int compareTo(Ethnology o) {
        return COMPARATOR.compare(this, o);
    }
}
