package nc.ird.malariaplantdb.service.xls.fillers.errors;

import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;

/**
 * Created by adri on 17/11/15.
 */
public abstract class FillerError {

    public enum ErrorCause {
        NO_ENTITY_FOUND,
        NOT_UNIQUE_MATCH
    }

    private PropVals propVals;

    private ErrorCause errorCause;

    private SheetInfo sheetInfo;

    public FillerError(PropVals propVals, ErrorCause errorCause) {
        this.propVals = propVals;
        this.errorCause = errorCause;
    }

    public PropVals getPropVals() {
        return propVals;
    }

    public ErrorCause getErrorCause() {
        return errorCause;
    }

    public SheetInfo getSheetInfo() {
        return sheetInfo;
    }
}
