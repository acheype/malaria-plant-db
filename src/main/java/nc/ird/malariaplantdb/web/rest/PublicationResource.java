package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.*;
import nc.ird.malariaplantdb.repository.AuthorRepository;
import nc.ird.malariaplantdb.repository.PublicationRepository;
import nc.ird.malariaplantdb.repository.search.AuthorSearchRepository;
import nc.ird.malariaplantdb.repository.search.PublicationSearchRepository;
import nc.ird.malariaplantdb.service.PublicationService;
import nc.ird.malariaplantdb.web.rest.dto.PubSummaryDTO;
import nc.ird.malariaplantdb.web.rest.util.HeaderUtil;
import nc.ird.malariaplantdb.web.rest.util.PaginationUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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
    private PublicationService publicationService;

    @Inject
    private PublicationSearchRepository publicationSearchRepository;

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    private AuthorSearchRepository authorSearchRepo;

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
        Publication result = publicationService.save(publication);
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
        Publication result = publicationService.save(publication);
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
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/publications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pubSummaries -> get all the pubSummaries.
     */
    @RequestMapping(value = "/pubSummaries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @Timed
    public ResponseEntity<List<PubSummaryDTO>> getAllPubSummaries(Pageable pageable)
        throws URISyntaxException {
        Page<Publication> pubPage = publicationRepository.findAll(pageable);
        Page<PubSummaryDTO> page = pubPage.map(pub -> new PubSummaryDTO(pub));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/database");
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
     * GET  /pubSummaries/:id -> get the "id" pubSummaries.
     */
    @RequestMapping(value = "/pubSummaries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @Timed
    public ResponseEntity<PubSummaryDTO> getPubSummary(@PathVariable Long id) {
        log.debug("REST request to get Publication : {}", id);
        return Optional.ofNullable(publicationRepository.findOne(id))
            .map(publication -> new ResponseEntity<>(
                new PubSummaryDTO(publication),
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /publications/:id -> delete the "id" publication and all associated data (authors, pubSpecies, species
     * when they are not still referenced, plant ingredients, remedies, ethnologies notes, in vivo pharmaco notes and in
     * vitro pharmaco notes)
     */
    @RequestMapping(value = "/publications/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        log.debug("REST request to delete Publication and all associated data : {}", id);

        Publication pub = publicationRepository.findOne(id);

        for (Author author : pub.getAuthors()){
            authorRepo.delete(author);
            authorSearchRepo.delete(author);
        }

        for (PubSpecies pubSp : pub.getPubSpecies()){
            publicationService.deletePubSpeciesAndAssociated(pubSp);
        }

        for (Ethnology ethnology : pub.getEthnologies()){
            publicationService.deleteEthnologyAndAssociated(ethnology);
        }

        for (InVivoPharmaco inVivo : pub.getInVivoPharmacos()){
            publicationService.deleteInVivoPharmacoAndAssociated(inVivo);
        }

        for (InVitroPharmaco inVitro : pub.getInVitroPharmacos()){
            publicationService.deleteInVitroPharmacoAndAssociated(inVitro);
        }

        publicationRepository.delete(pub);
        publicationSearchRepository.delete(pub);

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
            .stream(publicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * SEARCH  /_search/:query -> database main search, the publication corresponding
     * to the query.
    */
    @RequestMapping(value = "/_search/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    @Timed
    public ResponseEntity<List<PubSummaryDTO>> searchNestedPublications(@PathVariable String query, Pageable pageable)
        throws URISyntaxException, UnsupportedEncodingException {

        ArrayList<String> operators = new ArrayList<>(Arrays.asList("and", "or", "AND", "OR"));

        List<String> tokenList = new ArrayList<String>();
        // todo fix for the strings with the . inside
        Matcher m = Pattern.compile("([^\"][\\S\\.]*|\".+?\")[\\s\\.]*").matcher(query);
        while (m.find())
            tokenList.add(m.group(1));
        log.debug("tokens :" + String.join(", ", tokenList));

        String queryWithPartialSearch = String.join(" ", tokenList.stream().map(
            p -> operators.contains(p) || p.startsWith("-") || (p.startsWith("\"") && p.endsWith("\"")) ? p : "*" + p + "*"
        ).collect(Collectors.toList()));
        log.debug("query :" + queryWithPartialSearch);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
            QueryBuilders.queryStringQuery(queryWithPartialSearch)
                .lenient(true)
                .field("_all")
        ).withPageable(pageable).build();

        Page<Publication> pubESPage = publicationSearchRepository.search(searchQuery);
        Page<Publication> pubDBPage = pubESPage.map(pub -> publicationRepository.getOne(pub.getId()));
        Page<PubSummaryDTO> resultPage = pubDBPage.map(pub -> new PubSummaryDTO(pub));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(resultPage, "/database");
        return new ResponseEntity<>(resultPage.getContent(), headers, HttpStatus.OK);
    }

}
