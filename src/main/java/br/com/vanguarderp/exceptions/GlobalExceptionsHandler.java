package br.com.vanguarderp.exceptions;

import br.com.vanguarderp.util.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    @ExceptionHandler(MsgApiException.class)
    public ResponseEntity<ResponseApi> generalMsgApiExceptions(MsgApiException msgApiException, HttpServletRequest httpServletRequest) {
        logException(msgApiException, httpServletRequest);

        ResponseApi responseApi = new ResponseApi(
                new Date(),
                msgApiException.getStatus().value(),
                msgApiException.getStatus().getReasonPhrase(),
                msgApiException.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(msgApiException.getStatus())
                                                    .contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseApi> userNotFoundExceptions(UsernameNotFoundException ex, HttpServletRequest httpServletRequest) {
        logException(ex, httpServletRequest);

        ResponseApi responseApi = new ResponseApi(
                new Date(),
                HttpStatus.UNAUTHORIZED.value(),
                "Usuário não pôde ser autenticado.",
                ex.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseApi> methodNotSupportedExceptions(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        logException(ex, request);
        ResponseApi responseApi = new ResponseApi(new Date(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Erro de chamada ao método",
                "Método não permitido ou inválido: -> " + ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseApi);
    }




    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseApi> handlerNotFoundExceptions(NoHandlerFoundException ex, HttpServletRequest request) {

        logException(ex, request);
        ResponseApi responseApi = new ResponseApi(new Date(),
                HttpStatus.NOT_FOUND.value(),
                "URL Inválida",
                "Endpoint não encontrado ou parâmetro obrigatório não informado.",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseApi);

    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseApi> dataIntegrityViolationExceptions(DataIntegrityViolationException ex, HttpServletRequest request) {

        logException(ex, request);
        ResponseApi responseApi = new ResponseApi(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de integridade de dados.",
                ExceptionUtil.getMensagemValidacaoConstraint(ex),
                request.getRequestURI());

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseApi);

    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseApi> missingRequestParameterExceptions(MissingServletRequestParameterException ex, HttpServletRequest request) {

        logException(ex, request);
        ResponseApi responseApi = new ResponseApi(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Payload e demais dados não foram enviados corretamente.",
                ExceptionUtil.getMensagemParametros(ex),
                request.getRequestURI());


        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }


    /* Para erro que não estamos esperando RuntimeException*/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseApi> messageNotReadableExceptions(HttpMessageNotReadableException ex, HttpServletRequest request) {
        logException(ex, request);
        StringBuilder msgErro = new StringBuilder();

        if (ex.getMessage().startsWith("Required request body is missing")) {
            msgErro.append("Os dados da requisição não foram enviados.");
        }

        ResponseApi responseApi = new ResponseApi(new Date(),
                HttpStatus.BAD_REQUEST.value(),
                "Payload e demais dados não foram enviados corretamente.",
                msgErro.toString(),
                request.getRequestURI());

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }



    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ResponseApi> optimisticLockingFailureExceptions(ObjectOptimisticLockingFailureException  ex, HttpServletRequest request) {


        ResponseApi responseApi = new ResponseApi(new Date(), HttpStatus.BAD_REQUEST.value(),
                "Uma outra atualização foi identificada pelo sistema para esse cadastro.",
                "Este registro foi alterado por outro usuário. Consule novamente os dados, atualize e salve suas alterações.",
                request.getRequestURI());

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseApi> generalExceptions(Exception ex, HttpServletRequest request) {

        logException(ex, request);

        String msg = ExceptionUtil.getMensagemValidacaoConstraint(ex);
        String msgRetorno = "";

        if (msg.contains("No static resource")) {
            msgRetorno = "URl ou caminho não existe no Controller. " + msg;
        }else {
            msgRetorno = msg;
        }

        ResponseApi responseApi = new ResponseApi(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro geral ocorrido no sistema.",
                msgRetorno,
                request.getRequestURI());

        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseApi> constraintViolationExceptions(ConstraintViolationException ex, HttpServletRequest request) {
        logException(ex, request);

        ResponseApi responseApi = new ResponseApi(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Mensagem do sistema.",
                ExceptionUtil.getMensagemValidacaoConstraint(ex),
                request.getRequestURI());


        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
                .body(responseApi);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi> methodArgumentNotValidExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logException(ex, request);

        List<String> lista = new ArrayList<String>();

        for (ObjectError erro : ex.getAllErrors()) {
            lista.add(erro.getDefaultMessage());
        }

        ResponseApi responseApi = new ResponseApi(new Date(), HttpStatus.BAD_REQUEST.value(),
                "Valores não correspodem e não passaram nas validações do sistema.",
                String.join(", ", lista),
                request.getRequestURI());

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(responseApi);
    }


    private void logException(Exception ex, HttpServletRequest request) {
        LOGGER.error("Error [{} {}] - {}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getMessage(),
                ex);
    }





}
