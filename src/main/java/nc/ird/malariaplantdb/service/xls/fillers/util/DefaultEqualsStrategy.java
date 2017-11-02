package nc.ird.malariaplantdb.service.xls.fillers.util;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;

/**
 * The default equals strategy refer to the one defined for the PropVals class, that's to say two{@code
 * PropVals} objects are equals when they have the same values in the same order (independently of the key
 * value).
 */
public class DefaultEqualsStrategy implements EqualsStrategy {

    @Override
    public boolean equals(PropVals o1, PropVals o2) {
        return (o1 == null)? o2 == null : o1.equals(o2);
    }
}
