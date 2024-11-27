package br.org.serratec.grupo4.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.org.serratec.grupo4.domain.Postagem;
import io.swagger.v3.oas.annotations.media.Schema;

public class PostagemDTO {

    @Schema(description = "Nome_Usuario")
    private String usuarioNome;

    @Schema(description = "Id_da_Postagem")
    private Long id;

    @Schema(description = "Texto_do_Conteudo")
    private String conteudo;

    private List<ComentarioDTO> comentarios;

    public PostagemDTO() {
    }

    public PostagemDTO(Long id, String conteudo, List<ComentarioDTO> comentarios, String usuarioNome) {
        super();
        this.id = id;
        this.conteudo = conteudo;
        this.comentarios = comentarios;
        this.usuarioNome = usuarioNome;
    }

    public PostagemDTO(Postagem postagem) {
        this.id = postagem.getId();
        this.conteudo = postagem.getConteudo();
        this.usuarioNome = postagem.getUsuario().getNome();
        this.comentarios = postagem.getComentarios().stream().map(ComentarioDTO::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

}
