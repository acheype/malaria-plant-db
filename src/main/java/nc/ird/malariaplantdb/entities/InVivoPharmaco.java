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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * In vivo pharmacology entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in in vivo pharmacology
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "publication", "plantIngredients", "testedEntity", "extractionSolvent", "additiveProduct",
        "compoundName", "screeningTest", "parasite", "parasiteDetails", "animal", "treatmentRoute", "dose", "inhibition",
        "survivalPercent", "survivalTime", "ed50", "isToxicity", "ld50", "compilersObservations"})
@Entity
@Table(name = "in_vivo_pharmaco")
@Data
@EqualsAndHashCode(of = "id")
public class InVivoPharmaco {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "inVivoPharmaco")})

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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // foreign key name doesn't seem to work for schema generation (it's only work if it's declared for
    // the reverse ManyToOne relationship but we don't want it in the object schema)
    @JoinColumn(name = "in_vivo_pharmaco_id", foreignKey = @ForeignKey(name = "fk_in_vivo_pharmaco_id"))
    private List<PlantIngredient> plantIngredients;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false, name = "tested_entity")
    private String testedEntity;

    @JsonView(View.Detailed.class)
    @Column(name = "extraction_solvent")
    private String extractionSolvent;

    @JsonView(View.Detailed.class)
    @Column(name = "additive_product")
    private String additiveProduct;

    @JsonView(View.Detailed.class)
    @Column(name = "compound_name")
    private String compoundName;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false, name = "screening_test")
    private String screeningTest;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false)
    private String parasite;

    @JsonView(View.Detailed.class)
    @Column(name = "parasite_details")
    private String parasiteDetails;

    @JsonView(View.Detailed.class)
    private String animal;

    @JsonView(View.Detailed.class)
    @Column(name = "treatment_route")
    private String treatmentRoute;

    @JsonView(View.Detailed.class)
    private Float dose;

    @JsonView(View.Summary.class)
    @Min(0)
    @Max(100)
    private Float inhibition;

    @JsonView(View.Detailed.class)
    @Min(0)
    @Max(100)
    @Column(name = "survival_percent")
    private Float survivalPercent;

    @JsonView(View.Detailed.class)
    @Column(name = "survival_time")
    private Float survivalTime;

    @JsonView(View.Summary.class)
    private Float ed50;

    @JsonView(View.Detailed.class)
    @NotNull
    @Column(nullable = false, name = "is_toxicity")
    private Boolean isToxicity;

    @JsonView(View.Detailed.class)
    private Float ld50;

    @JsonView(View.Detailed.class)
    @Column(name = "compilers_observations")
    @Type(type = "org.hibernate.type.StringClobType")
    private String compilersObservations;

}
