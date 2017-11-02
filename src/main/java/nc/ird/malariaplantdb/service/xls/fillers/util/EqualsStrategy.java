package nc.ird.malariaplantdb.service.xls.fillers.util;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;

/**
 * Interface used to supply an equality strategy applied between two{@code PropVals} objects
 *
 * @author acheype
 */
public interface EqualsStrategy {

    boolean equals(PropVals o1, PropVals o2);
}
