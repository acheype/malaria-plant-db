package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.Remedy;
import nc.ird.malariaplantdb.repository.RemedyRepository;
import nc.ird.malariaplantdb.repository.search.RemedySearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Remedy.
 */
@RestController
@RequestMapping("/api")
public class RemedyResource {

    private final Logger log = LoggerFactory.getLogger(RemedyResource.class);

    @Inject
    private RemedyRepository remedyRepository;

    @Inject
    private RemedySearchRepository remedySearchRepository;

    /**
     * POST  /remedys -> Create a new remedy.
     */
    @RequestMapping(value = "/remedys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Remedy> createRemedy(@RequestBody Remedy remedy) throws URISyntaxException {
        log.debug("REST request to save Remedy : {}", remedy);
        if (remedy.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new remedy cannot already have an ID").body(null);
        }
        Remedy result = remedyRepository.save(remedy);
        remedySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/remedys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("remedy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /remedys -> Updates an existing remedy.
     */
    @RequestMapping(value = "/remedys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Remedy> updateRemedy(@RequestBody Remedy remedy) throws URISyntaxException {
        log.debug("REST request to update Remedy : {}", remedy);
        if (remedy.getId() == null) {
            return createRemedy(remedy);
        }
        Remedy result = remedyRepository.save(remedy);
        remedySearchRepository.save(remedy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("remedy", remedy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /remedys -> get all the remedys.
     */
    @RequestMapping(value = "/remedys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Remedy>> getAllRemedys(Pageable pageable)
        throws URISyntaxException {
        Page<Remedy> page = remedyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/remedys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /remedys/:id -> get the "id" remedy.
     */
    @RequestMapping(value = "/remedys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Remedy> getRemedy(@PathVariable Long id) {
        log.debug("REST request to get Remedy : {}", id);
        return Optional.ofNullable(remedyRepository.findOne(id))
            .map(remedy -> new ResponseEntity<>(
                remedy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /remedys/:id -> delete the "id" remedy.
     */
    @RequestMapping(value = "/remedys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRemedy(@PathVariable Long id) {
        log.debug("REST request to delete Remedy : {}", id);
        remedyRepository.delete(id);
        remedySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("remedy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/remedys/:query -> search for the remedy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/remedys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Remedy> searchRemedys(@PathVariable String query) {
        return StreamSupport
            .stream(remedySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
