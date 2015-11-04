package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import nc.ird.malariaplantdb.repository.InVitroPharmacoRepository;
import nc.ird.malariaplantdb.repository.search.InVitroPharmacoSearchRepository;

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
 * Test class for the InVitroPharmacoResource REST controller.
 *
 * @see InVitroPharmacoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InVitroPharmacoResourceTest {

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
    private static final String DEFAULT_MEASURE_METHOD = "SAMPLE_TEXT";
    private static final String UPDATED_MEASURE_METHOD = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_CONCENTRATION = new BigDecimal(0);
    private static final BigDecimal UPDATED_CONCENTRATION = new BigDecimal(1);

    private static final BigDecimal DEFAULT_MOL_CONCENTRATION = new BigDecimal(0);
    private static final BigDecimal UPDATED_MOL_CONCENTRATION = new BigDecimal(1);

    private static final BigDecimal DEFAULT_INHIBITION = new BigDecimal(0);
    private static final BigDecimal UPDATED_INHIBITION = new BigDecimal(1);

    private static final BigDecimal DEFAULT_IC50 = new BigDecimal(0);
    private static final BigDecimal UPDATED_IC50 = new BigDecimal(1);

    private static final BigDecimal DEFAULT_MOL_IC50 = new BigDecimal(0);
    private static final BigDecimal UPDATED_MOL_IC50 = new BigDecimal(1);

    private static final BigDecimal DEFAULT_TC50 = new BigDecimal(0);
    private static final BigDecimal UPDATED_TC50 = new BigDecimal(1);
    private static final String DEFAULT_COMPILERS_OBSERVATIONS = "SAMPLE_TEXT";
    private static final String UPDATED_COMPILERS_OBSERVATIONS = "UPDATED_TEXT";

    @Inject
    private InVitroPharmacoRepository inVitroPharmacoRepository;

    @Inject
    private InVitroPharmacoSearchRepository inVitroPharmacoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInVitroPharmacoMockMvc;

    private InVitroPharmaco inVitroPharmaco;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InVitroPharmacoResource inVitroPharmacoResource = new InVitroPharmacoResource();
        ReflectionTestUtils.setField(inVitroPharmacoResource, "inVitroPharmacoRepository", inVitroPharmacoRepository);
        ReflectionTestUtils.setField(inVitroPharmacoResource, "inVitroPharmacoSearchRepository", inVitroPharmacoSearchRepository);
        this.restInVitroPharmacoMockMvc = MockMvcBuilders.standaloneSetup(inVitroPharmacoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        inVitroPharmaco = new InVitroPharmaco();
        inVitroPharmaco.setTestedEntity(DEFAULT_TESTED_ENTITY);
        inVitroPharmaco.setExtractionSolvent(DEFAULT_EXTRACTION_SOLVENT);
        inVitroPharmaco.setAdditiveProduct(DEFAULT_ADDITIVE_PRODUCT);
        inVitroPharmaco.setCompoundName(DEFAULT_COMPOUND_NAME);
        inVitroPharmaco.setScreeningTest(DEFAULT_SCREENING_TEST);
        inVitroPharmaco.setMeasureMethod(DEFAULT_MEASURE_METHOD);
        inVitroPharmaco.setConcentration(DEFAULT_CONCENTRATION);
        inVitroPharmaco.setMolConcentration(DEFAULT_MOL_CONCENTRATION);
        inVitroPharmaco.setInhibition(DEFAULT_INHIBITION);
        inVitroPharmaco.setIc50(DEFAULT_IC50);
        inVitroPharmaco.setMolIc50(DEFAULT_MOL_IC50);
        inVitroPharmaco.setSelectivityIndex(DEFAULT_TC50);
        inVitroPharmaco.setCompilersObservations(DEFAULT_COMPILERS_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void createInVitroPharmaco() throws Exception {
        int databaseSizeBeforeCreate = inVitroPharmacoRepository.findAll().size();

        // Create the InVitroPharmaco

        restInVitroPharmacoMockMvc.perform(post("/api/inVitroPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVitroPharmaco)))
                .andExpect(status().isCreated());

        // Validate the InVitroPharmaco in the database
        List<InVitroPharmaco> inVitroPharmacos = inVitroPharmacoRepository.findAll();
        assertThat(inVitroPharmacos).hasSize(databaseSizeBeforeCreate + 1);
        InVitroPharmaco testInVitroPharmaco = inVitroPharmacos.get(inVitroPharmacos.size() - 1);
        assertThat(testInVitroPharmaco.getTestedEntity()).isEqualTo(DEFAULT_TESTED_ENTITY);
        assertThat(testInVitroPharmaco.getExtractionSolvent()).isEqualTo(DEFAULT_EXTRACTION_SOLVENT);
        assertThat(testInVitroPharmaco.getAdditiveProduct()).isEqualTo(DEFAULT_ADDITIVE_PRODUCT);
        assertThat(testInVitroPharmaco.getCompoundName()).isEqualTo(DEFAULT_COMPOUND_NAME);
        assertThat(testInVitroPharmaco.getScreeningTest()).isEqualTo(DEFAULT_SCREENING_TEST);
        assertThat(testInVitroPharmaco.getMeasureMethod()).isEqualTo(DEFAULT_MEASURE_METHOD);
        assertThat(testInVitroPharmaco.getConcentration()).isEqualTo(DEFAULT_CONCENTRATION);
        assertThat(testInVitroPharmaco.getMolConcentration()).isEqualTo(DEFAULT_MOL_CONCENTRATION);
        assertThat(testInVitroPharmaco.getInhibition()).isEqualTo(DEFAULT_INHIBITION);
        assertThat(testInVitroPharmaco.getIc50()).isEqualTo(DEFAULT_IC50);
        assertThat(testInVitroPharmaco.getMolIc50()).isEqualTo(DEFAULT_MOL_IC50);
        assertThat(testInVitroPharmaco.getSelectivityIndex()).isEqualTo(DEFAULT_TC50);
        assertThat(testInVitroPharmaco.getCompilersObservations()).isEqualTo(DEFAULT_COMPILERS_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void checkTestedEntityIsRequired() throws Exception {
        int databaseSizeBeforeTest = inVitroPharmacoRepository.findAll().size();
        // set the field null
        inVitroPharmaco.setTestedEntity(null);

        // Create the InVitroPharmaco, which fails.

        restInVitroPharmacoMockMvc.perform(post("/api/inVitroPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVitroPharmaco)))
                .andExpect(status().isBadRequest());

        List<InVitroPharmaco> inVitroPharmacos = inVitroPharmacoRepository.findAll();
        assertThat(inVitroPharmacos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScreeningTestIsRequired() throws Exception {
        int databaseSizeBeforeTest = inVitroPharmacoRepository.findAll().size();
        // set the field null
        inVitroPharmaco.setScreeningTest(null);

        // Create the InVitroPharmaco, which fails.

        restInVitroPharmacoMockMvc.perform(post("/api/inVitroPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVitroPharmaco)))
                .andExpect(status().isBadRequest());

        List<InVitroPharmaco> inVitroPharmacos = inVitroPharmacoRepository.findAll();
        assertThat(inVitroPharmacos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInVitroPharmacos() throws Exception {
        // Initialize the database
        inVitroPharmacoRepository.saveAndFlush(inVitroPharmaco);

        // Get all the inVitroPharmacos
        restInVitroPharmacoMockMvc.perform(get("/api/inVitroPharmacos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(inVitroPharmaco.getId().intValue())))
                .andExpect(jsonPath("$.[*].testedEntity").value(hasItem(DEFAULT_TESTED_ENTITY.toString())))
                .andExpect(jsonPath("$.[*].extractionSolvent").value(hasItem(DEFAULT_EXTRACTION_SOLVENT.toString())))
                .andExpect(jsonPath("$.[*].additiveProduct").value(hasItem(DEFAULT_ADDITIVE_PRODUCT.toString())))
                .andExpect(jsonPath("$.[*].compoundName").value(hasItem(DEFAULT_COMPOUND_NAME.toString())))
                .andExpect(jsonPath("$.[*].screeningTest").value(hasItem(DEFAULT_SCREENING_TEST.toString())))
                .andExpect(jsonPath("$.[*].measureMethod").value(hasItem(DEFAULT_MEASURE_METHOD.toString())))
                .andExpect(jsonPath("$.[*].concentration").value(hasItem(DEFAULT_CONCENTRATION.intValue())))
                .andExpect(jsonPath("$.[*].molConcentration").value(hasItem(DEFAULT_MOL_CONCENTRATION.intValue())))
                .andExpect(jsonPath("$.[*].inhibition").value(hasItem(DEFAULT_INHIBITION.intValue())))
                .andExpect(jsonPath("$.[*].ic50").value(hasItem(DEFAULT_IC50.intValue())))
                .andExpect(jsonPath("$.[*].molIc50").value(hasItem(DEFAULT_MOL_IC50.intValue())))
                .andExpect(jsonPath("$.[*].selectivityIndex").value(hasItem(DEFAULT_TC50.intValue())))
                .andExpect(jsonPath("$.[*].compilersObservations").value(hasItem(DEFAULT_COMPILERS_OBSERVATIONS.toString())));
    }

    @Test
    @Transactional
    public void getInVitroPharmaco() throws Exception {
        // Initialize the database
        inVitroPharmacoRepository.saveAndFlush(inVitroPharmaco);

        // Get the inVitroPharmaco
        restInVitroPharmacoMockMvc.perform(get("/api/inVitroPharmacos/{id}", inVitroPharmaco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(inVitroPharmaco.getId().intValue()))
            .andExpect(jsonPath("$.testedEntity").value(DEFAULT_TESTED_ENTITY.toString()))
            .andExpect(jsonPath("$.extractionSolvent").value(DEFAULT_EXTRACTION_SOLVENT.toString()))
            .andExpect(jsonPath("$.additiveProduct").value(DEFAULT_ADDITIVE_PRODUCT.toString()))
            .andExpect(jsonPath("$.compoundName").value(DEFAULT_COMPOUND_NAME.toString()))
            .andExpect(jsonPath("$.screeningTest").value(DEFAULT_SCREENING_TEST.toString()))
            .andExpect(jsonPath("$.measureMethod").value(DEFAULT_MEASURE_METHOD.toString()))
            .andExpect(jsonPath("$.concentration").value(DEFAULT_CONCENTRATION.intValue()))
            .andExpect(jsonPath("$.molConcentration").value(DEFAULT_MOL_CONCENTRATION.intValue()))
            .andExpect(jsonPath("$.inhibition").value(DEFAULT_INHIBITION.intValue()))
            .andExpect(jsonPath("$.ic50").value(DEFAULT_IC50.intValue()))
            .andExpect(jsonPath("$.molIc50").value(DEFAULT_MOL_IC50.intValue()))
            .andExpect(jsonPath("$.selectivityIndex").value(DEFAULT_TC50.intValue()))
            .andExpect(jsonPath("$.compilersObservations").value(DEFAULT_COMPILERS_OBSERVATIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInVitroPharmaco() throws Exception {
        // Get the inVitroPharmaco
        restInVitroPharmacoMockMvc.perform(get("/api/inVitroPharmacos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInVitroPharmaco() throws Exception {
        // Initialize the database
        inVitroPharmacoRepository.saveAndFlush(inVitroPharmaco);

		int databaseSizeBeforeUpdate = inVitroPharmacoRepository.findAll().size();

        // Update the inVitroPharmaco
        inVitroPharmaco.setTestedEntity(UPDATED_TESTED_ENTITY);
        inVitroPharmaco.setExtractionSolvent(UPDATED_EXTRACTION_SOLVENT);
        inVitroPharmaco.setAdditiveProduct(UPDATED_ADDITIVE_PRODUCT);
        inVitroPharmaco.setCompoundName(UPDATED_COMPOUND_NAME);
        inVitroPharmaco.setScreeningTest(UPDATED_SCREENING_TEST);
        inVitroPharmaco.setMeasureMethod(UPDATED_MEASURE_METHOD);
        inVitroPharmaco.setConcentration(UPDATED_CONCENTRATION);
        inVitroPharmaco.setMolConcentration(UPDATED_MOL_CONCENTRATION);
        inVitroPharmaco.setInhibition(UPDATED_INHIBITION);
        inVitroPharmaco.setIc50(UPDATED_IC50);
        inVitroPharmaco.setMolIc50(UPDATED_MOL_IC50);
        inVitroPharmaco.setSelectivityIndex(UPDATED_TC50);
        inVitroPharmaco.setCompilersObservations(UPDATED_COMPILERS_OBSERVATIONS);


        restInVitroPharmacoMockMvc.perform(put("/api/inVitroPharmacos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(inVitroPharmaco)))
                .andExpect(status().isOk());

        // Validate the InVitroPharmaco in the database
        List<InVitroPharmaco> inVitroPharmacos = inVitroPharmacoRepository.findAll();
        assertThat(inVitroPharmacos).hasSize(databaseSizeBeforeUpdate);
        InVitroPharmaco testInVitroPharmaco = inVitroPharmacos.get(inVitroPharmacos.size() - 1);
        assertThat(testInVitroPharmaco.getTestedEntity()).isEqualTo(UPDATED_TESTED_ENTITY);
        assertThat(testInVitroPharmaco.getExtractionSolvent()).isEqualTo(UPDATED_EXTRACTION_SOLVENT);
        assertThat(testInVitroPharmaco.getAdditiveProduct()).isEqualTo(UPDATED_ADDITIVE_PRODUCT);
        assertThat(testInVitroPharmaco.getCompoundName()).isEqualTo(UPDATED_COMPOUND_NAME);
        assertThat(testInVitroPharmaco.getScreeningTest()).isEqualTo(UPDATED_SCREENING_TEST);
        assertThat(testInVitroPharmaco.getMeasureMethod()).isEqualTo(UPDATED_MEASURE_METHOD);
        assertThat(testInVitroPharmaco.getConcentration()).isEqualTo(UPDATED_CONCENTRATION);
        assertThat(testInVitroPharmaco.getMolConcentration()).isEqualTo(UPDATED_MOL_CONCENTRATION);
        assertThat(testInVitroPharmaco.getInhibition()).isEqualTo(UPDATED_INHIBITION);
        assertThat(testInVitroPharmaco.getIc50()).isEqualTo(UPDATED_IC50);
        assertThat(testInVitroPharmaco.getMolIc50()).isEqualTo(UPDATED_MOL_IC50);
        assertThat(testInVitroPharmaco.getSelectivityIndex()).isEqualTo(UPDATED_TC50);
        assertThat(testInVitroPharmaco.getCompilersObservations()).isEqualTo(UPDATED_COMPILERS_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void deleteInVitroPharmaco() throws Exception {
        // Initialize the database
        inVitroPharmacoRepository.saveAndFlush(inVitroPharmaco);

		int databaseSizeBeforeDelete = inVitroPharmacoRepository.findAll().size();

        // Get the inVitroPharmaco
        restInVitroPharmacoMockMvc.perform(delete("/api/inVitroPharmacos/{id}", inVitroPharmaco.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InVitroPharmaco> inVitroPharmacos = inVitroPharmacoRepository.findAll();
        assertThat(inVitroPharmacos).hasSize(databaseSizeBeforeDelete - 1);
    }
}