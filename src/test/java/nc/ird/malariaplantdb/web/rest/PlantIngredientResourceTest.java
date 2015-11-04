package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.repository.PlantIngredientRepository;
import nc.ird.malariaplantdb.repository.search.PlantIngredientSearchRepository;

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
 * Test class for the PlantIngredientResource REST controller.
 *
 * @see PlantIngredientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlantIngredientResourceTest {

    private static final String DEFAULT_PART_USED = "SAMPLE_TEXT";
    private static final String UPDATED_PART_USED = "UPDATED_TEXT";

    @Inject
    private PlantIngredientRepository plantIngredientRepository;

    @Inject
    private PlantIngredientSearchRepository plantIngredientSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlantIngredientMockMvc;

    private PlantIngredient plantIngredient;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlantIngredientResource plantIngredientResource = new PlantIngredientResource();
        ReflectionTestUtils.setField(plantIngredientResource, "plantIngredientRepository", plantIngredientRepository);
        ReflectionTestUtils.setField(plantIngredientResource, "plantIngredientSearchRepository", plantIngredientSearchRepository);
        this.restPlantIngredientMockMvc = MockMvcBuilders.standaloneSetup(plantIngredientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        plantIngredient = new PlantIngredient();
        plantIngredient.setPartUsed(DEFAULT_PART_USED);
    }

    @Test
    @Transactional
    public void createPlantIngredient() throws Exception {
        int databaseSizeBeforeCreate = plantIngredientRepository.findAll().size();

        // Create the PlantIngredient

        restPlantIngredientMockMvc.perform(post("/api/plantIngredients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantIngredient)))
                .andExpect(status().isCreated());

        // Validate the PlantIngredient in the database
        List<PlantIngredient> plantIngredients = plantIngredientRepository.findAll();
        assertThat(plantIngredients).hasSize(databaseSizeBeforeCreate + 1);
        PlantIngredient testPlantIngredient = plantIngredients.get(plantIngredients.size() - 1);
        assertThat(testPlantIngredient.getPartUsed()).isEqualTo(DEFAULT_PART_USED);
    }

    @Test
    @Transactional
    public void checkPartUsedIsRequired() throws Exception {
        int databaseSizeBeforeTest = plantIngredientRepository.findAll().size();
        // set the field null
        plantIngredient.setPartUsed(null);

        // Create the PlantIngredient, which fails.

        restPlantIngredientMockMvc.perform(post("/api/plantIngredients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantIngredient)))
                .andExpect(status().isBadRequest());

        List<PlantIngredient> plantIngredients = plantIngredientRepository.findAll();
        assertThat(plantIngredients).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlantIngredients() throws Exception {
        // Initialize the database
        plantIngredientRepository.saveAndFlush(plantIngredient);

        // Get all the plantIngredients
        restPlantIngredientMockMvc.perform(get("/api/plantIngredients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plantIngredient.getId().intValue())))
                .andExpect(jsonPath("$.[*].partUsed").value(hasItem(DEFAULT_PART_USED.toString())));
    }

    @Test
    @Transactional
    public void getPlantIngredient() throws Exception {
        // Initialize the database
        plantIngredientRepository.saveAndFlush(plantIngredient);

        // Get the plantIngredient
        restPlantIngredientMockMvc.perform(get("/api/plantIngredients/{id}", plantIngredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plantIngredient.getId().intValue()))
            .andExpect(jsonPath("$.partUsed").value(DEFAULT_PART_USED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlantIngredient() throws Exception {
        // Get the plantIngredient
        restPlantIngredientMockMvc.perform(get("/api/plantIngredients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantIngredient() throws Exception {
        // Initialize the database
        plantIngredientRepository.saveAndFlush(plantIngredient);

		int databaseSizeBeforeUpdate = plantIngredientRepository.findAll().size();

        // Update the plantIngredient
        plantIngredient.setPartUsed(UPDATED_PART_USED);
        

        restPlantIngredientMockMvc.perform(put("/api/plantIngredients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantIngredient)))
                .andExpect(status().isOk());

        // Validate the PlantIngredient in the database
        List<PlantIngredient> plantIngredients = plantIngredientRepository.findAll();
        assertThat(plantIngredients).hasSize(databaseSizeBeforeUpdate);
        PlantIngredient testPlantIngredient = plantIngredients.get(plantIngredients.size() - 1);
        assertThat(testPlantIngredient.getPartUsed()).isEqualTo(UPDATED_PART_USED);
    }

    @Test
    @Transactional
    public void deletePlantIngredient() throws Exception {
        // Initialize the database
        plantIngredientRepository.saveAndFlush(plantIngredient);

		int databaseSizeBeforeDelete = plantIngredientRepository.findAll().size();

        // Get the plantIngredient
        restPlantIngredientMockMvc.perform(delete("/api/plantIngredients/{id}", plantIngredient.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PlantIngredient> plantIngredients = plantIngredientRepository.findAll();
        assertThat(plantIngredients).hasSize(databaseSizeBeforeDelete - 1);
    }
}