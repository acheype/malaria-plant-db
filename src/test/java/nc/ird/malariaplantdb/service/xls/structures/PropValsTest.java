package nc.ird.malariaplantdb.service.xls.structures;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * PropVals Tests
 *
 * @author acheype
 */
public class PropValsTest {

    @Test
    public void testEquals() throws Exception {
        PropVals propVals1 = new PropVals();
        propVals1.put("a", "toto");
        propVals1.put("b", 5f);
        propVals1.put("c", true);

        PropVals propVals2 = new PropVals();
        propVals2.put("d", "toto");
        propVals2.put("e", 5f);
        propVals2.put("f", true);

        Assertions.assertThat(propVals1.equals("test")).isFalse();
        Assertions.assertThat(propVals1.equals(propVals2)).isTrue();

        propVals2.put("g", "titi");
        Assertions.assertThat(propVals1.equals(propVals2)).isFalse();

        propVals1.put("d", "titi");
        Assertions.assertThat(propVals1.equals(propVals2)).isTrue();
        propVals2.put("a", "TOTO");
        Assertions.assertThat(propVals1.equals(propVals2)).isFalse();

        propVals1.put("g", "TOTO");
        Assertions.assertThat(propVals1.equals(propVals2)).isTrue();
        propVals1.put("A a", "   TuTu   Toto  ");
        propVals2.put("B b", "tutu TOTO");
        Assertions.assertThat(propVals1.equals(propVals2)).isFalse();

    }
}
