package br.org.serratec.grupo4.dto;

import java.time.LocalDate;

import br.org.serratec.grupo4.domain.Relacionamento;

public class SeguindoDTO {

    private String nome;

    private LocalDate dataDeSeguimento;

    public SeguindoDTO() {

    }

    public SeguindoDTO(String nome, LocalDate dataDeSeguimento) {
        this.nome = nome;
        this.dataDeSeguimento = dataDeSeguimento;
    }

    public SeguindoDTO(Relacionamento relacionamento) {
        this.nome = relacionamento.getId().getSeguido().getNome();
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
