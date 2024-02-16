package br.com.rentalsystem.locacoes.service.impl;

import br.com.rentalsystem.locacoes.domain.Veiculo;
import br.com.rentalsystem.locacoes.repository.VeiculoRepository;
import br.com.rentalsystem.locacoes.service.VeiculoService;
import br.com.rentalsystem.locacoes.service.dto.VeiculoDTO;
import br.com.rentalsystem.locacoes.service.mapper.VeiculoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link br.com.rentalsystem.locacoes.domain.Veiculo}.
 */
@Service
@Transactional
public class VeiculoServiceImpl implements VeiculoService {

    private final Logger log = LoggerFactory.getLogger(VeiculoServiceImpl.class);

    private final VeiculoRepository veiculoRepository;

    private final VeiculoMapper veiculoMapper;

    public VeiculoServiceImpl(VeiculoRepository veiculoRepository, VeiculoMapper veiculoMapper) {
        this.veiculoRepository = veiculoRepository;
        this.veiculoMapper = veiculoMapper;
    }

    @Override
    public VeiculoDTO save(VeiculoDTO veiculoDTO) {
        log.debug("Request to save Veiculo : {}", veiculoDTO);
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDTO);
        veiculo = veiculoRepository.save(veiculo);
        return veiculoMapper.toDto(veiculo);
    }

    @Override
    public VeiculoDTO update(VeiculoDTO veiculoDTO) {
        log.debug("Request to update Veiculo : {}", veiculoDTO);
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDTO);
        veiculo = veiculoRepository.save(veiculo);
        return veiculoMapper.toDto(veiculo);
    }

    @Override
    public Optional<VeiculoDTO> partialUpdate(VeiculoDTO veiculoDTO) {
        log.debug("Request to partially update Veiculo : {}", veiculoDTO);

        return veiculoRepository
            .findById(veiculoDTO.getId())
            .map(existingVeiculo -> {
                veiculoMapper.partialUpdate(existingVeiculo, veiculoDTO);

                return existingVeiculo;
            })
            .map(veiculoRepository::save)
            .map(veiculoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VeiculoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Veiculos");
        return veiculoRepository.findAll(pageable).map(veiculoMapper::toDto);
    }

    /**
     *  Get all the veiculos where Contrato is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VeiculoDTO> findAllWhereContratoIsNull() {
        log.debug("Request to get all veiculos where Contrato is null");
        return StreamSupport
            .stream(veiculoRepository.findAll().spliterator(), false)
            .filter(veiculo -> veiculo.getContrato() == null)
            .map(veiculoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VeiculoDTO> findOne(Long id) {
        log.debug("Request to get Veiculo : {}", id);
        return veiculoRepository.findById(id).map(veiculoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Veiculo : {}", id);
        veiculoRepository.deleteById(id);
    }
}
