package br.org.serratec.grupo4.exception;

public class IdUsuarioInvalido extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IdUsuarioInvalido(String message) {

        super(message);
    }

}
