package br.com.rentalsystem.locacoes.domain;

import static br.com.rentalsystem.locacoes.domain.ClienteTestSamples.*;
import static br.com.rentalsystem.locacoes.domain.ContratoTestSamples.*;
import static br.com.rentalsystem.locacoes.domain.VeiculoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.rentalsystem.locacoes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContratoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contrato.class);
        Contrato contrato1 = getContratoSample1();
        Contrato contrato2 = new Contrato();
        assertThat(contrato1).isNotEqualTo(contrato2);

        contrato2.setId(contrato1.getId());
        assertThat(contrato1).isEqualTo(contrato2);

        contrato2 = getContratoSample2();
        assertThat(contrato1).isNotEqualTo(contrato2);
    }

    @Test
    void clienteTest() throws Exception {
        Contrato contrato = getContratoRandomSampleGenerator();
        Cliente clienteBack = getClienteRandomSampleGenerator();

        contrato.setCliente(clienteBack);
        assertThat(contrato.getCliente()).isEqualTo(clienteBack);

        contrato.cliente(null);
        assertThat(contrato.getCliente()).isNull();
    }

    @Test
    void veiculoTest() throws Exception {
        Contrato contrato = getContratoRandomSampleGenerator();
        Veiculo veiculoBack = getVeiculoRandomSampleGenerator();

        contrato.setVeiculo(veiculoBack);
        assertThat(contrato.getVeiculo()).isEqualTo(veiculoBack);

        contrato.veiculo(null);
        assertThat(contrato.getVeiculo()).isNull();
    }
}
