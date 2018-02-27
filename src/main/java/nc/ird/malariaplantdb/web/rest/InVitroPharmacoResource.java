package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import nc.ird.malariaplantdb.repository.InVitroPharmacoRepository;
import nc.ird.malariaplantdb.repository.search.InVitroPharmacoSearchRepository;
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
 * REST controller for managing InVitroPharmaco.
 */
@RestController
@RequestMapping("/api")
public class InVitroPharmacoResource {

    private final Logger log = LoggerFactory.getLogger(InVitroPharmacoResource.class);

    @Inject
    private InVitroPharmacoRepository inVitroPharmacoRepository;

    @Inject
    private InVitroPharmacoSearchRepository inVitroPharmacoSearchRepository;

    @Inject
    private PublicationService publicationService;

    /**
     * POST  /inVitroPharmacos -> Create a new inVitroPharmaco.
     */
    @RequestMapping(value = "/inVitroPharmacos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InVitroPharmaco> createInVitroPharmaco(@Valid @RequestBody InVitroPharmaco inVitroPharmaco) throws URISyntaxException {
        log.debug("REST request to save InVitroPharmaco : {}", inVitroPharmaco);
        if (inVitroPharmaco.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new inVitroPharmaco cannot already have an ID").body(null);
        }
        if (inVitroPharmaco.getRemedy() != null)
            inVitroPharmaco.getRemedy().getPlantIngredients().stream().forEach(pi -> pi.setRemedy(inVitroPharmaco.getRemedy()));
        InVitroPharmaco result = inVitroPharmacoRepository.save(inVitroPharmaco);
        inVitroPharmacoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inVitroPharmacos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("inVitroPharmaco", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /inVitroPharmacos -> Updates an existing inVitroPharmaco.
     */
    @RequestMapping(value = "/inVitroPharmacos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InVitroPharmaco> updateInVitroPharmaco(@Valid @RequestBody InVitroPharmaco inVitroPharmaco) throws URISyntaxException {
        log.debug("REST request to update InVitroPharmaco : {}", inVitroPharmaco);
        if (inVitroPharmaco.getId() == null) {
            return createInVitroPharmaco(inVitroPharmaco);
        }
        if (inVitroPharmaco.getRemedy() != null)
            inVitroPharmaco.getRemedy().getPlantIngredients().stream().forEach(pi -> pi.setRemedy(inVitroPharmaco.getRemedy()));
        InVitroPharmaco result = inVitroPharmacoRepository.save(inVitroPharmaco);
        inVitroPharmacoSearchRepository.save(inVitroPharmaco);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("inVitroPharmaco", inVitroPharmaco.getId().toString()))
                .body(result);
    }

    /**
     * GET  /inVitroPharmacos -> get all the inVitroPharmacos.
     */
    @RequestMapping(value = "/inVitroPharmacos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InVitroPharmaco>> getAllInVitroPharmacos(Pageable pageable)
        throws URISyntaxException {
        Page<InVitroPharmaco> page = inVitroPharmacoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/inVitroPharmacos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inVitroPharmacos/:id -> get the "id" inVitroPharmaco.
     */
    @RequestMapping(value = "/inVitroPharmacos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InVitroPharmaco> getInVitroPharmaco(@PathVariable Long id) {
        log.debug("REST request to get InVitroPharmaco : {}", id);
        return Optional.ofNullable(inVitroPharmacoRepository.findOne(id))
            .map(inVitroPharmaco -> new ResponseEntity<>(
                inVitroPharmaco,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /publications/:id/invitropharmacos -> get all the inVitroPharmacos of the "id" publication.
     */
    @RequestMapping(value = "/publ/{id}/invitropharmacos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InVitroPharmaco>> getInVitroPharmacosByPublicationId(@PathVariable Long id) {
        log.debug("REST request to get the inVitroPharmacos of the Publication : {}", id);
        List<InVitroPharmaco> invitropharmacos = inVitroPharmacoRepository.findByPublicationId(id);
        return new ResponseEntity<>(invitropharmacos, HttpStatus.OK);
    }

    /**
     * GET  /publications/:pubId/remedy/:remId/inVitroPharmacos -> get all the inVitroPharmacos with the "id"
     * publication and the remedy id
     */
    @RequestMapping(value = "/publications/{pubId}/remedy/{remId}/inVitroPharmacos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InVitroPharmaco>> getInVitroPharmacosByPubIdAndRemedyId(@PathVariable Long pubId,
                                                                                       @PathVariable Long remId) {
        log.debug("REST request to get the InVitroPharmaco of the Publication : {}, and the Remedy : {}", pubId, remId);
        List<InVitroPharmaco> inVitroPharmacos = inVitroPharmacoRepository.findByPublicationIdAndRemedyId(pubId, remId);
        return new ResponseEntity<>(inVitroPharmacos,  HttpStatus.OK);
    }

    /**
     * DELETE  /inVitroPharmacos/:id -> delete the "id" inVitroPharmaco and all associated data (species when they are
     * not still referenced, plant ingredients, and remedies when they are not still referenced)
     */
    @RequestMapping(value = "/inVitroPharmacos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInVitroPharmaco(@PathVariable Long id) {
        log.debug("REST request to delete InVitroPharmaco and all associated data: {}", id);

        InVitroPharmaco inVitroPharmaco = inVitroPharmacoRepository.findOne(id);

        publicationService.deleteInVitroPharmacoAndAssociated(inVitroPharmaco);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inVitroPharmaco", id.toString())).build();
    }

    /**
     * SEARCH  /_search/inVitroPharmacos/:query -> search for the inVitroPharmaco corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/inVitroPharmacos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InVitroPharmaco> searchInVitroPharmacos(@PathVariable String query) {
        return StreamSupport
            .stream(inVitroPharmacoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
