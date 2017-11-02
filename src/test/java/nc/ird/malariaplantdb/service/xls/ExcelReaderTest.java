package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelReaderTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testReadOK() throws Exception {
//        List<SheetInfo> sheetInfos = new ArrayList<>();
//
//        SheetInfo sheetInfo = new SheetInfo(
//                PublicationLine.class,
//                "1 - PUBLI",
//                2,
//                Publication.class,
//                new ArrayList<>(),
//                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
//        sheetInfos.add(sheetInfo);
//
//        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/test.xlsm"));
//        ExcelReader reader = new ExcelReader(sheetInfos, "/xls/publi_mapping.xml");
//        ExcelReader.ReaderResult readerRes = reader.read(excelInput);
//
//        List<PublicationLine> importedBeans = readerRes.getDtosMap().getList(sheetInfo.getDtoClass());
//        log.debug(String.format("Imported in %s dto beans :", importedBeans.size()));
//        importedBeans
//                .stream()
//                .forEach(p -> log.debug(p.toString()));
//
//        assertTrue(importedBeans.get(0).getAuthors().equals("Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, " +
//                "F. /Rosário, V.E. / Krettli, A.U.  "));
//        assertTrue(importedBeans.get(1).getEntryType().equals("Journal article"));
//        assertTrue(importedBeans.get(2).getYear().equals(1981));
//        assertTrue(importedBeans.get(3).getTitle().equals("Antiplasmodial and antipyretic studies on root extracts of" +
//                " Anthocleista djalonensis against Plasmodium berghei"));
//        assertTrue(importedBeans.get(3).getMonth().equals("FEB"));
//
//        assertTrue(readerRes.getCellErrors().size() == 0);
//        assertTrue(readerRes.getDtosMap().getList(PublicationLine.class).size() == 11);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReadWithReadErrors() throws Exception {
        List<SheetInfo> sheetInfos = new ArrayList<>();

//        SheetInfo sheetInfo = new SheetInfo(
//                PublicationLine.class,
//                "1 - PUBLI",
//                2,
//                Publication.class,
//                new ArrayList<>(),
//                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
//        sheetInfos.add(sheetInfo);
//
//        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/test_with_read_errors.xlsm"));
//        ExcelReader reader = new ExcelReader(sheetInfos, "/xls/publi_mapping.xml");
//        ExcelReader.ReaderResult readerRes = reader.read(excelInput);
//
//        CellError specificError = readerRes.getCellErrors().get(0);
//        assertTrue(specificError.getMessage().equals("Error by reading the cell value. Please verify the value has " +
//                "the expected format."));
//        assertTrue(specificError.getSheet().equals(sheetInfo.getSheetLabel()));
//        assertTrue(specificError.getLine().equals(2));
//        assertTrue(specificError.getColumn().equals("Year"));
//        assertTrue(specificError.getSourceExceptionDetails().equals("ConversionException - Error converting from " +
//                "'String' to 'Integer' For input string: \"aaa\""));
//        log.debug(String.format("line in errors : %s", readerRes.getDtosMap().getList(PublicationLine.class).get(0)));
//        assertNull(readerRes.getDtosMap().getList(PublicationLine.class).get(0).getYear());
//        assertTrue(readerRes.getDtosMap().getList(PublicationLine.class).size() == 11);
    }

    //@SuppressWarnings("unchecked")
    @Test
    public void testReadWithColumnInfosParsingError() throws Exception {
//        List<SheetInfo> sheetInfos = new ArrayList<>();
//
//        String fieldInfosWithErrStr = PublicationMock.PUBLI_COLUMN_INFOS.replace("ColumnInfo(columnLetterRef=C| " +
//                        "columnLabel=Year| dtoPropertyName=year| outputProperty=year| " +
//                        "propertyTransformer=org.apache.commons.collections.functors.NOPTransformer)",
//                "ColumnInfo(columnLetterRef=XXX| columnLabel=Year| dtoPropertyName=year| " +
//                        "outputProperty=year| propertyTransformer=org.apache.commons.collections.functors" +
//                        ".NOPTransformer)");
//
//        SheetInfo sheetInfo = new SheetInfo(
//                PublicationLine.class,
//                "1 - PUBLI",
//                2,
//                Publication.class,
//                new ArrayList<>(),
//                PublicationMock.parseAndPopulateBeans(fieldInfosWithErrStr, ColumnInfo.class, "\\|"));
//        sheetInfos.add(sheetInfo);
//
//        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/test_with_read_errors.xlsm"));
//        ExcelReader reader = new ExcelReader(sheetInfos, "/xls/publi_mapping.xml");
//        ExcelReader.ReaderResult readerRes = reader.read(excelInput);
//
//        assertTrue(readerRes.getCellErrors().get(0).getMessage().equals("Can't read cell C2 on 1 - PUBLI spreadsheet"));
//        log.debug(String.format("line in errors : %s", readerRes.getDtosMap().getList(PublicationLine.class).get(0)));
//        assertNull(readerRes.getDtosMap().getList(PublicationLine.class).get(0).getYear());
//        assertTrue(readerRes.getDtosMap().getList(PublicationLine.class).size() == 11);
    }

}
