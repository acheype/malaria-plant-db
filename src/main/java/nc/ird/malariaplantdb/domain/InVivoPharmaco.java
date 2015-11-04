package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.InVivoPharmacoComparator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * In vivo pharmacology entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in in vivo pharmacology
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "publication", "plantIngredients", "testedEntity", "extractionSolvent", "additiveProduct",
    "compoundName", "screeningTest","treatmentRoute", "dose", "inhibition", "survivalPercent", "survivalTime", "ed50",
    "ld50", "compilersObservations"})
@Table(name = "in_vivo_pharmaco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="invivopharmaco")
public class InVivoPharmaco implements Serializable, Comparable<InVivoPharmaco> {

    private final static Comparator<InVivoPharmaco> COMPARATOR = new InVivoPharmacoComparator();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @ManyToOne
    private Publication publication;
    @ManyToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "IN_VIVO_PHARMACO_PLANT_INGREDIENT",
        joinColumns = @JoinColumn(name="in_vivo_pharmacos_id", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="plant_ingredients_id", referencedColumnName="ID"))
    @SortNatural
    private SortedSet<PlantIngredient> plantIngredients = new TreeSet<>();
    @NotNull
    @Size(max = 255)
    @Column(name = "tested_entity", length = 255, nullable = false)
    private String testedEntity;
    @Size(max = 255)
    @Column(name = "extraction_solvent", length = 255)
    private String extractionSolvent;
    @Size(max = 255)
    @Column(name = "additive_product", length = 255)
    private String additiveProduct;
    @Size(max = 255)
    @Column(name = "compound_name", length = 255)
    private String compoundName;
    @NotNull
    @Size(max = 255)
    @Column(name = "screening_test", length = 255, nullable = false)
    private String screeningTest;
    @Size(max = 255)
    @Column(name = "treatment_route", length = 255)
    private String treatmentRoute;
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer=7, fraction=4)
    @Column(name = "dose", precision=11, scale=4)
    private BigDecimal dose;
    @Min(value = 0)
    @Max(value = 100)
    @Digits(integer=3, fraction=2)
    @Column(name = "inhibition", precision=5, scale=2)
    private BigDecimal inhibition;
    @Min(value = 0)
    @Max(value = 100)
    @Digits(integer=3, fraction=2)
    @Column(name = "survival_percent", precision=5, scale=2)
    private BigDecimal survivalPercent;
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer=3, fraction=2)
    @Column(name = "survival_time", precision=5, scale=2)
    private BigDecimal survivalTime;
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer=7, fraction=4)
    @Column(name = "ed50", precision=11, scale=4)
    private BigDecimal ed50;
    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer=7, fraction=4)
    @Column(name = "ld50", precision=11, scale=4)
    private BigDecimal ld50;
    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "compilers_observations")
    private String compilersObservations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestedEntity() {
        return testedEntity;
    }

    public void setTestedEntity(String testedEntity) {
        this.testedEntity = testedEntity;
    }

    public String getExtractionSolvent() {
        return extractionSolvent;
    }

    public void setExtractionSolvent(String extractionSolvent) {
        this.extractionSolvent = extractionSolvent;
    }

    public String getAdditiveProduct() {
        return additiveProduct;
    }

    public void setAdditiveProduct(String additiveProduct) {
        this.additiveProduct = additiveProduct;
    }

    public String getCompoundName() {
        return compoundName;
    }

    public void setCompoundName(String compoundName) {
        this.compoundName = compoundName;
    }

    public String getScreeningTest() {
        return screeningTest;
    }

    public void setScreeningTest(String screeningTest) {
        this.screeningTest = screeningTest;
    }

    public String getTreatmentRoute() {
        return treatmentRoute;
    }

    public void setTreatmentRoute(String treatmentRoute) {
        this.treatmentRoute = treatmentRoute;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    public BigDecimal getInhibition() {
        return inhibition;
    }

    public void setInhibition(BigDecimal inhibition) {
        this.inhibition = inhibition;
    }

    public BigDecimal getSurvivalPercent() {
        return survivalPercent;
    }

    public void setSurvivalPercent(BigDecimal survivalPercent) {
        this.survivalPercent = survivalPercent;
    }

    public BigDecimal getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(BigDecimal survivalTime) {
        this.survivalTime = survivalTime;
    }

    public BigDecimal getEd50() {
        return ed50;
    }

    public void setEd50(BigDecimal ed50) {
        this.ed50 = ed50;
    }

    public BigDecimal getLd50() {
        return ld50;
    }

    public void setLd50(BigDecimal ld50) {
        this.ld50 = ld50;
    }

    public String getCompilersObservations() {
        return compilersObservations;
    }

    public void setCompilersObservations(String compilersObservations) {
        this.compilersObservations = compilersObservations;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InVivoPharmaco inVivoPharmaco = (InVivoPharmaco) o;

        return Objects.equals(id, inVivoPharmaco.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InVivoPharmaco{" +
                "id=" + id +
                ", testedEntity='" + testedEntity + "'" +
                ", extractionSolvent='" + extractionSolvent + "'" +
                ", additiveProduct='" + additiveProduct + "'" +
                ", compoundName='" + compoundName + "'" +
                ", screeningTest='" + screeningTest + "'" +
                ", treatmentRoute='" + treatmentRoute + "'" +
                ", dose='" + dose + "'" +
                ", inhibition='" + inhibition + "'" +
                ", survivalPercent='" + survivalPercent + "'" +
                ", survivalTime='" + survivalTime + "'" +
                ", ed50='" + ed50 + "'" +
                ", ld50='" + ld50 + "'" +
                ", compilersObservations='" + compilersObservations + "'" +
                '}';
    }

    @Override
    public int compareTo(@NotNull InVivoPharmaco o) {
        return COMPARATOR.compare(this, o);
    }
}
