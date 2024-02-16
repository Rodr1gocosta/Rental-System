package br.com.rentalsystem.locacoes.service.mapper;

import br.com.rentalsystem.locacoes.domain.Veiculo;
import br.com.rentalsystem.locacoes.service.dto.VeiculoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Veiculo} and its DTO {@link VeiculoDTO}.
 */
@Mapper(componentModel = "spring")
public interface VeiculoMapper extends EntityMapper<VeiculoDTO, Veiculo> {}
