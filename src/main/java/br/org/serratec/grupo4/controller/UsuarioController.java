package br.org.serratec.grupo4.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.serratec.grupo4.domain.Foto;
import br.org.serratec.grupo4.domain.Usuario;
import br.org.serratec.grupo4.dto.UsuarioDTO;
import br.org.serratec.grupo4.dto.UsuarioInserirDTO;
import br.org.serratec.grupo4.exception.IdUsuarioInvalido;
import br.org.serratec.grupo4.repository.UsuarioRepository;
import br.org.serratec.grupo4.service.FotoService;
import br.org.serratec.grupo4.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FotoService fotoService;

	@Operation(summary = "📝 Lista todos os usuarios", description = "Todos os Usuarios")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		return ResponseEntity.ok(usuarioService.listarUsuarios());
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "📖 Lista Paginado", description = ":)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })
	@GetMapping("/pagina")
	public ResponseEntity<Page<UsuarioDTO>> listarPaginado(
			@PageableDefault(direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
		Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
		Page<UsuarioDTO> usuariosDTO = usuarios.map(usuario -> new UsuarioDTO(usuario));
		return ResponseEntity.ok(usuariosDTO);
	}

	////////////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "🔎 Busca a Foto do Usuario pelo Id 🤳", description = "Verifique se o id está correto :)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
        @ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|")})
    @GetMapping("/{id}/foto")
	public ResponseEntity<?> buscarFoto(@PathVariable Long id) {
	    Foto foto = fotoService.buscarPorIdUsuario(id);
	    if (foto == null) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body("A foto não foi encontrada.");
	    }

	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, foto.getTipo());
	    headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(foto.getDados().length));

	    return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);

	}

	//////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "🔎 Busca o usuario pelo Id", description = "Verifique se o id está correto :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		try {
			UsuarioDTO usuarioOpt = usuarioService.buscarPorId(id);
			return ResponseEntity.ok(usuarioOpt);

		} catch (IdUsuarioInvalido e) {
			return ResponseEntity.notFound().build();
		}

	}

	////////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "📧 Busca o usuario pelo Email", description = "Verifique se o id está correto :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })

	@GetMapping("/email/{email}")
	public ResponseEntity<Optional<UsuarioDTO>> buscarPorEmail(@PathVariable String email) {
		Optional<UsuarioDTO> usuarioDTO = usuarioService.buscarPorEmail(email);
		if (usuarioDTO.isPresent()) {
			return ResponseEntity.ok(usuarioDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	///////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "👤 Busca o usuario pelo Nome", description = "Verifique se o id está correto :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Optional<UsuarioDTO>> buscarPorNome(@PathVariable String nome) {
		Optional<UsuarioDTO> usuarioDTO = usuarioService.buscarPorNome(nome);
		if (usuarioDTO.isPresent()) {
			return ResponseEntity.ok(usuarioDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "📚 Inserir Usuario", description = "Verifique se o id está correto :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<UsuarioDTO> inserir(@RequestPart(value = "file", required = false) MultipartFile file,
			@RequestPart UsuarioInserirDTO usuario) throws IOException {
		try {
			return ResponseEntity.ok(usuarioService.inserir(usuario, file));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "🔢 Atualiza o usuario pelo id", description = "Verifique se o id está correto :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })

	@PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestPart UsuarioInserirDTO usuario,
			@RequestHeader("Authorization") String token, @RequestPart MultipartFile file) {

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO = usuarioService.atualizar(usuario, id, token, file);

		return ResponseEntity.ok(usuarioDTO);
	}

	////////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "❌ Deleta o usuario pelo id", description = "Verifique se o id está correto :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|") })

 @DeleteMapping("/{id}")
        public ResponseEntity<String> deletar(@PathVariable Long id) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok("ok");
        }
}
