package com.screamatthewind.web.rest;

import com.screamatthewind.BillingApp;

import com.screamatthewind.domain.Template;
import com.screamatthewind.repository.TemplateRepository;
import com.screamatthewind.service.dto.TemplateDTO;
import com.screamatthewind.service.mapper.TemplateMapper;
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
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingApp.class)
public class TemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplateResource templateResource = new TemplateResource(templateRepository, templateMapper);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateResource)
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
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
            .name(DEFAULT_NAME);
        return template;
    }

    @Before
    public void initTest() {
        template = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template with an existing ID
        template.setId(1L);
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findOne(template.getId());
        // Disconnect from session so that the updates on updatedTemplate are not directly saved in db
        em.detach(updatedTemplate);
        updatedTemplate
            .name(UPDATED_NAME);
        TemplateDTO templateDTO = templateMapper.toDto(updatedTemplate);

        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);
        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Get the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Template.class);
        Template template1 = new Template();
        template1.setId(1L);
        Template template2 = new Template();
        template2.setId(template1.getId());
        assertThat(template1).isEqualTo(template2);
        template2.setId(2L);
        assertThat(template1).isNotEqualTo(template2);
        template1.setId(null);
        assertThat(template1).isNotEqualTo(template2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateDTO.class);
        TemplateDTO templateDTO1 = new TemplateDTO();
        templateDTO1.setId(1L);
        TemplateDTO templateDTO2 = new TemplateDTO();
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
        templateDTO2.setId(templateDTO1.getId());
        assertThat(templateDTO1).isEqualTo(templateDTO2);
        templateDTO2.setId(2L);
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
        templateDTO1.setId(null);
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(templateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(templateMapper.fromId(null)).isNull();
    }
}
