package br.com.rentalsystem.locacoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contrato.
 */
@Entity
@Table(name = "contrato")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "taxa_do_dia")
    private String taxaDoDia;

    @Column(name = "km_inicial")
    private Long kmInicial;

    @Column(name = "km_final")
    private Long kmFinal;

    @NotNull
    @Column(name = "periodo", nullable = false)
    private Instant periodo;

    @NotNull
    @Column(name = "contrato_data_inicio", nullable = false)
    private Instant contratoDataInicio;

    @NotNull
    @Column(name = "contrato_data_final", nullable = false)
    private Instant contratoDataFinal;

    @Column(name = "contrato_data_retirada")
    private Instant contratoDataRetirada;

    @Column(name = "contrato_data_entrega")
    private Instant contratoDataEntrega;

    @JsonIgnoreProperties(value = { "endereco", "contrato" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Cliente cliente;

    @JsonIgnoreProperties(value = { "contrato" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Veiculo veiculo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contrato id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxaDoDia() {
        return this.taxaDoDia;
    }

    public Contrato taxaDoDia(String taxaDoDia) {
        this.setTaxaDoDia(taxaDoDia);
        return this;
    }

    public void setTaxaDoDia(String taxaDoDia) {
        this.taxaDoDia = taxaDoDia;
    }

    public Long getKmInicial() {
        return this.kmInicial;
    }

    public Contrato kmInicial(Long kmInicial) {
        this.setKmInicial(kmInicial);
        return this;
    }

    public void setKmInicial(Long kmInicial) {
        this.kmInicial = kmInicial;
    }

    public Long getKmFinal() {
        return this.kmFinal;
    }

    public Contrato kmFinal(Long kmFinal) {
        this.setKmFinal(kmFinal);
        return this;
    }

    public void setKmFinal(Long kmFinal) {
        this.kmFinal = kmFinal;
    }

    public Instant getPeriodo() {
        return this.periodo;
    }

    public Contrato periodo(Instant periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public void setPeriodo(Instant periodo) {
        this.periodo = periodo;
    }

    public Instant getContratoDataInicio() {
        return this.contratoDataInicio;
    }

    public Contrato contratoDataInicio(Instant contratoDataInicio) {
        this.setContratoDataInicio(contratoDataInicio);
        return this;
    }

    public void setContratoDataInicio(Instant contratoDataInicio) {
        this.contratoDataInicio = contratoDataInicio;
    }

    public Instant getContratoDataFinal() {
        return this.contratoDataFinal;
    }

    public Contrato contratoDataFinal(Instant contratoDataFinal) {
        this.setContratoDataFinal(contratoDataFinal);
        return this;
    }

    public void setContratoDataFinal(Instant contratoDataFinal) {
        this.contratoDataFinal = contratoDataFinal;
    }

    public Instant getContratoDataRetirada() {
        return this.contratoDataRetirada;
    }

    public Contrato contratoDataRetirada(Instant contratoDataRetirada) {
        this.setContratoDataRetirada(contratoDataRetirada);
        return this;
    }

    public void setContratoDataRetirada(Instant contratoDataRetirada) {
        this.contratoDataRetirada = contratoDataRetirada;
    }

    public Instant getContratoDataEntrega() {
        return this.contratoDataEntrega;
    }

    public Contrato contratoDataEntrega(Instant contratoDataEntrega) {
        this.setContratoDataEntrega(contratoDataEntrega);
        return this;
    }

    public void setContratoDataEntrega(Instant contratoDataEntrega) {
        this.contratoDataEntrega = contratoDataEntrega;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Contrato cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public Veiculo getVeiculo() {
        return this.veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Contrato veiculo(Veiculo veiculo) {
        this.setVeiculo(veiculo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrato)) {
            return false;
        }
        return getId() != null && getId().equals(((Contrato) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contrato{" +
            "id=" + getId() +
            ", taxaDoDia='" + getTaxaDoDia() + "'" +
            ", kmInicial=" + getKmInicial() +
            ", kmFinal=" + getKmFinal() +
            ", periodo='" + getPeriodo() + "'" +
            ", contratoDataInicio='" + getContratoDataInicio() + "'" +
            ", contratoDataFinal='" + getContratoDataFinal() + "'" +
            ", contratoDataRetirada='" + getContratoDataRetirada() + "'" +
            ", contratoDataEntrega='" + getContratoDataEntrega() + "'" +
            "}";
    }
}
