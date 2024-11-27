package br.org.serratec.grupo4.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.grupo4.domain.Postagem;
import br.org.serratec.grupo4.domain.Usuario;
import br.org.serratec.grupo4.dto.PostagemDTO;
import br.org.serratec.grupo4.dto.PostagemInserirDTO;
import br.org.serratec.grupo4.exception.DadoNaoEncontradoException;
import br.org.serratec.grupo4.exception.IdUsuarioInvalido;
import br.org.serratec.grupo4.exception.ProprietarioIncompativelException;
import br.org.serratec.grupo4.repository.PostagemRepository;
import br.org.serratec.grupo4.repository.UsuarioRepository;
import br.org.serratec.grupo4.security.JwtUtil;

@Service
public class PostagemService {

	@Autowired
	PostagemRepository postagemRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private JwtUtil jwtUtil;

	public List<PostagemDTO> buscarTodos() {
		List<Postagem> postagems = postagemRepository.findAll();
		List<PostagemDTO> postagemsDTO = postagems.stream().map(PostagemDTO::new).toList();
		return postagemsDTO;
	}

	public List<PostagemDTO> ListarTodasPorUsuario(Long id) throws IdUsuarioInvalido {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isEmpty()) {
			throw new IdUsuarioInvalido("Usuário não encontrado");
		}
		List<Postagem> postagems = usuario.get().getPostagens();
		List<PostagemDTO> postagemsDTO = postagems.stream().map(PostagemDTO::new).toList();
		return postagemsDTO;
	}

	public Optional<PostagemDTO> buscarPorId(Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);
		Optional<PostagemDTO> postagemDto = Optional.ofNullable(new PostagemDTO(postagem.get()));
		return postagemDto;
	}

	public PostagemDTO inserir(PostagemInserirDTO postagemInserirDTO, String bearerToken) throws IdUsuarioInvalido {

		Long id = jwtUtil.getId(bearerToken);
		Optional<Usuario> usuarioOPT = usuarioRepository.findById(id);

		if (usuarioOPT.isEmpty()) {
			throw new IdUsuarioInvalido("Seu Usuário não foi encontrado");
		}

		Postagem postagem = new Postagem();
		postagem.setConteudo(postagemInserirDTO.getConteudo());
		postagem.setDataCriacao(LocalDate.now());
		postagem.setUsuario(usuarioOPT.get());
		postagem.setComentarios(null);
		postagem = postagemRepository.save(postagem);

		PostagemDTO postagemDTO = new PostagemDTO();
		postagemDTO.setUsuarioNome(usuarioOPT.get().getNome());
		postagemDTO.setId(postagem.getId());
		postagemDTO.setConteudo(postagem.getConteudo());
		postagemDTO.setComentarios(null);

		return postagemDTO;
	}

	public PostagemDTO atualizar(Long id, PostagemInserirDTO postagemInserirDTO, String bearerToken)
			throws DadoNaoEncontradoException, ProprietarioIncompativelException {

		Optional<Postagem> postagemOPT = postagemRepository.findById(id);
		if (postagemOPT.isEmpty()) {
			throw new DadoNaoEncontradoException("Postagem não encontrada");
		}

		Long idtoken = jwtUtil.getId(bearerToken);
		Postagem postagem = postagemOPT.get();
		if (!postagem.getUsuario().getId().equals(idtoken)) {
			throw new ProprietarioIncompativelException("Voce so pode alterar suas proprias postagens");
		}

		postagem.setConteudo(postagemInserirDTO.getConteudo());
		postagem = postagemRepository.save(postagem);

		PostagemDTO postagemDTO = new PostagemDTO(postagem);
		return postagemDTO;
	}

	public void deletar(String bearerToken, Long id) {

		Optional<Postagem> postagemOPT = postagemRepository.findById(id);
		if (postagemOPT.isEmpty()) {
			throw new DadoNaoEncontradoException("Postagem não encontrada");
		}

		Long idtoken = jwtUtil.getId(bearerToken);
		Postagem postagem = postagemOPT.get();
		if (!postagem.getUsuario().getId().equals(idtoken)) {
			throw new ProprietarioIncompativelException("Voce so pode deletar suas proprias postagens!");
		}

		postagemRepository.deleteById(id);

	}

}
