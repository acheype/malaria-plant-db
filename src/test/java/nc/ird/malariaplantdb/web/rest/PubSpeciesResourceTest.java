package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.PubSpecies;
import nc.ird.malariaplantdb.repository.PubSpeciesRepository;
import nc.ird.malariaplantdb.repository.search.PubSpeciesSearchRepository;

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
 * Test class for the PubSpeciesResource REST controller.
 *
 * @see PubSpeciesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PubSpeciesResourceTest {

    private static final String DEFAULT_SPECIES_NAME_IN_PUB = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIES_NAME_IN_PUB = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_HERBARIUM_VOUCHER = false;
    private static final Boolean UPDATED_IS_HERBARIUM_VOUCHER = true;
    private static final String DEFAULT_HERBARIUM = "SAMPLE_TEXT";
    private static final String UPDATED_HERBARIUM = "UPDATED_TEXT";
    private static final String DEFAULT_COUNTRY = "SAMPLE_TEXT";
    private static final String UPDATED_COUNTRY = "UPDATED_TEXT";
    private static final String DEFAULT_CONTINENT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTINENT = "UPDATED_TEXT";

    @Inject
    private PubSpeciesRepository pubSpeciesRepository;

    @Inject
    private PubSpeciesSearchRepository pubSpeciesSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPubSpeciesMockMvc;

    private PubSpecies pubSpecies;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PubSpeciesResource pubSpeciesResource = new PubSpeciesResource();
        ReflectionTestUtils.setField(pubSpeciesResource, "pubSpeciesRepository", pubSpeciesRepository);
        ReflectionTestUtils.setField(pubSpeciesResource, "pubSpeciesSearchRepository", pubSpeciesSearchRepository);
        this.restPubSpeciesMockMvc = MockMvcBuilders.standaloneSetup(pubSpeciesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pubSpecies = new PubSpecies();
        pubSpecies.setSpeciesNameInPub(DEFAULT_SPECIES_NAME_IN_PUB);
        pubSpecies.setIsHerbariumVoucher(DEFAULT_IS_HERBARIUM_VOUCHER);
        pubSpecies.setHerbarium(DEFAULT_HERBARIUM);
        pubSpecies.setCountry(DEFAULT_COUNTRY);
        pubSpecies.setContinent(DEFAULT_CONTINENT);
    }

    @Test
    @Transactional
    public void createPubSpecies() throws Exception {
        int databaseSizeBeforeCreate = pubSpeciesRepository.findAll().size();

        // Create the PubSpecies

        restPubSpeciesMockMvc.perform(post("/api/pubSpecies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pubSpecies)))
                .andExpect(status().isCreated());

        // Validate the PubSpecies in the database
        List<PubSpecies> pubSpeciesList = pubSpeciesRepository.findAll();
        assertThat(pubSpeciesList).hasSize(databaseSizeBeforeCreate + 1);
        PubSpecies testPubSpecies = pubSpeciesList.get(pubSpeciesList.size() - 1);
        assertThat(testPubSpecies.getSpeciesNameInPub()).isEqualTo(DEFAULT_SPECIES_NAME_IN_PUB);
        assertThat(testPubSpecies.getIsHerbariumVoucher()).isEqualTo(DEFAULT_IS_HERBARIUM_VOUCHER);
        assertThat(testPubSpecies.getHerbarium()).isEqualTo(DEFAULT_HERBARIUM);
        assertThat(testPubSpecies.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPubSpecies.getContinent()).isEqualTo(DEFAULT_CONTINENT);
    }

    @Test
    @Transactional
    public void checkSpeciesNameInPubIsRequired() throws Exception {
        int databaseSizeBeforeTest = pubSpeciesRepository.findAll().size();
        // set the field null
        pubSpecies.setSpeciesNameInPub(null);

        // Create the PubSpecies, which fails.

        restPubSpeciesMockMvc.perform(post("/api/pubSpecies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pubSpecies)))
                .andExpect(status().isBadRequest());

        List<PubSpecies> pubSpeciesList = pubSpeciesRepository.findAll();
        assertThat(pubSpeciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pubSpeciesRepository.findAll().size();
        // set the field null
        pubSpecies.setCountry(null);

        // Create the PubSpecies, which fails.

        restPubSpeciesMockMvc.perform(post("/api/pubSpecies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pubSpecies)))
                .andExpect(status().isBadRequest());

        List<PubSpecies> pubSpeciesList = pubSpeciesRepository.findAll();
        assertThat(pubSpeciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPubSpecies() throws Exception {
        // Initialize the database
        pubSpeciesRepository.saveAndFlush(pubSpecies);

        // Get all the pubSpecies
        restPubSpeciesMockMvc.perform(get("/api/pubSpecies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pubSpecies.getId().intValue())))
                .andExpect(jsonPath("$.[*].speciesNameInPub").value(hasItem(DEFAULT_SPECIES_NAME_IN_PUB.toString())))
                .andExpect(jsonPath("$.[*].isHerbariumVoucher").value(hasItem(DEFAULT_IS_HERBARIUM_VOUCHER.booleanValue())))
                .andExpect(jsonPath("$.[*].herbarium").value(hasItem(DEFAULT_HERBARIUM.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].continent").value(hasItem(DEFAULT_CONTINENT.toString())));
    }

    @Test
    @Transactional
    public void getPubSpecies() throws Exception {
        // Initialize the database
        pubSpeciesRepository.saveAndFlush(pubSpecies);

        // Get the pubSpecies
        restPubSpeciesMockMvc.perform(get("/api/pubSpecies/{id}", pubSpecies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pubSpecies.getId().intValue()))
            .andExpect(jsonPath("$.speciesNameInPub").value(DEFAULT_SPECIES_NAME_IN_PUB.toString()))
            .andExpect(jsonPath("$.isHerbariumVoucher").value(DEFAULT_IS_HERBARIUM_VOUCHER.booleanValue()))
            .andExpect(jsonPath("$.herbarium").value(DEFAULT_HERBARIUM.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.continent").value(DEFAULT_CONTINENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPubSpecies() throws Exception {
        // Get the pubSpecies
        restPubSpeciesMockMvc.perform(get("/api/pubSpecies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePubSpecies() throws Exception {
        // Initialize the database
        pubSpeciesRepository.saveAndFlush(pubSpecies);

		int databaseSizeBeforeUpdate = pubSpeciesRepository.findAll().size();

        // Update the pubSpecies
        pubSpecies.setSpeciesNameInPub(UPDATED_SPECIES_NAME_IN_PUB);
        pubSpecies.setIsHerbariumVoucher(UPDATED_IS_HERBARIUM_VOUCHER);
        pubSpecies.setHerbarium(UPDATED_HERBARIUM);
        pubSpecies.setCountry(UPDATED_COUNTRY);
        pubSpecies.setContinent(UPDATED_CONTINENT);


        restPubSpeciesMockMvc.perform(put("/api/pubSpecies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pubSpecies)))
                .andExpect(status().isOk());

        // Validate the PubSpecies in the database
        List<PubSpecies> pubSpeciesList = pubSpeciesRepository.findAll();
        assertThat(pubSpeciesList).hasSize(databaseSizeBeforeUpdate);
        PubSpecies testPubSpecies = pubSpeciesList.get(pubSpeciesList.size() - 1);
        assertThat(testPubSpecies.getSpeciesNameInPub()).isEqualTo(UPDATED_SPECIES_NAME_IN_PUB);
        assertThat(testPubSpecies.getIsHerbariumVoucher()).isEqualTo(UPDATED_IS_HERBARIUM_VOUCHER);
        assertThat(testPubSpecies.getHerbarium()).isEqualTo(UPDATED_HERBARIUM);
        assertThat(testPubSpecies.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPubSpecies.getContinent()).isEqualTo(UPDATED_CONTINENT);
    }

    @Test
    @Transactional
    public void deletePubSpecies() throws Exception {
        // Initialize the database
        pubSpeciesRepository.saveAndFlush(pubSpecies);

		int databaseSizeBeforeDelete = pubSpeciesRepository.findAll().size();

        // Get the pubSpecies
        restPubSpeciesMockMvc.perform(delete("/api/pubSpecies/{id}", pubSpecies.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PubSpecies> pubSpeciesList = pubSpeciesRepository.findAll();
        assertThat(pubSpeciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
