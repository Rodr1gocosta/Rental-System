package br.com.rentalsystem.locacoes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "telefone_1", nullable = false)
    private String telefone1;

    @Column(name = "telefone_2")
    private String telefone2;

    @NotNull
    @Size(max = 30)
    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @NotNull
    @Size(max = 11)
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @NotNull
    @Size(max = 10)
    @Column(name = "rg", length = 10, nullable = false)
    private String rg;

    @NotNull
    @Size(max = 12)
    @Column(name = "n_habilidacao", length = 12, nullable = false)
    private String nHabilidacao;

    @NotNull
    @Column(name = "estado_emissao_habilitacao", nullable = false)
    private LocalDate estadoEmissaoHabilitacao;

    @NotNull
    @Column(name = "validade_habilitacao", nullable = false)
    private LocalDate validadeHabilitacao;

    @JsonIgnoreProperties(value = { "cliente" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Endereco endereco;

    @JsonIgnoreProperties(value = { "cliente", "veiculo" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente")
    private Contrato contrato;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cliente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cliente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone1() {
        return this.telefone1;
    }

    public Cliente telefone1(String telefone1) {
        this.setTelefone1(telefone1);
        return this;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return this.telefone2;
    }

    public Cliente telefone2(String telefone2) {
        this.setTelefone2(telefone2);
        return this;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return this.email;
    }

    public Cliente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Cliente cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return this.rg;
    }

    public Cliente rg(String rg) {
        this.setRg(rg);
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getnHabilidacao() {
        return this.nHabilidacao;
    }

    public Cliente nHabilidacao(String nHabilidacao) {
        this.setnHabilidacao(nHabilidacao);
        return this;
    }

    public void setnHabilidacao(String nHabilidacao) {
        this.nHabilidacao = nHabilidacao;
    }

    public LocalDate getEstadoEmissaoHabilitacao() {
        return this.estadoEmissaoHabilitacao;
    }

    public Cliente estadoEmissaoHabilitacao(LocalDate estadoEmissaoHabilitacao) {
        this.setEstadoEmissaoHabilitacao(estadoEmissaoHabilitacao);
        return this;
    }

    public void setEstadoEmissaoHabilitacao(LocalDate estadoEmissaoHabilitacao) {
        this.estadoEmissaoHabilitacao = estadoEmissaoHabilitacao;
    }

    public LocalDate getValidadeHabilitacao() {
        return this.validadeHabilitacao;
    }

    public Cliente validadeHabilitacao(LocalDate validadeHabilitacao) {
        this.setValidadeHabilitacao(validadeHabilitacao);
        return this;
    }

    public void setValidadeHabilitacao(LocalDate validadeHabilitacao) {
        this.validadeHabilitacao = validadeHabilitacao;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cliente endereco(Endereco endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public Contrato getContrato() {
        return this.contrato;
    }

    public void setContrato(Contrato contrato) {
        if (this.contrato != null) {
            this.contrato.setCliente(null);
        }
        if (contrato != null) {
            contrato.setCliente(this);
        }
        this.contrato = contrato;
    }

    public Cliente contrato(Contrato contrato) {
        this.setContrato(contrato);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return getId() != null && getId().equals(((Cliente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone1='" + getTelefone1() + "'" +
            ", telefone2='" + getTelefone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", rg='" + getRg() + "'" +
            ", nHabilidacao='" + getnHabilidacao() + "'" +
            ", estadoEmissaoHabilitacao='" + getEstadoEmissaoHabilitacao() + "'" +
            ", validadeHabilitacao='" + getValidadeHabilitacao() + "'" +
            "}";
    }
}
