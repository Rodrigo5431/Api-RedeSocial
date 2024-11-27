package br.org.serratec.grupo4.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PostagemInserirDTO {

    @NotNull(message = "Conteudo não pode estar vazio!!")
    @NotBlank(message = "Conteudo não pode estar em branco!!")
    @Size(max = 400, message = "Conteudo não pode ultraprassar o limite de (max) caracteres!!")
    @Schema(description = "Conteudo")
    private String conteudo;

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
