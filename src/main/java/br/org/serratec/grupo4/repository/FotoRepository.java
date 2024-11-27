package br.org.serratec.grupo4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.grupo4.domain.Foto;
import br.org.serratec.grupo4.domain.Usuario;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    Optional<Foto> findByUsuario(Usuario usuario);

}
