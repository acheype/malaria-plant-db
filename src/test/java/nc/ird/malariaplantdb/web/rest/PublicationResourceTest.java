package nc.ird.malariaplantdb.web.rest;

import nc.ird.malariaplantdb.Application;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.domain.Compiler;
import nc.ird.malariaplantdb.repository.PublicationRepository;
import nc.ird.malariaplantdb.repository.search.PublicationSearchRepository;

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
 * Test class for the PublicationResource REST controller.
 *
 * @see PublicationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PublicationResourceTest {

    private static final String DEFAULT_ENTRY_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_ENTRY_TYPE = "UPDATED_TEXT";

    private static final Integer DEFAULT_YEAR = 0;
    private static final Integer UPDATED_YEAR = 1;
    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_JOURNAL = "SAMPLE_TEXT";
    private static final String UPDATED_JOURNAL = "UPDATED_TEXT";
    private static final String DEFAULT_PAGES = "SAMPLE_TEXT";
    private static final String UPDATED_PAGES = "UPDATED_TEXT";
    private static final String DEFAULT_VOLUME = "SAMPLE_TEXT";
    private static final String UPDATED_VOLUME = "UPDATED_TEXT";
    private static final String DEFAULT_NB_OF_VOLUMES = "SAMPLE_TEXT";
    private static final String UPDATED_NB_OF_VOLUMES = "UPDATED_TEXT";
    private static final String DEFAULT_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_BOOK_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_BOOK_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_PUBLISHER = "SAMPLE_TEXT";
    private static final String UPDATED_PUBLISHER = "UPDATED_TEXT";
    private static final String DEFAULT_EDITION = "SAMPLE_TEXT";
    private static final String UPDATED_EDITION = "UPDATED_TEXT";
    private static final String DEFAULT_CONFERENCE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CONFERENCE_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CONFERENCE_PLACE = "SAMPLE_TEXT";
    private static final String UPDATED_CONFERENCE_PLACE = "UPDATED_TEXT";
    private static final String DEFAULT_UNIVERSITY = "SAMPLE_TEXT";
    private static final String UPDATED_UNIVERSITY = "UPDATED_TEXT";
    private static final String DEFAULT_INSTITUTION = "SAMPLE_TEXT";
    private static final String UPDATED_INSTITUTION = "UPDATED_TEXT";
    private static final String DEFAULT_DOI = "SAMPLE_TEXT";
    private static final String UPDATED_DOI = "UPDATED_TEXT";
    private static final String DEFAULT_PMID = "SAMPLE_TEXT";
    private static final String UPDATED_PMID = "UPDATED_TEXT";
    private static final String DEFAULT_ISBN = "SAMPLE_TEXT";
    private static final String UPDATED_ISBN = "UPDATED_TEXT";
    private static final String DEFAULT_URL = "SAMPLE_TEXT";
    private static final String UPDATED_URL = "UPDATED_TEXT";
    private static final String DEFAULT_COMPILERS_NOTES = "SAMPLE_TEXT";
    private static final String UPDATED_COMPILERS_NOTES = "UPDATED_TEXT";

    @Inject
    private PublicationRepository publicationRepository;

    @Inject
    private PublicationSearchRepository publicationSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPublicationMockMvc;

    private Publication publication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PublicationResource publicationResource = new PublicationResource();
        ReflectionTestUtils.setField(publicationResource, "publicationRepository", publicationRepository);
        ReflectionTestUtils.setField(publicationResource, "publicationSearchRepository", publicationSearchRepository);
        this.restPublicationMockMvc = MockMvcBuilders.standaloneSetup(publicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        publication = new Publication();
        publication.setEntryType(DEFAULT_ENTRY_TYPE);
        publication.setYear(DEFAULT_YEAR);
        publication.setTitle(DEFAULT_TITLE);
        publication.setJournal(DEFAULT_JOURNAL);
        publication.setPages(DEFAULT_PAGES);
        publication.setVolume(DEFAULT_VOLUME);
        publication.setNbOfVolumes(DEFAULT_NB_OF_VOLUMES);
        publication.setNumber(DEFAULT_NUMBER);
        publication.setBookTitle(DEFAULT_BOOK_TITLE);
        publication.setPublisher(DEFAULT_PUBLISHER);
        publication.setEdition(DEFAULT_EDITION);
        publication.setConferenceName(DEFAULT_CONFERENCE_NAME);
        publication.setConferencePlace(DEFAULT_CONFERENCE_PLACE);
        publication.setUniversity(DEFAULT_UNIVERSITY);
        publication.setInstitution(DEFAULT_INSTITUTION);
        publication.setDoi(DEFAULT_DOI);
        publication.setPmid(DEFAULT_PMID);
        publication.setIsbn(DEFAULT_ISBN);
        publication.setUrl(DEFAULT_URL);
        publication.setCompilersNotes(DEFAULT_COMPILERS_NOTES);
    }

    @Test
    @Transactional
    public void createPublication() throws Exception {
        int databaseSizeBeforeCreate = publicationRepository.findAll().size();

        // Create the Publication

        restPublicationMockMvc.perform(post("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publication)))
                .andExpect(status().isCreated());

        // Validate the Publication in the database
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeCreate + 1);
        Publication testPublication = publications.get(publications.size() - 1);
        assertThat(testPublication.getEntryType()).isEqualTo(DEFAULT_ENTRY_TYPE);
        assertThat(testPublication.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPublication.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPublication.getJournal()).isEqualTo(DEFAULT_JOURNAL);
        assertThat(testPublication.getPages()).isEqualTo(DEFAULT_PAGES);
        assertThat(testPublication.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testPublication.getNbOfVolumes()).isEqualTo(DEFAULT_NB_OF_VOLUMES);
        assertThat(testPublication.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPublication.getBookTitle()).isEqualTo(DEFAULT_BOOK_TITLE);
        assertThat(testPublication.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testPublication.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testPublication.getConferenceName()).isEqualTo(DEFAULT_CONFERENCE_NAME);
        assertThat(testPublication.getConferencePlace()).isEqualTo(DEFAULT_CONFERENCE_PLACE);
        assertThat(testPublication.getUniversity()).isEqualTo(DEFAULT_UNIVERSITY);
        assertThat(testPublication.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
        assertThat(testPublication.getDoi()).isEqualTo(DEFAULT_DOI);
        assertThat(testPublication.getPmid()).isEqualTo(DEFAULT_PMID);
        assertThat(testPublication.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testPublication.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testPublication.getCompilersNotes()).isEqualTo(DEFAULT_COMPILERS_NOTES);
    }

    @Test
    @Transactional
    public void checkEntryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicationRepository.findAll().size();
        // set the field null
        publication.setEntryType(null);

        // Create the Publication, which fails.

        restPublicationMockMvc.perform(post("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publication)))
                .andExpect(status().isBadRequest());

        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicationRepository.findAll().size();
        // set the field null
        publication.setYear(null);

        // Create the Publication, which fails.

        restPublicationMockMvc.perform(post("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publication)))
                .andExpect(status().isBadRequest());

        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicationRepository.findAll().size();
        // set the field null
        publication.setTitle(null);

        // Create the Publication, which fails.

        restPublicationMockMvc.perform(post("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publication)))
                .andExpect(status().isBadRequest());

        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompilers() throws Exception {
        int databaseSizeBeforeTest = publicationRepository.findAll().size();
        // set the field null
        publication.setTitle(null);

        Compiler cp = new Compiler();
        cp.setId(1L);
        cp.setFamily("Cheype");
        cp.setGiven("Adrien");
        cp.setInstitution("IRD");
        cp.setEmail("adrien.cheype@ird.fr");

        publication.getCompilers().add(cp);

        assertThat(publication.getCompilers()).hasSize(1);
    }

    @Test
    @Transactional
    public void getAllPublications() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get all the publications
        restPublicationMockMvc.perform(get("/api/publications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(publication.getId().intValue())))
                .andExpect(jsonPath("$.[*].entryType").value(hasItem(DEFAULT_ENTRY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].journal").value(hasItem(DEFAULT_JOURNAL.toString())))
                .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES.toString())))
                .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.toString())))
                .andExpect(jsonPath("$.[*].nbOfVolumes").value(hasItem(DEFAULT_NB_OF_VOLUMES.toString())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].bookTitle").value(hasItem(DEFAULT_BOOK_TITLE.toString())))
                .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
                .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.toString())))
                .andExpect(jsonPath("$.[*].conferenceName").value(hasItem(DEFAULT_CONFERENCE_NAME.toString())))
                .andExpect(jsonPath("$.[*].conferencePlace").value(hasItem(DEFAULT_CONFERENCE_PLACE.toString())))
                .andExpect(jsonPath("$.[*].university").value(hasItem(DEFAULT_UNIVERSITY.toString())))
                .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION.toString())))
                .andExpect(jsonPath("$.[*].doi").value(hasItem(DEFAULT_DOI.toString())))
                .andExpect(jsonPath("$.[*].pmid").value(hasItem(DEFAULT_PMID.toString())))
                .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].compilersNotes").value(hasItem(DEFAULT_COMPILERS_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getPublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", publication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(publication.getId().intValue()))
            .andExpect(jsonPath("$.entryType").value(DEFAULT_ENTRY_TYPE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.journal").value(DEFAULT_JOURNAL.toString()))
            .andExpect(jsonPath("$.pages").value(DEFAULT_PAGES.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.toString()))
            .andExpect(jsonPath("$.nbOfVolumes").value(DEFAULT_NB_OF_VOLUMES.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.bookTitle").value(DEFAULT_BOOK_TITLE.toString()))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.toString()))
            .andExpect(jsonPath("$.conferenceName").value(DEFAULT_CONFERENCE_NAME.toString()))
            .andExpect(jsonPath("$.conferencePlace").value(DEFAULT_CONFERENCE_PLACE.toString()))
            .andExpect(jsonPath("$.university").value(DEFAULT_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.institution").value(DEFAULT_INSTITUTION.toString()))
            .andExpect(jsonPath("$.doi").value(DEFAULT_DOI.toString()))
            .andExpect(jsonPath("$.pmid").value(DEFAULT_PMID.toString()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.compilersNotes").value(DEFAULT_COMPILERS_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublication() throws Exception {
        // Get the publication
        restPublicationMockMvc.perform(get("/api/publications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

		int databaseSizeBeforeUpdate = publicationRepository.findAll().size();

        // Update the publication
        publication.setEntryType(UPDATED_ENTRY_TYPE);
        publication.setYear(UPDATED_YEAR);
        publication.setTitle(UPDATED_TITLE);
        publication.setJournal(UPDATED_JOURNAL);
        publication.setPages(UPDATED_PAGES);
        publication.setVolume(UPDATED_VOLUME);
        publication.setNbOfVolumes(UPDATED_NB_OF_VOLUMES);
        publication.setNumber(UPDATED_NUMBER);
        publication.setBookTitle(UPDATED_BOOK_TITLE);
        publication.setPublisher(UPDATED_PUBLISHER);
        publication.setEdition(UPDATED_EDITION);
        publication.setConferenceName(UPDATED_CONFERENCE_NAME);
        publication.setConferencePlace(UPDATED_CONFERENCE_PLACE);
        publication.setUniversity(UPDATED_UNIVERSITY);
        publication.setInstitution(UPDATED_INSTITUTION);
        publication.setDoi(UPDATED_DOI);
        publication.setPmid(UPDATED_PMID);
        publication.setIsbn(UPDATED_ISBN);
        publication.setUrl(UPDATED_URL);
        publication.setCompilersNotes(UPDATED_COMPILERS_NOTES);


        restPublicationMockMvc.perform(put("/api/publications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(publication)))
                .andExpect(status().isOk());

        // Validate the Publication in the database
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeUpdate);
        Publication testPublication = publications.get(publications.size() - 1);
        assertThat(testPublication.getEntryType()).isEqualTo(UPDATED_ENTRY_TYPE);
        assertThat(testPublication.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPublication.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPublication.getJournal()).isEqualTo(UPDATED_JOURNAL);
        assertThat(testPublication.getPages()).isEqualTo(UPDATED_PAGES);
        assertThat(testPublication.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPublication.getNbOfVolumes()).isEqualTo(UPDATED_NB_OF_VOLUMES);
        assertThat(testPublication.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPublication.getBookTitle()).isEqualTo(UPDATED_BOOK_TITLE);
        assertThat(testPublication.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testPublication.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testPublication.getConferenceName()).isEqualTo(UPDATED_CONFERENCE_NAME);
        assertThat(testPublication.getConferencePlace()).isEqualTo(UPDATED_CONFERENCE_PLACE);
        assertThat(testPublication.getUniversity()).isEqualTo(UPDATED_UNIVERSITY);
        assertThat(testPublication.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
        assertThat(testPublication.getDoi()).isEqualTo(UPDATED_DOI);
        assertThat(testPublication.getPmid()).isEqualTo(UPDATED_PMID);
        assertThat(testPublication.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testPublication.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testPublication.getCompilersNotes()).isEqualTo(UPDATED_COMPILERS_NOTES);
    }

    @Test
    @Transactional
    public void deletePublication() throws Exception {
        // Initialize the database
        publicationRepository.saveAndFlush(publication);

		int databaseSizeBeforeDelete = publicationRepository.findAll().size();

        // Get the publication
        restPublicationMockMvc.perform(delete("/api/publications/{id}", publication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Publication> publications = publicationRepository.findAll();
        assertThat(publications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
