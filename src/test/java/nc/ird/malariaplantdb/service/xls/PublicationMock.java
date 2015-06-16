package nc.ird.malariaplantdb.service.xls;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PublicationMock {

    private static String NOP_TRANSFORMER_CLASS = "org.apache.commons.collections.functors.NOPTransformer";

    private static String TRIM_STRING_TRANSFORMER_CLASS = "nc.ird.malariaplantdb.service.xls.transformers" +
            ".TrimStringTransformer";

    public final static String PUBLI_COLUMN_INFOS =
            "ColumnInfo(columnLetterRef=A| columnLabel=Entry Type| dtoPropertyName=entryType| " +
                    "outputProperty=entryType| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=B| columnLabel=Author(s)| dtoPropertyName=authors|" +
                    "outputProperty=authors| propertyTransformer=nc.ird.malariaplantdb.service.xls" +
                    ".transformers.StringToAuthorsList)," +
                    "ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                    "outputProperty=year| propertyTransformer=" + NOP_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=D| columnLabel=Title| dtoPropertyName=title| " +
                    "outputProperty=title| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=E| columnLabel=Journal| dtoPropertyName=journal| " +
                    "outputProperty=journal| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=F| columnLabel=Month| dtoPropertyName=month| " +
                    "outputProperty=month| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=G| columnLabel=Page| dtoPropertyName=page| " +
                    "outputProperty=page| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=H| columnLabel=Volume| dtoPropertyName=volume| " +
                    "outputProperty=volume| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=I| columnLabel=Nb of volumes| dtoPropertyName=nbOfVolumes| " +
                    "outputProperty=nbOfVolumes| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=J| columnLabel=Number| dtoPropertyName=number| " +
                    "outputProperty=number| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=K| columnLabel=Book title| dtoPropertyName=bookTitle| " +
                    "outputProperty=bookTitle| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=L| columnLabel=Publisher| dtoPropertyName=publisher| " +
                    "outputProperty=publisher| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=M| columnLabel=Edition| dtoPropertyName=edition| " +
                    "outputProperty=edition| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=N| columnLabel=University| dtoPropertyName=university| " +
                    "outputProperty=university| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=O| columnLabel=Institution| dtoPropertyName=institution| " +
                    "outputProperty=institution| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=P| columnLabel=DOI| dtoPropertyName=doi| " +
                    "outputProperty=doi| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=Q| columnLabel=PMID| dtoPropertyName=pmid| " +
                    "outputProperty=pmid| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=R| columnLabel=ISBN| dtoPropertyName=isbn| " +
                    "outputProperty=isbn| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=S| columnLabel=URL| dtoPropertyName=url| " +
                    "outputProperty=url| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=T| columnLabel=Reviewed by compiler(s)| dtoPropertyName=isReviewed| " +
                    "outputProperty=isReviewed| propertyTransformer=" + NOP_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=U| columnLabel=Compiler(s) name(s)| dtoPropertyName=compilers| " +
                    "outputProperty=| propertyTransformer=" + NOP_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=V| columnLabel=Note from compiler(s)| dtoPropertyName=compilersNotes| " +
                    "outputProperty=compilersNotes| propertyTransformer=" + TRIM_STRING_TRANSFORMER_CLASS + ")";

    public final static String PUB_LINES = "PublicationLine(title=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| authors=Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, F. /Rosário, V.E. / " +
            "Krettli, A.U.  | year=2008| entryType=Journal article| journal=International Journal for Parasitology|" +
            " month=MAY| page=1505-1511| volume=38| doi=10.1016/j.ijpara.2008.05.007| pmid=18599059| " +
            " isReviewed=true| compilers=Deharo, Eric / Genevieve, Bourdy|" +
            " compilersNotes=there is no plant named Ampelozyziphus in the plantlist.org)," +
            " PublicationLine(title=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol. | authors=Krettli, A.U./ Brandao, M.G.L. / Grandi, T.S.M. / Rocha, E.M.M./ Sawyer," +
            " D.R.| year=1992| entryType=Journal article| journal=Journal of Ethnopharacology|" +
            "page=175–182 | volume=36| isReviewed=true| compilers=Deharo, Eric)," +
            "PublicationLine(title=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke, " +
            "vulgarmente denominada 'cervejade-indio' contra o Plasmodium berghei| authors=Botelho, M.G.A./" +
            "Paulino-Filho, H.F./ Krettli, A.U.| year=1981| entryType=Journal article, " +
            "journal=Anais do VII Simpo´ sio de Plantas Medicinais do Brazil|page=437–442| volume=8| " +
            "isReviewed=false)," +
            "PublicationLine(title=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| authors=Akpan, E.J./ Okokon, J.E./ Etuk, I.C.| year=2012| " +
            "entryType=Journal article| journal=Asian Pacific Journal of Tropical Disease| month=FEB| page=36-42|" +
            "doi=10.1016/S2222-1808(12)60009-7| url=http://linkinghub.elsevier.com/retrieve/pii/S2222180812600097| " +
            "isReviewed=true| compilers=Deharo, Eric)";

    public final static String PUB_LINES_WITH_ERROR = "PublicationLine(title=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| authors=Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, F. /Rosário, V.E. / " +
            "Krettli, A.U.  | year=3001| entryType=Journal article| journal=International Journal for Parasitology|" +
            " month=MAY| page=1505-1511| volume=38| doi=10.1016/j.ijpara.2008.05.007| pmid=18599059| " +
            "isReviewed=true| compilers=Deharo, Eric|" +
            " compilersNotes=there is no plant named Ampelozyziphus in the plantlist.org)," +
            " PublicationLine(title=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol. | authors=Krettli, A.U., Brandao, M.G.L. , Grandi, T.S.M. , Rocha, E.M.M., Sawyer," +
            " D.R.| year=1992| entryType=Journal article| journal=Journal of Ethnopharacology|" +
            "page=175–182 | volume=36)," +
            "PublicationLine(title=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke, " +
            "vulgarmente denominada 'cervejade-indio' contra o Plasmodium berghei| authors=Botelho M.G.A./" +
            "Paulino-Filho, H.F./ Krettli, A.U.| year=1981| entryType=Journal article, " +
            "journal=Anais do VII Simpo´ sio de Plantas Medicinais do Brazil|page=437–442| volume=8 | " +
            "isReviewed=false| compilers=Deharo, Eric)," +
            "PublicationLine(title=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| authors=Akpan, E.J./ Okokon, J.E./ Etuk, I.C.| year=2012| " +
            "entryType=Journal article| journal=Asian Pacific Journal of Tropical Disease| month=FEB| page=36-42|" +
            "doi=10.1016/S2222-1808(12)60009-7| url=http://linkinghub.elsevier.com/retrieve/pii/S2222180812600097| " +
            "isReviewed=true)";

    public final static String XLS_REF_TEST_COLUMN_INFOS =
            "ColumnInfo(columnLetterRef=C| columnLabel=Year| dtoPropertyName=year| " +
                    "outputProperty=| propertyTransformer=" + NOP_TRANSFORMER_CLASS + "), " +
                    "ColumnInfo(columnLetterRef=D| columnLabel=Title| dtoPropertyName=title| " +
                    "outputProperty=| propertyTransformer=" + NOP_TRANSFORMER_CLASS + ")";

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
                BeanUtils.setProperty(bean, propertyVals[0].trim(), valueStr);
            }
            beanList.add(bean);
        }

        return beanList;
    }

}
