package com.screamatthewind.web.rest;

import com.screamatthewind.BillingApp;

import com.screamatthewind.domain.Stb;
import com.screamatthewind.repository.StbRepository;
import com.screamatthewind.service.dto.StbDTO;
import com.screamatthewind.service.mapper.StbMapper;
import com.screamatthewind.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.screamatthewind.web.rest.TestUtil.sameInstant;
import static com.screamatthewind.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StbResource REST controller.
 *
 * @see StbResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingApp.class)
public class StbResourceIntTest {

    private static final String DEFAULT_MAC = "AAAAAAAAAA";
    private static final String UPDATED_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final ZonedDateTime DEFAULT_ADD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StbRepository stbRepository;

    @Autowired
    private StbMapper stbMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStbMockMvc;

    private Stb stb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StbResource stbResource = new StbResource(stbRepository, stbMapper);
        this.restStbMockMvc = MockMvcBuilders.standaloneSetup(stbResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stb createEntity(EntityManager em) {
        Stb stb = new Stb()
            .mac(DEFAULT_MAC)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .enabled(DEFAULT_ENABLED)
            .addDate(DEFAULT_ADD_DATE);
        return stb;
    }

    @Before
    public void initTest() {
        stb = createEntity(em);
    }

    @Test
    @Transactional
    public void createStb() throws Exception {
        int databaseSizeBeforeCreate = stbRepository.findAll().size();

        // Create the Stb
        StbDTO stbDTO = stbMapper.toDto(stb);
        restStbMockMvc.perform(post("/api/stbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stbDTO)))
            .andExpect(status().isCreated());

        // Validate the Stb in the database
        List<Stb> stbList = stbRepository.findAll();
        assertThat(stbList).hasSize(databaseSizeBeforeCreate + 1);
        Stb testStb = stbList.get(stbList.size() - 1);
        assertThat(testStb.getMac()).isEqualTo(DEFAULT_MAC);
        assertThat(testStb.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testStb.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testStb.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
    }

    @Test
    @Transactional
    public void createStbWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stbRepository.findAll().size();

        // Create the Stb with an existing ID
        stb.setId(1L);
        StbDTO stbDTO = stbMapper.toDto(stb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStbMockMvc.perform(post("/api/stbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stb in the database
        List<Stb> stbList = stbRepository.findAll();
        assertThat(stbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMacIsRequired() throws Exception {
        int databaseSizeBeforeTest = stbRepository.findAll().size();
        // set the field null
        stb.setMac(null);

        // Create the Stb, which fails.
        StbDTO stbDTO = stbMapper.toDto(stb);

        restStbMockMvc.perform(post("/api/stbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stbDTO)))
            .andExpect(status().isBadRequest());

        List<Stb> stbList = stbRepository.findAll();
        assertThat(stbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStbs() throws Exception {
        // Initialize the database
        stbRepository.saveAndFlush(stb);

        // Get all the stbList
        restStbMockMvc.perform(get("/api/stbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stb.getId().intValue())))
            .andExpect(jsonPath("$.[*].mac").value(hasItem(DEFAULT_MAC.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(sameInstant(DEFAULT_ADD_DATE))));
    }

    @Test
    @Transactional
    public void getStb() throws Exception {
        // Initialize the database
        stbRepository.saveAndFlush(stb);

        // Get the stb
        restStbMockMvc.perform(get("/api/stbs/{id}", stb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stb.getId().intValue()))
            .andExpect(jsonPath("$.mac").value(DEFAULT_MAC.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.addDate").value(sameInstant(DEFAULT_ADD_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingStb() throws Exception {
        // Get the stb
        restStbMockMvc.perform(get("/api/stbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStb() throws Exception {
        // Initialize the database
        stbRepository.saveAndFlush(stb);
        int databaseSizeBeforeUpdate = stbRepository.findAll().size();

        // Update the stb
        Stb updatedStb = stbRepository.findOne(stb.getId());
        // Disconnect from session so that the updates on updatedStb are not directly saved in db
        em.detach(updatedStb);
        updatedStb
            .mac(UPDATED_MAC)
            .ipAddress(UPDATED_IP_ADDRESS)
            .enabled(UPDATED_ENABLED)
            .addDate(UPDATED_ADD_DATE);
        StbDTO stbDTO = stbMapper.toDto(updatedStb);

        restStbMockMvc.perform(put("/api/stbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stbDTO)))
            .andExpect(status().isOk());

        // Validate the Stb in the database
        List<Stb> stbList = stbRepository.findAll();
        assertThat(stbList).hasSize(databaseSizeBeforeUpdate);
        Stb testStb = stbList.get(stbList.size() - 1);
        assertThat(testStb.getMac()).isEqualTo(UPDATED_MAC);
        assertThat(testStb.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testStb.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testStb.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStb() throws Exception {
        int databaseSizeBeforeUpdate = stbRepository.findAll().size();

        // Create the Stb
        StbDTO stbDTO = stbMapper.toDto(stb);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStbMockMvc.perform(put("/api/stbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stbDTO)))
            .andExpect(status().isCreated());

        // Validate the Stb in the database
        List<Stb> stbList = stbRepository.findAll();
        assertThat(stbList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStb() throws Exception {
        // Initialize the database
        stbRepository.saveAndFlush(stb);
        int databaseSizeBeforeDelete = stbRepository.findAll().size();

        // Get the stb
        restStbMockMvc.perform(delete("/api/stbs/{id}", stb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stb> stbList = stbRepository.findAll();
        assertThat(stbList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stb.class);
        Stb stb1 = new Stb();
        stb1.setId(1L);
        Stb stb2 = new Stb();
        stb2.setId(stb1.getId());
        assertThat(stb1).isEqualTo(stb2);
        stb2.setId(2L);
        assertThat(stb1).isNotEqualTo(stb2);
        stb1.setId(null);
        assertThat(stb1).isNotEqualTo(stb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StbDTO.class);
        StbDTO stbDTO1 = new StbDTO();
        stbDTO1.setId(1L);
        StbDTO stbDTO2 = new StbDTO();
        assertThat(stbDTO1).isNotEqualTo(stbDTO2);
        stbDTO2.setId(stbDTO1.getId());
        assertThat(stbDTO1).isEqualTo(stbDTO2);
        stbDTO2.setId(2L);
        assertThat(stbDTO1).isNotEqualTo(stbDTO2);
        stbDTO1.setId(null);
        assertThat(stbDTO1).isNotEqualTo(stbDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stbMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stbMapper.fromId(null)).isNull();
    }
}
