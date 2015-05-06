package nc.ird.malariaplantdb.service.xls;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by adri on 20/03/15.
 */
@Slf4j
@AllArgsConstructor
public class ExcelChecker {

    private List<SheetInfo> sheetInfos;

    public List<CellError> checkBusinessRules(ClassMap dtosMap){
        ArrayList<CellError> cellErrors = new ArrayList<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        for (SheetInfo sheetInfo : sheetInfos){
            int curLine = sheetInfo.getStartRow();
            List readBeans = dtosMap.getList(sheetInfo.getDtoClass());
            for (Object readBean : readBeans){
                Set<ConstraintViolation<Object>> constraintViolations =
                        validator.validate(readBean);
                for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                    ColumnInfo columnInfo = sheetInfo.getColumnInfoByDtoPropertyName(constraintViolation
                            .getPropertyPath().toString());

                    if (columnInfo == null){
                        throw new ImportRuntimeException("An unexpected error occurs during the Excel file checking",
                                new IllegalArgumentException(String.format("Impossible to find the dtoPropertyName " +
                                        "'%s' in the sheetInfos", constraintViolation.getPropertyPath())));
                    }

                    cellErrors.add(
                            new CellError(
                                    constraintViolation.getMessage(),
                                    sheetInfo.getSheetLabel(),
                                    curLine,
                                    columnInfo.getColumnName()
                            )
                    );
                }
                curLine++;
            }
        }

        log.debug("Cell Errors : " + (cellErrors.isEmpty() ? "<empty>" : ""));
        cellErrors.stream().forEach(ce -> log.debug(ce.toString()));

        return cellErrors;
    }
}
