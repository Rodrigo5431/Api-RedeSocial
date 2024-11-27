package br.org.serratec.grupo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.org.serratec.grupo4.domain.Usuario;
import br.org.serratec.grupo4.repository.UsuarioRepository;

@Service
public class UsuarioDetalheImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return usuario;
    }

}
