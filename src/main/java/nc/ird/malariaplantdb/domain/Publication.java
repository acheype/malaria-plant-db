package nc.ird.malariaplantdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Publication entity
 * <p/>
 * Standard publication fields (based on Zotero fields and APA style).
 *
 * @author acheype
 */
@Entity
@JsonPropertyOrder({"id", "entryType", "authors", "year", "title", "journal", "pages", "volume", "nbOfVolumes",
    "number", "bookTitle", "publisher", "edition", "conferenceName", "conferencePlace", "university", "institution",
    "doi", "pmid", "isbn", "url", "isReviewed", "compilers", "compilersNotes", "pubSpecies", "ethnologies",
    "inVivoPharmacos", "inVitroPharmacos", "citation"})
@Table(name = "publication", uniqueConstraints = @UniqueConstraint(columnNames = {"title"},
    name = "uk_publication_title"))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "publication")
public class Publication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "entry_type", length = 255, nullable = false)
    private String entryType;

    @JsonIgnoreProperties(value = {"publication"})
    @OneToMany(mappedBy = "publication", fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @SortNatural
    private SortedSet<Author> authors = new TreeSet<>();

    @NotNull
    @Min(value = 0)
    @Max(value = 3000)
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "journal", length = 255)
    private String journal;

    @Size(max = 255)
    @Column(name = "pages", length = 255)
    private String pages;

    @Size(max = 255)
    @Column(name = "volume", length = 255)
    private String volume;

    @Size(max = 255)
    @Column(name = "nb_of_volumes", length = 255)
    private String nbOfVolumes;

    @Size(max = 255)
    @Column(name = "number", length = 255)
    private String number;

    @Size(max = 255)
    @Column(name = "book_title", length = 255)
    private String bookTitle;

    @Size(max = 255)
    @Column(name = "publisher", length = 255)
    private String publisher;

    @Size(max = 255)
    @Column(name = "edition", length = 255)
    private String edition;

    @Size(max = 255)
    @Column(name = "conference_name", length = 255)
    private String conferenceName;

    @Size(max = 255)
    @Column(name = "conference_place", length = 255)
    private String conferencePlace;

    @Size(max = 255)
    @Column(name = "university", length = 255)
    private String university;

    @Size(max = 255)
    @Column(name = "institution", length = 255)
    private String institution;

    @Size(max = 255)
    @Column(name = "doi", length = 255)
    private String doi;

    @Size(max = 255)
    @Column(name = "pmid", length = 255)
    private String pmid;

    @Size(max = 255)
    @Column(name = "isbn", length = 255)
    private String isbn;

    @Size(max = 255)
    @Column(name = "url", length = 255)
    private String url;

    @ManyToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 100)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "publication_compiler",
        joinColumns = @JoinColumn(name = "publications_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "compilers_id", referencedColumnName = "ID"))
    @SortNatural
    private SortedSet<Compiler> compilers = new TreeSet<>();

    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "compilers_notes")
    private String compilersNotes;

    @Lob
    @Type(type = "org.hibernate.type.StringClobType")
    @Column(name = "citation")
    private String citation;

    @JsonIgnore
    @OneToMany(mappedBy = "publication")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @SortNatural
    private SortedSet<PubSpecies> pubSpecies = new TreeSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "publication")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @SortNatural
    private SortedSet<Ethnology> ethnologies = new TreeSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "publication")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @SortNatural
    private SortedSet<InVivoPharmaco> inVivoPharmacos = new TreeSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "publication")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @SortNatural
    private SortedSet<InVitroPharmaco> inVitroPharmacos = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(SortedSet<Author> Authors) {
        this.authors = Authors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNbOfVolumes() {
        return nbOfVolumes;
    }

    public void setNbOfVolumes(String nbOfVolumes) {
        this.nbOfVolumes = nbOfVolumes;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getConferencePlace() {
        return conferencePlace;
    }

    public void setConferencePlace(String conferencePlace) {
        this.conferencePlace = conferencePlace;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Compiler> getCompilers() {
        return compilers;
    }

    public void setCompilers(SortedSet<Compiler> Compilers) {
        this.compilers = Compilers;
    }

    public String getCompilersNotes() {
        return compilersNotes;
    }

    public void setCompilersNotes(String compilersNotes) {
        this.compilersNotes = compilersNotes;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public Set<PubSpecies> getPubSpecies() {
        return pubSpecies;
    }

    public void setPubSpecies(SortedSet<PubSpecies> PubSpeciess) {
        this.pubSpecies = PubSpeciess;
    }

    public Set<Ethnology> getEthnologies() {
        return ethnologies;
    }

    public void setEthnologies(SortedSet<Ethnology> Ethnologys) {
        this.ethnologies = Ethnologys;
    }

    public Set<InVivoPharmaco> getInVivoPharmacos() {
        return inVivoPharmacos;
    }

    public void setInVivoPharmacos(SortedSet<InVivoPharmaco> InVivoPharmacos) {
        this.inVivoPharmacos = InVivoPharmacos;
    }

    public Set<InVitroPharmaco> getInVitroPharmacos() {
        return inVitroPharmacos;
    }

    public void setInVitroPharmacos(SortedSet<InVitroPharmaco> InVitroPharmacos) {
        this.inVitroPharmacos = InVitroPharmacos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Publication publication = (Publication) o;

        return Objects.equals(id, publication.id);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Publication{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", entryType='" + entryType + "'" +
            ", year='" + year + "'" +
            ", journal='" + journal + "'" +
            ", pages='" + pages + "'" +
            ", volume='" + volume + "'" +
            ", nbOfVolumes='" + nbOfVolumes + "'" +
            ", number='" + number + "'" +
            ", bookTitle='" + bookTitle + "'" +
            ", publisher='" + publisher + "'" +
            ", edition='" + edition + "'" +
            ", conferenceName='" + conferenceName + "'" +
            ", conferencePlace='" + conferencePlace + "'" +
            ", university='" + university + "'" +
            ", institution='" + institution + "'" +
            ", doi='" + doi + "'" +
            ", pmid='" + pmid + "'" +
            ", isbn='" + isbn + "'" +
            ", url='" + url + "'" +
            ", compilersNotes='" + compilersNotes + "'" +
            ", citation='" + citation + "'" +
            '}';
    }

}
