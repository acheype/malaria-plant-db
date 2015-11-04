package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.Species;
import nc.ird.malariaplantdb.repository.SpeciesRepository;
import nc.ird.malariaplantdb.repository.search.SpeciesSearchRepository;
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
 * REST controller for managing Species.
 */
@RestController
@RequestMapping("/api")
public class SpeciesResource {

    private final Logger log = LoggerFactory.getLogger(SpeciesResource.class);

    @Inject
    private SpeciesRepository speciesRepository;

    @Inject
    private SpeciesSearchRepository speciesSearchRepository;

    /**
     * POST  /species -> Create a new species.
     */
    @RequestMapping(value = "/species",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Species> createSpecies(@Valid @RequestBody Species species) throws URISyntaxException {
        log.debug("REST request to save Species : {}", species);
        if (species.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new species cannot already have an ID").body(null);
        }
        Species result = speciesRepository.save(species);
        speciesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/species/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("species", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /species -> Updates an existing species.
     */
    @RequestMapping(value = "/species",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Species> updateSpecies(@Valid @RequestBody Species species) throws URISyntaxException {
        log.debug("REST request to update Species : {}", species);
        if (species.getId() == null) {
            return createSpecies(species);
        }
        Species result = speciesRepository.save(species);
        speciesSearchRepository.save(species);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("species", species.getId().toString()))
                .body(result);
    }

    /**
     * GET  /species -> get all the species.
     */
    @RequestMapping(value = "/species",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Species>> getAllSpecies(Pageable pageable)
        throws URISyntaxException {
        Page<Species> page = speciesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/species");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /species/:id -> get the "id" species.
     */
    @RequestMapping(value = "/species/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Species> getSpecies(@PathVariable Long id) {
        log.debug("REST request to get Species : {}", id);
        return Optional.ofNullable(speciesRepository.findOne(id))
            .map(species -> new ResponseEntity<>(
                species,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /species/:id -> delete the "id" species.
     */
    @RequestMapping(value = "/species/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSpecies(@PathVariable Long id) {
        log.debug("REST request to delete Species : {}", id);
        speciesRepository.delete(id);
        speciesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("species", id.toString())).build();
    }

    /**
     * SEARCH  /_search/species/:query -> search for the species corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/species/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Species> searchSpecies(@PathVariable String query) {
        return StreamSupport
            .stream(speciesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
