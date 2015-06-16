package nc.ird.malariaplantdb.service.xls;


import lombok.Data;
import lombok.EqualsAndHashCode;
import nc.ird.malariaplantdb.entities.Publication;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class XlsRefTest {

    private Long id;

    private Publication publication;

}
