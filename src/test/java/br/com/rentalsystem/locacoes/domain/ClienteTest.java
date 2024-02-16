package br.com.rentalsystem.locacoes.domain;

import static br.com.rentalsystem.locacoes.domain.ClienteTestSamples.*;
import static br.com.rentalsystem.locacoes.domain.ContratoTestSamples.*;
import static br.com.rentalsystem.locacoes.domain.EnderecoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.rentalsystem.locacoes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = getClienteSample1();
        Cliente cliente2 = new Cliente();
        assertThat(cliente1).isNotEqualTo(cliente2);

        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);

        cliente2 = getClienteSample2();
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    void enderecoTest() throws Exception {
        Cliente cliente = getClienteRandomSampleGenerator();
        Endereco enderecoBack = getEnderecoRandomSampleGenerator();

        cliente.setEndereco(enderecoBack);
        assertThat(cliente.getEndereco()).isEqualTo(enderecoBack);

        cliente.endereco(null);
        assertThat(cliente.getEndereco()).isNull();
    }

    @Test
    void contratoTest() throws Exception {
        Cliente cliente = getClienteRandomSampleGenerator();
        Contrato contratoBack = getContratoRandomSampleGenerator();

        cliente.setContrato(contratoBack);
        assertThat(cliente.getContrato()).isEqualTo(contratoBack);
        assertThat(contratoBack.getCliente()).isEqualTo(cliente);

        cliente.contrato(null);
        assertThat(cliente.getContrato()).isNull();
        assertThat(contratoBack.getCliente()).isNull();
    }
}
