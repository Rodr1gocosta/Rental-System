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
    @Mapping(target = "cep", source = "cep")
    @Mapping(target = "logradouro", source = "logradouro")
    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "complemento", source = "complemento")
    @Mapping(target = "bairro", source = "bairro")
    EnderecoDTO toDtoEnderecoId(Endereco endereco);
}
