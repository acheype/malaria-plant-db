package nc.ird.malariaplantdb.service;

import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.domain.util.CitationConverter;
import nc.ird.malariaplantdb.repository.PublicationRepository;
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
}
