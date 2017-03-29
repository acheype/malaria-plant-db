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

    private String citation;

    private String doi;

    private String pmid;

    private String isbn;

    private String url;

    private SortedSet<String> countries = new TreeSet<>();

    private SortedMap<String, PISummaryDTO> plantIngredients = new TreeMap<>();

    public PubSummaryDTO(Publication pub) {
        citation = pub.getCitation();
        doi = pub.getDoi();
        pmid = pub.getPmid();
        isbn = pub.getIsbn();
        url = pub.getUrl();

        countries.addAll(pub.getPubSpecies().stream()
            .map(PubSpecies::getCountry)
            .collect(Collectors.toList()));

        for (InVivoPharmaco inVivoPharmaco : pub.getInVivoPharmacos()){
            String plantIngredientStr = buildPlantIngredientsKey(inVivoPharmaco.getPlantIngredients());

            if (!plantIngredients.containsKey(plantIngredientStr))
                plantIngredients.put(plantIngredientStr, new PISummaryDTO());

            plantIngredients.get(plantIngredientStr).setTestedEntity(PISummaryDTO.TestType.IN_VIVO,
                inVivoPharmaco.getScreeningTest(), inVivoPharmaco.getInhibition(), inVivoPharmaco.getEd50(), null,
                null, null);
        }

        for (InVitroPharmaco inVitroPharmaco : pub.getInVitroPharmacos()){
            String plantIngredientStr = buildPlantIngredientsKey(inVitroPharmaco.getPlantIngredients());

            if (!plantIngredients.containsKey(plantIngredientStr))
                plantIngredients.put(plantIngredientStr, new PISummaryDTO());

            plantIngredients.get(plantIngredientStr).setTestedEntity(PISummaryDTO.TestType.IN_VITRO,
                inVitroPharmaco.getScreeningTest(), null, null, inVitroPharmaco.getInhibition(),
                inVitroPharmaco.getIc50(), inVitroPharmaco.getMolIc50());
        }
    }

    private String buildPlantIngredientsKey(SortedSet<PlantIngredient> plantIngredients) {
        return plantIngredients.stream()
                    .map(pi -> pi.getSpecies().getFamily() + " " + pi.getSpecies().getSpecies() + ", " + pi.getPartUsed())
                    .collect(Collectors.joining(" / "));
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

    public SortedMap<String, PISummaryDTO> getPlantIngredients() {
        return plantIngredients;
    }

    public void setPlantIngredients(SortedMap<String, PISummaryDTO> plantIngredients) {
        this.plantIngredients = plantIngredients;
    }

}
