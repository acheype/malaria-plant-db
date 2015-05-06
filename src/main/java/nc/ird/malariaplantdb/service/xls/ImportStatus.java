package nc.ird.malariaplantdb.service.xls;

import lombok.*;

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

    private List<CellError> readErrors = new ArrayList<>();

    private List<CellError> businessErrors = new ArrayList<>();

    private List<CellError> dbIntegrityErrors = new ArrayList<>();

    public List<CellError> getReadErrorsBySheet(@NonNull String sheet){
        return readErrors.stream().filter(e -> sheet.equals(e.getSheet())).collect(Collectors.toList());
    }

}
