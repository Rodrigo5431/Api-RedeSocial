package br.org.serratec.grupo4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.serratec.grupo4.domain.Relacionamento;
import br.org.serratec.grupo4.domain.UsuarioRelacionamentoPK;
import br.org.serratec.grupo4.dto.RelacionamentoDTO;

@Repository
public interface RelacionamentoRepository extends JpaRepository<Relacionamento, UsuarioRelacionamentoPK> {

    @Query(value = """
             SELECT SGD.NOME AS nome_seguidor, SG.NOME AS nome_seguido, R.DATA_INICIO_SEGUIMENTO AS data_inicio_seguimento
            FROM RELACIONAMENTO AS R
            INNER JOIN USUARIO AS SGD ON R.ID_SEGUIDOR = SGD.ID_USUARIO
            INNER JOIN USUARIO AS SG ON R.ID_SEGUIDO = SG.ID_USUARIO
            WHERE R.ID_SEGUIDOR = :usuarioId
            ORDER BY SG.NOME;
            """, nativeQuery = true)
    List<RelacionamentoDTO> findSeguidoresPorUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = """
            SELECT SGD.NOME AS nome_seguido, SG.NOME AS nome_seguidor, R.DATA_INICIO_SEGUIMENTO AS data_inicio_seguimento
            FROM RELACIONAMENTO AS R
            INNER JOIN USUARIO AS SGD ON R.ID_SEGUIDOR = SGD.ID_USUARIO
            INNER JOIN USUARIO AS SG ON R.ID_SEGUIDO = SG.ID_USUARIO
            WHERE R.ID_SEGUIDO = :usuarioId
            ORDER BY SG.NOME
            """, nativeQuery = true)
    List<RelacionamentoDTO> findSeguindoPorUsuarioId(@Param("usuarioId") Long usuarioId);
}