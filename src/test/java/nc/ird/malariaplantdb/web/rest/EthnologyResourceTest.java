package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.Ethnology;
import nc.ird.malariaplantdb.repository.EthnologyRepository;
import nc.ird.malariaplantdb.repository.search.EthnologySearchRepository;

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
 * Test class for the EthnologyResource REST controller.
 *
 * @see EthnologyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EthnologyResourceTest {

    private static final String DEFAULT_ETHNO_RELEVANCY = "SAMPLE_TEXT";
    private static final String UPDATED_ETHNO_RELEVANCY = "UPDATED_TEXT";
    private static final String DEFAULT_TREATMENT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TREATMENT_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_TRADITIONAL_RECIPE_DETAILS = "SAMPLE_TEXT";
    private static final String UPDATED_TRADITIONAL_RECIPE_DETAILS = "UPDATED_TEXT";
    private static final String DEFAULT_PREPARATION_MODE = "SAMPLE_TEXT";
    private static final String UPDATED_PREPARATION_MODE = "UPDATED_TEXT";
    private static final String DEFAULT_ADMINISTRATION_ROUTE = "SAMPLE_TEXT";
    private static final String UPDATED_ADMINISTRATION_ROUTE = "UPDATED_TEXT";

    @Inject
    private EthnologyRepository ethnologyRepository;

    @Inject
    private EthnologySearchRepository ethnologySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEthnologyMockMvc;

    private Ethnology ethnology;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EthnologyResource ethnologyResource = new EthnologyResource();
        ReflectionTestUtils.setField(ethnologyResource, "ethnologyRepository", ethnologyRepository);
        ReflectionTestUtils.setField(ethnologyResource, "ethnologySearchRepository", ethnologySearchRepository);
        this.restEthnologyMockMvc = MockMvcBuilders.standaloneSetup(ethnologyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ethnology = new Ethnology();
        ethnology.setEthnoRelevancy(DEFAULT_ETHNO_RELEVANCY);
        ethnology.setTreatmentType(DEFAULT_TREATMENT_TYPE);
        ethnology.setTraditionalRecipeDetails(DEFAULT_TRADITIONAL_RECIPE_DETAILS);
        ethnology.setPreparationMode(DEFAULT_PREPARATION_MODE);
        ethnology.setAdministrationRoute(DEFAULT_ADMINISTRATION_ROUTE);
    }

    @Test
    @Transactional
    public void createEthnology() throws Exception {
        int databaseSizeBeforeCreate = ethnologyRepository.findAll().size();

        // Create the Ethnology

        restEthnologyMockMvc.perform(post("/api/ethnologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnology)))
                .andExpect(status().isCreated());

        // Validate the Ethnology in the database
        List<Ethnology> ethnologies = ethnologyRepository.findAll();
        assertThat(ethnologies).hasSize(databaseSizeBeforeCreate + 1);
        Ethnology testEthnology = ethnologies.get(ethnologies.size() - 1);
        assertThat(testEthnology.getEthnoRelevancy()).isEqualTo(DEFAULT_ETHNO_RELEVANCY);
        assertThat(testEthnology.getTreatmentType()).isEqualTo(DEFAULT_TREATMENT_TYPE);
        assertThat(testEthnology.getTraditionalRecipeDetails()).isEqualTo(DEFAULT_TRADITIONAL_RECIPE_DETAILS);
        assertThat(testEthnology.getPreparationMode()).isEqualTo(DEFAULT_PREPARATION_MODE);
        assertThat(testEthnology.getAdministrationRoute()).isEqualTo(DEFAULT_ADMINISTRATION_ROUTE);
    }

    @Test
    @Transactional
    public void checkEthnoRelevancyIsRequired() throws Exception {
        int databaseSizeBeforeTest = ethnologyRepository.findAll().size();
        // set the field null
        ethnology.setEthnoRelevancy(null);

        // Create the Ethnology, which fails.

        restEthnologyMockMvc.perform(post("/api/ethnologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnology)))
                .andExpect(status().isBadRequest());

        List<Ethnology> ethnologies = ethnologyRepository.findAll();
        assertThat(ethnologies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTreatmentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ethnologyRepository.findAll().size();
        // set the field null
        ethnology.setTreatmentType(null);

        // Create the Ethnology, which fails.

        restEthnologyMockMvc.perform(post("/api/ethnologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnology)))
                .andExpect(status().isBadRequest());

        List<Ethnology> ethnologies = ethnologyRepository.findAll();
        assertThat(ethnologies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEthnologies() throws Exception {
        // Initialize the database
        ethnologyRepository.saveAndFlush(ethnology);

        // Get all the ethnologies
        restEthnologyMockMvc.perform(get("/api/ethnologies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ethnology.getId().intValue())))
                .andExpect(jsonPath("$.[*].ethnoRelevancy").value(hasItem(DEFAULT_ETHNO_RELEVANCY.toString())))
                .andExpect(jsonPath("$.[*].treatmentType").value(hasItem(DEFAULT_TREATMENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].traditionalRecipeDetails").value(hasItem(DEFAULT_TRADITIONAL_RECIPE_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].preparationMode").value(hasItem(DEFAULT_PREPARATION_MODE.toString())))
                .andExpect(jsonPath("$.[*].administrationRoute").value(hasItem(DEFAULT_ADMINISTRATION_ROUTE.toString())));
    }

    @Test
    @Transactional
    public void getEthnology() throws Exception {
        // Initialize the database
        ethnologyRepository.saveAndFlush(ethnology);

        // Get the ethnology
        restEthnologyMockMvc.perform(get("/api/ethnologies/{id}", ethnology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ethnology.getId().intValue()))
            .andExpect(jsonPath("$.ethnoRelevancy").value(DEFAULT_ETHNO_RELEVANCY.toString()))
            .andExpect(jsonPath("$.treatmentType").value(DEFAULT_TREATMENT_TYPE.toString()))
            .andExpect(jsonPath("$.traditionalRecipeDetails").value(DEFAULT_TRADITIONAL_RECIPE_DETAILS.toString()))
            .andExpect(jsonPath("$.preparationMode").value(DEFAULT_PREPARATION_MODE.toString()))
            .andExpect(jsonPath("$.administrationRoute").value(DEFAULT_ADMINISTRATION_ROUTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEthnology() throws Exception {
        // Get the ethnology
        restEthnologyMockMvc.perform(get("/api/ethnologies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEthnology() throws Exception {
        // Initialize the database
        ethnologyRepository.saveAndFlush(ethnology);

		int databaseSizeBeforeUpdate = ethnologyRepository.findAll().size();

        // Update the ethnology
        ethnology.setEthnoRelevancy(UPDATED_ETHNO_RELEVANCY);
        ethnology.setTreatmentType(UPDATED_TREATMENT_TYPE);
        ethnology.setTraditionalRecipeDetails(UPDATED_TRADITIONAL_RECIPE_DETAILS);
        ethnology.setPreparationMode(UPDATED_PREPARATION_MODE);
        ethnology.setAdministrationRoute(UPDATED_ADMINISTRATION_ROUTE);


        restEthnologyMockMvc.perform(put("/api/ethnologies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ethnology)))
                .andExpect(status().isOk());

        // Validate the Ethnology in the database
        List<Ethnology> ethnologies = ethnologyRepository.findAll();
        assertThat(ethnologies).hasSize(databaseSizeBeforeUpdate);
        Ethnology testEthnology = ethnologies.get(ethnologies.size() - 1);
        assertThat(testEthnology.getEthnoRelevancy()).isEqualTo(UPDATED_ETHNO_RELEVANCY);
        assertThat(testEthnology.getTreatmentType()).isEqualTo(UPDATED_TREATMENT_TYPE);
        assertThat(testEthnology.getTraditionalRecipeDetails()).isEqualTo(UPDATED_TRADITIONAL_RECIPE_DETAILS);
        assertThat(testEthnology.getPreparationMode()).isEqualTo(UPDATED_PREPARATION_MODE);
        assertThat(testEthnology.getAdministrationRoute()).isEqualTo(UPDATED_ADMINISTRATION_ROUTE);
    }

    @Test
    @Transactional
    public void deleteEthnology() throws Exception {
        // Initialize the database
        ethnologyRepository.saveAndFlush(ethnology);

		int databaseSizeBeforeDelete = ethnologyRepository.findAll().size();

        // Get the ethnology
        restEthnologyMockMvc.perform(delete("/api/ethnologies/{id}", ethnology.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ethnology> ethnologies = ethnologyRepository.findAll();
        assertThat(ethnologies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
