package br.com.rentalsystem.locacoes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rentalsystem.locacoes.IntegrationTest;
import br.com.rentalsystem.locacoes.domain.Contrato;
import br.com.rentalsystem.locacoes.repository.ContratoRepository;
import br.com.rentalsystem.locacoes.service.dto.ContratoDTO;
import br.com.rentalsystem.locacoes.service.mapper.ContratoMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContratoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContratoResourceIT {

    private static final String DEFAULT_TAXA_DO_DIA = "AAAAAAAAAA";
    private static final String UPDATED_TAXA_DO_DIA = "BBBBBBBBBB";

    private static final Long DEFAULT_KM_INICIAL = 1L;
    private static final Long UPDATED_KM_INICIAL = 2L;

    private static final Long DEFAULT_KM_FINAL = 1L;
    private static final Long UPDATED_KM_FINAL = 2L;

    private static final Instant DEFAULT_PERIODO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIODO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRATO_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRATO_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRATO_DATA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRATO_DATA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRATO_DATA_RETIRADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRATO_DATA_RETIRADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONTRATO_DATA_ENTREGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTRATO_DATA_ENTREGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/contratoes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoMapper contratoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratoMockMvc;

    private Contrato contrato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrato createEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .taxaDoDia(DEFAULT_TAXA_DO_DIA)
            .kmInicial(DEFAULT_KM_INICIAL)
            .kmFinal(DEFAULT_KM_FINAL)
            .periodo(DEFAULT_PERIODO)
            .contratoDataInicio(DEFAULT_CONTRATO_DATA_INICIO)
            .contratoDataFinal(DEFAULT_CONTRATO_DATA_FINAL)
            .contratoDataRetirada(DEFAULT_CONTRATO_DATA_RETIRADA)
            .contratoDataEntrega(DEFAULT_CONTRATO_DATA_ENTREGA);
        return contrato;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrato createUpdatedEntity(EntityManager em) {
        Contrato contrato = new Contrato()
            .taxaDoDia(UPDATED_TAXA_DO_DIA)
            .kmInicial(UPDATED_KM_INICIAL)
            .kmFinal(UPDATED_KM_FINAL)
            .periodo(UPDATED_PERIODO)
            .contratoDataInicio(UPDATED_CONTRATO_DATA_INICIO)
            .contratoDataFinal(UPDATED_CONTRATO_DATA_FINAL)
            .contratoDataRetirada(UPDATED_CONTRATO_DATA_RETIRADA)
            .contratoDataEntrega(UPDATED_CONTRATO_DATA_ENTREGA);
        return contrato;
    }

    @BeforeEach
    public void initTest() {
        contrato = createEntity(em);
    }

    @Test
    @Transactional
    void createContrato() throws Exception {
        int databaseSizeBeforeCreate = contratoRepository.findAll().size();
        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);
        restContratoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate + 1);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getTaxaDoDia()).isEqualTo(DEFAULT_TAXA_DO_DIA);
        assertThat(testContrato.getKmInicial()).isEqualTo(DEFAULT_KM_INICIAL);
        assertThat(testContrato.getKmFinal()).isEqualTo(DEFAULT_KM_FINAL);
        assertThat(testContrato.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testContrato.getContratoDataInicio()).isEqualTo(DEFAULT_CONTRATO_DATA_INICIO);
        assertThat(testContrato.getContratoDataFinal()).isEqualTo(DEFAULT_CONTRATO_DATA_FINAL);
        assertThat(testContrato.getContratoDataRetirada()).isEqualTo(DEFAULT_CONTRATO_DATA_RETIRADA);
        assertThat(testContrato.getContratoDataEntrega()).isEqualTo(DEFAULT_CONTRATO_DATA_ENTREGA);
    }

    @Test
    @Transactional
    void createContratoWithExistingId() throws Exception {
        // Create the Contrato with an existing ID
        contrato.setId(1L);
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        int databaseSizeBeforeCreate = contratoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPeriodoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setPeriodo(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContratoDataInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setContratoDataInicio(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContratoDataFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratoRepository.findAll().size();
        // set the field null
        contrato.setContratoDataFinal(null);

        // Create the Contrato, which fails.
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        restContratoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isBadRequest());

        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContratoes() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get all the contratoList
        restContratoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrato.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxaDoDia").value(hasItem(DEFAULT_TAXA_DO_DIA)))
            .andExpect(jsonPath("$.[*].kmInicial").value(hasItem(DEFAULT_KM_INICIAL.intValue())))
            .andExpect(jsonPath("$.[*].kmFinal").value(hasItem(DEFAULT_KM_FINAL.intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO.toString())))
            .andExpect(jsonPath("$.[*].contratoDataInicio").value(hasItem(DEFAULT_CONTRATO_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].contratoDataFinal").value(hasItem(DEFAULT_CONTRATO_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].contratoDataRetirada").value(hasItem(DEFAULT_CONTRATO_DATA_RETIRADA.toString())))
            .andExpect(jsonPath("$.[*].contratoDataEntrega").value(hasItem(DEFAULT_CONTRATO_DATA_ENTREGA.toString())));
    }

    @Test
    @Transactional
    void getContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        // Get the contrato
        restContratoMockMvc
            .perform(get(ENTITY_API_URL_ID, contrato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contrato.getId().intValue()))
            .andExpect(jsonPath("$.taxaDoDia").value(DEFAULT_TAXA_DO_DIA))
            .andExpect(jsonPath("$.kmInicial").value(DEFAULT_KM_INICIAL.intValue()))
            .andExpect(jsonPath("$.kmFinal").value(DEFAULT_KM_FINAL.intValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO.toString()))
            .andExpect(jsonPath("$.contratoDataInicio").value(DEFAULT_CONTRATO_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.contratoDataFinal").value(DEFAULT_CONTRATO_DATA_FINAL.toString()))
            .andExpect(jsonPath("$.contratoDataRetirada").value(DEFAULT_CONTRATO_DATA_RETIRADA.toString()))
            .andExpect(jsonPath("$.contratoDataEntrega").value(DEFAULT_CONTRATO_DATA_ENTREGA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContrato() throws Exception {
        // Get the contrato
        restContratoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato
        Contrato updatedContrato = contratoRepository.findById(contrato.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContrato are not directly saved in db
        em.detach(updatedContrato);
        updatedContrato
            .taxaDoDia(UPDATED_TAXA_DO_DIA)
            .kmInicial(UPDATED_KM_INICIAL)
            .kmFinal(UPDATED_KM_FINAL)
            .periodo(UPDATED_PERIODO)
            .contratoDataInicio(UPDATED_CONTRATO_DATA_INICIO)
            .contratoDataFinal(UPDATED_CONTRATO_DATA_FINAL)
            .contratoDataRetirada(UPDATED_CONTRATO_DATA_RETIRADA)
            .contratoDataEntrega(UPDATED_CONTRATO_DATA_ENTREGA);
        ContratoDTO contratoDTO = contratoMapper.toDto(updatedContrato);

        restContratoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contratoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getTaxaDoDia()).isEqualTo(UPDATED_TAXA_DO_DIA);
        assertThat(testContrato.getKmInicial()).isEqualTo(UPDATED_KM_INICIAL);
        assertThat(testContrato.getKmFinal()).isEqualTo(UPDATED_KM_FINAL);
        assertThat(testContrato.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testContrato.getContratoDataInicio()).isEqualTo(UPDATED_CONTRATO_DATA_INICIO);
        assertThat(testContrato.getContratoDataFinal()).isEqualTo(UPDATED_CONTRATO_DATA_FINAL);
        assertThat(testContrato.getContratoDataRetirada()).isEqualTo(UPDATED_CONTRATO_DATA_RETIRADA);
        assertThat(testContrato.getContratoDataEntrega()).isEqualTo(UPDATED_CONTRATO_DATA_ENTREGA);
    }

    @Test
    @Transactional
    void putNonExistingContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();
        contrato.setId(longCount.incrementAndGet());

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contratoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();
        contrato.setId(longCount.incrementAndGet());

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contratoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();
        contrato.setId(longCount.incrementAndGet());

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contratoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContratoWithPatch() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato using partial update
        Contrato partialUpdatedContrato = new Contrato();
        partialUpdatedContrato.setId(contrato.getId());

        partialUpdatedContrato.kmFinal(UPDATED_KM_FINAL).periodo(UPDATED_PERIODO).contratoDataInicio(UPDATED_CONTRATO_DATA_INICIO);

        restContratoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContrato.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContrato))
            )
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getTaxaDoDia()).isEqualTo(DEFAULT_TAXA_DO_DIA);
        assertThat(testContrato.getKmInicial()).isEqualTo(DEFAULT_KM_INICIAL);
        assertThat(testContrato.getKmFinal()).isEqualTo(UPDATED_KM_FINAL);
        assertThat(testContrato.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testContrato.getContratoDataInicio()).isEqualTo(UPDATED_CONTRATO_DATA_INICIO);
        assertThat(testContrato.getContratoDataFinal()).isEqualTo(DEFAULT_CONTRATO_DATA_FINAL);
        assertThat(testContrato.getContratoDataRetirada()).isEqualTo(DEFAULT_CONTRATO_DATA_RETIRADA);
        assertThat(testContrato.getContratoDataEntrega()).isEqualTo(DEFAULT_CONTRATO_DATA_ENTREGA);
    }

    @Test
    @Transactional
    void fullUpdateContratoWithPatch() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();

        // Update the contrato using partial update
        Contrato partialUpdatedContrato = new Contrato();
        partialUpdatedContrato.setId(contrato.getId());

        partialUpdatedContrato
            .taxaDoDia(UPDATED_TAXA_DO_DIA)
            .kmInicial(UPDATED_KM_INICIAL)
            .kmFinal(UPDATED_KM_FINAL)
            .periodo(UPDATED_PERIODO)
            .contratoDataInicio(UPDATED_CONTRATO_DATA_INICIO)
            .contratoDataFinal(UPDATED_CONTRATO_DATA_FINAL)
            .contratoDataRetirada(UPDATED_CONTRATO_DATA_RETIRADA)
            .contratoDataEntrega(UPDATED_CONTRATO_DATA_ENTREGA);

        restContratoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContrato.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContrato))
            )
            .andExpect(status().isOk());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
        Contrato testContrato = contratoList.get(contratoList.size() - 1);
        assertThat(testContrato.getTaxaDoDia()).isEqualTo(UPDATED_TAXA_DO_DIA);
        assertThat(testContrato.getKmInicial()).isEqualTo(UPDATED_KM_INICIAL);
        assertThat(testContrato.getKmFinal()).isEqualTo(UPDATED_KM_FINAL);
        assertThat(testContrato.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testContrato.getContratoDataInicio()).isEqualTo(UPDATED_CONTRATO_DATA_INICIO);
        assertThat(testContrato.getContratoDataFinal()).isEqualTo(UPDATED_CONTRATO_DATA_FINAL);
        assertThat(testContrato.getContratoDataRetirada()).isEqualTo(UPDATED_CONTRATO_DATA_RETIRADA);
        assertThat(testContrato.getContratoDataEntrega()).isEqualTo(UPDATED_CONTRATO_DATA_ENTREGA);
    }

    @Test
    @Transactional
    void patchNonExistingContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();
        contrato.setId(longCount.incrementAndGet());

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contratoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contratoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();
        contrato.setId(longCount.incrementAndGet());

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contratoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContrato() throws Exception {
        int databaseSizeBeforeUpdate = contratoRepository.findAll().size();
        contrato.setId(longCount.incrementAndGet());

        // Create the Contrato
        ContratoDTO contratoDTO = contratoMapper.toDto(contrato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contratoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contrato in the database
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContrato() throws Exception {
        // Initialize the database
        contratoRepository.saveAndFlush(contrato);

        int databaseSizeBeforeDelete = contratoRepository.findAll().size();

        // Delete the contrato
        restContratoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contrato.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrato> contratoList = contratoRepository.findAll();
        assertThat(contratoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
