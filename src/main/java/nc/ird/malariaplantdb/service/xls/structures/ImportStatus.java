package nc.ird.malariaplantdb.service.xls.structures;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Importation process status
 *
 * @author acheype
 */
@Getter
@ToString
public class ImportStatus {

    boolean checkDone = false;

    private List<CellError> readErrors = new ArrayList<>();

    private List<CellError> businessErrors = new ArrayList<>();

    private List<CellError> integrityErrors = new ArrayList<>();

    public List<CellError> getReadErrorsBySheet(@NonNull String sheet) {
        return readErrors.stream().filter(e -> sheet.equals(e.getSheet())).collect(Collectors.toList());
    }

    public boolean isStatusOK() {
        return checkDone && readErrors.isEmpty() && businessErrors.isEmpty() && integrityErrors.isEmpty();
    }

    public void setCheckDone(boolean checkDone) {
        this.checkDone = checkDone;
    }
}
