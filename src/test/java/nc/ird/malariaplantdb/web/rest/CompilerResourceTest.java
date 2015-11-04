package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.Compiler;
import nc.ird.malariaplantdb.repository.CompilerRepository;
import nc.ird.malariaplantdb.repository.search.CompilerSearchRepository;

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
 * Test class for the CompilerResource REST controller.
 *
 * @see CompilerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CompilerResourceTest {

    private static final String DEFAULT_FAMILY = "SAMPLE_TEXT";
    private static final String UPDATED_FAMILY = "UPDATED_TEXT";
    private static final String DEFAULT_GIVEN = "SAMPLE_TEXT";
    private static final String UPDATED_GIVEN = "UPDATED_TEXT";
    private static final String DEFAULT_INSTITUTION = "SAMPLE_TEXT";
    private static final String UPDATED_INSTITUTION = "UPDATED_TEXT";
    private static final String DEFAULT_INSTITUTION_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_INSTITUTION_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";

    @Inject
    private CompilerRepository compilerRepository;

    @Inject
    private CompilerSearchRepository compilerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCompilerMockMvc;

    private Compiler compiler;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompilerResource compilerResource = new CompilerResource();
        ReflectionTestUtils.setField(compilerResource, "compilerRepository", compilerRepository);
        ReflectionTestUtils.setField(compilerResource, "compilerSearchRepository", compilerSearchRepository);
        this.restCompilerMockMvc = MockMvcBuilders.standaloneSetup(compilerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        compiler = new Compiler();
        compiler.setFamily(DEFAULT_FAMILY);
        compiler.setGiven(DEFAULT_GIVEN);
        compiler.setInstitution(DEFAULT_INSTITUTION);
        compiler.setInstitutionAddress(DEFAULT_INSTITUTION_ADDRESS);
        compiler.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createCompiler() throws Exception {
        int databaseSizeBeforeCreate = compilerRepository.findAll().size();

        // Create the Compiler

        restCompilerMockMvc.perform(post("/api/compilers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(compiler)))
                .andExpect(status().isCreated());

        // Validate the Compiler in the database
        List<Compiler> compilers = compilerRepository.findAll();
        assertThat(compilers).hasSize(databaseSizeBeforeCreate + 1);
        Compiler testCompiler = compilers.get(compilers.size() - 1);
        assertThat(testCompiler.getFamily()).isEqualTo(DEFAULT_FAMILY);
        assertThat(testCompiler.getGiven()).isEqualTo(DEFAULT_GIVEN);
        assertThat(testCompiler.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testCompiler.getInstitutionAddress()).isEqualTo(DEFAULT_INSTITUTION_ADDRESS);
        assertThat(testCompiler.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void checkFamilyIsRequired() throws Exception {
        int databaseSizeBeforeTest = compilerRepository.findAll().size();
        // set the field null
        compiler.setFamily(null);

        // Create the Compiler, which fails.

        restCompilerMockMvc.perform(post("/api/compilers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(compiler)))
                .andExpect(status().isBadRequest());

        List<Compiler> compilers = compilerRepository.findAll();
        assertThat(compilers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGivenIsRequired() throws Exception {
        int databaseSizeBeforeTest = compilerRepository.findAll().size();
        // set the field null
        compiler.setGiven(null);

        // Create the Compiler, which fails.

        restCompilerMockMvc.perform(post("/api/compilers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(compiler)))
                .andExpect(status().isBadRequest());

        List<Compiler> compilers = compilerRepository.findAll();
        assertThat(compilers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = compilerRepository.findAll().size();
        // set the field null
        compiler.setEmail(null);

        // Create the Compiler, which fails.

        restCompilerMockMvc.perform(post("/api/compilers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(compiler)))
                .andExpect(status().isBadRequest());

        List<Compiler> compilers = compilerRepository.findAll();
        assertThat(compilers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompilers() throws Exception {
        // Initialize the database
        compilerRepository.saveAndFlush(compiler);

        // Get all the compilers
        restCompilerMockMvc.perform(get("/api/compilers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(compiler.getId().intValue())))
                .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY.toString())))
                .andExpect(jsonPath("$.[*].given").value(hasItem(DEFAULT_GIVEN.toString())))
                .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION.toString())))
                .andExpect(jsonPath("$.[*].institutionAddress").value(hasItem(DEFAULT_INSTITUTION_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getCompiler() throws Exception {
        // Initialize the database
        compilerRepository.saveAndFlush(compiler);

        // Get the compiler
        restCompilerMockMvc.perform(get("/api/compilers/{id}", compiler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(compiler.getId().intValue()))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY.toString()))
            .andExpect(jsonPath("$.given").value(DEFAULT_GIVEN.toString()))
            .andExpect(jsonPath("$.institution").value(DEFAULT_INSTITUTION.toString()))
            .andExpect(jsonPath("$.institutionAddress").value(DEFAULT_INSTITUTION_ADDRESS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompiler() throws Exception {
        // Get the compiler
        restCompilerMockMvc.perform(get("/api/compilers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompiler() throws Exception {
        // Initialize the database
        compilerRepository.saveAndFlush(compiler);

		int databaseSizeBeforeUpdate = compilerRepository.findAll().size();

        // Update the compiler
        compiler.setFamily(UPDATED_FAMILY);
        compiler.setGiven(UPDATED_GIVEN);
        compiler.setInstitution(UPDATED_INSTITUTION);
        compiler.setInstitutionAddress(UPDATED_INSTITUTION_ADDRESS);
        compiler.setEmail(UPDATED_EMAIL);
        

        restCompilerMockMvc.perform(put("/api/compilers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(compiler)))
                .andExpect(status().isOk());

        // Validate the Compiler in the database
        List<Compiler> compilers = compilerRepository.findAll();
        assertThat(compilers).hasSize(databaseSizeBeforeUpdate);
        Compiler testCompiler = compilers.get(compilers.size() - 1);
        assertThat(testCompiler.getFamily()).isEqualTo(UPDATED_FAMILY);
        assertThat(testCompiler.getGiven()).isEqualTo(UPDATED_GIVEN);
        assertThat(testCompiler.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testCompiler.getInstitutionAddress()).isEqualTo(UPDATED_INSTITUTION_ADDRESS);
        assertThat(testCompiler.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteCompiler() throws Exception {
        // Initialize the database
        compilerRepository.saveAndFlush(compiler);

		int databaseSizeBeforeDelete = compilerRepository.findAll().size();

        // Get the compiler
        restCompilerMockMvc.perform(delete("/api/compilers/{id}", compiler.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Compiler> compilers = compilerRepository.findAll();
        assertThat(compilers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
