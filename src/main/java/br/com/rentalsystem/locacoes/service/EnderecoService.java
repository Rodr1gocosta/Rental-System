package br.com.rentalsystem.locacoes.service;

import br.com.rentalsystem.locacoes.service.dto.EnderecoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.com.rentalsystem.locacoes.domain.Endereco}.
 */
public interface EnderecoService {
    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save.
     * @return the persisted entity.
     */
    EnderecoDTO save(EnderecoDTO enderecoDTO);

    /**
     * Updates a endereco.
     *
     * @param enderecoDTO the entity to update.
     * @return the persisted entity.
     */
    EnderecoDTO update(EnderecoDTO enderecoDTO);

    /**
     * Partially updates a endereco.
     *
     * @param enderecoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EnderecoDTO> partialUpdate(EnderecoDTO enderecoDTO);

    /**
     * Get all the enderecos.
     *
     * @return the list of entities.
     */
    List<EnderecoDTO> findAll();

    /**
     * Get all the EnderecoDTO where Cliente is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<EnderecoDTO> findAllWhereClienteIsNull();

    /**
     * Get the "id" endereco.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnderecoDTO> findOne(Long id);

    /**
     * Delete the "id" endereco.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
