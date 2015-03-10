package nc.ird.malariaplantdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nc.ird.malariaplantdb.service.json.View;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Ethnology entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in ethnology
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "publication", "plantIngredients", "ethnoRelevancy", "ethnoRelevancyRef",
        "treatmentType", "isTraditionalRecipe", "traditionalRecipeDetails", "preparationMode", "administrationRoute"})
@Entity
@Table(name = "ethnology")
@Data
@EqualsAndHashCode(of = "id")
public class Ethnology {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "ethnology")})

    @JsonView(View.Summary.class)
    @NotNull
    @Id
    @GeneratedValue(generator = "table-hilo-generator")
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", foreignKey = @ForeignKey(name = "fk_publication_id"), nullable = false)
    private Publication publication;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // foreign key name doesn't seem to work for schema generation (it's only work if it's declared for
    // the reverse ManyToOne relationship but we don't want it in the object schema)
    @JoinColumn(name = "ethnology_id", foreignKey = @ForeignKey(name = "fk_ethnology_id"))
    private List<PlantIngredient> plantIngredients;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false, name = "ethno_relevancy")
    @Type(type = "org.hibernate.type.StringClobType")
    private String ethnoRelevancy;

    @JsonView(View.Detailed.class)
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ethno_relevancy_ref_id", foreignKey = @ForeignKey(name = "fk_publication_relevancy_id"))
    private Publication ethnoRelevancyRef;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false, name = "treatment_type")
    private String treatmentType;

    @JsonView(View.Detailed.class)
    @NotNull
    @Column(nullable = false, name = "is_traditional_recipe")
    private Boolean isTraditionalRecipe;

    @JsonView(View.Detailed.class)
    @Column(name = "traditional_recipe_details")
    @Type(type = "org.hibernate.type.StringClobType")
    private String traditionalRecipeDetails;

    @JsonView(View.Detailed.class)
    @Column(name = "preparation_mode")
    private String preparationMode;

    @JsonView(View.Detailed.class)
    @Column(name = "administration_route")
    private String administrationRoute;

}
