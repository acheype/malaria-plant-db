package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.Ethnology;
import nc.ird.malariaplantdb.repository.EthnologyRepository;
import nc.ird.malariaplantdb.repository.search.EthnologySearchRepository;
import nc.ird.malariaplantdb.service.PublicationService;
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
 * REST controller for managing Ethnology.
 */
@RestController
@RequestMapping("/api")
public class EthnologyResource {

    private final Logger log = LoggerFactory.getLogger(EthnologyResource.class);

    @Inject
    private EthnologyRepository ethnologyRepository;

    @Inject
    private EthnologySearchRepository ethnologySearchRepository;

    @Inject
    private PublicationService publicationService;

    /**
     * POST  /ethnologies -> Create a new ethnology.
     */
    @RequestMapping(value = "/ethnologies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnology> createEthnology(@Valid @RequestBody Ethnology ethnology) throws URISyntaxException {
        log.debug("REST request to save Ethnology : {}", ethnology);
        if (ethnology.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new ethnology cannot already have an ID").body(null);
        }

        if (ethnology.getRemedy() != null)
            ethnology.getRemedy().getPlantIngredients().stream().forEach(pi -> pi.setRemedy(ethnology.getRemedy()));
        Ethnology result = ethnologyRepository.save(ethnology);
        ethnologySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ethnologies/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("ethnology", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /ethnologies -> Updates an existing ethnology.
     */
    @RequestMapping(value = "/ethnologies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnology> updateEthnology(@Valid @RequestBody Ethnology ethnology) throws URISyntaxException {
        log.debug("REST request to update Ethnology : {}", ethnology);
        if (ethnology.getId() == null) {
            return createEthnology(ethnology);
        }

        if (ethnology.getRemedy() != null)
            ethnology.getRemedy().getPlantIngredients().stream().forEach(pi -> pi.setRemedy(ethnology.getRemedy()));
        Ethnology result = ethnologyRepository.save(ethnology);
        ethnologySearchRepository.save(ethnology);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("ethnology", ethnology.getId().toString()))
                .body(result);
    }

    /**
     * GET  /ethnologies -> get all the ethnologies.
     */
    @RequestMapping(value = "/ethnologies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ethnology>> getAllEthnologies(Pageable pageable)
        throws URISyntaxException {
        Page<Ethnology> page = ethnologyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/ethnologies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ethnologies/:id -> get the "id" ethnology.
     */
    @RequestMapping(value = "/ethnologies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnology> getEthnology(@PathVariable Long id) {
        log.debug("REST request to get Ethnology : {}", id);
        return Optional.ofNullable(ethnologyRepository.findOne(id))
            .map(ethnology -> new ResponseEntity<>(
                ethnology,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /publications/:id/ethnologies -> get all the ethnologies of the "id" publication.
     */
    @RequestMapping(value = "/publications/{id}/ethnologies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ethnology>> getEthnologiesByPublicationId(@PathVariable Long id) {
        log.debug("REST request to get the Ethnologies of the Publication : {}", id);
        List<Ethnology> ethnologies = ethnologyRepository.findByPublicationId(id);
        return new ResponseEntity<>(ethnologies,  HttpStatus.OK);
    }

    /**
     * GET  /publications/:pubId/remedy/{remId}/ethnology -> get the ethnology with the "id" publication and the
     * remedy id
     */
    @RequestMapping(value = "/publications/{pubId}/remedy/{remId}/ethnology",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ethnology> getEthnologyByPubIdAndRemedyId(@PathVariable Long pubId, @PathVariable Long
        remId) {
        log.debug("REST request to get the Ethnology of the Publication : {}, and the Remedy : {}", pubId, remId);

        Ethnology ethnology = ethnologyRepository.findByPublicationIdAndAndRemedyId(pubId, remId);

        return new ResponseEntity<>(ethnology, HttpStatus.OK);
    }

    /**
     * DELETE  /ethnologies/:id -> delete the "id" ethnology and all associated data (species
     * when they are not still referenced, plant ingredients, and remedies when they are not still referenced)
     */
    @RequestMapping(value = "/ethnologies/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEthnology(@PathVariable Long id) {
        log.debug("REST request to delete Ethnology and all associated data: {}", id);

        Ethnology ethnology = ethnologyRepository.findOne(id);

        publicationService.deleteEthnologyAndAssociated(ethnology);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ethnology", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ethnologies/:query -> search for the ethnology corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/ethnologies/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Ethnology> searchEthnologies(@PathVariable String query) {
        return StreamSupport
            .stream(ethnologySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
