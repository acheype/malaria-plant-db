package nc.ird.malariaplantdb.service.xls;

import lombok.*;

import java.util.List;
import java.util.Optional;

/**
 * Information for a Excel sheet
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SheetInfo {

    /**
     * DTO class which corresponds to the sheet
     */
    private Class dtoClass;

    /**
     * Excel sheet name to tell the user the sheet source reference
     */
    private String sheetLabel;

    /**
     * The number line of the first row imported from the sheet
    */
    private int startRow;

    /**
     * The output entity class in which the importation data will be loaded
     */
    private Class outputEntityClass;

    /**
     * Fields information list used for the importation
     */
    private List<ColumnInfo> columnInfos;

    /**
     * Access to a column info by its dto property name name
     * @param dtoPropertyName the dto property name searched
     * @return the column info object or {@code null} if not found
     */
    public ColumnInfo getColumnInfoByDtoPropertyName(@NonNull String dtoPropertyName){
        return columnInfos.stream()
                .filter(c -> dtoPropertyName.equals(c.getDtoPropertyName()))
                .findFirst()
                .orElse(null);
    }

}
