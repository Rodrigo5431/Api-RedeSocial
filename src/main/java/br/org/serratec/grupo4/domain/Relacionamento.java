package br.org.serratec.grupo4.domain;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Valid
public class Relacionamento {

    @EmbeddedId
    private UsuarioRelacionamentoPK id;

    @NotNull
    @CreationTimestamp
    @Column(name = "data_inicio_seguimento", nullable = false, updatable = false)
    @Schema(description = "Data_Seguimento")
    private LocalDate dataInicioSeguimento;

}
