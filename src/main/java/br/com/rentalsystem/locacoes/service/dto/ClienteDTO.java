package br.com.rentalsystem.locacoes.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.rentalsystem.locacoes.domain.Cliente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String telefone1;

    private String telefone2;

    @NotNull
    @Size(max = 30)
    private String email;

    @NotNull
    @Size(max = 11)
    private String cpf;

    @NotNull
    @Size(max = 10)
    private String rg;

    @NotNull
    @Size(max = 12)
    private String nHabilidacao;

    @NotNull
    private LocalDate estadoEmissaoHabilitacao;

    @NotNull
    private LocalDate validadeHabilitacao;

    private EnderecoDTO endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getnHabilidacao() {
        return nHabilidacao;
    }

    public void setnHabilidacao(String nHabilidacao) {
        this.nHabilidacao = nHabilidacao;
    }

    public LocalDate getEstadoEmissaoHabilitacao() {
        return estadoEmissaoHabilitacao;
    }

    public void setEstadoEmissaoHabilitacao(LocalDate estadoEmissaoHabilitacao) {
        this.estadoEmissaoHabilitacao = estadoEmissaoHabilitacao;
    }

    public LocalDate getValidadeHabilitacao() {
        return validadeHabilitacao;
    }

    public void setValidadeHabilitacao(LocalDate validadeHabilitacao) {
        this.validadeHabilitacao = validadeHabilitacao;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteDTO)) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteDTO{" +
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
            ", endereco=" + getEndereco() +
            "}";
    }
}
