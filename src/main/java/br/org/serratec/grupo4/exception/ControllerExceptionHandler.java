package br.org.serratec.grupo4.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> erros = new ArrayList<>();
        for (FieldError e : ex.getBindingResult().getFieldErrors()) {
            erros.add(e.getField() + ": " + e.getDefaultMessage());
        }

        ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos",
                LocalDateTime.now(), erros);

        return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    private ResponseEntity<Object> handleEmailException(EmailException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(SenhaException.class)
    private ResponseEntity<Object> handleSenhaException(SenhaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RelacionamentoException.class)
    private ResponseEntity<Object> handleRelacionamentoException(RelacionamentoException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(IdUsuarioInvalido.class)
    private ResponseEntity<Object> handleIdUsuarioException(IdUsuarioInvalido ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(ProprietarioIncompativelException.class)
    private ResponseEntity<Object> handleProprietarioException(ProprietarioIncompativelException ex) {
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(DadoNaoEncontradoException.class)
    private ResponseEntity<Object> handleDadoException(DadoNaoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

}
