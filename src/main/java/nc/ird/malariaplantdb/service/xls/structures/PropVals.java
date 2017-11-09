package nc.ird.malariaplantdb.service.xls.structures;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>Ordered list of the tuple (Property name, Value)</p>
 * <p>Two{@code PropVals} objects are equals when they have the same values in the same order (independently of the key
 * value).</p>
 *
 * @author acheype
 */
public class PropVals extends LinkedHashMap<String, Object>{

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PropVals oPropVals = (PropVals) o;
        if (oPropVals.size() != this.size())
            return false;

        Iterator<Map.Entry<String, Object>> thisEntriesIt = this.entrySet().iterator();
        Iterator<Map.Entry<String, Object>> oEntriesIt = oPropVals.entrySet().iterator();
        while (thisEntriesIt.hasNext() && oEntriesIt.hasNext()) {

            Map.Entry<String, Object> thisCurEntry = thisEntriesIt.next();
            Map.Entry<String, Object> oCurEntry = oEntriesIt.next();

            // code removed because the normalization is applied only fewer cases and can be implemented with a
            // specific EqualsStrategy class.
//            // During the comparison, if both values are Strings, a {@code trim()} and a {@code toLowerCase()} will be
//            // applied to compare them.
//            if (oCurEntry.getValue() instanceof String && thisCurEntry.getValue() instanceof String) {
//                String oStrVal = (String) oCurEntry.getValue();
//                String thisStrVal = (String) thisCurEntry.getValue();
//                if (!oStrVal.trim().toLowerCase().equals(thisStrVal.trim().toLowerCase()))
//                    return false;
//            } else
            if (!Objects.equals(oCurEntry.getValue(), thisCurEntry.getValue()))
                return false;
        }
        return true;
    }
}
