package nc.ird.malariaplantdb.service.xls.infos;

import lombok.*;

import java.util.List;

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

    // property removed because an other solution have been found, could be enabled again for some cases
//    /**
//     * <p>The entity class from which the importation process will start. In this case, the reading stage
//     * will be omitted ({@code startRow} will be ignored) and the values will be directly read from this entity by the
//     * loaders. That option is useful in rare case, when the importation need to transform the model in several steps
//     * .</p>
//     * <ul><li>The {@code sheetLabel} must be the same as the dto's one used to produce the entity, in this way the
//     * cell errors of these different steps will refer to the same sheet label.</li>
//     * <li>The {@code importOrder} must be greater than the dto's one used to produce the entity, so entity values will
//     * already be loaded.</li>
//     */
//    private Class<?> fromOtherEntity;

    /**
     * The number line of the first row imported from the sheet
     */
    private int startRow;

    /**
     * The output entity class in which the importation data will be loaded
     */
    private Class outputEntityClass;

    /**
     * Information about the Xls entity references to set in the final entity
     */
    private List<XlsEntityRefInfo> xlsEntityRefInfos;

    /**
     * Information about the DB entity references to set in the final entity
     */
    private List<DbEntityRefInfo> dbEntityRefInfos;

    /**
     * Fields information list used for the importation
     */
    private List<ColumnInfo> columnInfos;

    /**
     * Access to a column info by its dto property name. For nested, indexed or mapped properties, refer to the first
     * name element.
     *
     * @param dtoPropertyName the dto property name searched
     * @return the column info object or {@code null} if not found
     */
    public ColumnInfo getColumnInfoByDtoProperty(@NonNull String dtoPropertyName) {

        return columnInfos.stream()
                .filter(c -> dtoPropertyName.split("(\\.)|(\\[)|(\\()")[0]
                        .equals(c.getDtoPropertyName()))
                .findAny()
                .orElse(null);
    }

    /**
     * Access to a column info by its output property name. For nested, indexed or mapped properties, refer to the first
     * name element.
     *
     * @param outputProperty the output property name searched
     * @return the column info object or {@code null} if not found
     */
    public ColumnInfo getColumnInfoByOutputProperty(@NonNull String outputProperty) {
        return columnInfos.stream()
                .filter(c -> outputProperty//.split("(\\.)|(\\[)|(\\()")[0]
                        .equals(c.getOutputProperty()))
                .findAny()
                .orElse(null);
    }

}
