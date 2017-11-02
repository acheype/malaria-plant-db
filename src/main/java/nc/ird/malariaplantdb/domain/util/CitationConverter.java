package nc.ird.malariaplantdb.domain.util;

import de.undercouch.citeproc.CSL;
import de.undercouch.citeproc.csl.CSLItemData;
import de.undercouch.citeproc.csl.CSLItemDataBuilder;
import de.undercouch.citeproc.csl.CSLName;
import de.undercouch.citeproc.csl.CSLType;
import nc.ird.malariaplantdb.domain.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Use the citeproc-java library to convert a publication to its citation defined by a Citation Style Language (CSL).
 * The CSL style name could be found in this <a href="https://github.com/citation-style-language/styles">GitHub
 * repository</a>.
 */
public class CitationConverter implements Converter<Publication, String> {

    private final Logger log = LoggerFactory.getLogger(CitationConverter.class);

    public static final String CSL_STYLE = "apa";

    @Override
    public String convert(Publication pub) {
        CSLItemData item;

        switch(pub.getEntryType().toLowerCase()){
            case "journal article":
                item = buildJournalArticleItem(pub);
                break;
            case "book":
                item = buildBookItem(pub);
                break;
            case "conference paper":
                item = buildConferencePaperItem(pub);
                break;
            case "chapter":
                item = buildChapterItem(pub);
                break;
            case "master's thesis":
                item = buildThesisItem(pub);
                break;
            case "phd thesis":
                item = buildThesisItem(pub);
                break;
            case "tech report":
                item = buildTechReportItem(pub);
                break;
            default :
                item = buildJournalArticleItem(pub);
                break;
        }

        try {
            return CSL.makeAdhocBibliography(CSL_STYLE, "text", item).makeString();
        } catch (Exception e) {
            log.error("Impossible to convert the citation with citeproc, publication ID is : {}, exception is: {}",
                pub.getId(), e.getMessage());
            return null;
        }
    }

    private CSLItemData buildJournalArticleItem(Publication pub) {

        return new CSLItemDataBuilder()
            .type(CSLType.ARTICLE_JOURNAL)
            .title(pub.getTitle())
            .author(pub.getAuthors().stream().map(author -> new CSLName(author.getFamily(), author.getGiven(), null,
                null, null, null, null, null, null, null)).toArray(CSLName[]::new))
            .issued(pub.getYear())
            .containerTitle(pub.getJournal())
            .page(pub.getPages())
            .volume(pub.getVolume())
            .numberOfVolumes(pub.getNbOfVolumes())
            .DOI(pub.getDoi())
            .PMID(pub.getPmid())
            .URL(pub.getUrl())
            .build();
    }

    private CSLItemData buildBookItem(Publication pub) {

        return new CSLItemDataBuilder()
            .type(CSLType.BOOK)
            .title(pub.getTitle())
            .author(pub.getAuthors().stream().map(author -> new CSLName(author.getFamily(), author.getGiven(), null,
                null, null, null, null, null, null, null)).toArray(CSLName[]::new))
            .issued(pub.getYear())
            .volume(pub.getVolume())
            .numberOfVolumes(pub.getNbOfVolumes())
            .publisher(pub.getPublisher())
            .edition(pub.getEdition())
            .DOI(pub.getDoi())
            .ISBN(pub.getIsbn())
            .URL(pub.getUrl())
            .build();
    }

    private CSLItemData buildConferencePaperItem(Publication pub){

        return new CSLItemDataBuilder()
            .type(CSLType.PAPER_CONFERENCE)
            .title(pub.getTitle())
            .author(pub.getAuthors().stream().map(author -> new CSLName(author.getFamily(), author.getGiven(), null,
                null, null, null, null, null, null, null)).toArray(CSLName[]::new))
            .issued(pub.getYear())
            .page(pub.getPages())
            .volume(pub.getVolume())
            .containerTitle(pub.getConferenceName())
            .eventplace(pub.getConferencePlace())
            .DOI(pub.getDoi())
            .PMID(pub.getPmid())
            .URL(pub.getUrl())
            .build();
    }

    private CSLItemData buildChapterItem(Publication pub) {

        return new CSLItemDataBuilder()
            .type(CSLType.CHAPTER)
            .title(pub.getTitle())
            .author(pub.getAuthors().stream().map(author -> new CSLName(author.getFamily(), author.getGiven(), null,
                null, null, null, null, null, null, null)).toArray(CSLName[]::new))
            .issued(pub.getYear())
            .page(pub.getPages())
            .volume(pub.getVolume())
            .numberOfVolumes(pub.getNbOfVolumes())
            .containerTitle(pub.getBookTitle())
            .publisher(pub.getPublisher())
            .edition(pub.getEdition())
            .DOI(pub.getDoi())
            .ISBN(pub.getIsbn())
            .URL(pub.getUrl())
            .build();
    }

    private CSLItemData buildThesisItem(Publication pub) {

        return new CSLItemDataBuilder()
            .type(CSLType.THESIS)
            .title(pub.getTitle())
            .author(pub.getAuthors().stream().map(author -> new CSLName(author.getFamily(), author.getGiven(), null,
                null, null, null, null, null, null, null)).toArray(CSLName[]::new))
            .issued(pub.getYear())
            .number(pub.getNumber())
            .publisher(pub.getPublisher())
            .DOI(pub.getDoi())
            .URL(pub.getUrl())
            .build();
    }

    private CSLItemData buildTechReportItem(Publication pub) {

        return new CSLItemDataBuilder()
            .type(CSLType.REPORT)
            .title(pub.getTitle())
            .author(pub.getAuthors().stream().map(author -> new CSLName(author.getFamily(), author.getGiven(), null,
                null, null, null, null, null, null, null)).toArray(CSLName[]::new))
            .issued(pub.getYear())
            .page(pub.getPages())
            .number(pub.getNumber())
            .edition(pub.getEdition())
            .publisher(pub.getInstitution())
            .DOI(pub.getDoi())
            .URL(pub.getUrl())
            .build();
    }

}
