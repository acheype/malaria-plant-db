package nc.ird.malariaplantdb.service.xls;

import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adri on 07/04/15.
 */
public class PublicationMock {

    public final static String COLUMN_INFOS_STR =
            "ColumnInfo(columnLetterRef=A| columnName=Entry Type| dtoPropertyName=entryType| " +
                    "outputProperty=entryType| propertyTransformer=org.apache.commons.collections.functors" +
                    ".NOPTransformer), " +
            "ColumnInfo(columnLetterRef=B| columnName=Author(s)| dtoPropertyName=authors), " +
            "ColumnInfo(columnLetterRef=C| columnName=Year| dtoPropertyName=year), " +
            "ColumnInfo(columnLetterRef=D| columnName=Title| dtoPropertyName=title), " +
            "ColumnInfo(columnLetterRef=E| columnName=Journal| dtoPropertyName=journal), " +
            "ColumnInfo(columnLetterRef=F| columnName=Month| dtoPropertyName=month), " +
            "ColumnInfo(columnLetterRef=G| columnName=Page| dtoPropertyName=page), " +
            "ColumnInfo(columnLetterRef=H| columnName=Volume| dtoPropertyName=volume), " +
            "ColumnInfo(columnLetterRef=I| columnName=Nb of volumes| dtoPropertyName=nbOfVolumes), " +
            "ColumnInfo(columnLetterRef=J| columnName=Number| dtoPropertyName=number), " +
            "ColumnInfo(columnLetterRef=K| columnName=Book title| dtoPropertyName=bookTitle), " +
            "ColumnInfo(columnLetterRef=L| columnName=Publisher| dtoPropertyName=publisher), " +
            "ColumnInfo(columnLetterRef=M| columnName=Edition| dtoPropertyName=edition), " +
            "ColumnInfo(columnLetterRef=N| columnName=University| dtoPropertyName=university), " +
            "ColumnInfo(columnLetterRef=O| columnName=Institution| dtoPropertyName=institution), " +
            "ColumnInfo(columnLetterRef=P| columnName=DOI| dtoPropertyName=doi), " +
            "ColumnInfo(columnLetterRef=Q| columnName=PMID| dtoPropertyName=pmid), " +
            "ColumnInfo(columnLetterRef=R| columnName=ISBN| dtoPropertyName=isbn), " +
            "ColumnInfo(columnLetterRef=S| columnName=URL| dtoPropertyName=url), " +
            "ColumnInfo(columnLetterRef=T| columnName=Reviewed by compiler(s)| dtoPropertyName=isReviewed), " +
            "ColumnInfo(columnLetterRef=U| columnName=Compiler(s) name(s)| dtoPropertyName=compilers), " +
            "ColumnInfo(columnLetterRef=V| columnName=Note from compiler(s)| dtoPropertyName=compilersNotes)";

    public final static String COLUMN_INFOS_STR_WITH_ERROR =
            "ColumnInfo(columnLetterRef=A| columnName=Entry Type| dtoPropertyName=entryType), " +
                    "ColumnInfo(columnLetterRef=B| columnName=Author(s)| dtoPropertyName=authors), " +
                    "ColumnInfo(columnLetterRef=C| columnName=Year| dtoPropertyName=year), " +
                    "ColumnInfo(columnLetterRef=D| columnName=Title| dtoPropertyName=title), " +
                    "ColumnInfo(columnLetterRef=E| columnName=Journal| dtoPropertyName=journal), " +
                    "ColumnInfo(columnLetterRef=F| columnName=Month| dtoPropertyName=month), " +
                    "ColumnInfo(columnLetterRef=G| columnName=Page| dtoPropertyName=page), " +
                    "ColumnInfo(columnLetterRef=H| columnName=Volume| dtoPropertyName=volume), " +
                    "ColumnInfo(columnLetterRef=I| columnName=Nb of volumes| dtoPropertyName=nbOfVolumes), " +
                    "ColumnInfo(columnLetterRef=J| columnName=Number| dtoPropertyName=number), " +
                    "ColumnInfo(columnLetterRef=K| columnName=Book title| dtoPropertyName=bookTitle), " +
                    "ColumnInfo(columnLetterRef=L| columnName=Publisher| dtoPropertyName=publisher), " +
                    "ColumnInfo(columnLetterRef=M| columnName=Edition| dtoPropertyName=edition), " +
                    "ColumnInfo(columnLetterRef=N| columnName=University| dtoPropertyName=university), " +
                    "ColumnInfo(columnLetterRef=O| columnName=Institution| dtoPropertyName=institution), " +
                    "ColumnInfo(columnLetterRef=P| columnName=DOI| dtoPropertyName=doi), " +
                    "ColumnInfo(columnLetterRef=Q| columnName=PMID| dtoPropertyName=pmid), " +
                    "ColumnInfo(columnLetterRef=R| columnName=ISBN| dtoPropertyName=isbn), " +
                    "ColumnInfo(columnLetterRef=S| columnName=URL| dtoPropertyName=url), " +
                    "ColumnInfo(columnLetterRef=T| columnName=Reviewed by compiler(s)| " +
                    "dtoPropertyName=isReviewedXXXXX)," +
                    "ColumnInfo(columnLetterRef=U| columnName=Compiler(s) name(s)| dtoPropertyName=compilers), " +
                    "ColumnInfo(columnLetterRef=V| columnName=Note from compiler(s)| dtoPropertyName=compilersNotes)";



