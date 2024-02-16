package br.com.rentalsystem.locacoes.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.rentalsystem.locacoes.domain.Contrato} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContratoDTO implements Serializable {

    private Long id;

    private String taxaDoDia;

    private Long kmInicial;

    private Long kmFinal;

    @NotNull
    private Instant periodo;

    @NotNull
    private Instant contratoDataInicio;

    @NotNull
    private Instant contratoDataFinal;

    private Instant contratoDataRetirada;

    private Instant contratoDataEntrega;

    private ClienteDTO cliente;

    private VeiculoDTO veiculo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxaDoDia() {
        return taxaDoDia;
    }

    public void setTaxaDoDia(String taxaDoDia) {
        this.taxaDoDia = taxaDoDia;
    }

    public Long getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(Long kmInicial) {
        this.kmInicial = kmInicial;
    }

    public Long getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(Long kmFinal) {
        this.kmFinal = kmFinal;
    }

    public Instant getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Instant periodo) {
        this.periodo = periodo;
    }

    public Instant getContratoDataInicio() {
        return contratoDataInicio;
    }

    public void setContratoDataInicio(Instant contratoDataInicio) {
        this.contratoDataInicio = contratoDataInicio;
    }

    public Instant getContratoDataFinal() {
        return contratoDataFinal;
    }

    public void setContratoDataFinal(Instant contratoDataFinal) {
        this.contratoDataFinal = contratoDataFinal;
    }

    public Instant getContratoDataRetirada() {
        return contratoDataRetirada;
    }

    public void setContratoDataRetirada(Instant contratoDataRetirada) {
        this.contratoDataRetirada = contratoDataRetirada;
    }

    public Instant getContratoDataEntrega() {
        return contratoDataEntrega;
    }

    public void setContratoDataEntrega(Instant contratoDataEntrega) {
        this.contratoDataEntrega = contratoDataEntrega;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public VeiculoDTO getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoDTO veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratoDTO)) {
            return false;
        }

        ContratoDTO contratoDTO = (ContratoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contratoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratoDTO{" +
            "id=" + getId() +
            ", taxaDoDia='" + getTaxaDoDia() + "'" +
            ", kmInicial=" + getKmInicial() +
            ", kmFinal=" + getKmFinal() +
            ", periodo='" + getPeriodo() + "'" +
            ", contratoDataInicio='" + getContratoDataInicio() + "'" +
            ", contratoDataFinal='" + getContratoDataFinal() + "'" +
            ", contratoDataRetirada='" + getContratoDataRetirada() + "'" +
            ", contratoDataEntrega='" + getContratoDataEntrega() + "'" +
            ", cliente=" + getCliente() +
            ", veiculo=" + getVeiculo() +
            "}";
    }
}
