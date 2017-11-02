package nc.ird.malariaplantdb.service.xls.fillers;

import nc.ird.malariaplantdb.service.xls.fillers.errors.DbFillerError;
import nc.ird.malariaplantdb.service.xls.fillers.errors.FillerError;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class DbEntityRefFiller extends EntityRefFiller {

    /**
     * Search in the database the {@code propValsSearched} values.
     * @param propValsSearched the {@code PropVals} object which defines the entries <Parameter, Values> from the dto
     * @return the list of the objects found
     */
    protected abstract List<Object> findValuesInDB(PropVals propValsSearched);

    /**
     * {@inheritDoc}
     */
    public Object findEntityRef(PropVals propValsSearched) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalArgumentException {

        List<Object> result = findValuesInDB(propValsSearched);

        if (result.isEmpty())
            this.setResultError(new DbFillerError(propValsSearched, FillerError.ErrorCause.NO_ENTITY_FOUND));
        else {
            if (result.size() != 1)
                this.setResultError(new DbFillerError(propValsSearched, FillerError.ErrorCause.NOT_UNIQUE_MATCH));
            else
                return result.get(0);
        }
        return null;
    }
}