    public final static String PUB_SHEETS_STR = "PublicationSheet(title=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| authors=Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, F. /Rosário, V.E. / " +
            "Krettli, A.U.  | year=2008| entryType=Journal article| journal=International Journal for Parasitology|" +
            " month=MAY| page=1505-1511| volume=38| doi=10.1016/j.ijpara.2008.05.007| pmid=18599059| " +
            " isReviewed=true| compilers=Deharo, Eric|" +
            " compilersNotes=there is no plant named Ampelozyziphus in the plantlist.org)," +
            " PublicationSheet(title=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol. | authors=Krettli, A.U., Brandao, M.G.L. , Grandi, T.S.M. , Rocha, E.M.M., Sawyer," +
            " D.R.| year=1992| entryType=Journal article| journal=Journal of Ethnopharacology|" +
            "page=175–182 | volume=36| isReviewed=true| compilers=Deharo, Eric)," +
            "PublicationSheet(title=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke, " +
            "vulgarmente denominada ‘‘cervejade- indio” contra o Plasmodium berghei.| authors=Botelho, M.G.A./" +
            "Paulino-Filho, H.F./ Krettli, A.U.| year=1981| entryType=Journal article, " +
            "journal=Anais do VII Simpo´ sio de Plantas Medicinais do Brazil|page=437–442| volume=8| " +
            "isReviewed=false| compilers=Deharo, Eric)," +
            "PublicationSheet(title=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| authors=Akpan, E.J./ Okokon, J.E./ Etuk, I.C.| year=2012| " +
            "entryType=Journal article| journal=Asian Pacific Journal of Tropical Disease| month=FEB| page=36-42|" +
            "doi=10.1016/S2222-1808(12)60009-7| url=http://linkinghub.elsevier.com/retrieve/pii/S2222180812600097| " +
            "isReviewed=false| compilers=Deharo, Eric)";

    public final static String PUB_SHEETS_STR_WITH_ERROR = "PublicationSheet(title=Ampelozyziphus amazonicus Ducke," +
            " a medicinal plant used to prevent malaria in the Amazon Region, hampers the development of Plasmodium " +
            "berghei sporozoites.| authors=Andrade-Neto, V.F./ Brandão, M.G.L. / Nogueira, F. /Rosário, V.E. / " +
            "Krettli, A.U.  | year=3001| entryType=Journal article| journal=International Journal for Parasitology|" +
            " month=MAY| page=1505-1511| volume=38| doi=10.1016/j.ijpara.2008.05.007| pmid=18599059| " +
            "isReviewed=true| compilers=Deharo, Eric|" +
            " compilersNotes=there is no plant named Ampelozyziphus in the plantlist.org)," +
            " PublicationSheet(title=Survey of medicinal plants used as antimalarials in the Amazon. J. " +
            "Ethnopharmacol. | authors=Krettli, A.U., Brandao, M.G.L. , Grandi, T.S.M. , Rocha, E.M.M., Sawyer," +
            " D.R.| year=1992| entryType=Journal article| journal=Journal of Ethnopharacology|" +
            "page=175–182 | volume=36)," +
            "PublicationSheet(title=Quimioterapia experimental antimalarica usando a Rhamnaceae A. amazonicus Ducke, " +
            "vulgarmente denominada ‘‘cervejade- indio” contra o Plasmodium berghei.| authors=Botelho M.G.A./" +
            "Paulino-Filho, H.F./ Krettli, A.U.| year=1981| entryType=Journal article, " +
            "journal=Anais do VII Simpo´ sio de Plantas Medicinais do Brazil|page=437–442| volume=8 | " +
            "isReviewed=false| compilers=Deharo, Eric|)," +
            "PublicationSheet(title=Antiplasmodial and antipyretic studies on root extracts of Anthocleista " +
            "djalonensis against Plasmodium berghei| authors=Akpan, E.J./ Okokon, J.E./ Etuk, I.C.| year=2012| " +
            "entryType=Journal article| journal=Asian Pacific Journal of Tropical Disease| month=FEB| page=36-42|" +
            "doi=10.1016/S2222-1808(12)60009-7| url=http://linkinghub.elsevier.com/retrieve/pii/S2222180812600097| " +
            "isReviewed=true)";

    protected static <T> List<T> parseAndPopulateBeans(String beansStr, Class<T> clazz, String propertySeparator) throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        List<T> beanList = new ArrayList<>();

        for (String token : beansStr.substring(0, beansStr.length() - 1).split("\\),")) {
            T bean = clazz.newInstance();
            token = token.trim();
            String fieldsStr = token.substring(clazz.getSimpleName().length() + 1, token.length());

            for (String fieldStr : fieldsStr.split(propertySeparator)) {
                String[] property = fieldStr.split("=");

                BeanUtils.setProperty(bean, property[0].trim(), property[1].trim());
            }
            beanList.add(bean);
        }

        return beanList;
    }

}
