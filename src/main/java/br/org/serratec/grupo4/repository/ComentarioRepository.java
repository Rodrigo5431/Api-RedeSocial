package br.org.serratec.grupo4.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.serratec.grupo4.domain.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query(value = """
			SELECT u.nome AS nome, c.data_criacao AS dataComentario, c.conteudo_coment AS conteudo
			FROM usuario AS u
			INNER JOIN
			comentario AS c
			ON u.id_usuario = c.id_usuario
			WHERE c.id_postagem=:postagemId """, nativeQuery = true)

    List<Map<String, Object>> findNomeEDataComentarioByPostagemId(@Param("postagemId") Long postagemId);

}
