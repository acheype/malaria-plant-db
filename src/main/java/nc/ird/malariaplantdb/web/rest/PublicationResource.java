package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.repository.PublicationRepository;
import nc.ird.malariaplantdb.repository.search.PublicationSearchRepository;
import nc.ird.malariaplantdb.web.rest.util.HeaderUtil;
import nc.ird.malariaplantdb.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Publication.
 */
@RestController
@RequestMapping("/api")
public class PublicationResource {

    private final Logger log = LoggerFactory.getLogger(PublicationResource.class);

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationSearchRepository publicationSearchRepository;

    /**
     * POST  /publications -> Create a new publication.
     */
    @RequestMapping(value = "/publications",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Publication> createPublication(@Valid @RequestBody Publication publication) throws URISyntaxException {
        log.debug("REST request to save Publication : {}", publication);
        if (publication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new publication cannot already have an ID").body(null);
        }
        Publication result = publicationRepository.save(publication);
        publicationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/publications/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("publication", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /publications -> Updates an existing publication.
     */
    @RequestMapping(value = "/publications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Publication> updatePublication(@Valid @RequestBody Publication publication) throws URISyntaxException {
        log.debug("REST request to update Publication : {}", publication);
        if (publication.getId() == null) {
            return createPublication(publication);
        }
        Publication result = publicationRepository.save(publication);
        publicationSearchRepository.save(publication);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("publication", publication.getId().toString()))
                .body(result);
    }

    /**
     * GET  /publications -> get all the publications.
     */
    @RequestMapping(value = "/publications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Publication>> getAllPublications(Pageable pageable)
        throws URISyntaxException {
        Page<Publication> page = publicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/publications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /publications/:id -> get the "id" publication.
     */
    @RequestMapping(value = "/publications/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Publication> getPublication(@PathVariable Long id) {
        log.debug("REST request to get Publication : {}", id);
        return Optional.ofNullable(publicationRepository.findOne(id))
            .map(publication -> new ResponseEntity<>(
                publication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /publications/:id -> delete the "id" publication.
     */
    @RequestMapping(value = "/publications/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        log.debug("REST request to delete Publication : {}", id);
        publicationRepository.delete(id);
        publicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("publication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/publications/:query -> search for the publication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/publications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Publication> searchPublications(@PathVariable String query) {
        return StreamSupport
            .stream(publicationSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
