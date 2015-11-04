package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.Compiler;
import nc.ird.malariaplantdb.repository.CompilerRepository;
import nc.ird.malariaplantdb.repository.search.CompilerSearchRepository;
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
 * REST controller for managing Compiler.
 */
@RestController
@RequestMapping("/api")
public class CompilerResource {

    private final Logger log = LoggerFactory.getLogger(CompilerResource.class);

    @Inject
    private CompilerRepository compilerRepository;

    @Inject
    private CompilerSearchRepository compilerSearchRepository;

    /**
     * POST  /compilers -> Create a new compiler.
     */
    @RequestMapping(value = "/compilers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Compiler> createCompiler(@Valid @RequestBody Compiler compiler) throws URISyntaxException {
        log.debug("REST request to save Compiler : {}", compiler);
        if (compiler.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new compiler cannot already have an ID").body(null);
        }
        Compiler result = compilerRepository.save(compiler);
        compilerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/compilers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("compiler", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /compilers -> Updates an existing compiler.
     */
    @RequestMapping(value = "/compilers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Compiler> updateCompiler(@Valid @RequestBody Compiler compiler) throws URISyntaxException {
        log.debug("REST request to update Compiler : {}", compiler);
        if (compiler.getId() == null) {
            return createCompiler(compiler);
        }
        Compiler result = compilerRepository.save(compiler);
        compilerSearchRepository.save(compiler);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("compiler", compiler.getId().toString()))
                .body(result);
    }

    /**
     * GET  /compilers -> get all the compilers.
     */
    @RequestMapping(value = "/compilers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Compiler>> getAllCompilers(Pageable pageable)
        throws URISyntaxException {
        Page<Compiler> page = compilerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compilers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compilers/:id -> get the "id" compiler.
     */
    @RequestMapping(value = "/compilers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Compiler> getCompiler(@PathVariable Long id) {
        log.debug("REST request to get Compiler : {}", id);
        return Optional.ofNullable(compilerRepository.findOne(id))
            .map(compiler -> new ResponseEntity<>(
                compiler,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /compilers/:id -> delete the "id" compiler.
     */
    @RequestMapping(value = "/compilers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCompiler(@PathVariable Long id) {
        log.debug("REST request to delete Compiler : {}", id);
        compilerRepository.delete(id);
        compilerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("compiler", id.toString())).build();
    }

    /**
     * SEARCH  /_search/compilers/:query -> search for the compiler corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/compilers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Compiler> searchCompilers(@PathVariable String query) {
        return StreamSupport
            .stream(compilerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
