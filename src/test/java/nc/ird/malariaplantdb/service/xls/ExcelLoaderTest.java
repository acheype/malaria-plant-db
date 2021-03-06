package nc.ird.malariaplantdb.service.xls;

import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.domain.Author;
import nc.ird.malariaplantdb.domain.Compiler;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.service.xls.dto.PublicationLine;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.fillers.util.DefaultEqualsStrategy;
import nc.ird.malariaplantdb.service.xls.infos.ColumnInfo;
import nc.ird.malariaplantdb.service.xls.infos.DbEntityRefInfo;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import nc.ird.malariaplantdb.service.xls.infos.XlsEntityRefInfo;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import nc.ird.malariaplantdb.service.xls.transformers.CompilersStrTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.NOPEntitiesTransformer;
import org.apache.commons.collections.functors.NOPTransformer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
public class ExcelLoaderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testLoadEntities() throws Exception {

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

        ExcelLoader excelLoader = new ExcelLoader(sheetInfos);
        ExcelLoader.LoaderResult loaderResult = excelLoader.loadEntities(dtosMap);

        assertTrue(loaderResult.getEntitiesMap().getList(Publication.class).size() == 4);

        List<Author> authorsTest1 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(0).getAuthors());
        assertTrue(authorsTest1.get(2).getFamily().equals("Nogueira") && authorsTest1.get(2).getGiven().equals(
                "F."));
        List<Author>  authorsTest2 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(2).getAuthors());
        assertTrue(authorsTest2.get(0).getFamily().equals("Botelho") && authorsTest2.get(0).getGiven().equals(
                "M.G.A."));
    }

    @Test
    public void testLoadEntitiesWithException() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        String fieldInfosWithErrStr = PublicationMock.PUBLI_COLUMN_INFOS.replace("ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                "outputProperty=year| propertyTransformer=" + PublicationMock.NOP_TRANSFORMER_CLASS +
                "| afterLoadingTransformer=" + PublicationMock.NOP_ENTITIES_TRANSFORMER_CLASS+ "),",
            "ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                "outputProperty=yearXXX| propertyTransformer=" + PublicationMock.NOP_TRANSFORMER_CLASS +
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
                PublicationMock.PUB_LINES, PublicationLine.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelLoader excelLoader = new ExcelLoader(sheetInfos);

        exception.expect(ImportRuntimeException.class);
        ExcelLoader.LoaderResult loaderResult = excelLoader.loadEntities(dtosMap);

        assertTrue(loaderResult.getEntitiesMap().getList(Publication.class).size() == 4);

        List<Author> authorsTest1 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(0).getAuthors());
        assertTrue(authorsTest1.get(2).getFamily().equals(" Nogueira") && authorsTest1.get(2).getGiven().equals(
                " F. "));
        List<Author> authorsTest2 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(2).getAuthors());
        assertTrue(authorsTest2.get(0).getFamily().equals("Botelho") && authorsTest2.get(0).getGiven().equals(
                " M.G.A."));
    }

    @Test
    public void testLoadEntitiesWithDbEntityRef() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo = new SheetInfo(
                PublicationLine.class,
                "1 - PUBLI",
                2,
                Publication.class,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new DbEntityRefInfo(
                                new String[]{"compilers"},
                                CompilersStrTransformer.class,
                                CompilersDbEntityRefFillerStub.class,
                                "compilers",
                                NOPTransformer.class,
                                NOPEntitiesTransformer.class)
                )),
                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo);

        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationLine.class, PublicationMock.parseAndPopulateBeans(
                PublicationMock.PUB_LINES, PublicationLine.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelLoader excelLoader = new ExcelLoader(sheetInfos);
        ExcelLoader.LoaderResult loaderResult = excelLoader.loadEntities(dtosMap);

        List<Compiler> compilers1 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(0)
                .getCompilers());
        assertTrue(compilers1.size() == 2);
        assertTrue(compilers1.get(0).getFamily().equals("SameFamily1"));
        assertTrue(compilers1.get(0).getGiven().equals("SameGiven1"));
        assertTrue(compilers1.get(1).getFamily().equals("SameFamily2"));
        assertTrue(compilers1.get(1).getGiven().equals("SameGiven2"));
        List<Compiler> compilers2 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(1)
                .getCompilers());
        assertTrue(compilers2.size() == 1);
        assertTrue(compilers2.get(0).getFamily().equals("SameFamily1"));
        assertTrue(compilers2.get(0).getGiven().equals("SameGiven1"));
        List<Compiler> compilers3 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(2)
                .getCompilers());
        assertTrue(compilers3.size() == 1);
        assertTrue(compilers3.get(0).getFamily().equals("SameFamily1"));
        assertTrue(compilers3.get(0).getGiven().equals("SameGiven1"));
        List<Compiler> compilers4 = new ArrayList<>(loaderResult.getEntitiesMap().getList(Publication.class).get(3)
                .getCompilers());
        assertTrue(compilers4.size() == 1);
        assertTrue(compilers4.get(0).getFamily().equals("SameFamily1"));
        assertTrue(compilers4.get(0).getGiven().equals("SameGiven1"));

        assertTrue(loaderResult.getEntitiesMap().getList(Publication.class).size() == 4);
    }

    @Test
    public void testLoadEntitiesWithXlsEntityRef() throws Exception {

        List<SheetInfo> sheetInfos = new ArrayList<>();

        SheetInfo sheetInfo1 = new SheetInfo(
                PublicationLine.class,
                "1 - PUBLI",
                2,
                Publication.class,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        new DbEntityRefInfo(
                                new String[]{"compilers"},
                                CompilersStrTransformer.class,
                                CompilersDbEntityRefFillerStub.class,
                                "compilers",
                                NOPTransformer.class,
                                NOPEntitiesTransformer.class)
                )),
                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo1);
        SheetInfo sheetInfo2 = new SheetInfo(
                XlsRefTestLine.class,
                "TEST XLS REF",
                2,
                XlsRefTest.class,
                new ArrayList<>(Arrays.asList(
                        new XlsEntityRefInfo(
                                new String[]{"refTitle", "refYear"},
                                NOPTransformer.class,
                                Publication.class,
                                new String[]{"title", "year"},
                                new String[]{"Title", "Year"},
                                XlsEntityRefFiller.class,
                                DefaultEqualsStrategy.class,
                                "publication",
                                NOPTransformer.class,
                                NOPEntitiesTransformer.class)
                )),
                new ArrayList<>(),
                PublicationMock.parseAndPopulateBeans(PublicationMock.XLS_REF_TEST_COLUMN_INFOS, ColumnInfo.class, "\\|"));
        sheetInfos.add(sheetInfo2);


        ClassMap dtosMap = new ClassMap();
        dtosMap.putList(PublicationLine.class, PublicationMock.parseAndPopulateBeans(
                PublicationMock.PUB_LINES, PublicationLine.class, "\\|"));
        dtosMap.putList(XlsRefTestLine.class, PublicationMock.parseAndPopulateBeans(
                PublicationMock.XLS_REF_TEST_LINES, XlsRefTestLine.class, "\\|"));

        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));

        assertTrue(dtosMap.getList(XlsRefTestLine.class).size() == 4);
        dtosMap.getList(XlsRefTestLine.class).stream().forEach(p -> log.debug(p.toString()));

        ExcelLoader excelLoader = new ExcelLoader(sheetInfos);
        ExcelLoader.LoaderResult loaderResult = excelLoader.loadEntities(dtosMap);

        Publication refPub1 = loaderResult.getEntitiesMap().getList(XlsRefTest.class).get(0).getPublication();
        Publication pub1 = loaderResult.getEntitiesMap().getList(Publication.class).get(0);
        assertTrue(refPub1.getTitle().equals(pub1.getTitle()));

        Publication refPub2 = loaderResult.getEntitiesMap().getList(XlsRefTest.class).get(1).getPublication();
        Publication pub2 = loaderResult.getEntitiesMap().getList(Publication.class).get(1);
        assertTrue(refPub2.getTitle().equals(pub2.getTitle()));

        Publication refPub3 = loaderResult.getEntitiesMap().getList(XlsRefTest.class).get(2).getPublication();
        Publication pub3 = loaderResult.getEntitiesMap().getList(Publication.class).get(2);
        assertTrue(refPub3.getTitle().equals(pub3.getTitle()));

        Publication refPub4 = loaderResult.getEntitiesMap().getList(XlsRefTest.class).get(3).getPublication();
        Publication pub4 = loaderResult.getEntitiesMap().getList(Publication.class).get(3);
        assertTrue(refPub4.getTitle().equals(pub4.getTitle()));

        assertTrue(loaderResult.getEntitiesMap().getList(Publication.class).size() == 4);
    }

    @Test
    public void testLoadEntitiesWithErrorsInXlsEntityRef() throws Exception {

//        List<SheetInfo> sheetInfos = new ArrayList<>();
//
//        SheetInfo sheetInfo1 = new SheetInfo(
//                PublicationLine.class,
//                "1 - PUBLI",
//                2,
//                Publication.class,
//                new ArrayList<>(Arrays.asList(
//                        new DbEntityRefInfo(
//                                new String[]{"compilers"},
//                                NOPTransformer.class,
//                                true,
//                                new String[]{},
//                                "compilers",
//                                CompilersDbEntityRefFillerStub.class)
//                )),
//                PublicationMock.parseAndPopulateBeans(PublicationMock.PUBLI_COLUMN_INFOS, ColumnInfo.class, "\\|"));
//        sheetInfos.add(sheetInfo1);
//        SheetInfo sheetInfo2 = new SheetInfo(
//                XlsRefTestLine.class,
//                "TEST XLS REF",
//                2,
//                XlsRefTest.class,
//                new ArrayList<>(Arrays.asList(
//                        new EntityRefInfo(
//                                new String[]{"refTitle", "refYear"},
//                                NOPTransformer.class,
//                                true,
//                                new String[]{"title", "year"},
//                                "publication",
//                                XlsEntityRefFiller.class)
//                )),
//                PublicationMock.parseAndPopulateBeans(PublicationMock.XLS_REF_TEST_COLUMN_INFOS,
//                        ColumnInfo.class, "\\|"));
//        sheetInfos.add(sheetInfo2);
//
//
//        ClassMap dtosMap = new ClassMap();
//        dtosMap.putList(PublicationLine.class, PublicationMock.parseAndPopulateBeans(
//                PublicationMock.PUB_LINES, PublicationLine.class, "\\|"));
//        dtosMap.putList(XlsRefTestLine.class, PublicationMock.parseAndPopulateBeans(
//                PublicationMock.XLS_REF_TEST_LINES.replace(
//                        "XlsRefTestLine(refTitle=Ampelozyziphus amazonicus Ducke",
//                        "XlsRefTestLine(refTitle=XXXXX").replace(
//                        "XlsRefTestLine(refTitle=Survey of medicinal",
//                        "XlsRefTestLine(refTitle=YYYYY"
//                ), XlsRefTestLine.class, "\\|"));
//
//        assertTrue(dtosMap.getList(PublicationLine.class).size() == 4);
//        dtosMap.getList(PublicationLine.class).stream().forEach(p -> log.debug(p.toString()));
//
//        assertTrue(dtosMap.getList(XlsRefTestLine.class).size() == 4);
//        dtosMap.getList(XlsRefTestLine.class).stream().forEach(p -> log.debug(p.toString()));
//
//        ExcelLoader excelLoader = new ExcelLoader(sheetInfos);
//        ExcelLoader.LoaderResult loaderResult = excelLoader.loadEntities(dtosMap);
//
//        assertTrue(loaderResult.getEntitiesMap().getList(Publication.class).size() == 4);
//
//        List<CellError> cellErrors = loaderResult.getCellErrors();
//        assertTrue(("Can't find in the '1 - PUBLI' sheet a line with these values : Title = 'XXXXX, " +
//                "a medicinal plant" +
//                " used to prevent malaria in the Amazon Region, hampers the development of Plasmodium berghei " +
//                "sporozoites.', Year = '2008'").equals(cellErrors.get(0).getMessage()));
//        assertTrue("TEST XLS REF".equals(cellErrors.get(0).getSheet()));
//        assertTrue(cellErrors.get(0).getLine() == 2);
//        assertTrue("refTitle, refYear".equals(cellErrors.get(0).getColumn()));
//
//        assertTrue(("Can't find in the '1 - PUBLI' sheet a line with these values : Title = 'YYYYY plants used as " +
//                "antimalarials in the Amazon. J. Ethnopharmacol.', Year = '1992'")
//                .equals(cellErrors.get(1).getMessage()));
//        assertTrue("TEST XLS REF".equals(cellErrors.get(1).getSheet()));
//        assertTrue(cellErrors.get(1).getLine() == 3);
//        assertTrue("refTitle, refYear".equals(cellErrors.get(1).getColumn()));
    }

}
