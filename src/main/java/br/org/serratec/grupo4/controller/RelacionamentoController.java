package br.org.serratec.grupo4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.grupo4.dto.RelacionamentoDTO;
import br.org.serratec.grupo4.dto.SeguindoDTO;
import br.org.serratec.grupo4.service.RelacionamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/relacionamentos")
public class RelacionamentoController {

	@Autowired
	private RelacionamentoService relacionamentoService;

	@Operation(summary = "💭 Me Segue", description = "Lista os Seguidores :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|"), 
	 })
	@GetMapping("/seguem")
	public ResponseEntity<List<RelacionamentoDTO>> meusSeguidores(@RequestHeader("Authorization") String token) {
		List<RelacionamentoDTO> seguidores = relacionamentoService.ListarSeguidoresUsuario(token);
		return ResponseEntity.ok(seguidores);
	}
//////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "💭 To Seguindo", description = "Lista quem o usuario segue :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|"),
	       })
	@GetMapping("/sigo")
	public ResponseEntity<List<RelacionamentoDTO>> Seguindo(@RequestHeader("Authorization") String token) {
		List<RelacionamentoDTO> seguidores = relacionamentoService.ListarSeguindoUsuario(token);
		return ResponseEntity.ok(seguidores);
	}
//////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "😁 Seguir Usuario", description = "Seguir novas pessoas :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|"),
			@ApiResponse(responseCode = "409", description = "Você ja segue este Usuario (>‿◠)✌ ")})

	@PostMapping("/{idSeguido}")
	public ResponseEntity<SeguindoDTO> seguirUsuario(@RequestHeader("Authorization") String token,
			@PathVariable Long idSeguido) {
		SeguindoDTO seguindo = relacionamentoService.seguirUsuario(token, idSeguido);
		return ResponseEntity.ok(seguindo);
	}
//////////////////////////////////////////////////////////////////////////////////////////////

	@Operation(summary = "🙄 Deixar de Seguir", description = "Gente Chata!! Deixa de seguir mesmo :)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operação efetuada com sucesso ｡◕‿◕｡"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação (•ิ_•ิ)"),
			@ApiResponse(responseCode = "404", description = "Recurso não encontrado ⊙▂⊙"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação |˚–˚|"),
	        @ApiResponse(responseCode = "409", description = "Você ja deixou de seguir este Usuário (>‿◠)✌")})
	@DeleteMapping("/{idSeguido}")
	public String deixarSeguir(@RequestHeader("Authorization") String token, @PathVariable Long idSeguido) {
		relacionamentoService.deixarSeguir(token, idSeguido);
		String mensagem = "Voce deixou de seguir essa pessoa!";
		return mensagem;
	}

}
