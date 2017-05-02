package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import nc.ird.malariaplantdb.repository.InVivoPharmacoRepository;
import nc.ird.malariaplantdb.repository.search.InVivoPharmacoSearchRepository;
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
 * REST controller for managing InVivoPharmaco.
 */
@RestController
@RequestMapping("/api")
public class InVivoPharmacoResource {

    private final Logger log = LoggerFactory.getLogger(InVivoPharmacoResource.class);

    @Inject
    private InVivoPharmacoRepository inVivoPharmacoRepository;

    @Inject
    private InVivoPharmacoSearchRepository inVivoPharmacoSearchRepository;

    /**
     * POST  /inVivoPharmacos -> Create a new inVivoPharmaco.
     */
    @RequestMapping(value = "/inVivoPharmacos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InVivoPharmaco> createInVivoPharmaco(@Valid @RequestBody InVivoPharmaco inVivoPharmaco) throws URISyntaxException {
        log.debug("REST request to save InVivoPharmaco : {}", inVivoPharmaco);
        if (inVivoPharmaco.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inVivoPharmaco cannot already have an ID").body(null);
        }
        InVivoPharmaco result = inVivoPharmacoRepository.save(inVivoPharmaco);
        inVivoPharmacoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inVivoPharmacos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("inVivoPharmaco", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /inVivoPharmacos -> Updates an existing inVivoPharmaco.
     */
    @RequestMapping(value = "/inVivoPharmacos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InVivoPharmaco> updateInVivoPharmaco(@Valid @RequestBody InVivoPharmaco inVivoPharmaco) throws URISyntaxException {
        log.debug("REST request to update InVivoPharmaco : {}", inVivoPharmaco);
        if (inVivoPharmaco.getId() == null) {
            return createInVivoPharmaco(inVivoPharmaco);
        }
        InVivoPharmaco result = inVivoPharmacoRepository.save(inVivoPharmaco);
        inVivoPharmacoSearchRepository.save(inVivoPharmaco);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("inVivoPharmaco", inVivoPharmaco.getId().toString()))
                .body(result);
    }

    /**
     * GET  /inVivoPharmacos -> get all the inVivoPharmacos.
     */
    @RequestMapping(value = "/inVivoPharmacos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InVivoPharmaco>> getAllInVivoPharmacos(Pageable pageable)
        throws URISyntaxException {
        Page<InVivoPharmaco> page = inVivoPharmacoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inVivoPharmacos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inVivoPharmacos/:id -> get the "id" inVivoPharmaco.
     */
    @RequestMapping(value = "/inVivoPharmacos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InVivoPharmaco> getInVivoPharmaco(@PathVariable Long id) {
        log.debug("REST request to get InVivoPharmaco : {}", id);
        return Optional.ofNullable(inVivoPharmacoRepository.findOne(id))
            .map(inVivoPharmaco -> new ResponseEntity<>(
                inVivoPharmaco,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /publications/:id/inVivoPharmacos -> get all the inVivoPharmacos of the "id" publication.
     */
    @RequestMapping(value = "/pub/{id}/inVivoPharmacos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InVivoPharmaco>> getInVivoPharmacosByPublicationId(@PathVariable Long id) {
        log.debug("REST request to get the InVivoPharmacos of the Publication : {}", id);
        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findByPublicationId(id);
        return new ResponseEntity<>(inVivoPharmacos, HttpStatus.OK);
    }

    /**
     * GET  /publications/:pubId/pi/:piIds/inVivoPharmacos -> get all the inVivoPharmacos with the "id" publication and the
     * list of plant ingredient ids
     */
    @RequestMapping(value = "/publications/{pubId}/pi/{piIds}/inVivoPharmacos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InVivoPharmaco>> getInVivoByPubIdAndPiIds(@PathVariable Long pubId, @PathVariable
    List<Long> piIds) {
        log.debug("REST request to get the InVivoPharmaco of the Publication : {}, and the PlantIngredient(s) : {}",
            pubId, piIds.stream().map(id -> id.toString()).collect(Collectors.joining(",")));
        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findByPublicationIdAndPlantIngredients(pubId, piIds);
        return new ResponseEntity<>(inVivoPharmacos,  HttpStatus.OK);
    }

    /**
     * DELETE  /inVivoPharmacos/:id -> delete the "id" inVivoPharmaco.
     */
    @RequestMapping(value = "/inVivoPharmacos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInVivoPharmaco(@PathVariable Long id) {
        log.debug("REST request to delete InVivoPharmaco : {}", id);
        inVivoPharmacoRepository.delete(id);
        inVivoPharmacoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inVivoPharmaco", id.toString())).build();
    }

    /**
     * SEARCH  /_search/inVivoPharmacos/:query -> search for the inVivoPharmaco corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/inVivoPharmacos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InVivoPharmaco> searchInVivoPharmacos(@PathVariable String query) {
        return StreamSupport
            .stream(inVivoPharmacoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
