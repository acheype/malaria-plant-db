package nc.ird.malariaplantdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nc.ird.malariaplantdb.service.json.View;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Compiler entity
 * <p>
 * Must have a family and a given name. Email is mandatory.
 * A compiler can not to be bounded to a publication yet
 *
 * @author acheype
 */
@XmlRootElement
@JsonPropertyOrder({"id", "publications", "family", "given", "institution", "institutionAddress", "email"})
@Entity
@Table(name = "compiler", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}, name = "uk_email"))
@Data
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Check(constraints = "DTYPE <> 'Administrator' OR password IS NOT null")
public class Compiler {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@org.hibernate.annotations.Parameter(value = "hibernate_id_generation", name = "compiler")})

    @JsonView(View.Summary.class)
    @NotNull
    @Id
    @GeneratedValue(generator = "table-hilo-generator")
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToMany(mappedBy = "compilers")
    private List<Publication> publications = new ArrayList<>();

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false)
    private String family;

    @JsonView(View.Summary.class)
    @NotEmpty
    @Column(nullable = false)
    private String given;

    @JsonView(View.Detailed.class)
    private String institution;

    @JsonView(View.Detailed.class)
    @Column(name = "institution_address")
    private String institutionAddress;

    @JsonView(View.Detailed.class)
    @NotEmpty
    @Email
    private String email;

}
