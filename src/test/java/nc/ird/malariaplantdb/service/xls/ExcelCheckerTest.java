package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.service.xls.dto.PublicationSheet;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
public class ExcelCheckerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCheckBusinessRules() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo = new SheetInfo(
                PublicationSheet.class,
                "1 - PUBLI",
                2,
                Publication.class,
                PublicationMock.parseAndPopulateBeans(PublicationMock.COLUMN_INFOS_STR, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationSheet.class, PublicationMock.parseAndPopulateBeans(
                PublicationMock.PUB_SHEETS_STR, PublicationSheet.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationSheet.class).size() == 4);
        dtosMap.getList(PublicationSheet.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelChecker excelChecker = new ExcelChecker(sheetInfos);

        List<CellError> cellErrors = excelChecker.checkBusinessRules(dtosMap);

        assertTrue(cellErrors.isEmpty());
    }

    @Test
    public void testCheckBusinessRulesWithException() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo = new SheetInfo(
                PublicationSheet.class,
                "1 - PUBLI",
                2,
                Publication.class,
                PublicationMock.parseAndPopulateBeans(PublicationMock.COLUMN_INFOS_STR_WITH_ERROR, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationSheet.class, PublicationMock.parseAndPopulateBeans(
                PublicationMock.PUB_SHEETS_STR_WITH_ERROR, PublicationSheet.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationSheet.class).size() == 4);
        dtosMap.getList(PublicationSheet.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelChecker excelChecker = new ExcelChecker(sheetInfos);

        exception.expect(ImportRuntimeException.class);
        List<CellError> cellErrors = excelChecker.checkBusinessRules(dtosMap);

        assertTrue(cellErrors.isEmpty());
    }

    @Test
    public void testCheckBusinessRulesWithCellErrors() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo = new SheetInfo(
                PublicationSheet.class,
                "1 - PUBLI",
                2,
                Publication.class,
                PublicationMock.parseAndPopulateBeans(PublicationMock.COLUMN_INFOS_STR, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationSheet.class, PublicationMock.parseAndPopulateBeans(
                PublicationMock.PUB_SHEETS_STR_WITH_ERROR, PublicationSheet.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationSheet.class).size() == 4);
        dtosMap.getList(PublicationSheet.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelChecker excelChecker = new ExcelChecker(sheetInfos);

        List<CellError> cellErrors = excelChecker.checkBusinessRules(dtosMap);

        assertTrue(cellErrors.size() == 6);
        assertTrue(cellErrors.get(3).getMessage().equals("As 'Reviewed by compiler(s)' is NO, " +
                "'Compiler(s) name(s)' must be empty"));
        assertTrue(cellErrors.get(4).getMessage().equals("As 'Reviewed by compiler(s)' is YES, " +
                "'Compiler(s) name(s)' must not be empty"));
        assertTrue(cellErrors.get(5).getMessage().equals("As 'Reviewed by compiler(s)' is YES, " +
                "'Note from compiler(s)' must not be empty"));
    }
}