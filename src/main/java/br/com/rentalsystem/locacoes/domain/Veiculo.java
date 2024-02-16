package br.com.rentalsystem.locacoes.domain;

import br.com.rentalsystem.locacoes.domain.enumeration.Classe;
import br.com.rentalsystem.locacoes.domain.enumeration.Grupo;
import br.com.rentalsystem.locacoes.domain.enumeration.Situacao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Veiculo.
 */
@Entity
@Table(name = "veiculo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 7)
    @Column(name = "placa", length = 7, nullable = false)
    private String placa;

    @NotNull
    @Size(max = 20)
    @Column(name = "marca", length = 20, nullable = false)
    private String marca;

    @NotNull
    @Size(max = 20)
    @Column(name = "modelo", length = 20, nullable = false)
    private String modelo;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "classe", nullable = false)
    private Classe classe;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grupo", nullable = false)
    private Grupo grupo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situcao", nullable = false)
    private Situacao situcao;

    @JsonIgnoreProperties(value = { "cliente", "veiculo" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "veiculo")
    private Contrato contrato;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Veiculo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return this.placa;
    }

    public Veiculo placa(String placa) {
        this.setPlaca(placa);
        return this;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return this.marca;
    }

    public Veiculo marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Veiculo modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDate getDataAquisicao() {
        return this.dataAquisicao;
    }

    public Veiculo dataAquisicao(LocalDate dataAquisicao) {
        this.setDataAquisicao(dataAquisicao);
        return this;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Classe getClasse() {
        return this.classe;
    }

    public Veiculo classe(Classe classe) {
        this.setClasse(classe);
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public Veiculo grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Situacao getSitucao() {
        return this.situcao;
    }

    public Veiculo situcao(Situacao situcao) {
        this.setSitucao(situcao);
        return this;
    }

    public void setSitucao(Situacao situcao) {
        this.situcao = situcao;
    }

    public Contrato getContrato() {
        return this.contrato;
    }

    public void setContrato(Contrato contrato) {
        if (this.contrato != null) {
            this.contrato.setVeiculo(null);
        }
        if (contrato != null) {
            contrato.setVeiculo(this);
        }
        this.contrato = contrato;
    }

    public Veiculo contrato(Contrato contrato) {
        this.setContrato(contrato);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Veiculo)) {
            return false;
        }
        return getId() != null && getId().equals(((Veiculo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Veiculo{" +
            "id=" + getId() +
            ", placa='" + getPlaca() + "'" +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", dataAquisicao='" + getDataAquisicao() + "'" +
            ", classe='" + getClasse() + "'" +
            ", grupo='" + getGrupo() + "'" +
            ", situcao='" + getSitucao() + "'" +
            "}";
    }
}
