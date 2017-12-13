package nc.ird.malariaplantdb.service.xls;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PublicationMock {

    public static String NOP_TRANSFORMER_CLASS = "org.apache.commons.collections.functors.NOPTransformer";

    public static String NOP_ENTITIES_TRANSFORMER_CLASS = "nc.ird.malariaplantdb.service.xls.transformers" +
        ".NOPEntitiesTransformer";

    public static String STRING_NORMALIZER_CLASS = "nc.ird.malariaplantdb.service.xls.transformers" +
            ".StringNormalizer";

    public final static String PUBLI_COLUMN_INFOS =
            "ColumnInfo(columnLetterRef=A| columnLabel=Entry Type| dtoPropertyName=entryType| " +
                    "outputProperty=entryType| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=B| columnLabel=Author(s)| dtoPropertyName=authors|" +
                    "outputProperty=authors| propertyTransformer=nc.ird.malariaplantdb.service.xls" +
                    ".transformers.StrToAuthorsSet| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                    "outputProperty=year| propertyTransformer=" + NOP_TRANSFORMER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=D| columnLabel=Title| dtoPropertyName=title| " +
                    "outputProperty=title| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=E| columnLabel=Journal| dtoPropertyName=journal| " +
                    "outputProperty=journal| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=F| columnLabel=Pages| dtoPropertyName=pages| " +
                    "outputProperty=pages| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=G| columnLabel=Volume| dtoPropertyName=volume| " +
                    "outputProperty=volume| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=H| columnLabel=Nb of volumes| dtoPropertyName=nbOfVolumes| " +
                    "outputProperty=nbOfVolumes| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=I| columnLabel=Number| dtoPropertyName=number| " +
                    "outputProperty=number| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=J| columnLabel=Book title| dtoPropertyName=bookTitle| " +
                    "outputProperty=bookTitle| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=K| columnLabel=Publisher| dtoPropertyName=publisher| " +
                    "outputProperty=publisher| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=L| columnLabel=Edition| dtoPropertyName=edition| " +
                    "outputProperty=edition| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=M| columnLabel=Conference name| dtoPropertyName=conferenceName| " +
                    "outputProperty=conferenceName| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=N| columnLabel=Conference place| dtoPropertyName=conferencePlace| " +
                    "outputProperty=conferencePlace| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=O| columnLabel=University| dtoPropertyName=university| " +
                    "outputProperty=university| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=P| columnLabel=Institution| dtoPropertyName=institution| " +
                    "outputProperty=institution| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=Q| columnLabel=DOI| dtoPropertyName=doi| " +
                    "outputProperty=doi| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=R| columnLabel=PMID| dtoPropertyName=pmid| " +
                    "outputProperty=pmid| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=S| columnLabel=ISBN| dtoPropertyName=isbn| " +
                    "outputProperty=isbn| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=T| columnLabel=URL| dtoPropertyName=url| " +
                    "outputProperty=url| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=U| columnLabel=Compiler(s) name(s)| dtoPropertyName=compilers| " +
                    "outputProperty=null| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=V| columnLabel=Note from compiler(s)| dtoPropertyName=compilersNotes| " +
                    "outputProperty=compilersNotes| propertyTransformer=" + STRING_NORMALIZER_CLASS +
                    "| afterLoadingTransformer=" + NOP_ENTITIES_TRANSFORMER_CLASS+ ")";

    public final static String PUB_LINES = "PublicationLine(title=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| authors=Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, F. /Rosário, V.E. / " +
            "Krettli, A.U.  | year=2008| entryType=Journal article| journal=International Journal for Parasitology|" +
            " pages=1505-1511| volume=38| doi=10.1016/j.ijpara.2008.05.007| pmid=18599059| " +
            " compilers=Deharo, Eric / Genevieve, Bourdy|" +
            " compilersNotes=there is no plant named Ampelozyziphus in the plantlist.org)," +
            " PublicationLine(title=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol. | authors=Krettli, A.U./ Brandao, M.G.L. / Grandi, T.S.M. / Rocha, E.M.M./ Sawyer," +
            " D.R.| year=1992| entryType=Journal article| journal=Journal of Ethnopharacology|" +
            "pages=175–182 | volume=36| compilers=Deharo, Eric)," +
            "PublicationLine(title=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke, " +
            "vulgarmente denominada 'cervejade-indio' contra o Plasmodium berghei| authors=Botelho, M.G.A./" +
            "Paulino-Filho, H.F./ Krettli, A.U.| year=1981| entryType=Journal article, " +
            "journal=Anais do VII Simpo´ sio de Plantas Medicinais do Brazil|pages=437–442| volume=8| " +
            "compilers=Deharo, Eric)," +
            "PublicationLine(title=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| authors=Akpan, E.J./ Okokon, J.E./ Etuk, I.C.| year=2012| " +
            "entryType=Journal article| journal=Asian Pacific Journal of Tropical Disease| pages=36-42|" +
            "doi=10.1016/S2222-1808(12)60009-7| url=http://linkinghub.elsevier.com/retrieve/pii/S2222180812600097| " +
            "compilers=Deharo, Eric)";

    public final static String PUB_LINES_WITH_ERROR = "PublicationLine(title=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| authors=Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, F. /Rosário, V.E. / " +
            "Krettli, A.U.  | year=3001| entryType=Journal article| journal=International Journal for Parasitology|" +
            " pages=1505-1511| volume=38| doi=10.1016/j.ijpara.2008.05.007| pmid=18599059| " +
            "compilers=Deharo, Eric|" +
            " compilersNotes=there is no plant named Ampelozyziphus in the plantlist.org)," +
            " PublicationLine(title=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol. | authors=Krettli, A.U., Brandao, M.G.L. , Grandi, T.S.M. , Rocha, E.M.M., Sawyer," +
            " D.R.| year=1992| entryType=Journal article| journal=Journal of Ethnopharacology|" +
            "pages=175–182 | volume=36)," +
            "PublicationLine(title=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke, " +
            "vulgarmente denominada 'cervejade-indio' contra o Plasmodium berghei| authors=Botelho M.G.A./" +
            "Paulino-Filho, H.F./ Krettli, A.U.| year=1981| entryType=Journal article, " +
            "journal=Anais do VII Simpo´ sio de Plantas Medicinais do Brazil|pages=437–442| volume=8 | " +
            "compilers=Deharo, Eric)," +
            "PublicationLine(title=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| authors=Akpan, E.J./ Okokon, J.E./ Etuk, I.C.| year=2012| " +
            "entryType=Journal article| journal=Asian Pacific Journal of Tropical Disease| pages=36-42|" +
            "doi=10.1016/S2222-1808(12)60009-7| url=http://linkinghub.elsevier.com/retrieve/pii/S2222180812600097| " +
            "compilers=Deharo Eric)";

    public final static String XLS_REF_TEST_COLUMN_INFOS =
            "ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                    "outputProperty=| propertyTransformer=" + NOP_TRANSFORMER_CLASS + "| afterLoadingTransformer=" +
                    NOP_ENTITIES_TRANSFORMER_CLASS+ ")," +
                    "ColumnInfo(columnLetterRef=D| columnLabel=Title| dtoPropertyName=title| " +
                    "outputProperty=| propertyTransformer=" + NOP_TRANSFORMER_CLASS + "| afterLoadingTransformer=" +
                    NOP_ENTITIES_TRANSFORMER_CLASS+ ")";

    public final static String XLS_REF_TEST_LINES = "XlsRefTestLine(refTitle=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| refYear=2008), " +
            "XlsRefTestLine(refTitle=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol.| refYear=1992), " +
            "XlsRefTestLine(refTitle=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke," +
            " vulgarmente denominada 'cervejade-indio' contra o Plasmodium berghei| refYear=1981)," +
            "XlsRefTestLine(refTitle=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| refYear=2012)";

    protected static <T> List<T> parseAndPopulateBeans(String beansStr, Class<T> clazz, String propertySeparator) throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchFieldException, NoSuchMethodException {
        List<T> beanList = new ArrayList<>();

        for (String token : beansStr.substring(0, beansStr.length() - 1).split("\\),")) {
            T bean = clazz.newInstance();
            token = token.trim();
            String fieldsStr = token.substring(clazz.getSimpleName().length() + 1, token.length());

            for (String fieldStr : fieldsStr.split(propertySeparator)) {
                String[] propertyVals = fieldStr.split("=");
                String valueStr = propertyVals.length <= 1 ? null : propertyVals[1].trim();
                String propertyName = propertyVals[0].trim();
                if (PropertyUtils.isWriteable(bean, propertyName))
                    BeanUtils.setProperty(bean, propertyName, valueStr);
                else throw new IllegalAccessException("The value '" + valueStr + "' can't be written in the property " +
                        "'" + propertyName + "'");
            }
            beanList.add(bean);
        }

        return beanList;
    }

}
