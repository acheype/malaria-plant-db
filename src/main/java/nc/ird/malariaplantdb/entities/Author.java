package nc.ird.malariaplantdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nc.ird.malariaplantdb.service.json.View;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author entity
 * <p>
 * For institutional and other single names, only the family name could be entered.
 *
 * @author : acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "publication", "family", "given"})
@Entity
@Table(name = "author")
@Data
@EqualsAndHashCode(of = "id")
public class Author {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@Parameter(value = "hibernate_id_generation", name = "author")})

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
    // could be null for an institutional and other single name
    private String given;

}