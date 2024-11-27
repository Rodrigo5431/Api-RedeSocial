package br.org.serratec.grupo4.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.serratec.grupo4.domain.Foto;
import br.org.serratec.grupo4.domain.Usuario;
import br.org.serratec.grupo4.repository.FotoRepository;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    public Foto inserir(Usuario usuario, MultipartFile file) throws IOException {

        Foto foto = new Foto();
        foto.setNome(file.getName());
        foto.setTipo(file.getContentType());
        foto.setDados(file.getBytes());
        foto.setUsuario(usuario);
        return fotoRepository.save(foto);
    }

    @Transactional
    public Foto buscarPorIdUsuario(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        Optional<Foto> foto = fotoRepository.findByUsuario(usuario);
        if (foto.isEmpty()) {
            return null;
        }
        return foto.get();
    }

    @Transactional
    public Foto atualizar(Usuario usuario, MultipartFile file) throws IOException {
        Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuario);

        Foto foto = new Foto();

        if (fotoOpt.isPresent()) {
            foto = fotoOpt.get();
        } else {
            foto.setUsuario(usuario);
        }
        foto.setNome(file.getName());
        foto.setTipo(file.getContentType());
        foto.setDados(file.getBytes());

        return fotoRepository.save(foto);
    }

}
