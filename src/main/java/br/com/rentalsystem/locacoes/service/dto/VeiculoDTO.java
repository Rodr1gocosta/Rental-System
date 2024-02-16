package br.com.rentalsystem.locacoes.service.dto;

import br.com.rentalsystem.locacoes.domain.enumeration.Classe;
import br.com.rentalsystem.locacoes.domain.enumeration.Grupo;
import br.com.rentalsystem.locacoes.domain.enumeration.Situacao;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.rentalsystem.locacoes.domain.Veiculo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VeiculoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 7)
    private String placa;

    @NotNull
    @Size(max = 20)
    private String marca;

    @NotNull
    @Size(max = 20)
    private String modelo;

    private LocalDate dataAquisicao;

    @NotNull
    private Classe classe;

    @NotNull
    private Grupo grupo;

    @NotNull
    private Situacao situcao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Situacao getSitucao() {
        return situcao;
    }

    public void setSitucao(Situacao situcao) {
        this.situcao = situcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VeiculoDTO)) {
            return false;
        }

        VeiculoDTO veiculoDTO = (VeiculoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, veiculoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VeiculoDTO{" +
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
