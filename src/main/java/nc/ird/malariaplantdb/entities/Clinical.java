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
import java.util.List;

/**
 * Clinical study entity
 * <p>
 * Represents for the plant ingredients of a publication the relevant data in clinical study
 * For the moment, it's just the information about if there is relevant data.
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "publication", "plantIngredients"})
@Entity
@Table(name = "clinical")
@Data
@EqualsAndHashCode(of = "id")
public class Clinical {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "clinical")})

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
    @JoinColumn(name = "clinical_id", foreignKey = @ForeignKey(name = "fk_clinical_id"))
    private List<PlantIngredient> plantIngredients;

}
