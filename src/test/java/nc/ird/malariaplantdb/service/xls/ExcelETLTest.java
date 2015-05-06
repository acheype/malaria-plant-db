package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.dto.PublicationSheet;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

@Slf4j
public class ExcelETLTest {

    @Test
    public void startImportProcess() throws Exception {
        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls", "/xls/xls_mapping.xml");

        InputStream excelInput = new BufferedInputStream(getClass().getResourceAsStream("/xls/test_old.xlsm"));
        etl.startImportProcess(excelInput);


    }

    @Test
    public void testGetDtoAnnotations() throws Exception {
        ExcelETL etl = new ExcelETL("nc.ird.malariaplantdb.service.xls", "/xls/xls_mapping.xml");

        etl.getSheetInfos()
                .stream()
                .forEach(a -> log.debug(a.toString()));

        SheetInfo publiSheet = etl.getSheetInfos().get(0);
        assertTrue(publiSheet.getDtoClass() == PublicationSheet.class);
        assertTrue(publiSheet.getSheetLabel().equals("1 - PUBLI"));

        ColumnInfo titleCol = publiSheet.getColumnInfos().get(3);
        assertTrue(titleCol.getColumnLetterRef().equals("D"));
        assertTrue(titleCol.getColumnName().equals("Title"));
        assertTrue(titleCol.getDtoPropertyName().equals("title"));

        ColumnInfo compilersNoteCol = publiSheet.getColumnInfos().get(21);
        assertTrue(compilersNoteCol.getColumnLetterRef().equals("V"));
        assertTrue(compilersNoteCol.getColumnName().equals("Note from compiler(s)"));
        assertTrue(compilersNoteCol.getDtoPropertyName().equals("compilersNotes"));
    }
}