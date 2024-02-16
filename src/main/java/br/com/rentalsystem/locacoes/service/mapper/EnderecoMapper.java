package br.com.rentalsystem.locacoes.service.mapper;

import br.com.rentalsystem.locacoes.domain.Endereco;
import br.com.rentalsystem.locacoes.service.dto.EnderecoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {}
