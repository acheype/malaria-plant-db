package nc.ird.malariaplantdb.entities;

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
 * Plant ingredient entity
 *
 * Represent a part of a mix referred in a publication
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "species", "partUsed"})
@Entity
@Table(name = "plant_ingredient")
@Data
@EqualsAndHashCode(of = "id")
public class PlantIngredient {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "plant_ingredient")})

    @JsonView(View.Summary.class)
    @NotNull
    @Id
    @GeneratedValue(generator = "table-hilo-generator")
    private Long id;

    @JsonView(View.Summary.class)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", foreignKey = @ForeignKey(name = "fk_species_id"), nullable = false)
    private Species species;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false, name = "part_used")
    private String partUsed;

}
