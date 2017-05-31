package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.Remedy;
import nc.ird.malariaplantdb.repository.RemedyRepository;
import nc.ird.malariaplantdb.repository.search.RemedySearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RemedyResource REST controller.
 *
 * @see RemedyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RemedyResourceTest {


    @Inject
    private RemedyRepository remedyRepository;

    @Inject
    private RemedySearchRepository remedySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRemedyMockMvc;

    private Remedy remedy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RemedyResource remedyResource = new RemedyResource();
        ReflectionTestUtils.setField(remedyResource, "remedyRepository", remedyRepository);
        ReflectionTestUtils.setField(remedyResource, "remedySearchRepository", remedySearchRepository);
        this.restRemedyMockMvc = MockMvcBuilders.standaloneSetup(remedyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        remedy = new Remedy();
    }

    @Test
    @Transactional
    public void createRemedy() throws Exception {
        int databaseSizeBeforeCreate = remedyRepository.findAll().size();

        // Create the Remedy

        restRemedyMockMvc.perform(post("/api/remedys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(remedy)))
                .andExpect(status().isCreated());

        // Validate the Remedy in the database
        List<Remedy> remedys = remedyRepository.findAll();
        assertThat(remedys).hasSize(databaseSizeBeforeCreate + 1);
        Remedy testRemedy = remedys.get(remedys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllRemedys() throws Exception {
        // Initialize the database
        remedyRepository.saveAndFlush(remedy);

        // Get all the remedys
        restRemedyMockMvc.perform(get("/api/remedys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(remedy.getId().intValue())));
    }

    @Test
    @Transactional
    public void getRemedy() throws Exception {
        // Initialize the database
        remedyRepository.saveAndFlush(remedy);

        // Get the remedy
        restRemedyMockMvc.perform(get("/api/remedys/{id}", remedy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(remedy.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRemedy() throws Exception {
        // Get the remedy
        restRemedyMockMvc.perform(get("/api/remedys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemedy() throws Exception {
        // Initialize the database
        remedyRepository.saveAndFlush(remedy);

		int databaseSizeBeforeUpdate = remedyRepository.findAll().size();

        // Update the remedy

        restRemedyMockMvc.perform(put("/api/remedys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(remedy)))
                .andExpect(status().isOk());

        // Validate the Remedy in the database
        List<Remedy> remedys = remedyRepository.findAll();
        assertThat(remedys).hasSize(databaseSizeBeforeUpdate);
        Remedy testRemedy = remedys.get(remedys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteRemedy() throws Exception {
        // Initialize the database
        remedyRepository.saveAndFlush(remedy);

		int databaseSizeBeforeDelete = remedyRepository.findAll().size();

        // Get the remedy
        restRemedyMockMvc.perform(delete("/api/remedys/{id}", remedy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Remedy> remedys = remedyRepository.findAll();
        assertThat(remedys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
