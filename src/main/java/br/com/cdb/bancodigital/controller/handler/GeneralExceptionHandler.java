package br.com.cdb.bancodigital.controller.handler;

import br.com.cdb.bancodigital.service.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> ArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("mensagem", "erro de validação no corpo na requisição");
        result.put("caminho", request.getRequestURI());

        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            erros.put(fieldName, message);
        });
        result.put("erros", erros);

        return ResponseEntity.status(status).body(result);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> paramNotValid(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("mensagem", "erro de validação nos parametros da requisição");
        result.put("caminho", request.getRequestURI());

        List<String> erros = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> {
            String[] aux = cv.getPropertyPath().toString().split("\\.");
            String parametro = aux[aux.length - 1];

            erros.add(parametro+": "+cv.getMessage());
        });

        result.put("erros", erros);

        return ResponseEntity.status(status).body(result);

    }

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
    public ResponseEntity<HashMap<String, Object>> pagamentoInvalido(PagamentoInvalidoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        HashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("status", status.value());
        result.put("mensagem", e.getMessage());
        result.put("valorRestante", e.getValorRestante());

        if(e.getInicioMes() != null && e.getFinalMes() != null){
            result.put("inicioMes", e.getInicioMes());
            result.put("finalMes", e.getFinalMes());
        }

        result.put("caminho", request.getRequestURI());

        return ResponseEntity.status(status).body(result);
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

    @ExceptionHandler(DeserializacaoException.class)
    public ResponseEntity<MensagemDeErro> jsonDeserializeError (DeserializacaoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemDeErro msgError = new MensagemDeErro();
        msgError.setStatus(status.value());
        msgError.setMensagem(e.getMessage());
        msgError.setCaminho(request.getRequestURI());

        return ResponseEntity.status(status).body(msgError);
    }



//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<MensagemDeErro> jsonDeserializeError(HttpMessageNotReadableException e, HttpServletRequest request){
//
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//
//        MensagemDeErro msgError = new MensagemDeErro();
//        msgError.setStatus(status.value());
//        msgError.setCaminho(request.getRequestURI());
//
//        //Aqui se verifica se a falha da deserialização ocorreu devido um enum ter recebido um valor
//        //invalido
//        if (e.getCause() != null && e.getCause() instanceof InvalidFormatException) {
//
//            Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: ");
//            Matcher match = ENUM_MSG.matcher(e.getCause().getMessage());
//
//            if (match.find()) {
//                String[] split = e.getMessage().split(":");
//                String valoresValidos = split[split.length - 1];
//                msgError.setMensagem("Tipo inválido, por favor informe um dos seguintes valores:"+valoresValidos);
//
//            }
//            else
//                msgError.setMensagem(e.getMessage());
//        }
//        else {
//            e.printStackTrace();
//            msgError.setMensagem(e.getMessage());
//           //msgError.setMensagem("Existe algo errado com o json da requisição");
//        }
//
//        return ResponseEntity.status(status).body(msgError);
//    }

}
