package br.org.serratec.grupo4.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ComentarioInserirDTO {

	@NotNull(message = "ID da Postagem não pode estar vazio!!")
	private Long idPostagem;

    @NotBlank(message = "Comentário não pode estar em branco!!")
    @Size(max = 400, message = "Comentario não pode ultraprassar o limite de (max) caracteres!!")
    @Schema(description = "Conteúdo do comentario")
    private String texto;

   public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
    }
}
