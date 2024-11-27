package br.org.serratec.grupo4.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Valid
public class Usuario implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_usuario")
    @Schema(description = "Id do Usuário")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    @Schema(description = "Nome do Usuário")
    private String nome;

    @Column(nullable = false, length = 50)
    @Schema(description = "descricao do Usuário")
    private String descricao;

    @Column(nullable = false, length = 150, unique = true)
    @Schema(description = "Email do Usuário")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Senha do Usuário")
    private String senha;

    @Column(name = "url", nullable = true)
    private String url;

    private LocalDate dataNascimento;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Foto foto;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL,  orphanRemoval = true)
    @Schema(description = "Postagens")
    private List<Postagem> postagens = new ArrayList<>();

    // Seguidores (usuários que seguem este usuário)
    // Ajuste necessário para tornar mais claro a relação entre usuário e
    // relacionamento
    @OneToMany(mappedBy = "id.seguido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Relacionamento> seguidores = new HashSet<>();

    // Seguidos (usuários que este usuário está seguindo)
    // Ajuste necessário para tornar mais claro a relação entre usuário e
    // relacionamento
    @OneToMany(mappedBy = "id.seguidor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Relacionamento> seguidos = new HashSet<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL,  orphanRemoval = true)
    @Schema(description = "Comentarios")
    private List<Comentario> comentarios = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
