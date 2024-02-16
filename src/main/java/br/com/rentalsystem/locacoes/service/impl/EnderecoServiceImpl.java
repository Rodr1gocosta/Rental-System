package br.com.rentalsystem.locacoes.service.impl;

import br.com.rentalsystem.locacoes.domain.Endereco;
import br.com.rentalsystem.locacoes.repository.EnderecoRepository;
import br.com.rentalsystem.locacoes.service.EnderecoService;
import br.com.rentalsystem.locacoes.service.dto.EnderecoDTO;
import br.com.rentalsystem.locacoes.service.mapper.EnderecoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.rentalsystem.locacoes.domain.Endereco}.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    @Override
    public EnderecoDTO update(EnderecoDTO enderecoDTO) {
        log.debug("Request to update Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    @Override
    public Optional<EnderecoDTO> partialUpdate(EnderecoDTO enderecoDTO) {
        log.debug("Request to partially update Endereco : {}", enderecoDTO);

        return enderecoRepository
            .findById(enderecoDTO.getId())
            .map(existingEndereco -> {
                enderecoMapper.partialUpdate(existingEndereco, enderecoDTO);

                return existingEndereco;
            })
            .map(enderecoRepository::save)
            .map(enderecoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findAll() {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll().stream().map(enderecoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the enderecos where Cliente is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findAllWhereClienteIsNull() {
        log.debug("Request to get all enderecos where Cliente is null");
        return StreamSupport
            .stream(enderecoRepository.findAll().spliterator(), false)
            .filter(endereco -> endereco.getCliente() == null)
            .map(enderecoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id).map(enderecoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
