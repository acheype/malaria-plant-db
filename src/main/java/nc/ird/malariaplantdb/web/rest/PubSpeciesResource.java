package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.PubSpecies;
import nc.ird.malariaplantdb.repository.PubSpeciesRepository;
import nc.ird.malariaplantdb.repository.search.PubSpeciesSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing PubSpecies.
 */
@RestController
@RequestMapping("/api")
public class PubSpeciesResource {

    private final Logger log = LoggerFactory.getLogger(PubSpeciesResource.class);

    @Inject
    private PubSpeciesRepository pubSpeciesRepository;

    @Inject
    private PubSpeciesSearchRepository pubSpeciesSearchRepository;

    /**
     * POST  /pubSpecies -> Create a new pubSpecies.
     */
    @RequestMapping(value = "/pubSpecies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PubSpecies> createPubSpecies(@Valid @RequestBody PubSpecies pubSpecies) throws URISyntaxException {
        log.debug("REST request to save PubSpecies : {}", pubSpecies);
        if (pubSpecies.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pubSpecies cannot already have an ID").body(null);
        }
        PubSpecies result = pubSpeciesRepository.save(pubSpecies);
        pubSpeciesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pubSpecies/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("pubSpecies", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /pubSpecies -> Updates an existing pubSpecies.
     */
    @RequestMapping(value = "/pubSpecies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PubSpecies> updatePubSpecies(@Valid @RequestBody PubSpecies pubSpecies) throws URISyntaxException {
        log.debug("REST request to update PubSpecies : {}", pubSpecies);
        if (pubSpecies.getId() == null) {
            return createPubSpecies(pubSpecies);
        }
        PubSpecies result = pubSpeciesRepository.save(pubSpecies);
        pubSpeciesSearchRepository.save(pubSpecies);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("pubSpecies", pubSpecies.getId().toString()))
                .body(result);
    }

    /**
     * GET  /pubSpecies -> get all the pubSpecies.
     */
    @RequestMapping(value = "/pubSpecies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PubSpecies>> getAllPubSpecies(Pageable pageable)
        throws URISyntaxException {
        Page<PubSpecies> page = pubSpeciesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pubSpecies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pubSpecies/:id -> get the "id" pubSpecies.
     */
    @RequestMapping(value = "/pubSpecies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PubSpecies> getPubSpecies(@PathVariable Long id) {
        log.debug("REST request to get PubSpecies : {}", id);
        return Optional.ofNullable(pubSpeciesRepository.findOne(id))
            .map(pubSpecies -> new ResponseEntity<>(
                pubSpecies,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /publications/:id/pubspecies -> get all the pubspecies of the "id" publication.
     */
    @RequestMapping(value = "/publications/{id}/pubspecies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PubSpecies>> getPubSpeciesByPublicationId(@PathVariable Long id) {
        log.debug("REST request to get the Pubspecies of the Publication : {}", id);
        List<PubSpecies> pubSpecies = pubSpeciesRepository.findByPublicationId(id);
        return new ResponseEntity<>(pubSpecies,  HttpStatus.OK);
    }

    /**
     * DELETE  /pubSpecies/:id -> delete the "id" pubSpecies.
     */
    @RequestMapping(value = "/pubSpecies/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePubSpecies(@PathVariable Long id) {
        log.debug("REST request to delete PubSpecies : {}", id);
        pubSpeciesRepository.delete(id);
        pubSpeciesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pubSpecies", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pubSpecies/:query -> search for the pubSpecies corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pubSpecies/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PubSpecies> searchPubSpecies(@PathVariable String query) {
        return StreamSupport
            .stream(pubSpeciesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
