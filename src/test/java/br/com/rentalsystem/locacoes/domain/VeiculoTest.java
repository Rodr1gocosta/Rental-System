package br.com.rentalsystem.locacoes.domain;

import static br.com.rentalsystem.locacoes.domain.ContratoTestSamples.*;
import static br.com.rentalsystem.locacoes.domain.VeiculoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.rentalsystem.locacoes.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VeiculoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veiculo.class);
        Veiculo veiculo1 = getVeiculoSample1();
        Veiculo veiculo2 = new Veiculo();
        assertThat(veiculo1).isNotEqualTo(veiculo2);

        veiculo2.setId(veiculo1.getId());
        assertThat(veiculo1).isEqualTo(veiculo2);

        veiculo2 = getVeiculoSample2();
        assertThat(veiculo1).isNotEqualTo(veiculo2);
    }

    @Test
    void contratoTest() throws Exception {
        Veiculo veiculo = getVeiculoRandomSampleGenerator();
        Contrato contratoBack = getContratoRandomSampleGenerator();

        veiculo.setContrato(contratoBack);
        assertThat(veiculo.getContrato()).isEqualTo(contratoBack);
        assertThat(contratoBack.getVeiculo()).isEqualTo(veiculo);

        veiculo.contrato(null);
        assertThat(veiculo.getContrato()).isNull();
        assertThat(contratoBack.getVeiculo()).isNull();
    }
}
