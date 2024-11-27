package br.org.serratec.grupo4.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.grupo4.domain.Comentario;
import br.org.serratec.grupo4.domain.Postagem;
import br.org.serratec.grupo4.domain.Usuario;
import br.org.serratec.grupo4.dto.ComentarioDTO;
import br.org.serratec.grupo4.dto.ComentarioInserirDTO;
import br.org.serratec.grupo4.exception.DadoNaoEncontradoException;
import br.org.serratec.grupo4.exception.IdUsuarioInvalido;
import br.org.serratec.grupo4.exception.ProprietarioIncompativelException;
import br.org.serratec.grupo4.exception.RelacionamentoException;
import br.org.serratec.grupo4.repository.ComentarioRepository;
import br.org.serratec.grupo4.repository.PostagemRepository;
import br.org.serratec.grupo4.repository.UsuarioRepository;
import br.org.serratec.grupo4.security.JwtUtil;

@Service
public class ComentarioService {

	@Autowired
	ComentarioRepository comentarioRepository;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PostagemRepository postagemRepository;

	public List<ComentarioDTO> buscarTodos() {
		List<Comentario> comentarios = comentarioRepository.findAll();
		List<ComentarioDTO> comentariosDTO = comentarios.stream().map(ComentarioDTO::new).toList();
		return comentariosDTO;
	}

	public Optional<ComentarioDTO> buscarPorId(Long id) {
		Optional<Comentario> comentario = comentarioRepository.findById(id);
		Optional<ComentarioDTO> comentarioDTO = Optional.ofNullable(new ComentarioDTO(comentario.get()));
		return comentarioDTO;
	}

	public ComentarioDTO inserir(ComentarioInserirDTO comentarioInserirDTO, String bearerToken)
			throws IdUsuarioInvalido {

		Long idUsuario = jwtUtil.getId(bearerToken);
		Optional<Usuario> usuarioOPT = usuarioRepository.findById(idUsuario);
		if (usuarioOPT.isEmpty()) {
			throw new IdUsuarioInvalido("Seu Usuário não foi encontrado");
		}

		Long idPostagem = comentarioInserirDTO.getIdPostagem();
		Optional<Postagem> postagemOPT = postagemRepository.findById(idPostagem);
		if (postagemOPT.isEmpty()) {
			throw new DadoNaoEncontradoException("Postagem não encontrada");
		}

		Postagem postagem = postagemOPT.get();
		Long idUsuarioPostagem = postagem.getUsuario().getId();
		Usuario usuario = usuarioOPT.get();
		if (!usuario.getSeguidos().stream().anyMatch(r -> r.getId().getSeguido().getId().equals(idUsuarioPostagem))) {
			throw new RelacionamentoException("Você so pode comentar em postagens de usuarios que você segue");
		}

		Comentario comentario = new Comentario();
		comentario.setTexto(comentarioInserirDTO.getTexto());
		comentario.setDataCriacao(LocalDate.now());
		comentario.setUsuario(usuario);
		comentario.setPostagem(postagem);

		comentario = comentarioRepository.save(comentario);

		ComentarioDTO comentarioDTO = new ComentarioDTO(comentario);
		return comentarioDTO;
	}

	public ComentarioDTO atualizar(Long id, ComentarioInserirDTO comentarioInserirDTO, String bearerToken)
			throws DadoNaoEncontradoException, ProprietarioIncompativelException {

		Optional<Comentario> comentarioOPT = comentarioRepository.findById(id);

		if (comentarioOPT.isEmpty()) {
			throw new DadoNaoEncontradoException("Comentario não encontrado");
		}
		Long idtoken = jwtUtil.getId(bearerToken);

		if (!comentarioOPT.get().getUsuario().getId().equals(idtoken)) {
			throw new ProprietarioIncompativelException("Voce so pode alterar suas proprias postagens");
		}

		Comentario comentario = comentarioOPT.get();
		comentario.setId(id);
		comentario.setTexto(comentarioInserirDTO.getTexto());

		comentario = comentarioRepository.save(comentario);

		ComentarioDTO comentarioDTO = new ComentarioDTO(comentario);
		return comentarioDTO;
	}

	public void deletar(Long id, String bearerToken) {
		Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
		if (comentarioOpt.isEmpty()) {
			throw new IdUsuarioInvalido("Id do comentario não encontrado");
		}

		Long idtoken = jwtUtil.getId(bearerToken);
		if (!id.equals(idtoken)) {
			throw new ProprietarioIncompativelException("Voce so pode alterar seu proprio comentario");
		}

		comentarioRepository.deleteById(id);
	}

	public List<Map<String, Object>> getNomeEDataComentarioByPostagemId(Long postagemId) {
		return comentarioRepository.findNomeEDataComentarioByPostagemId(postagemId);
	}
}
