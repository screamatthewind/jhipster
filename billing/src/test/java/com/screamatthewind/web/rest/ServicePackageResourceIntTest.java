package com.screamatthewind.web.rest;

import com.screamatthewind.BillingApp;

import com.screamatthewind.domain.ServicePackage;
import com.screamatthewind.repository.ServicePackageRepository;
import com.screamatthewind.service.dto.ServicePackageDTO;
import com.screamatthewind.service.mapper.ServicePackageMapper;
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
 * Test class for the ServicePackageResource REST controller.
 *
 * @see ServicePackageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingApp.class)
public class ServicePackageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final ZonedDateTime DEFAULT_ADD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Autowired
    private ServicePackageMapper servicePackageMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServicePackageMockMvc;

    private ServicePackage servicePackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServicePackageResource servicePackageResource = new ServicePackageResource(servicePackageRepository, servicePackageMapper);
        this.restServicePackageMockMvc = MockMvcBuilders.standaloneSetup(servicePackageResource)
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
    public static ServicePackage createEntity(EntityManager em) {
        ServicePackage servicePackage = new ServicePackage()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .enabled(DEFAULT_ENABLED)
            .addDate(DEFAULT_ADD_DATE);
        return servicePackage;
    }

    @Before
    public void initTest() {
        servicePackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createServicePackage() throws Exception {
        int databaseSizeBeforeCreate = servicePackageRepository.findAll().size();

        // Create the ServicePackage
        ServicePackageDTO servicePackageDTO = servicePackageMapper.toDto(servicePackage);
        restServicePackageMockMvc.perform(post("/api/service-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicePackageDTO)))
            .andExpect(status().isCreated());

        // Validate the ServicePackage in the database
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeCreate + 1);
        ServicePackage testServicePackage = servicePackageList.get(servicePackageList.size() - 1);
        assertThat(testServicePackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServicePackage.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testServicePackage.isEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testServicePackage.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
    }

    @Test
    @Transactional
    public void createServicePackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicePackageRepository.findAll().size();

        // Create the ServicePackage with an existing ID
        servicePackage.setId(1L);
        ServicePackageDTO servicePackageDTO = servicePackageMapper.toDto(servicePackage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicePackageMockMvc.perform(post("/api/service-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicePackageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServicePackage in the database
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllServicePackages() throws Exception {
        // Initialize the database
        servicePackageRepository.saveAndFlush(servicePackage);

        // Get all the servicePackageList
        restServicePackageMockMvc.perform(get("/api/service-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicePackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(sameInstant(DEFAULT_ADD_DATE))));
    }

    @Test
    @Transactional
    public void getServicePackage() throws Exception {
        // Initialize the database
        servicePackageRepository.saveAndFlush(servicePackage);

        // Get the servicePackage
        restServicePackageMockMvc.perform(get("/api/service-packages/{id}", servicePackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servicePackage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.addDate").value(sameInstant(DEFAULT_ADD_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingServicePackage() throws Exception {
        // Get the servicePackage
        restServicePackageMockMvc.perform(get("/api/service-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicePackage() throws Exception {
        // Initialize the database
        servicePackageRepository.saveAndFlush(servicePackage);
        int databaseSizeBeforeUpdate = servicePackageRepository.findAll().size();

        // Update the servicePackage
        ServicePackage updatedServicePackage = servicePackageRepository.findOne(servicePackage.getId());
        // Disconnect from session so that the updates on updatedServicePackage are not directly saved in db
        em.detach(updatedServicePackage);
        updatedServicePackage
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .enabled(UPDATED_ENABLED)
            .addDate(UPDATED_ADD_DATE);
        ServicePackageDTO servicePackageDTO = servicePackageMapper.toDto(updatedServicePackage);

        restServicePackageMockMvc.perform(put("/api/service-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicePackageDTO)))
            .andExpect(status().isOk());

        // Validate the ServicePackage in the database
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeUpdate);
        ServicePackage testServicePackage = servicePackageList.get(servicePackageList.size() - 1);
        assertThat(testServicePackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServicePackage.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testServicePackage.isEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testServicePackage.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingServicePackage() throws Exception {
        int databaseSizeBeforeUpdate = servicePackageRepository.findAll().size();

        // Create the ServicePackage
        ServicePackageDTO servicePackageDTO = servicePackageMapper.toDto(servicePackage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServicePackageMockMvc.perform(put("/api/service-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicePackageDTO)))
            .andExpect(status().isCreated());

        // Validate the ServicePackage in the database
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServicePackage() throws Exception {
        // Initialize the database
        servicePackageRepository.saveAndFlush(servicePackage);
        int databaseSizeBeforeDelete = servicePackageRepository.findAll().size();

        // Get the servicePackage
        restServicePackageMockMvc.perform(delete("/api/service-packages/{id}", servicePackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicePackage.class);
        ServicePackage servicePackage1 = new ServicePackage();
        servicePackage1.setId(1L);
        ServicePackage servicePackage2 = new ServicePackage();
        servicePackage2.setId(servicePackage1.getId());
        assertThat(servicePackage1).isEqualTo(servicePackage2);
        servicePackage2.setId(2L);
        assertThat(servicePackage1).isNotEqualTo(servicePackage2);
        servicePackage1.setId(null);
        assertThat(servicePackage1).isNotEqualTo(servicePackage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicePackageDTO.class);
        ServicePackageDTO servicePackageDTO1 = new ServicePackageDTO();
        servicePackageDTO1.setId(1L);
        ServicePackageDTO servicePackageDTO2 = new ServicePackageDTO();
        assertThat(servicePackageDTO1).isNotEqualTo(servicePackageDTO2);
        servicePackageDTO2.setId(servicePackageDTO1.getId());
        assertThat(servicePackageDTO1).isEqualTo(servicePackageDTO2);
        servicePackageDTO2.setId(2L);
        assertThat(servicePackageDTO1).isNotEqualTo(servicePackageDTO2);
        servicePackageDTO1.setId(null);
        assertThat(servicePackageDTO1).isNotEqualTo(servicePackageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(servicePackageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(servicePackageMapper.fromId(null)).isNull();
    }
}
