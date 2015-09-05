package eu.thinking_aloud.portfolio.web.rest;

import eu.thinking_aloud.portfolio.Application;
import eu.thinking_aloud.portfolio.domain.Quicksort;
import eu.thinking_aloud.portfolio.repository.QuicksortRepository;

import eu.thinking_aloud.portfolio.web.rest.util.QuickSorter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the QuicksortResource REST controller.
 *
 * @see QuicksortResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QuicksortResourceTest {

    private static final String DEFAULT_UNSORTED = "3,2,1";
    private static final String UPDATED_UNSORTED = "4,3,2,1";
    private static final String DEFAULT_SORTED = "1, 2, 3";
    private static final String UPDATED_SORTED = "1, 2, 3, 4";

    @Inject
    private QuicksortRepository quicksortRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restQuicksortMockMvc;

    private Quicksort quicksort;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuicksortResource quicksortResource = new QuicksortResource();
        ReflectionTestUtils.setField(quicksortResource, "quicksortRepository", quicksortRepository);
        this.restQuicksortMockMvc = MockMvcBuilders.standaloneSetup(quicksortResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        quicksortRepository.deleteAll();
        quicksort = new Quicksort();
        quicksort.setUnsorted(DEFAULT_UNSORTED);
        quicksort.setSorted(DEFAULT_SORTED);
    }

    @Test
    public void createQuicksort() throws Exception {
        int databaseSizeBeforeCreate = quicksortRepository.findAll().size();

        // Create the Quicksort

        restQuicksortMockMvc.perform(post("/api/quicksorts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quicksort)))
                .andExpect(status().isCreated());

        // Validate the Quicksort in the database
        List<Quicksort> quicksorts = quicksortRepository.findAll();
        assertThat(quicksorts).hasSize(databaseSizeBeforeCreate + 1);
        Quicksort testQuicksort = quicksorts.get(quicksorts.size() - 1);
        assertThat(testQuicksort.getUnsorted()).isEqualTo(DEFAULT_UNSORTED);
        assertThat(testQuicksort.getSorted()).isEqualTo(DEFAULT_SORTED);
    }

    @Test
    public void checkUnsortedIsRequired() throws Exception {
        int databaseSizeBeforeTest = quicksortRepository.findAll().size();
        // set the field null
        quicksort.setUnsorted(null);

        // Create the Quicksort, which fails.

        restQuicksortMockMvc.perform(post("/api/quicksorts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quicksort)))
                .andExpect(status().isBadRequest());

        List<Quicksort> quicksorts = quicksortRepository.findAll();
        assertThat(quicksorts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllQuicksorts() throws Exception {
        // Initialize the database
        quicksortRepository.save(quicksort);

        // Get all the quicksorts
        restQuicksortMockMvc.perform(get("/api/quicksorts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quicksort.getId())))
                .andExpect(jsonPath("$.[*].unsorted").value(hasItem(DEFAULT_UNSORTED.toString())))
                .andExpect(jsonPath("$.[*].sorted").value(hasItem(DEFAULT_SORTED.toString())));
    }

    @Test
    public void getQuicksort() throws Exception {
        // Initialize the database
        quicksortRepository.save(quicksort);

        // Get the quicksort
        restQuicksortMockMvc.perform(get("/api/quicksorts/{id}", quicksort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(quicksort.getId()))
            .andExpect(jsonPath("$.unsorted").value(DEFAULT_UNSORTED.toString()))
            .andExpect(jsonPath("$.sorted").value(DEFAULT_SORTED.toString()));
    }

    @Test
    public void getNonExistingQuicksort() throws Exception {
        // Get the quicksort
        restQuicksortMockMvc.perform(get("/api/quicksorts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateQuicksort() throws Exception {
        // Initialize the database
        quicksortRepository.save(quicksort);

		int databaseSizeBeforeUpdate = quicksortRepository.findAll().size();

        // Update the quicksort
        quicksort.setUnsorted(UPDATED_UNSORTED);
        quicksort.setSorted(UPDATED_SORTED);

        restQuicksortMockMvc.perform(put("/api/quicksorts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quicksort)))
                .andExpect(status().isOk());

        // Validate the Quicksort in the database
        List<Quicksort> quicksorts = quicksortRepository.findAll();
        assertThat(quicksorts).hasSize(databaseSizeBeforeUpdate);
        Quicksort testQuicksort = quicksorts.get(quicksorts.size() - 1);
        assertThat(testQuicksort.getUnsorted()).isEqualTo(UPDATED_UNSORTED);
        assertThat(testQuicksort.getSorted()).isEqualTo(UPDATED_SORTED);
    }

    @Test
    public void deleteQuicksort() throws Exception {
        // Initialize the database
        quicksortRepository.save(quicksort);

		int databaseSizeBeforeDelete = quicksortRepository.findAll().size();

        // Get the quicksort
        restQuicksortMockMvc.perform(delete("/api/quicksorts/{id}", quicksort.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Quicksort> quicksorts = quicksortRepository.findAll();
        assertThat(quicksorts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
