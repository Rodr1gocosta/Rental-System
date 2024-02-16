package br.com.rentalsystem.locacoes.domain;

import static br.com.rentalsystem.locacoes.domain.ClienteTestSamples.*;
import static br.com.rentalsystem.locacoes.domain.EnderecoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.rentalsystem.locacoes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Endereco.class);
        Endereco endereco1 = getEnderecoSample1();
        Endereco endereco2 = new Endereco();
        assertThat(endereco1).isNotEqualTo(endereco2);

        endereco2.setId(endereco1.getId());
        assertThat(endereco1).isEqualTo(endereco2);

        endereco2 = getEnderecoSample2();
        assertThat(endereco1).isNotEqualTo(endereco2);
    }

    @Test
    void clienteTest() throws Exception {
        Endereco endereco = getEnderecoRandomSampleGenerator();
        Cliente clienteBack = getClienteRandomSampleGenerator();

        endereco.setCliente(clienteBack);
        assertThat(endereco.getCliente()).isEqualTo(clienteBack);
        assertThat(clienteBack.getEndereco()).isEqualTo(endereco);

        endereco.cliente(null);
        assertThat(endereco.getCliente()).isNull();
        assertThat(clienteBack.getEndereco()).isNull();
    }
}
