package nc.ird.malariaplantdb.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.repository.PlantIngredientRepository;
import nc.ird.malariaplantdb.repository.search.PlantIngredientSearchRepository;
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
 * REST controller for managing PlantIngredient.
 */
@RestController
@RequestMapping("/api")
public class PlantIngredientResource {

    private final Logger log = LoggerFactory.getLogger(PlantIngredientResource.class);

    @Inject
    private PlantIngredientRepository plantIngredientRepository;

    @Inject
    private PlantIngredientSearchRepository plantIngredientSearchRepository;

    /**
     * POST  /plantIngredients -> Create a new plantIngredient.
     */
    @RequestMapping(value = "/plantIngredients",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlantIngredient> createPlantIngredient(@Valid @RequestBody PlantIngredient plantIngredient) throws URISyntaxException {
        log.debug("REST request to save PlantIngredient : {}", plantIngredient);
        if (plantIngredient.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plantIngredient cannot already have an ID").body(null);
        }
        PlantIngredient result = plantIngredientRepository.save(plantIngredient);
        plantIngredientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/plantIngredients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("plantIngredient", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /plantIngredients -> Updates an existing plantIngredient.
     */
    @RequestMapping(value = "/plantIngredients",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlantIngredient> updatePlantIngredient(@Valid @RequestBody PlantIngredient plantIngredient) throws URISyntaxException {
        log.debug("REST request to update PlantIngredient : {}", plantIngredient);
        if (plantIngredient.getId() == null) {
            return createPlantIngredient(plantIngredient);
        }
        PlantIngredient result = plantIngredientRepository.save(plantIngredient);
        plantIngredientSearchRepository.save(plantIngredient);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("plantIngredient", plantIngredient.getId().toString()))
                .body(result);
    }

    /**
     * GET  /plantIngredients -> get all the plantIngredients.
     */
    @RequestMapping(value = "/plantIngredients",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PlantIngredient>> getAllPlantIngredients(Pageable pageable)
        throws URISyntaxException {
        Page<PlantIngredient> page = plantIngredientRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantIngredients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plantIngredients/:id -> get the "id" plantIngredient.
     */
    @RequestMapping(value = "/plantIngredients/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlantIngredient> getPlantIngredient(@PathVariable Long id) {
        log.debug("REST request to get PlantIngredient : {}", id);
        return Optional.ofNullable(plantIngredientRepository.findOne(id))
            .map(plantIngredient -> new ResponseEntity<>(
                plantIngredient,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /plantIngredients/:id -> delete the "id" plantIngredient.
     */
    @RequestMapping(value = "/plantIngredients/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlantIngredient(@PathVariable Long id) {
        log.debug("REST request to delete PlantIngredient : {}", id);
        plantIngredientRepository.delete(id);
        plantIngredientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plantIngredient", id.toString())).build();
    }

    /**
     * SEARCH  /_search/plantIngredients/:query -> search for the plantIngredient corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/plantIngredients/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PlantIngredient> searchPlantIngredients(@PathVariable String query) {
        return StreamSupport
            .stream(plantIngredientSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
