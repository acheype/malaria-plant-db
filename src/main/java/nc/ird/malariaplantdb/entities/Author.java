package nc.ird.malariaplantdb.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author entity
 * <p>
 * For institutional and other single names, only the family name could be entered.
 *
 * @author : acheype
 */
@XmlRootElement
@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Author {
    @GenericGenerator(name = "table-hilo-generator", strategy = "org.hibernate.id.TableHiLoGenerator",
            parameters = {@Parameter(value = "hibernate_id_generation", name = "table")})

    @Id
    @GeneratedValue(generator = "table-hilo-generator")
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String family;

    // could be null for an institutional and other single name
    private String given;

}
