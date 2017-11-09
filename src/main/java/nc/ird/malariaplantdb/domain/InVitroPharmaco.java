package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.InVitroPharmacoComparator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/**
 * In vitro pharmacology entity
 *
 * Represents for the plant ingredients of a publication the relevant data in in vitro pharmacology
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "publication", "remedy", "testedEntity", "extractionSolvent", "additiveProduct",
    "compoundName", "screeningTest", "measureMethod", "concentration", "molConcentration", "inhibition", "ic50",
    "molIc50", "selectivityIndex", "compilersObservations"})
@Table(name = "in_vitro_pharmaco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "invitropharmaco")
public class InVitroPharmaco implements Serializable, Comparable<InVitroPharmaco> {

    private final static Comparator<InVitroPharmaco> COMPARATOR = new InVitroPharmacoComparator();

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
    @Column(name = "measure_method", length = 255)
    private String measureMethod;

    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    @Column(name = "concentration", precision = 11, scale = 4)
    private BigDecimal concentration;

    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    @Column(name = "mol_concentration", precision = 11, scale = 4)
    private BigDecimal molConcentration;

    @Min(value = 0)
    @Max(value = 100)
    @Digits(integer = 3, fraction = 2)
    @Column(name = "inhibition", precision = 5, scale = 2)
    private BigDecimal inhibition;

    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    @Column(name = "ic50", precision = 11, scale = 4)
    private BigDecimal ic50;

    @Min(value = 0)
    @Max(value = 1000000)
    @Digits(integer = 7, fraction = 4)
    @Column(name = "mol_ic50", precision = 11, scale = 4)
    private BigDecimal molIc50;

    @Min(value = 0)
    @Max(value = 100)
    @Digits(integer = 3, fraction = 2)
    @Column(name = "selectivity_index", precision = 5, scale = 2)
    private BigDecimal selectivityIndex;

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

    public String getMeasureMethod() {
        return measureMethod;
    }

    public void setMeasureMethod(String measureMethod) {
        this.measureMethod = measureMethod;
    }

    public BigDecimal getConcentration() {
        return concentration;
    }

    public void setConcentration(BigDecimal concentration) {
        this.concentration = concentration;
    }

    public BigDecimal getMolConcentration() {
        return molConcentration;
    }

    public void setMolConcentration(BigDecimal molConcentration) {
        this.molConcentration = molConcentration;
    }

    public BigDecimal getInhibition() {
        return inhibition;
    }

    public void setInhibition(BigDecimal inhibition) {
        this.inhibition = inhibition;
    }

    public BigDecimal getIc50() {
        return ic50;
    }

    public void setIc50(BigDecimal ic50) {
        this.ic50 = ic50;
    }

    public BigDecimal getMolIc50() {
        return molIc50;
    }

    public void setMolIc50(BigDecimal molIc50) {
        this.molIc50 = molIc50;
    }

    public BigDecimal getSelectivityIndex() {
        return selectivityIndex;
    }

    public void setSelectivityIndex(BigDecimal selectivityIndex) {
        this.selectivityIndex = selectivityIndex;
    }

    public String getCompilersObservations() {
        return compilersObservations;
    }

    public void setCompilersObservations(String compilersObservations) {
        this.compilersObservations = compilersObservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InVitroPharmaco inVitroPharmaco = (InVitroPharmaco) o;

        return Objects.equals(id, inVitroPharmaco.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InVitroPharmaco{" +
            "id=" + id +
            ", testedEntity='" + testedEntity + "'" +
            ", extractionSolvent='" + extractionSolvent + "'" +
            ", additiveProduct='" + additiveProduct + "'" +
            ", compoundName='" + compoundName + "'" +
            ", screeningTest='" + screeningTest + "'" +
            ", measureMethod='" + measureMethod + "'" +
            ", concentration='" + concentration + "'" +
            ", molConcentration='" + molConcentration + "'" +
            ", inhibition='" + inhibition + "'" +
            ", ic50='" + ic50 + "'" +
            ", molIc50='" + molIc50 + "'" +
            ", selectivityIndex='" + selectivityIndex + "'" +
            ", compilersObservations='" + compilersObservations + "'" +
            '}';
    }

    @Override
    public int compareTo(@NotNull InVitroPharmaco o) {
        return COMPARATOR.compare(this, o);
    }
}
