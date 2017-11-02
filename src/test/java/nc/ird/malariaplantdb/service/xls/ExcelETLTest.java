package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.dto.PublicationLine;
import nc.ird.malariaplantdb.service.xls.infos.ColumnInfo;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Slf4j
public class ExcelETLTest {

//    @Test
//    public void startImportProcess() throws Exception {
//        // test with real data
//
//        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls.dto", "/xls/publi_mapping.xml");
//
//        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/publi_test.xlsm"));
//        etl.startImportProcess(excelInput);
//
//    }

    @Test
    public void testGetDtoAnnotations() throws Exception {
        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls", "/xls/publi_mapping.xml");

        etl.getSheetInfos()
                .stream()
                .forEach(a -> log.debug(a.toString()));

        SheetInfo publiSheet = etl.getSheetInfos().get(0);
        assertTrue(publiSheet.getDtoClass() == PublicationLine.class);
        assertTrue(publiSheet.getSheetLabel().equals("1 - PUBLI"));

        ColumnInfo titleCol = publiSheet.getColumnInfos().get(3);
        assertTrue(titleCol.getColumnLetterRef().equals("D"));
        assertTrue(titleCol.getColumnLabel().equals("Title"));
        assertTrue(titleCol.getDtoPropertyName().equals("title"));

        ColumnInfo compilersNoteCol = publiSheet.getColumnInfos().get(21);
        assertTrue(compilersNoteCol.getColumnLetterRef().equals("V"));
        assertTrue(compilersNoteCol.getColumnLabel().equals("Note from compiler(s)"));
        assertTrue(compilersNoteCol.getDtoPropertyName().equals("compilersNotes"));
    }
}
