package com.screamatthewind.web.rest;

import com.screamatthewind.BillingApp;

import com.screamatthewind.domain.Channel;
import com.screamatthewind.repository.ChannelRepository;
import com.screamatthewind.service.dto.ChannelDTO;
import com.screamatthewind.service.mapper.ChannelMapper;
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
 * Test class for the ChannelResource REST controller.
 *
 * @see ChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingApp.class)
public class ChannelResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Integer DEFAULT_EPG_CORRECTION = 1;
    private static final Integer UPDATED_EPG_CORRECTION = 2;

    private static final Integer DEFAULT_XMLTV_ID = 1;
    private static final Integer UPDATED_XMLTV_ID = 2;

    private static final ZonedDateTime DEFAULT_ADD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChannelMockMvc;

    private Channel channel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChannelResource channelResource = new ChannelResource(channelRepository, channelMapper);
        this.restChannelMockMvc = MockMvcBuilders.standaloneSetup(channelResource)
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
    public static Channel createEntity(EntityManager em) {
        Channel channel = new Channel()
            .number(DEFAULT_NUMBER)
            .name(DEFAULT_NAME)
            .link(DEFAULT_LINK)
            .epgCorrection(DEFAULT_EPG_CORRECTION)
            .xmltvId(DEFAULT_XMLTV_ID)
            .addDate(DEFAULT_ADD_DATE);
        return channel;
    }

    @Before
    public void initTest() {
        channel = createEntity(em);
    }

    @Test
    @Transactional
    public void createChannel() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel
        ChannelDTO channelDTO = channelMapper.toDto(channel);
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isCreated());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate + 1);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChannel.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testChannel.getEpgCorrection()).isEqualTo(DEFAULT_EPG_CORRECTION);
        assertThat(testChannel.getXmltvId()).isEqualTo(DEFAULT_XMLTV_ID);
        assertThat(testChannel.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
    }

    @Test
    @Transactional
    public void createChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = channelRepository.findAll().size();

        // Create the Channel with an existing ID
        channel.setId(1L);
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setNumber(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setName(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = channelRepository.findAll().size();
        // set the field null
        channel.setLink(null);

        // Create the Channel, which fails.
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        restChannelMockMvc.perform(post("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isBadRequest());

        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChannels() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get all the channelList
        restChannelMockMvc.perform(get("/api/channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channel.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].epgCorrection").value(hasItem(DEFAULT_EPG_CORRECTION)))
            .andExpect(jsonPath("$.[*].xmltvId").value(hasItem(DEFAULT_XMLTV_ID)))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(sameInstant(DEFAULT_ADD_DATE))));
    }

    @Test
    @Transactional
    public void getChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);

        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", channel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(channel.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.epgCorrection").value(DEFAULT_EPG_CORRECTION))
            .andExpect(jsonPath("$.xmltvId").value(DEFAULT_XMLTV_ID))
            .andExpect(jsonPath("$.addDate").value(sameInstant(DEFAULT_ADD_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingChannel() throws Exception {
        // Get the channel
        restChannelMockMvc.perform(get("/api/channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);
        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Update the channel
        Channel updatedChannel = channelRepository.findOne(channel.getId());
        // Disconnect from session so that the updates on updatedChannel are not directly saved in db
        em.detach(updatedChannel);
        updatedChannel
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .link(UPDATED_LINK)
            .epgCorrection(UPDATED_EPG_CORRECTION)
            .xmltvId(UPDATED_XMLTV_ID)
            .addDate(UPDATED_ADD_DATE);
        ChannelDTO channelDTO = channelMapper.toDto(updatedChannel);

        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isOk());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate);
        Channel testChannel = channelList.get(channelList.size() - 1);
        assertThat(testChannel.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChannel.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testChannel.getEpgCorrection()).isEqualTo(UPDATED_EPG_CORRECTION);
        assertThat(testChannel.getXmltvId()).isEqualTo(UPDATED_XMLTV_ID);
        assertThat(testChannel.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingChannel() throws Exception {
        int databaseSizeBeforeUpdate = channelRepository.findAll().size();

        // Create the Channel
        ChannelDTO channelDTO = channelMapper.toDto(channel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChannelMockMvc.perform(put("/api/channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(channelDTO)))
            .andExpect(status().isCreated());

        // Validate the Channel in the database
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChannel() throws Exception {
        // Initialize the database
        channelRepository.saveAndFlush(channel);
        int databaseSizeBeforeDelete = channelRepository.findAll().size();

        // Get the channel
        restChannelMockMvc.perform(delete("/api/channels/{id}", channel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Channel> channelList = channelRepository.findAll();
        assertThat(channelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Channel.class);
        Channel channel1 = new Channel();
        channel1.setId(1L);
        Channel channel2 = new Channel();
        channel2.setId(channel1.getId());
        assertThat(channel1).isEqualTo(channel2);
        channel2.setId(2L);
        assertThat(channel1).isNotEqualTo(channel2);
        channel1.setId(null);
        assertThat(channel1).isNotEqualTo(channel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelDTO.class);
        ChannelDTO channelDTO1 = new ChannelDTO();
        channelDTO1.setId(1L);
        ChannelDTO channelDTO2 = new ChannelDTO();
        assertThat(channelDTO1).isNotEqualTo(channelDTO2);
        channelDTO2.setId(channelDTO1.getId());
        assertThat(channelDTO1).isEqualTo(channelDTO2);
        channelDTO2.setId(2L);
        assertThat(channelDTO1).isNotEqualTo(channelDTO2);
        channelDTO1.setId(null);
        assertThat(channelDTO1).isNotEqualTo(channelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(channelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(channelMapper.fromId(null)).isNull();
    }
}
