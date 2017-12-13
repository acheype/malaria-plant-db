package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.dto.PublicationLine;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.infos.ColumnInfo;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import nc.ird.malariaplantdb.service.xls.structures.CellError;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class ExcelCheckerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCheckBusinessRules() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo = new SheetInfo(
                PublicationLine.class,
                "1 - PUBLI",
                2,
                Publication.class,
                new ArrayList<>(),
                new ArrayList<>(),
                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationLine.class, PublicationMock.parseAndPopulateBeans(
            PublicationMock.PUB_LINES, PublicationLine.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelChecker excelChecker = new ExcelChecker(sheetInfos);

        List<CellError> cellErrors = excelChecker.checkBusinessRules(dtosMap);

        assertTrue(cellErrors.isEmpty());
    }

    @Test
    public void testCheckBusinessRulesWithException() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        String fieldInfosWithErrStr = PublicationMock.PUBLI_COLUMN_INFOS.replace("ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                "outputProperty=year| propertyTransformer=" + PublicationMock.NOP_TRANSFORMER_CLASS +
                "| afterLoadingTransformer=" + PublicationMock.NOP_ENTITIES_TRANSFORMER_CLASS+ "),",
            "ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=XXX| " +
                "outputProperty=year| propertyTransformer=" + PublicationMock.NOP_TRANSFORMER_CLASS +
                "| afterLoadingTransformer=" + PublicationMock.NOP_ENTITIES_TRANSFORMER_CLASS+ "),");

        SheetInfo sheetInfo = new SheetInfo(
                PublicationLine.class,
                "1 - PUBLI",
                2,
                Publication.class,
                new ArrayList<>(),
                new ArrayList<>(),
                PublicationMock.parseAndPopulateBeans(fieldInfosWithErrStr, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationLine.class, PublicationMock.parseAndPopulateBeans(
            PublicationMock.PUB_LINES_WITH_ERROR, PublicationLine.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelChecker excelChecker = new ExcelChecker(sheetInfos);

        exception.expect(ImportRuntimeException.class);
        excelChecker.checkBusinessRules(dtosMap);
    }

    @Test
    public void testCheckBusinessRulesWithCellErrors() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo = new SheetInfo(
                PublicationLine.class,
                "1 - PUBLI",
                2,
                Publication.class,
                new ArrayList<>(),
                new ArrayList<>(),
                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationLine.class, PublicationMock.parseAndPopulateBeans(
            PublicationMock.PUB_LINES_WITH_ERROR, PublicationLine.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelChecker excelChecker = new ExcelChecker(sheetInfos);

        List<CellError> cellErrors = excelChecker.checkBusinessRules(dtosMap);

        assertTrue(cellErrors.size() == 5);

        assertNotNull(cellErrors.stream().filter(ce -> "The integer must be ranged between 0 and 3000".equals(
                ce.getMessage()))
                .filter(ce -> "Year".equals(ce.getColumn()))
                .filter(ce -> ce.getLine() == 2)
                .findAny()
                .orElse(null));

        assertNotNull(cellErrors.stream().filter(ce -> "The cell is empty or the value invalid".equals(
                ce.getMessage()))
                .filter(ce -> "Compiler(s) name(s)".equals(ce.getColumn()))
                .filter(ce -> ce.getLine() == 3)
                .findAny()
                .orElse(null));

        assertNotNull(cellErrors.stream().filter(ce -> ("The authors value is not well formatted. Please enter each " +
                "author name with the last name first, a coma (,) then the given name initials (with comas)." +
                " For several authors, please separate each complete name by a slash (/).").equals(
                ce.getMessage()))
                .filter(ce -> ce.getLine() == 3)
                .findAny()
                .orElse(null));

        assertNotNull(cellErrors.stream().filter(ce -> ("The authors value is not well formatted. Please enter each " +
                "author name with the last name first, a coma (,) then the given name initials (with comas)." +
                " For several authors, please separate each complete name by a slash (/).").equals(
                ce.getMessage()))
                .filter(ce -> ce.getLine() == 4)
                .findAny()
                .orElse(null));
    }
}
