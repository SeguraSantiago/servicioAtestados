package co.prosegur.servicioAtestados.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.prosegur.servicioAtestados.error.ApiErrorResponse;
import co.prosegur.servicioAtestados.error.AtestadosNotFoundException;
import co.prosegur.servicioAtestados.error.AtestadosNullPointerException;
import lombok.extern.slf4j.Slf4j;

/**
 * @RestControllerAdvice : Se utiliza para el manejo de excepciones del controlador de peticiones.- 
 * */


@Slf4j
@RestControllerAdvice
public class ApiAtestadosExceptionHandler {

	
	/* Excepcion controlada: No encontro datos con el filtro informado*/
    @ExceptionHandler(AtestadosNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(AtestadosNotFoundException ex){
    	ApiErrorResponse response = new ApiErrorResponse(
    									HttpStatus.NOT_FOUND.toString(), 
										"No se encontraron datos con el siguiente filtro: " + ex.getLote());
    	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /* Status Error: 500 */
    @ExceptionHandler(AtestadosNullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(AtestadosNullPointerException ex){
    	String message = ex.getLocalizedMessage();
    	log.error(HttpStatus.INTERNAL_SERVER_ERROR + ": " + message, ex);
    	ApiErrorResponse response = new ApiErrorResponse(
    									HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
										message);
    	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
	    
    /*Status Error: 400 */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ApiErrorResponse> handleApiException(MethodArgumentTypeMismatchException ex) {
        ApiErrorResponse response = 
            new ApiErrorResponse(ex.getErrorCode(),
                 ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ApiErrorResponse> handleApiException(MethodArgumentNotValidException ex) {
    	
    	Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.debug(errors.toString(), ex);
        ApiErrorResponse response = 
            new ApiErrorResponse(HttpStatus.BAD_REQUEST.toString(),
            		errors.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
    	log.debug(ex.toString(), ex);
    	ApiErrorResponse response = 
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                		ex.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
    	log.debug(ex.toString(), ex);
    	ApiErrorResponse response = 
                new ApiErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                		ex.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }    
    
    @ExceptionHandler({ NullPointerException.class,  IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleApiException(final RuntimeException ex, final WebRequest request) {
    	String message = ex.getLocalizedMessage();
    	log.error(HttpStatus.INTERNAL_SERVER_ERROR.toString() + ": "+ ex.getLocalizedMessage(), ex);
        ApiErrorResponse response = 
                new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
