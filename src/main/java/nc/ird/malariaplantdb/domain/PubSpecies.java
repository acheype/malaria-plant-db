package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.PubSpeciesComparator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * PubSpecies entity
 * <p/>
 * Information about a species referenced in a publication
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "publication", "species", "speciesNameInPub", "isHerbariumVoucher", "herbarium",
    "country", "continent"})
@Table(name = "pub_species")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pubspecies")
public class PubSpecies implements Serializable, Comparable<PubSpecies> {

    private final static Comparator<PubSpecies> COMPARATOR = new PubSpeciesComparator();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    private Publication publication;

    @NotNull
    @ManyToOne
    @Field(type = FieldType.Nested)
    private Species species = new Species();

    @NotNull
    @Size(max = 255)
    @Column(name = "species_name_in_pub", length = 255, nullable = false)
    private String speciesNameInPub;

    @NotNull
    @Column(name = "is_herbarium_voucher")
    private Boolean isHerbariumVoucher;

    @Column(name = "herbarium")
    private String herbarium;

    @NotNull
    @Size(max = 255)
    @Column(name = "country", length = 255, nullable = false)
    private String country;

    @Size(max = 255)
    @Column(name = "continent", length = 255)
    private String continent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication Publication) {
        this.publication = Publication;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getSpeciesNameInPub() {
        return speciesNameInPub;
    }

    public void setSpeciesNameInPub(String speciesNameInPub) {
        this.speciesNameInPub = speciesNameInPub;
    }

    public Boolean getIsHerbariumVoucher() {
        return isHerbariumVoucher;
    }

    public void setIsHerbariumVoucher(Boolean isHerbariumVoucher) {
        this.isHerbariumVoucher = isHerbariumVoucher;
    }

    public String getHerbarium() {
        return herbarium;
    }

    public void setHerbarium(String herbarium) {
        this.herbarium = herbarium;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PubSpecies pubSpecies = (PubSpecies) o;

        return Objects.equals(id, pubSpecies.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PubSpecies{" +
            "id=" + id +
            ", speciesNameInPub='" + speciesNameInPub + "'" +
            ", isHerbariumVoucher='" + isHerbariumVoucher + "'" +
            ", herbarium='" + herbarium + "'" +
            ", country='" + country + "'" +
            ", continent='" + continent + "'" +
            '}';
    }

    @Override
    public int compareTo(@NotNull PubSpecies o) {
        return COMPARATOR.compare(this, o);
    }
}
