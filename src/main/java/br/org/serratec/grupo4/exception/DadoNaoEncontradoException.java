package br.org.serratec.grupo4.exception;

public class DadoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DadoNaoEncontradoException(String message) {
        super(message);
    }
}
