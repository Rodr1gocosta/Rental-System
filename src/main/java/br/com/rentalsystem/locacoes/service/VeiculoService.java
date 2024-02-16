package br.com.rentalsystem.locacoes.service;

import br.com.rentalsystem.locacoes.service.dto.VeiculoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.rentalsystem.locacoes.domain.Veiculo}.
 */
public interface VeiculoService {
    /**
     * Save a veiculo.
     *
     * @param veiculoDTO the entity to save.
     * @return the persisted entity.
     */
    VeiculoDTO save(VeiculoDTO veiculoDTO);

    /**
     * Updates a veiculo.
     *
     * @param veiculoDTO the entity to update.
     * @return the persisted entity.
     */
    VeiculoDTO update(VeiculoDTO veiculoDTO);

    /**
     * Partially updates a veiculo.
     *
     * @param veiculoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VeiculoDTO> partialUpdate(VeiculoDTO veiculoDTO);

    /**
     * Get all the veiculos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VeiculoDTO> findAll(Pageable pageable);

    /**
     * Get all the VeiculoDTO where Contrato is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<VeiculoDTO> findAllWhereContratoIsNull();

    /**
     * Get the "id" veiculo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VeiculoDTO> findOne(Long id);

    /**
     * Delete the "id" veiculo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
