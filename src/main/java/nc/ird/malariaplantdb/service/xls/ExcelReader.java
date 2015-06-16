package nc.ird.malariaplantdb.service.xls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import net.sf.jxls.reader.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExcelReader based on JXLS library
 *
 * @author acheype
 */
@Slf4j
public class ExcelReader {

    private List<SheetInfo> sheetInfos;

    /**
     * Xls mapping file path for the xls reading process config file written in xml
     */
    private String xlsMappingFilepath;

    public ExcelReader(List<SheetInfo> sheetInfos, String xlsMappingFilepath) {
        this.sheetInfos = sheetInfos;
        this.xlsMappingFilepath = xlsMappingFilepath;
    }

    @SuppressWarnings("unchecked")
    public ReaderResult read(InputStream xlsDataInputStream) throws ImportException {

        // TODO : verify in the XLS mapping file that the declared 'items' corresponds to the annotated dtos
        // example of exception we could throw : new ImportException("Impossible to find the dto '%s' in the xls
        // mapping file. For each annotated dto, you have to declare once his class name in the 'items' field of this
        // xml file.");


        XLSReader jxlsReader;
        try {
            jxlsReader = getJxlsReader();
        } catch (IOException | SAXException e) {
            throw new ImportException("Error in the importation process initialization : a problem occur with the " +
                    "xls mapping file", e);
        } catch (RuntimeException e) {
            throw new ImportRuntimeException("Error in the importation process initialization : a problem occur " +
                    "with the xls mapping file", e);
        }

        try (InputStream inputXLS = new BufferedInputStream(xlsDataInputStream)) {
            Map<String, Object> dtoBeans = new HashMap<>();

            for (SheetInfo sheetInfo : sheetInfos) {
                dtoBeans.put(sheetInfo.getDtoClass().getSimpleName(), new ArrayList<>());
            }

            XLSReadStatus readStatus = jxlsReader.read(inputXLS, dtoBeans);

            List<CellError> cellErrors = new ArrayList<>();
            log.debug("Cell Errors : " + (readStatus.getReadMessages().isEmpty() ? "<empty>" : ""));
            for (Object readMsg : readStatus.getReadMessages()) {
                cellErrors.add(CellError.buildFromReadMessage(sheetInfos, (XLSReadMessage) readMsg));
                log.debug(cellErrors.toString());
            }

            ClassMap dtosMap = new ClassMap();
            for (SheetInfo sheetInfo : sheetInfos) {
                dtosMap.putList(sheetInfo.getDtoClass(), (ArrayList<Object>) dtoBeans.get(sheetInfo.getDtoClass().getSimpleName()));
            }

            return new ReaderResult(dtosMap, cellErrors);

        } catch (IOException | InvalidFormatException e) {
            throw new ImportException("An unexpected error occurs during the Excel file reading", e);
        } catch (RuntimeException e) {
            throw new ImportRuntimeException("An unexpected error occurs during the Excel file reading", e);
        }
    }

    private XLSReader getJxlsReader() throws IOException, SAXException {
        InputStream inputXML = new BufferedInputStream(getClass().getResourceAsStream(xlsMappingFilepath));

        // continue if an reading error occurs
        ReaderConfig.getInstance().setSkipErrors(true);

        return ReaderBuilder.buildFromXML(inputXML);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class ReaderResult {

        private ClassMap dtosMap;

        private List<CellError> cellErrors;
    }


}
