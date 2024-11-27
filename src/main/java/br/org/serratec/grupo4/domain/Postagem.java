package br.org.serratec.grupo4.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Valid
public class Postagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Schema(description="Id da Postagem")
	private Long id;
	

	@Column(name = "conteudo", nullable = false, length = 400)	
	@Schema(description="Conteudo da Postagem")
	private String conteudo;
	

	@Column(name = "data_criacao", nullable = false, updatable = true)
	@Schema(description="Data de Criação da Postagem")
	private LocalDate dataCriacao;


	@OneToMany(mappedBy = "postagem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Schema(description="Comentario Usuario")
	private List<Comentario> comentarios = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	@Schema(description="Id Usuario")
	private Usuario usuario; // id do usuário que fez a postagem 

	
	

	
}
