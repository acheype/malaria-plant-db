package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertTrue;

@Slf4j
public class ImportStatusTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGetReadErrorsBySheet() throws Exception {
        ImportStatus status = new ImportStatus();
        status.getReadErrors().add(new CellError("error1", "sheet1", 1, "col1"));
        status.getReadErrors().add(new CellError("error2", "sheet2", 1, "col1"));
        status.getReadErrors().add(new CellError("error3", "sheet1", 1, "col2"));
        status.getReadErrors().add(new CellError("error4", "sheet1", 1, "col3"));
        status.getReadErrors().add(new CellError("error5", "sheet2", 1, "col1"));

        log.debug(StringUtils.collectionToCommaDelimitedString(status.getReadErrorsBySheet("sheet1")));
        assertTrue(status.getReadErrorsBySheet("sheet1").size() == 3);
        assertTrue(status.getReadErrorsBySheet("sheet2").size() == 2);

        exception.expect(NullPointerException.class);
        status.getReadErrorsBySheet(null);
    }
}