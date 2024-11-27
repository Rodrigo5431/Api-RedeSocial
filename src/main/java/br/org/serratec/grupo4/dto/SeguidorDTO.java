package br.org.serratec.grupo4.dto;

import java.time.LocalDate;

import br.org.serratec.grupo4.domain.Relacionamento;

public class SeguidorDTO {

    private String nome;

    private LocalDate dataDeSeguimento;

    public SeguidorDTO() {

    }

    public SeguidorDTO(String nome, LocalDate dataDeSeguimento) {
        this.nome = nome;
        this.dataDeSeguimento = dataDeSeguimento;
    }

    public SeguidorDTO(Relacionamento relacionamento) {
        this.nome = relacionamento.getId().getSeguidor().getNome();
        this.dataDeSeguimento = relacionamento.getDataInicioSeguimento();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeSeguimento() {
        return dataDeSeguimento;
    }

    public void setDataDeSeguimento(LocalDate dataDeSeguimento) {
        this.dataDeSeguimento = dataDeSeguimento;
    }

}
