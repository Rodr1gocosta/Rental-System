package br.com.rentalsystem.locacoes.service.mapper;

import br.com.rentalsystem.locacoes.domain.Cliente;
import br.com.rentalsystem.locacoes.domain.Contrato;
import br.com.rentalsystem.locacoes.domain.Veiculo;
import br.com.rentalsystem.locacoes.service.dto.ClienteDTO;
import br.com.rentalsystem.locacoes.service.dto.ContratoDTO;
import br.com.rentalsystem.locacoes.service.dto.VeiculoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contrato} and its DTO {@link ContratoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContratoMapper extends EntityMapper<ContratoDTO, Contrato> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    @Mapping(target = "veiculo", source = "veiculo", qualifiedByName = "veiculoId")
    ContratoDTO toDto(Contrato s);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);

    @Named("veiculoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VeiculoDTO toDtoVeiculoId(Veiculo veiculo);
}
