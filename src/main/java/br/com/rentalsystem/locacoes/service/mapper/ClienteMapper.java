package br.com.rentalsystem.locacoes.service.mapper;

import br.com.rentalsystem.locacoes.domain.Cliente;
import br.com.rentalsystem.locacoes.domain.Endereco;
import br.com.rentalsystem.locacoes.service.dto.ClienteDTO;
import br.com.rentalsystem.locacoes.service.dto.EnderecoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "endereco", source = "endereco", qualifiedByName = "enderecoId")
    ClienteDTO toDto(Cliente s);

    @Named("enderecoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EnderecoDTO toDtoEnderecoId(Endereco endereco);
}
