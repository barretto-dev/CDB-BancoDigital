package br.com.cdb.bancodigital.controller.handler;

import br.com.cdb.bancodigital.service.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<MensagemDeErro> entityNotFound(EntidadeNaoEncontradaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }

    @ExceptionHandler(BancoDeDadosException.class)
    public ResponseEntity<MensagemDeErro> database(BancoDeDadosException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }

    @ExceptionHandler(EnumInvalidoException.class)
    public ResponseEntity<MensagemDeErro> enumInvalido(BancoDeDadosException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public ResponseEntity<MensagemDeErro> pagamentoInvalido(PagamentoInvalidoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }

    @ExceptionHandler(TransferenciaPixInvalidaException.class)
    public ResponseEntity<MensagemDeErro> transferenciaInvalida(TransferenciaPixInvalidaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }

    @ExceptionHandler(OperacaoProibidaException.class)
    public ResponseEntity<MensagemDeErro> operacaoProibida(OperacaoProibidaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MensagemDeErro> jsonDeserializeError(HttpMessageNotReadableException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setCaminho(request.getRequestURI());

        //Aqui se verifica se a falha da deserialização ocorreu devido um enum ter recebido um valor
        //invalido
        if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {

            Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: ");
            Matcher match = ENUM_MSG.matcher(e.getCause().getMessage());

            if (match.find()) {
                String[] split = e.getMessage().split(":");
                String valoresValidos = split[split.length - 1];
                msgError.setMensagem("Tipo inválido, por favor informe um dos seguintes valores:"+valoresValidos);

            }
        }
        else {
            e.printStackTrace();
            msgError.setMensagem("Existe algo errado com o json da requisição");
        }

        return ResponseEntity.status(status).body(msgError);
    }

}
