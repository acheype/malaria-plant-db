package nc.ird.malariaplantdb.service.xls;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.entities.Publication;
import nc.ird.malariaplantdb.service.xls.dto.PublicationSheet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
@AllArgsConstructor
public class ExcelLoaderTest {


    @Test
    public void testLoadEntities() throws Exception {

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

        ExcelLoader excelLoader = new ExcelLoader(sheetInfos);
        ClassMap entitiesMap = excelLoader.loadEntities(dtosMap);

        assertTrue(entitiesMap.size() == 4);

    }
}