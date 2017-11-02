package nc.ird.malariaplantdb.service.xls;

import lombok.Data;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.XlsEntityRef;
import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ImportDto(sheetLabel = "TEST XLS REF", importOrder = 2, startRow = 2, outputEntityClass = XlsRefTest.class,
        xlsEntityRef = {
                @XlsEntityRef(
                        dtoProperties = {"refTile", "refYear"},
                        xlsEntityRefType = Publication.class,
                        xlsEntityRefProperties = {"title", "year"},
                        xlsEntityRefPropertiesLabels = {"Title", "Year"},
                        filler = XlsEntityRefFiller.class,
                        outputProperty = "publication"
                )})
public class XlsRefTestLine {

    @ImportProperty(columnLetterRef = "A", columnLabel = "Referenced title")
    @NotEmpty(message = "The cell is empty or the value invalid")
    private String refTitle;

    @ImportProperty(columnLetterRef = "B", columnLabel = "Referenced year")
    @NotNull(message = "The cell is empty or the value invalid")
    @Range(min = 0, max = 3000, message = "The integer must be ranged between {min} and {max}")
    private Integer refYear;
}
