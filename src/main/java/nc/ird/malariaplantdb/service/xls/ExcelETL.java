package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.annotations.DbEntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.ImportDto;
import nc.ird.malariaplantdb.service.xls.annotations.ImportProperty;
import nc.ird.malariaplantdb.service.xls.annotations.XlsEntityRef;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportException;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.infos.ColumnInfo;
import nc.ird.malariaplantdb.service.xls.infos.DbEntityRefInfo;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import nc.ird.malariaplantdb.service.xls.infos.XlsEntityRefInfo;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import nc.ird.malariaplantdb.service.xls.structures.ImportStatus;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

/**
 * Main class for the Excel importation process. As an ETL, it has the responsibility to read the data from the Excel
 * file, to check the business and database integrity rules and to finally load the data (by transforming some
 * properties) to finals persisted entities.
 *
 * @author acheype
 */
@Slf4j
public class ExcelETL {

    /**
     * Base package scanned to find classes labeled with the annotation ImportDto
     */
    private String dtosBasePackage;

    /**
     * Xls mapping file path for the xls reading process config file written in xml
     */
    private String xlsMappingFilepath;

    private List<SheetInfo> sheetInfos;

    private ClassMap dtosMap;

    private ClassMap entitiesMap;

    private ImportStatus importStatus;

    /**
     * Constructor
     *
     * @param dtosBasePackage    Base package scanned to find classes labeled with the annotations ImportDto
     * @param xlsMappingFilepath Xls mapping file path for the xls reading process config file written in xml
     */
    public ExcelETL(String dtosBasePackage, String xlsMappingFilepath) {
        this.dtosBasePackage = dtosBasePackage;
        this.xlsMappingFilepath = xlsMappingFilepath;
        sheetInfos = buildSheetInfos();
        dtosMap = new ClassMap();
        entitiesMap = new ClassMap();
        importStatus = new ImportStatus();
    }

    public void checkDataForImport(InputStream xlsDataInputStream) throws ImportException {
        readXlsFile(xlsDataInputStream);
        checkDtos();
        importStatus.setCheckDone(true);
    }

    public void startImportProcess() throws ImportException {
        if (getImportStatus().isStatusOK()) {
            loadDtos();
        } else throw new ImportException("The data must be check (function checkDataForImport) and mustn't result any " +
                "error (importStatus.isStatusOK() == true)");

        getImportStatus().setImportedPubNb(entitiesMap.getList(Publication.class).size());
    }

    private void readXlsFile(InputStream xlsDataInputStream) throws ImportException {
        ExcelReader excelReader = new ExcelReader(getSheetInfos(), xlsMappingFilepath);
        ExcelReader.ReaderResult readerResult = excelReader.read(xlsDataInputStream);
        getImportStatus().getReadErrors().addAll(readerResult.getCellErrors());
        dtosMap = readerResult.getDtosMap();
    }

    private void checkDtos() {
        ExcelChecker excelChecker = new ExcelChecker(getSheetInfos());
        getImportStatus().getBusinessErrors().addAll(excelChecker.checkBusinessRules(dtosMap));
    }

    private void loadDtos() {
        ExcelLoader excelLoader = new ExcelLoader(getSheetInfos());
        ExcelLoader.LoaderResult loaderResult = excelLoader.loadEntities(dtosMap);
        getImportStatus().getIntegrityErrors().addAll(loaderResult.getCellErrors());

        entitiesMap = loaderResult.getEntitiesMap();
    }

    private List<SheetInfo> buildSheetInfos() {
        Reflections reflections = new Reflections(dtosBasePackage, new SubTypesScanner(false),
                new TypeAnnotationsScanner());
        Set<Class<?>> dtoClasses = reflections.getTypesAnnotatedWith(ImportDto.class);

        return dtoClasses
                .stream()
                .sorted(Comparator.comparing(c -> c.getAnnotation(ImportDto.class).importOrder()))
                .map(c -> new SheetInfo(c,
                        c.getAnnotation(ImportDto.class).sheetLabel(),
                        c.getAnnotation(ImportDto.class).startRow(),
                        c.getAnnotation(ImportDto.class).outputEntityClass(),
                        buildXlsRefInfos(c.getAnnotation(ImportDto.class).xlsEntityRef()),
                        buildDbRefInfos(c.getAnnotation(ImportDto.class).dbEntityRef()),
                        buildPropertiesInfos(c)))
                .collect(Collectors.toList());
    }

    private List<XlsEntityRefInfo> buildXlsRefInfos(XlsEntityRef[] xlsEntityRefs) {
        return Arrays.stream(xlsEntityRefs).map(
                d -> new XlsEntityRefInfo(d.dtoProperties(),
                        d.dtoPropertiesTransformer(),
                        d.xlsEntityRefType(),
                        d.xlsEntityRefProperties(),
                        d.xlsEntityRefPropertiesLabels(),
                        d.filler(),
                        d.fillerEqualsStrategy(),
                        d.outputProperty(),
                        d.outputTransformer(),
                        d.afterFillingTransformer())
        ).collect(Collectors.toList());
    }

    private List<DbEntityRefInfo> buildDbRefInfos(DbEntityRef[] dbEntityRefs) {
        return Arrays.stream(dbEntityRefs).map(
                d -> new DbEntityRefInfo(d.dtoProperties(),
                        d.dtoPropertiesTransformer(),
                        d.filler(),
                        d.outputProperty(),
                        d.outputTransformer(),
                        d.afterFillingTransformer())
        ).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private List<ColumnInfo> buildPropertiesInfos(Class dtoClass) {
        Set<Field> dtoFields = getAllFields(dtoClass, withAnnotation(ImportProperty.class));

        List<ColumnInfo> columnInfos = dtoFields
            .stream()
            .sorted(Comparator.comparing(f -> f.getAnnotation(ImportProperty.class).columnLetterRef()))
            .map(f -> new ColumnInfo(
                f.getAnnotation(ImportProperty.class).columnLetterRef(),
                f.getAnnotation(ImportProperty.class).columnLabel(),
                f.getName(),
                //Arrays.asList(f.getAnnotation(ImportProperty.class).propertyLoader().otherProperties()),
                f.getAnnotation(ImportProperty.class).propertyLoader().transformer(),
                f.getAnnotation(ImportProperty.class).propertyLoader().outputProperty(),
                f.getAnnotation(ImportProperty.class).afterLoadingTransformer()))
                .collect(Collectors.toList());

        dtoFields.iterator().next().getAnnotation(ImportProperty.class);

        // Verify that the column letter is unique for the class fields
        List<String> columnLettersList = columnInfos
                .stream()
                .map(ColumnInfo::getColumnLetterRef)
                .collect(Collectors.toList());

        Set<String> columnLettersSet = new HashSet<>();
        for (String letter : columnLettersList) {
            if (!columnLettersSet.add(letter)) {
                throw new ImportRuntimeException(String.format("Error in the importation process initialization : the " +
                                "columnLetterRef" +
                                " value '%s' is not unique for all the fields of the %s class", letter,
                        dtoClass.getSimpleName()));
            }
        }
        return columnInfos;
    }

    public ClassMap getDtosMap() {
        return dtosMap;
    }

    public ClassMap getEntitiesMap() {
        return entitiesMap;
    }

    public ImportStatus getImportStatus() {
        return importStatus;
    }

    public List<SheetInfo> getSheetInfos() {
        return sheetInfos;
    }
}
