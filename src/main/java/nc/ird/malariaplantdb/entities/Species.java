package nc.ird.malariaplantdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nc.ird.malariaplantdb.service.json.View;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Species entity
 * <p>
 * Species referenced in a publication
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "publication", "family", "species", "speciesNameInPub", "isHerbariumVoucher", "herbarium",
        "localName", "collectionSite", "country", "continent"})
@Entity
@Table(name = "species", uniqueConstraints = @UniqueConstraint(columnNames = {"publication_id", "species"}, name = "uk_pub_species"))
@Data
@EqualsAndHashCode(of = "id")
public class Species {

    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "species")})

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
    private String family;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false)
    private String species;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Column(nullable = false, name = "species_name_in_pub")
    private String speciesNameInPub;

    @JsonView(View.Detailed.class)
    @NotNull
    @Column(nullable = false, name = "is_herbarium_voucher")
    private Boolean isHerbariumVoucher;

    @JsonView(View.Detailed.class)
    private String herbarium;

    @JsonView(View.Detailed.class)
    @Column(name = "local_name")
    private String localName;

    @JsonView(View.Detailed.class)
    @Column(name = "collection_site")
    private String collectionSite;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false)
    private String country;

    @JsonView(View.Detailed.class)
    private String continent;

}
