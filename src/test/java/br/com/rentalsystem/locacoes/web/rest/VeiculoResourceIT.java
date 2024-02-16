package br.com.rentalsystem.locacoes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rentalsystem.locacoes.IntegrationTest;
import br.com.rentalsystem.locacoes.domain.Veiculo;
import br.com.rentalsystem.locacoes.domain.enumeration.Classe;
import br.com.rentalsystem.locacoes.domain.enumeration.Grupo;
import br.com.rentalsystem.locacoes.domain.enumeration.Situacao;
import br.com.rentalsystem.locacoes.repository.VeiculoRepository;
import br.com.rentalsystem.locacoes.service.dto.VeiculoDTO;
import br.com.rentalsystem.locacoes.service.mapper.VeiculoMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link VeiculoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VeiculoResourceIT {

    private static final String DEFAULT_PLACA = "AAAAAAA";
    private static final String UPDATED_PLACA = "BBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_MODELO = "AAAAAAAAAA";
    private static final String UPDATED_MODELO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_AQUISICAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_AQUISICAO = LocalDate.now(ZoneId.systemDefault());

    private static final Classe DEFAULT_CLASSE = Classe.PASSEIO;
    private static final Classe UPDATED_CLASSE = Classe.CARGA;

    private static final Grupo DEFAULT_GRUPO = Grupo.HATCH_COMPACTO;
    private static final Grupo UPDATED_GRUPO = Grupo.HATCH_MEDIO;

    private static final Situacao DEFAULT_SITUCAO = Situacao.ALUGADO;
    private static final Situacao UPDATED_SITUCAO = Situacao.PARA_PREPARAR;

    private static final String ENTITY_API_URL = "/api/veiculos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoMapper veiculoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVeiculoMockMvc;

    private Veiculo veiculo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veiculo createEntity(EntityManager em) {
        Veiculo veiculo = new Veiculo()
            .placa(DEFAULT_PLACA)
            .marca(DEFAULT_MARCA)
            .modelo(DEFAULT_MODELO)
            .dataAquisicao(DEFAULT_DATA_AQUISICAO)
            .classe(DEFAULT_CLASSE)
            .grupo(DEFAULT_GRUPO)
            .situcao(DEFAULT_SITUCAO);
        return veiculo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veiculo createUpdatedEntity(EntityManager em) {
        Veiculo veiculo = new Veiculo()
            .placa(UPDATED_PLACA)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .dataAquisicao(UPDATED_DATA_AQUISICAO)
            .classe(UPDATED_CLASSE)
            .grupo(UPDATED_GRUPO)
            .situcao(UPDATED_SITUCAO);
        return veiculo;
    }

    @BeforeEach
    public void initTest() {
        veiculo = createEntity(em);
    }

    @Test
    @Transactional
    void createVeiculo() throws Exception {
        int databaseSizeBeforeCreate = veiculoRepository.findAll().size();
        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);
        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isCreated());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeCreate + 1);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getPlaca()).isEqualTo(DEFAULT_PLACA);
        assertThat(testVeiculo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testVeiculo.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testVeiculo.getDataAquisicao()).isEqualTo(DEFAULT_DATA_AQUISICAO);
        assertThat(testVeiculo.getClasse()).isEqualTo(DEFAULT_CLASSE);
        assertThat(testVeiculo.getGrupo()).isEqualTo(DEFAULT_GRUPO);
        assertThat(testVeiculo.getSitucao()).isEqualTo(DEFAULT_SITUCAO);
    }

    @Test
    @Transactional
    void createVeiculoWithExistingId() throws Exception {
        // Create the Veiculo with an existing ID
        veiculo.setId(1L);
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        int databaseSizeBeforeCreate = veiculoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPlacaIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setPlaca(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setMarca(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setModelo(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setClasse(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGrupoIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setGrupo(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSitucaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = veiculoRepository.findAll().size();
        // set the field null
        veiculo.setSitucao(null);

        // Create the Veiculo, which fails.
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        restVeiculoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVeiculos() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        // Get all the veiculoList
        restVeiculoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].placa").value(hasItem(DEFAULT_PLACA)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO)))
            .andExpect(jsonPath("$.[*].dataAquisicao").value(hasItem(DEFAULT_DATA_AQUISICAO.toString())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE.toString())))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].situcao").value(hasItem(DEFAULT_SITUCAO.toString())));
    }

    @Test
    @Transactional
    void getVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        // Get the veiculo
        restVeiculoMockMvc
            .perform(get(ENTITY_API_URL_ID, veiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(veiculo.getId().intValue()))
            .andExpect(jsonPath("$.placa").value(DEFAULT_PLACA))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO))
            .andExpect(jsonPath("$.dataAquisicao").value(DEFAULT_DATA_AQUISICAO.toString()))
            .andExpect(jsonPath("$.classe").value(DEFAULT_CLASSE.toString()))
            .andExpect(jsonPath("$.grupo").value(DEFAULT_GRUPO.toString()))
            .andExpect(jsonPath("$.situcao").value(DEFAULT_SITUCAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingVeiculo() throws Exception {
        // Get the veiculo
        restVeiculoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Update the veiculo
        Veiculo updatedVeiculo = veiculoRepository.findById(veiculo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVeiculo are not directly saved in db
        em.detach(updatedVeiculo);
        updatedVeiculo
            .placa(UPDATED_PLACA)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .dataAquisicao(UPDATED_DATA_AQUISICAO)
            .classe(UPDATED_CLASSE)
            .grupo(UPDATED_GRUPO)
            .situcao(UPDATED_SITUCAO);
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(updatedVeiculo);

        restVeiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, veiculoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(veiculoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getPlaca()).isEqualTo(UPDATED_PLACA);
        assertThat(testVeiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVeiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVeiculo.getDataAquisicao()).isEqualTo(UPDATED_DATA_AQUISICAO);
        assertThat(testVeiculo.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testVeiculo.getGrupo()).isEqualTo(UPDATED_GRUPO);
        assertThat(testVeiculo.getSitucao()).isEqualTo(UPDATED_SITUCAO);
    }

    @Test
    @Transactional
    void putNonExistingVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();
        veiculo.setId(longCount.incrementAndGet());

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, veiculoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(veiculoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();
        veiculo.setId(longCount.incrementAndGet());

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeiculoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(veiculoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();
        veiculo.setId(longCount.incrementAndGet());

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeiculoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veiculoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVeiculoWithPatch() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Update the veiculo using partial update
        Veiculo partialUpdatedVeiculo = new Veiculo();
        partialUpdatedVeiculo.setId(veiculo.getId());

        partialUpdatedVeiculo.marca(UPDATED_MARCA).modelo(UPDATED_MODELO).dataAquisicao(UPDATED_DATA_AQUISICAO).classe(UPDATED_CLASSE);

        restVeiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVeiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVeiculo))
            )
            .andExpect(status().isOk());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getPlaca()).isEqualTo(DEFAULT_PLACA);
        assertThat(testVeiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVeiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVeiculo.getDataAquisicao()).isEqualTo(UPDATED_DATA_AQUISICAO);
        assertThat(testVeiculo.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testVeiculo.getGrupo()).isEqualTo(DEFAULT_GRUPO);
        assertThat(testVeiculo.getSitucao()).isEqualTo(DEFAULT_SITUCAO);
    }

    @Test
    @Transactional
    void fullUpdateVeiculoWithPatch() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();

        // Update the veiculo using partial update
        Veiculo partialUpdatedVeiculo = new Veiculo();
        partialUpdatedVeiculo.setId(veiculo.getId());

        partialUpdatedVeiculo
            .placa(UPDATED_PLACA)
            .marca(UPDATED_MARCA)
            .modelo(UPDATED_MODELO)
            .dataAquisicao(UPDATED_DATA_AQUISICAO)
            .classe(UPDATED_CLASSE)
            .grupo(UPDATED_GRUPO)
            .situcao(UPDATED_SITUCAO);

        restVeiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVeiculo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVeiculo))
            )
            .andExpect(status().isOk());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
        Veiculo testVeiculo = veiculoList.get(veiculoList.size() - 1);
        assertThat(testVeiculo.getPlaca()).isEqualTo(UPDATED_PLACA);
        assertThat(testVeiculo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testVeiculo.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testVeiculo.getDataAquisicao()).isEqualTo(UPDATED_DATA_AQUISICAO);
        assertThat(testVeiculo.getClasse()).isEqualTo(UPDATED_CLASSE);
        assertThat(testVeiculo.getGrupo()).isEqualTo(UPDATED_GRUPO);
        assertThat(testVeiculo.getSitucao()).isEqualTo(UPDATED_SITUCAO);
    }

    @Test
    @Transactional
    void patchNonExistingVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();
        veiculo.setId(longCount.incrementAndGet());

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, veiculoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(veiculoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();
        veiculo.setId(longCount.incrementAndGet());

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeiculoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(veiculoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVeiculo() throws Exception {
        int databaseSizeBeforeUpdate = veiculoRepository.findAll().size();
        veiculo.setId(longCount.incrementAndGet());

        // Create the Veiculo
        VeiculoDTO veiculoDTO = veiculoMapper.toDto(veiculo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeiculoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(veiculoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Veiculo in the database
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVeiculo() throws Exception {
        // Initialize the database
        veiculoRepository.saveAndFlush(veiculo);

        int databaseSizeBeforeDelete = veiculoRepository.findAll().size();

        // Delete the veiculo
        restVeiculoMockMvc
            .perform(delete(ENTITY_API_URL_ID, veiculo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Veiculo> veiculoList = veiculoRepository.findAll();
        assertThat(veiculoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
