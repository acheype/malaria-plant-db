package nc.ird.malariaplantdb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Administrator
 * <p>
 * Special compiler who have access to manage the malaria plant db data.
 *
 * @author acheype
 */
@XmlRootElement
@Entity
@Data
@EqualsAndHashCode(of = "id", callSuper = true)
public class Administrator extends Compiler {

    @JsonIgnore
    @NotEmpty
    public String password;

}
