package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.EthnologyComparator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Ethnology entity
 * <p/>
 * Represents for the plant ingredients of a publication the relevant data in ethnology
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "publication", "remedy", "ethnoRelevancy", "treatmentType",
    "traditionalRecipeDetails", "preparationMode", "administrationRoute"})
@Table(name = "ethnology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ethnology")
public class Ethnology implements Serializable, Comparable<Ethnology> {

    private final static Comparator<Ethnology> COMPARATOR = new EthnologyComparator();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Publication publication;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Remedy remedy;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "ethno_relevancy", nullable = false)
    private String ethnoRelevancy;

    @NotNull
    @Size(max = 255)
    @Column(name = "treatment_type", length = 255, nullable = false)
    private String treatmentType;

    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "traditional_recipe_details")
    private String traditionalRecipeDetails;

    @Size(max = 255)
    @Column(name = "preparation_mode", length = 255)
    private String preparationMode;

    @Size(max = 255)
    @Column(name = "administration_route", length = 255)
    private String administrationRoute;

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

    public Remedy getRemedy() {
        return remedy;
    }

    public void setRemedy(Remedy remedy) {
        this.remedy = remedy;
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

        return Objects.equals(id, ethnology.id);

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

    @Override
    public int compareTo(@NotNull Ethnology o) {
        return COMPARATOR.compare(this, o);
    }
}
