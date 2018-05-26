package com.screamatthewind.web.rest;

import com.screamatthewind.BillingApp;

import com.screamatthewind.domain.PeriodType;
import com.screamatthewind.repository.PeriodTypeRepository;
import com.screamatthewind.service.dto.PeriodTypeDTO;
import com.screamatthewind.service.mapper.PeriodTypeMapper;
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
import java.util.List;

import static com.screamatthewind.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeriodTypeResource REST controller.
 *
 * @see PeriodTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingApp.class)
public class PeriodTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PeriodTypeRepository periodTypeRepository;

    @Autowired
    private PeriodTypeMapper periodTypeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeriodTypeMockMvc;

    private PeriodType periodType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodTypeResource periodTypeResource = new PeriodTypeResource(periodTypeRepository, periodTypeMapper);
        this.restPeriodTypeMockMvc = MockMvcBuilders.standaloneSetup(periodTypeResource)
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
    public static PeriodType createEntity(EntityManager em) {
        PeriodType periodType = new PeriodType()
            .name(DEFAULT_NAME);
        return periodType;
    }

    @Before
    public void initTest() {
        periodType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodType() throws Exception {
        int databaseSizeBeforeCreate = periodTypeRepository.findAll().size();

        // Create the PeriodType
        PeriodTypeDTO periodTypeDTO = periodTypeMapper.toDto(periodType);
        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PeriodType testPeriodType = periodTypeList.get(periodTypeList.size() - 1);
        assertThat(testPeriodType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPeriodTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodTypeRepository.findAll().size();

        // Create the PeriodType with an existing ID
        periodType.setId(1L);
        PeriodTypeDTO periodTypeDTO = periodTypeMapper.toDto(periodType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodTypeMockMvc.perform(post("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeriodTypes() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        // Get all the periodTypeList
        restPeriodTypeMockMvc.perform(get("/api/period-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);

        // Get the periodType
        restPeriodTypeMockMvc.perform(get("/api/period-types/{id}", periodType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodType() throws Exception {
        // Get the periodType
        restPeriodTypeMockMvc.perform(get("/api/period-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);
        int databaseSizeBeforeUpdate = periodTypeRepository.findAll().size();

        // Update the periodType
        PeriodType updatedPeriodType = periodTypeRepository.findOne(periodType.getId());
        // Disconnect from session so that the updates on updatedPeriodType are not directly saved in db
        em.detach(updatedPeriodType);
        updatedPeriodType
            .name(UPDATED_NAME);
        PeriodTypeDTO periodTypeDTO = periodTypeMapper.toDto(updatedPeriodType);

        restPeriodTypeMockMvc.perform(put("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeUpdate);
        PeriodType testPeriodType = periodTypeList.get(periodTypeList.size() - 1);
        assertThat(testPeriodType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodType() throws Exception {
        int databaseSizeBeforeUpdate = periodTypeRepository.findAll().size();

        // Create the PeriodType
        PeriodTypeDTO periodTypeDTO = periodTypeMapper.toDto(periodType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeriodTypeMockMvc.perform(put("/api/period-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PeriodType in the database
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeriodType() throws Exception {
        // Initialize the database
        periodTypeRepository.saveAndFlush(periodType);
        int databaseSizeBeforeDelete = periodTypeRepository.findAll().size();

        // Get the periodType
        restPeriodTypeMockMvc.perform(delete("/api/period-types/{id}", periodType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeriodType> periodTypeList = periodTypeRepository.findAll();
        assertThat(periodTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodType.class);
        PeriodType periodType1 = new PeriodType();
        periodType1.setId(1L);
        PeriodType periodType2 = new PeriodType();
        periodType2.setId(periodType1.getId());
        assertThat(periodType1).isEqualTo(periodType2);
        periodType2.setId(2L);
        assertThat(periodType1).isNotEqualTo(periodType2);
        periodType1.setId(null);
        assertThat(periodType1).isNotEqualTo(periodType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodTypeDTO.class);
        PeriodTypeDTO periodTypeDTO1 = new PeriodTypeDTO();
        periodTypeDTO1.setId(1L);
        PeriodTypeDTO periodTypeDTO2 = new PeriodTypeDTO();
        assertThat(periodTypeDTO1).isNotEqualTo(periodTypeDTO2);
        periodTypeDTO2.setId(periodTypeDTO1.getId());
        assertThat(periodTypeDTO1).isEqualTo(periodTypeDTO2);
        periodTypeDTO2.setId(2L);
        assertThat(periodTypeDTO1).isNotEqualTo(periodTypeDTO2);
        periodTypeDTO1.setId(null);
        assertThat(periodTypeDTO1).isNotEqualTo(periodTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodTypeMapper.fromId(null)).isNull();
    }
}
