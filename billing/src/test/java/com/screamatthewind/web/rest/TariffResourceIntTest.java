package com.screamatthewind.web.rest;

import com.screamatthewind.BillingApp;

import com.screamatthewind.domain.Tariff;
import com.screamatthewind.repository.TariffRepository;
import com.screamatthewind.service.dto.TariffDTO;
import com.screamatthewind.service.mapper.TariffMapper;
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
 * Test class for the TariffResource REST controller.
 *
 * @see TariffResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingApp.class)
public class TariffResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Integer DEFAULT_PERIOD = 1;
    private static final Integer UPDATED_PERIOD = 2;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final ZonedDateTime DEFAULT_ADD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private TariffMapper tariffMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTariffMockMvc;

    private Tariff tariff;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TariffResource tariffResource = new TariffResource(tariffRepository, tariffMapper);
        this.restTariffMockMvc = MockMvcBuilders.standaloneSetup(tariffResource)
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
    public static Tariff createEntity(EntityManager em) {
        Tariff tariff = new Tariff()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .period(DEFAULT_PERIOD)
            .enabled(DEFAULT_ENABLED)
            .addDate(DEFAULT_ADD_DATE);
        return tariff;
    }

    @Before
    public void initTest() {
        tariff = createEntity(em);
    }

    @Test
    @Transactional
    public void createTariff() throws Exception {
        int databaseSizeBeforeCreate = tariffRepository.findAll().size();

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);
        restTariffMockMvc.perform(post("/api/tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isCreated());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeCreate + 1);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTariff.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTariff.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testTariff.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testTariff.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
    }

    @Test
    @Transactional
    public void createTariffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tariffRepository.findAll().size();

        // Create the Tariff with an existing ID
        tariff.setId(1L);
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTariffMockMvc.perform(post("/api/tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTariffs() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        // Get all the tariffList
        restTariffMockMvc.perform(get("/api/tariffs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tariff.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(sameInstant(DEFAULT_ADD_DATE))));
    }

    @Test
    @Transactional
    public void getTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);

        // Get the tariff
        restTariffMockMvc.perform(get("/api/tariffs/{id}", tariff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tariff.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.addDate").value(sameInstant(DEFAULT_ADD_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingTariff() throws Exception {
        // Get the tariff
        restTariffMockMvc.perform(get("/api/tariffs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Update the tariff
        Tariff updatedTariff = tariffRepository.findOne(tariff.getId());
        // Disconnect from session so that the updates on updatedTariff are not directly saved in db
        em.detach(updatedTariff);
        updatedTariff
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .period(UPDATED_PERIOD)
            .enabled(UPDATED_ENABLED)
            .addDate(UPDATED_ADD_DATE);
        TariffDTO tariffDTO = tariffMapper.toDto(updatedTariff);

        restTariffMockMvc.perform(put("/api/tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isOk());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate);
        Tariff testTariff = tariffList.get(tariffList.size() - 1);
        assertThat(testTariff.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTariff.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTariff.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testTariff.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testTariff.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTariff() throws Exception {
        int databaseSizeBeforeUpdate = tariffRepository.findAll().size();

        // Create the Tariff
        TariffDTO tariffDTO = tariffMapper.toDto(tariff);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTariffMockMvc.perform(put("/api/tariffs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tariffDTO)))
            .andExpect(status().isCreated());

        // Validate the Tariff in the database
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTariff() throws Exception {
        // Initialize the database
        tariffRepository.saveAndFlush(tariff);
        int databaseSizeBeforeDelete = tariffRepository.findAll().size();

        // Get the tariff
        restTariffMockMvc.perform(delete("/api/tariffs/{id}", tariff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tariff> tariffList = tariffRepository.findAll();
        assertThat(tariffList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tariff.class);
        Tariff tariff1 = new Tariff();
        tariff1.setId(1L);
        Tariff tariff2 = new Tariff();
        tariff2.setId(tariff1.getId());
        assertThat(tariff1).isEqualTo(tariff2);
        tariff2.setId(2L);
        assertThat(tariff1).isNotEqualTo(tariff2);
        tariff1.setId(null);
        assertThat(tariff1).isNotEqualTo(tariff2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TariffDTO.class);
        TariffDTO tariffDTO1 = new TariffDTO();
        tariffDTO1.setId(1L);
        TariffDTO tariffDTO2 = new TariffDTO();
        assertThat(tariffDTO1).isNotEqualTo(tariffDTO2);
        tariffDTO2.setId(tariffDTO1.getId());
        assertThat(tariffDTO1).isEqualTo(tariffDTO2);
        tariffDTO2.setId(2L);
        assertThat(tariffDTO1).isNotEqualTo(tariffDTO2);
        tariffDTO1.setId(null);
        assertThat(tariffDTO1).isNotEqualTo(tariffDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tariffMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tariffMapper.fromId(null)).isNull();
    }
}
