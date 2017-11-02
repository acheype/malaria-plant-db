package nc.ird.malariaplantdb.service.xls.fillers.errors;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;

import java.util.List;

/**
 * Created by adri on 17/11/15.
 */
public class XlsFillerError extends FillerError {

    private Class<?> refEntityClass;

    private List<String> xlsEntityRefProperties;

    private List<String> xlsEntityRefPropertiesLabels;

    public XlsFillerError(PropVals propVals, ErrorCause errorCause, Class<?> refEntityClass, List<String>
            xlsEntityRefProperties, List<String> xlsEntityRefPropertiesLabels) {
        super(propVals, errorCause);
        this.refEntityClass = refEntityClass;
        this.xlsEntityRefProperties = xlsEntityRefProperties;
        this.xlsEntityRefPropertiesLabels = xlsEntityRefPropertiesLabels;
    }

    public Class<?> getRefEntityClass() {
        return refEntityClass;
    }

    public List<String> getXlsEntityRefProperties() {
        return xlsEntityRefProperties;
    }

    public List<String> getXlsEntityRefPropertiesLabels() {
        return xlsEntityRefPropertiesLabels;
    }
}
