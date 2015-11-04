package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nc.ird.malariaplantdb.domain.util.comparator.CompilerComparator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;


/**
 * Compiler entity
 * <p>
 * Must have a family and a given name. Email is mandatory.
 * A compiler can not to be bounded to a publication yet
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "family", "given", "institution", "institutionAddress", "email"})
@Table(name = "compiler", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}, name = "uk_compiler_email"))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="compiler")
public class Compiler implements Serializable, Comparable<Compiler> {

    private final static Comparator<Compiler> COMPARATOR = new CompilerComparator();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(max = 255)
    @Column(name = "family", length = 255, nullable = false)
    private String family;
    @NotNull
    @Size(max = 255)
    @Column(name = "given", length = 255, nullable = false)
    private String given;
    @Size(max = 255)
    @Column(name = "institution", length = 255)
    private String institution;
    @Size(max = 255)
    @Lob
    @Type(type="org.hibernate.type.StringClobType")
    @Column(name = "institution_address", length = 255)
    private String institutionAddress;
    @NotNull
    @Size(max = 255)
    @Email
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getInstitutionAddress() {
        return institutionAddress;
    }

    public void setInstitutionAddress(String institutionAddress) {
        this.institutionAddress = institutionAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Compiler compiler = (Compiler) o;

        return Objects.equals(id, compiler.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Compiler{" +
                "id=" + id +
                ", family='" + family + "'" +
                ", given='" + given + "'" +
                ", institution='" + institution + "'" +
                ", institutionAddress='" + institutionAddress + "'" +
                ", email='" + email + "'" +
                '}';
    }

    @Override
    public int compareTo(@NotNull Compiler o) {
        return COMPARATOR.compare(this, o);
    }
}
