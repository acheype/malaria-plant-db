package nc.ird.malariaplantdb.web.rest.dto;

import nc.ird.malariaplantdb.domain.*;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Publication summary used in the list view
 *
 * @author acheype
 */
public class PubSummaryDTO {

    private Long id;

    private String citation;

    private String doi;

    private String pmid;

    private String isbn;

    private String url;

    private SortedSet<String> countries = new TreeSet<>();

    private SortedMap<String, RemSummaryDTO> remedies = new TreeMap<>();

    public PubSummaryDTO(Publication pub) {
        id = pub.getId();
        citation = pub.getCitation();
        doi = pub.getDoi();
        pmid = pub.getPmid();
        isbn = pub.getIsbn();
        url = pub.getUrl();

        countries.addAll(pub.getPubSpecies().stream()
            .map(PubSpecies::getCountry)
            .collect(Collectors.toList()));

        for (Ethnology ethno : pub.getEthnologies()){
            String remedyKey = buildRemedyKey(ethno.getRemedy());
            remedies.put(remedyKey, new RemSummaryDTO(ethno.getRemedy().getId()));
        }

        for (InVivoPharmaco inVivoPharmaco : pub.getInVivoPharmacos()){
            String remedyKey = buildRemedyKey(inVivoPharmaco.getRemedy());

            if (remedies.values().stream().noneMatch(r -> inVivoPharmaco.getRemedy().getId().equals(r.getRemedyId()))) {
                remedies.put(remedyKey, new RemSummaryDTO(inVivoPharmaco.getRemedy().getId()));
            }

            remedies.get(remedyKey).setTestedEntity(RemSummaryDTO.TestType.IN_VIVO,
                inVivoPharmaco.getScreeningTest(), inVivoPharmaco.getInhibition(), inVivoPharmaco.getEd50(), null,
                null, null);
        }

        for (InVitroPharmaco inVitroPharmaco : pub.getInVitroPharmacos()){
            String remedyKey = buildRemedyKey(inVitroPharmaco.getRemedy());

            if (remedies.values().stream().noneMatch(
                    r -> inVitroPharmaco.getRemedy().getId().equals(r.getRemedyId()))) {
                remedies.put(remedyKey, new RemSummaryDTO(inVitroPharmaco.getRemedy().getId()));
            }

            remedies.get(remedyKey).setTestedEntity(RemSummaryDTO.TestType.IN_VITRO,
                inVitroPharmaco.getScreeningTest(), null, null, inVitroPharmaco.getInhibition(),
                inVitroPharmaco.getIc50(), inVitroPharmaco.getMolIc50());
        }
    }

    private String buildRemedyKey(Remedy remedy) {
        return remedy.getPlantIngredients().stream()
                    .map(pi -> pi.getSpecies().getSpecies() + ", " + pi.getPartUsed())
                    .collect(Collectors.joining(" / "));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
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

    public SortedSet<String> getCountries() {
        return countries;
    }

    public void setCountries(SortedSet<String> countries) {
        this.countries = countries;
    }

    public SortedMap<String, RemSummaryDTO> getRemedies() {
        return remedies;
    }

    public void setRemedies(SortedMap<String, RemSummaryDTO> remedies) {
        this.remedies = remedies;
    }

}
