package nc.ird.malariaplantdb.service;

import nc.ird.malariaplantdb.domain.*;
import nc.ird.malariaplantdb.domain.util.CitationConverter;
import nc.ird.malariaplantdb.repository.*;
import nc.ird.malariaplantdb.repository.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service for the Publication entity
 */
@Service
@Transactional
public class PublicationService {

    private final Logger log = LoggerFactory.getLogger(PublicationService.class);

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private SpeciesRepository speciesRepository;

    @Inject
    private SpeciesSearchRepository speciesSearchRepository;

    @Inject
    private PubSpeciesRepository pubSpeciesRepository;

    @Inject
    private PubSpeciesSearchRepository pubSpeciesSearchRepository;

    @Inject
    private PlantIngredientRepository plantIngredientRepository;

    @Inject
    private PlantIngredientSearchRepository plantIngredientSearchRepository;

    @Inject
    private EthnologyRepository ethnologyRepository;

    @Inject
    private EthnologySearchRepository ethnologySearchRepository;

    @Inject
    private InVivoPharmacoRepository inVivoPharmacoRepository;

    @Inject
    private InVivoPharmacoSearchRepository inVivoPharmacoSearchRepository;

    @Inject
    private InVitroPharmacoRepository inVitroPharmacoRepository;

    @Inject
    private InVitroPharmacoSearchRepository inVitroPharmacoSearchRepository;

    @Inject
    private RemedyRepository remedyRepository;

    @Inject
    private RemedySearchRepository remedySearchRepository;

    /**
     * Save a publication entity which the citation has been set
     *
     * @param publication the publication to save
     * @return the saved publication
     */
    public Publication save(Publication publication){

        CitationConverter converter = new CitationConverter();
        publication.setCitation(converter.convert(publication));

        return publicationRepository.save(publication);
    }

    /**
     * Delete the PubSpecies and the associated Species when it is not still referenced
     *
     * @param pubSpecies the pubSpecies to delete
     */
    public void deletePubSpeciesAndAssociated(PubSpecies pubSpecies) {
        pubSpeciesRepository.delete(pubSpecies);
        pubSpeciesSearchRepository.delete(pubSpecies);

        if (plantIngredientRepository.findBySpeciesId(pubSpecies.getSpecies().getId()).size() == 0
            && pubSpeciesRepository.findBySpeciesId(pubSpecies.getSpecies().getId()).size() == 0) {
            speciesRepository.delete(pubSpecies.getSpecies());
            speciesSearchRepository.delete(pubSpecies.getSpecies());
        }
    }

    /**
     * Delete the ethnology and all associated data (species when they are not still referenced, plant ingredients, and
     * remedies when they are not still referenced)
     *
     * @param ethnology the ethnology to delete
     */
    public void deleteEthnologyAndAssociated(Ethnology ethnology) {
        ethnologyRepository.delete(ethnology);
        ethnologySearchRepository.delete(ethnology);

        if (ethnologyRepository.findByRemedyId(ethnology.getRemedy().getId()).size() == 0
            && inVivoPharmacoRepository.findByRemedyId(ethnology.getRemedy().getId()).size() == 0
            && inVitroPharmacoRepository.findByRemedyId(ethnology.getRemedy().getId()).size() == 0) {

            remedyRepository.delete(ethnology.getRemedy());
            remedySearchRepository.delete(ethnology.getRemedy());

            for (PlantIngredient pi : ethnology.getRemedy().getPlantIngredients()) {
                // no need because of the Cascade on Remedy.plantIngredients
                //plantIngredientRepository.delete(pi);
                plantIngredientSearchRepository.delete(pi);
                if (plantIngredientRepository.findBySpeciesId(pi.getSpecies().getId()).size() == 0
                    && pubSpeciesRepository.findBySpeciesId(pi.getSpecies().getId()).size() == 0) {
                    speciesRepository.delete(pi.getSpecies());
                    speciesSearchRepository.delete(pi.getSpecies());
                }
            }
        }
    }

    /**
     * Delete the inVivoPharmaco and and all associated data (species when they are not still referenced, plant ingredients, and
     * remedies when they are not still referenced)
     *
     * @param inVivoPharmaco the inVivoPharmaco to delete
     */
    public void deleteInVivoPharmacoAndAssociated(InVivoPharmaco inVivoPharmaco) {
        inVivoPharmacoRepository.delete(inVivoPharmaco);
        inVivoPharmacoSearchRepository.delete(inVivoPharmaco);

        if (ethnologyRepository.findByRemedyId(inVivoPharmaco.getRemedy().getId()).size() == 0
            && inVivoPharmacoRepository.findByRemedyId(inVivoPharmaco.getRemedy().getId()).size() == 0
            && inVitroPharmacoRepository.findByRemedyId(inVivoPharmaco.getRemedy().getId()).size() == 0) {

            remedyRepository.delete(inVivoPharmaco.getRemedy());
            remedySearchRepository.delete(inVivoPharmaco.getRemedy());

            for (PlantIngredient pi : inVivoPharmaco.getRemedy().getPlantIngredients()) {
                // no need because of the Cascade on Remedy.plantIngredients
                //plantIngredientRepository.delete(pi);
                plantIngredientSearchRepository.delete(pi);
                if (plantIngredientRepository.findBySpeciesId(pi.getSpecies().getId()).size() == 0
                    && pubSpeciesRepository.findBySpeciesId(pi.getSpecies().getId()).size() == 0) {
                    speciesRepository.delete(pi.getSpecies());
                    speciesSearchRepository.delete(pi.getSpecies());
                }
            }
        }
    }

    /**
     * Delete the inVitroPharmaco and and all associated data (species when they are not still referenced, plant
     * ingredients, and
     * remedies when they are not still referenced)
     *
     * @param inVitroPharmaco the inVivoPharmaco to delete
     */
    public void deleteInVitroPharmacoAndAssociated(InVitroPharmaco inVitroPharmaco) {
        inVitroPharmacoRepository.delete(inVitroPharmaco);
        inVitroPharmacoSearchRepository.delete(inVitroPharmaco);

        if (ethnologyRepository.findByRemedyId(inVitroPharmaco.getRemedy().getId()).size() == 0
            && inVivoPharmacoRepository.findByRemedyId(inVitroPharmaco.getRemedy().getId()).size() == 0
            && inVitroPharmacoRepository.findByRemedyId(inVitroPharmaco.getRemedy().getId()).size() == 0) {

            remedyRepository.delete(inVitroPharmaco.getRemedy());
            remedySearchRepository.delete(inVitroPharmaco.getRemedy());

            for (PlantIngredient pi : inVitroPharmaco.getRemedy().getPlantIngredients()) {
                // no need because of the Cascade on Remedy.plantIngredients
                //plantIngredientRepository.delete(pi);
                plantIngredientSearchRepository.delete(pi);
                if (plantIngredientRepository.findBySpeciesId(pi.getSpecies().getId()).size() == 0
                    && pubSpeciesRepository.findBySpeciesId(pi.getSpecies().getId()).size() == 0) {
                    speciesRepository.delete(pi.getSpecies());
                    speciesSearchRepository.delete(pi.getSpecies());
                }
            }
        }
    }

}
