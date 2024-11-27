package br.org.serratec.grupo4.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.org.serratec.grupo4.domain.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

public class UsuarioDTO {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "Nome")
    private String nome;

    @Schema(description = "descricao")
    private String descricao;

    @Schema(description = "Email")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @Column(name = "url")
    private String url;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nome, String descricao, String email, LocalDate dataNascimento, String url) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.url = url;
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.descricao = usuario.getDescricao();
        this.email = usuario.getEmail();
        this.dataNascimento = usuario.getDataNascimento();
        this.url = usuario.getUrl();
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
