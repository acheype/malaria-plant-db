package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import nc.ird.malariaplantdb.repository.InVivoPharmacoRepository;
import nc.ird.malariaplantdb.repository.search.InVivoPharmacoSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the InVivoPharmacoResource REST controller.
 *
 * @see InVivoPharmacoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InVivoPharmacoResourceTest {

    private static final String DEFAULT_TESTED_ENTITY = "SAMPLE_TEXT";
    private static final String UPDATED_TESTED_ENTITY = "UPDATED_TEXT";
    private static final String DEFAULT_EXTRACTION_SOLVENT = "SAMPLE_TEXT";
    private static final String UPDATED_EXTRACTION_SOLVENT = "UPDATED_TEXT";
    private static final String DEFAULT_ADDITIVE_PRODUCT = "SAMPLE_TEXT";
    private static final String UPDATED_ADDITIVE_PRODUCT = "UPDATED_TEXT";
    private static final String DEFAULT_COMPOUND_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_COMPOUND_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SCREENING_TEST = "SAMPLE_TEXT";
    private static final String UPDATED_SCREENING_TEST = "UPDATED_TEXT";
    private static final String DEFAULT_TREATMENT_ROUTE = "SAMPLE_TEXT";
    private static final String UPDATED_TREATMENT_ROUTE = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_DOSE = new BigDecimal(0);
    private static final BigDecimal UPDATED_DOSE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_INHIBITION = new BigDecimal(0);
    private static final BigDecimal UPDATED_INHIBITION = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SURVIVAL_PERCENT = new BigDecimal(0);
    private static final BigDecimal UPDATED_SURVIVAL_PERCENT = new BigDecimal(1);

    private static final BigDecimal DEFAULT_SURVIVAL_TIME = new BigDecimal(0);
    private static final BigDecimal UPDATED_SURVIVAL_TIME = new BigDecimal(1);

    private static final BigDecimal DEFAULT_ED50 = new BigDecimal(0);
    private static final BigDecimal UPDATED_ED50 = new BigDecimal(1);

    private static final BigDecimal DEFAULT_LD50 = new BigDecimal(0);
    private static final BigDecimal UPDATED_LD50 = new BigDecimal(1);
    private static final String DEFAULT_COMPILERS_OBSERVATIONS = "SAMPLE_TEXT";
    private static final String UPDATED_COMPILERS_OBSERVATIONS = "UPDATED_TEXT";

    @Inject
    private InVivoPharmacoRepository inVivoPharmacoRepository;

    @Inject
    private InVivoPharmacoSearchRepository inVivoPharmacoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInVivoPharmacoMockMvc;

    private InVivoPharmaco inVivoPharmaco;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InVivoPharmacoResource inVivoPharmacoResource = new InVivoPharmacoResource();
        ReflectionTestUtils.setField(inVivoPharmacoResource, "inVivoPharmacoRepository", inVivoPharmacoRepository);
        ReflectionTestUtils.setField(inVivoPharmacoResource, "inVivoPharmacoSearchRepository", inVivoPharmacoSearchRepository);
        this.restInVivoPharmacoMockMvc = MockMvcBuilders.standaloneSetup(inVivoPharmacoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        inVivoPharmaco = new InVivoPharmaco();
        inVivoPharmaco.setTestedEntity(DEFAULT_TESTED_ENTITY);
        inVivoPharmaco.setExtractionSolvent(DEFAULT_EXTRACTION_SOLVENT);
        inVivoPharmaco.setAdditiveProduct(DEFAULT_ADDITIVE_PRODUCT);
        inVivoPharmaco.setCompoundName(DEFAULT_COMPOUND_NAME);
        inVivoPharmaco.setScreeningTest(DEFAULT_SCREENING_TEST);
        inVivoPharmaco.setTreatmentRoute(DEFAULT_TREATMENT_ROUTE);
        inVivoPharmaco.setDose(DEFAULT_DOSE);
        inVivoPharmaco.setInhibition(DEFAULT_INHIBITION);
        inVivoPharmaco.setSurvivalPercent(DEFAULT_SURVIVAL_PERCENT);
        inVivoPharmaco.setSurvivalTime(DEFAULT_SURVIVAL_TIME);
        inVivoPharmaco.setEd50(DEFAULT_ED50);
        inVivoPharmaco.setLd50(DEFAULT_LD50);
        inVivoPharmaco.setCompilersObservations(DEFAULT_COMPILERS_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void createInVivoPharmaco() throws Exception {
        int databaseSizeBeforeCreate = inVivoPharmacoRepository.findAll().size();

        // Create the InVivoPharmaco

        restInVivoPharmacoMockMvc.perform(post("/api/inVivoPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVivoPharmaco)))
                .andExpect(status().isCreated());

        // Validate the InVivoPharmaco in the database
        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findAll();
        assertThat(inVivoPharmacos).hasSize(databaseSizeBeforeCreate + 1);
        InVivoPharmaco testInVivoPharmaco = inVivoPharmacos.get(inVivoPharmacos.size() - 1);
        assertThat(testInVivoPharmaco.getTestedEntity()).isEqualTo(DEFAULT_TESTED_ENTITY);
        assertThat(testInVivoPharmaco.getExtractionSolvent()).isEqualTo(DEFAULT_EXTRACTION_SOLVENT);
        assertThat(testInVivoPharmaco.getAdditiveProduct()).isEqualTo(DEFAULT_ADDITIVE_PRODUCT);
        assertThat(testInVivoPharmaco.getCompoundName()).isEqualTo(DEFAULT_COMPOUND_NAME);
        assertThat(testInVivoPharmaco.getScreeningTest()).isEqualTo(DEFAULT_SCREENING_TEST);
        assertThat(testInVivoPharmaco.getTreatmentRoute()).isEqualTo(DEFAULT_TREATMENT_ROUTE);
        assertThat(testInVivoPharmaco.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testInVivoPharmaco.getInhibition()).isEqualTo(DEFAULT_INHIBITION);
        assertThat(testInVivoPharmaco.getSurvivalPercent()).isEqualTo(DEFAULT_SURVIVAL_PERCENT);
        assertThat(testInVivoPharmaco.getSurvivalTime()).isEqualTo(DEFAULT_SURVIVAL_TIME);
        assertThat(testInVivoPharmaco.getEd50()).isEqualTo(DEFAULT_ED50);
        assertThat(testInVivoPharmaco.getLd50()).isEqualTo(DEFAULT_LD50);
        assertThat(testInVivoPharmaco.getCompilersObservations()).isEqualTo(DEFAULT_COMPILERS_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void checkTestedEntityIsRequired() throws Exception {
        int databaseSizeBeforeTest = inVivoPharmacoRepository.findAll().size();
        // set the field null
        inVivoPharmaco.setTestedEntity(null);

        // Create the InVivoPharmaco, which fails.

        restInVivoPharmacoMockMvc.perform(post("/api/inVivoPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVivoPharmaco)))
                .andExpect(status().isBadRequest());

        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findAll();
        assertThat(inVivoPharmacos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScreeningTestIsRequired() throws Exception {
        int databaseSizeBeforeTest = inVivoPharmacoRepository.findAll().size();
        // set the field null
        inVivoPharmaco.setScreeningTest(null);

        // Create the InVivoPharmaco, which fails.

        restInVivoPharmacoMockMvc.perform(post("/api/inVivoPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVivoPharmaco)))
                .andExpect(status().isBadRequest());

        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findAll();
        assertThat(inVivoPharmacos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInVivoPharmacos() throws Exception {
        // Initialize the database
        inVivoPharmacoRepository.saveAndFlush(inVivoPharmaco);

        // Get all the inVivoPharmacos
        restInVivoPharmacoMockMvc.perform(get("/api/inVivoPharmacos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(inVivoPharmaco.getId().intValue())))
                .andExpect(jsonPath("$.[*].testedEntity").value(hasItem(DEFAULT_TESTED_ENTITY.toString())))
                .andExpect(jsonPath("$.[*].extractionSolvent").value(hasItem(DEFAULT_EXTRACTION_SOLVENT.toString())))
                .andExpect(jsonPath("$.[*].additiveProduct").value(hasItem(DEFAULT_ADDITIVE_PRODUCT.toString())))
                .andExpect(jsonPath("$.[*].compoundName").value(hasItem(DEFAULT_COMPOUND_NAME.toString())))
                .andExpect(jsonPath("$.[*].screeningTest").value(hasItem(DEFAULT_SCREENING_TEST.toString())))
                .andExpect(jsonPath("$.[*].treatmentRoute").value(hasItem(DEFAULT_TREATMENT_ROUTE.toString())))
                .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE.intValue())))
                .andExpect(jsonPath("$.[*].inhibition").value(hasItem(DEFAULT_INHIBITION.intValue())))
                .andExpect(jsonPath("$.[*].survivalPercent").value(hasItem(DEFAULT_SURVIVAL_PERCENT.intValue())))
                .andExpect(jsonPath("$.[*].survivalTime").value(hasItem(DEFAULT_SURVIVAL_TIME.intValue())))
                .andExpect(jsonPath("$.[*].ed50").value(hasItem(DEFAULT_ED50.intValue())))
                .andExpect(jsonPath("$.[*].ld50").value(hasItem(DEFAULT_LD50.intValue())))
                .andExpect(jsonPath("$.[*].compilersObservations").value(hasItem(DEFAULT_COMPILERS_OBSERVATIONS.toString())));
    }

    @Test
    @Transactional
    public void getInVivoPharmaco() throws Exception {
        // Initialize the database
        inVivoPharmacoRepository.saveAndFlush(inVivoPharmaco);

        // Get the inVivoPharmaco
        restInVivoPharmacoMockMvc.perform(get("/api/inVivoPharmacos/{id}", inVivoPharmaco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inVivoPharmaco.getId().intValue()))
            .andExpect(jsonPath("$.testedEntity").value(DEFAULT_TESTED_ENTITY.toString()))
            .andExpect(jsonPath("$.extractionSolvent").value(DEFAULT_EXTRACTION_SOLVENT.toString()))
            .andExpect(jsonPath("$.additiveProduct").value(DEFAULT_ADDITIVE_PRODUCT.toString()))
            .andExpect(jsonPath("$.compoundName").value(DEFAULT_COMPOUND_NAME.toString()))
            .andExpect(jsonPath("$.screeningTest").value(DEFAULT_SCREENING_TEST.toString()))
            .andExpect(jsonPath("$.treatmentRoute").value(DEFAULT_TREATMENT_ROUTE.toString()))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE.intValue()))
            .andExpect(jsonPath("$.inhibition").value(DEFAULT_INHIBITION.intValue()))
            .andExpect(jsonPath("$.survivalPercent").value(DEFAULT_SURVIVAL_PERCENT.intValue()))
            .andExpect(jsonPath("$.survivalTime").value(DEFAULT_SURVIVAL_TIME.intValue()))
            .andExpect(jsonPath("$.ed50").value(DEFAULT_ED50.intValue()))
            .andExpect(jsonPath("$.ld50").value(DEFAULT_LD50.intValue()))
            .andExpect(jsonPath("$.compilersObservations").value(DEFAULT_COMPILERS_OBSERVATIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInVivoPharmaco() throws Exception {
        // Get the inVivoPharmaco
        restInVivoPharmacoMockMvc.perform(get("/api/inVivoPharmacos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInVivoPharmaco() throws Exception {
        // Initialize the database
        inVivoPharmacoRepository.saveAndFlush(inVivoPharmaco);

		int databaseSizeBeforeUpdate = inVivoPharmacoRepository.findAll().size();

        // Update the inVivoPharmaco
        inVivoPharmaco.setTestedEntity(UPDATED_TESTED_ENTITY);
        inVivoPharmaco.setExtractionSolvent(UPDATED_EXTRACTION_SOLVENT);
        inVivoPharmaco.setAdditiveProduct(UPDATED_ADDITIVE_PRODUCT);
        inVivoPharmaco.setCompoundName(UPDATED_COMPOUND_NAME);
        inVivoPharmaco.setScreeningTest(UPDATED_SCREENING_TEST);
        inVivoPharmaco.setTreatmentRoute(UPDATED_TREATMENT_ROUTE);
        inVivoPharmaco.setDose(UPDATED_DOSE);
        inVivoPharmaco.setInhibition(UPDATED_INHIBITION);
        inVivoPharmaco.setSurvivalPercent(UPDATED_SURVIVAL_PERCENT);
        inVivoPharmaco.setSurvivalTime(UPDATED_SURVIVAL_TIME);
        inVivoPharmaco.setEd50(UPDATED_ED50);
        inVivoPharmaco.setLd50(UPDATED_LD50);
        inVivoPharmaco.setCompilersObservations(UPDATED_COMPILERS_OBSERVATIONS);
        

        restInVivoPharmacoMockMvc.perform(put("/api/inVivoPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVivoPharmaco)))
                .andExpect(status().isOk());

        // Validate the InVivoPharmaco in the database
        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findAll();
        assertThat(inVivoPharmacos).hasSize(databaseSizeBeforeUpdate);
        InVivoPharmaco testInVivoPharmaco = inVivoPharmacos.get(inVivoPharmacos.size() - 1);
        assertThat(testInVivoPharmaco.getTestedEntity()).isEqualTo(UPDATED_TESTED_ENTITY);
        assertThat(testInVivoPharmaco.getExtractionSolvent()).isEqualTo(UPDATED_EXTRACTION_SOLVENT);
        assertThat(testInVivoPharmaco.getAdditiveProduct()).isEqualTo(UPDATED_ADDITIVE_PRODUCT);
        assertThat(testInVivoPharmaco.getCompoundName()).isEqualTo(UPDATED_COMPOUND_NAME);
        assertThat(testInVivoPharmaco.getScreeningTest()).isEqualTo(UPDATED_SCREENING_TEST);
        assertThat(testInVivoPharmaco.getTreatmentRoute()).isEqualTo(UPDATED_TREATMENT_ROUTE);
        assertThat(testInVivoPharmaco.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testInVivoPharmaco.getInhibition()).isEqualTo(UPDATED_INHIBITION);
        assertThat(testInVivoPharmaco.getSurvivalPercent()).isEqualTo(UPDATED_SURVIVAL_PERCENT);
        assertThat(testInVivoPharmaco.getSurvivalTime()).isEqualTo(UPDATED_SURVIVAL_TIME);
        assertThat(testInVivoPharmaco.getEd50()).isEqualTo(UPDATED_ED50);
        assertThat(testInVivoPharmaco.getLd50()).isEqualTo(UPDATED_LD50);
        assertThat(testInVivoPharmaco.getCompilersObservations()).isEqualTo(UPDATED_COMPILERS_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void deleteInVivoPharmaco() throws Exception {
        // Initialize the database
        inVivoPharmacoRepository.saveAndFlush(inVivoPharmaco);

		int databaseSizeBeforeDelete = inVivoPharmacoRepository.findAll().size();

        // Get the inVivoPharmaco
        restInVivoPharmacoMockMvc.perform(delete("/api/inVivoPharmacos/{id}", inVivoPharmaco.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InVivoPharmaco> inVivoPharmacos = inVivoPharmacoRepository.findAll();
        assertThat(inVivoPharmacos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
