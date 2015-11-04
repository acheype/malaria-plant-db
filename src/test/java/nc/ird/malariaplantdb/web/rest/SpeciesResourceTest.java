package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.Species;
import nc.ird.malariaplantdb.repository.SpeciesRepository;
import nc.ird.malariaplantdb.repository.search.SpeciesSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SpeciesResource REST controller.
 *
 * @see SpeciesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SpeciesResourceTest {

    private static final String DEFAULT_FAMILY = "SAMPLE_TEXT";
    private static final String UPDATED_FAMILY = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIES = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIES = "UPDATED_TEXT";

    @Inject
    private SpeciesRepository speciesRepository;

    @Inject
    private SpeciesSearchRepository speciesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSpeciesMockMvc;

    private Species species;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpeciesResource speciesResource = new SpeciesResource();
        ReflectionTestUtils.setField(speciesResource, "speciesRepository", speciesRepository);
        ReflectionTestUtils.setField(speciesResource, "speciesSearchRepository", speciesSearchRepository);
        this.restSpeciesMockMvc = MockMvcBuilders.standaloneSetup(speciesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        species = new Species();
        species.setFamily(DEFAULT_FAMILY);
        species.setSpecies(DEFAULT_SPECIES);
    }

    @Test
    @Transactional
    public void createSpecies() throws Exception {
        int databaseSizeBeforeCreate = speciesRepository.findAll().size();

        // Create the Species

        restSpeciesMockMvc.perform(post("/api/species")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(species)))
                .andExpect(status().isCreated());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeCreate + 1);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testSpecies.getSpecies()).isEqualTo(DEFAULT_SPECIES);
    }

    @Test
    @Transactional
    public void checkFamilyIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setFamily(null);

        // Create the Species, which fails.

        restSpeciesMockMvc.perform(post("/api/species")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(species)))
                .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setSpecies(null);

        // Create the Species, which fails.

        restSpeciesMockMvc.perform(post("/api/species")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(species)))
                .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get all the species
        restSpeciesMockMvc.perform(get("/api/species"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(species.getId().intValue())))
                .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY.toString())))
                .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES.toString())));
    }

    @Test
    @Transactional
    public void getSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get the species
        restSpeciesMockMvc.perform(get("/api/species/{id}", species.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(species.getId().intValue()))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY.toString()))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecies() throws Exception {
        // Get the species
        restSpeciesMockMvc.perform(get("/api/species/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

		int databaseSizeBeforeUpdate = speciesRepository.findAll().size();

        // Update the species
        species.setFamily(UPDATED_FAMILY);
        species.setSpecies(UPDATED_SPECIES);


        restSpeciesMockMvc.perform(put("/api/species")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(species)))
                .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testSpecies.getSpecies()).isEqualTo(UPDATED_SPECIES);
    }

    @Test
    @Transactional
    public void deleteSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

		int databaseSizeBeforeDelete = speciesRepository.findAll().size();

        // Get the species
        restSpeciesMockMvc.perform(delete("/api/species/{id}", species.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
