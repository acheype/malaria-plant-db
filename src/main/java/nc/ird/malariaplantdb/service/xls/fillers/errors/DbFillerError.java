package nc.ird.malariaplantdb.service.xls.fillers.errors;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;

/**
 * Created by adri on 17/11/15.
 */
public class DbFillerError extends FillerError {

    public DbFillerError(PropVals propVals, ErrorCause errorCause) {
        super(propVals, errorCause);
    }

}
