package br.org.serratec.grupo4.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long id;

    @NotNull(message = "Os dados da foto não podem ser nulos")
    @Lob
    @Column(columnDefinition = "BLOB")
    @Schema(description = "Dados da Foto")
    private byte[] dados;

    @Schema(description = "Tipo de Foto")
    private String tipo;

    @Schema(description = "Nome da Foto")
    private String nome;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Foto() {
    }

    public Foto(Long id, byte[] dados, String tipo, String nome, Usuario usuario) {
        this.id = id;
        this.dados = dados;
        this.tipo = tipo;
        this.nome = nome;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
