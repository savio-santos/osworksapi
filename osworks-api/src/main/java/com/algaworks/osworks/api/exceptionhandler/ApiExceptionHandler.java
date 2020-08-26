package com.algaworks.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontradaException(NegocioException ex, WebRequest req) {
		var status = HttpStatus.NOT_FOUND;

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitle(ex.getMessage());
		problema.setDatahora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, req);
	}
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest req) {
		var status = HttpStatus.BAD_REQUEST;

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitle(ex.getMessage());
		problema.setDatahora(OffsetDateTime.now());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, req);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub

		var campos = new ArrayList<Problema.Campo>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = error.getDefaultMessage();

			campos.add(new Problema.Campo(nome, mensagem));
		}

		var problema = new Problema();
		problema.setTitle("Um ou mais dados estão invalidos." + " Faça o preechimento correto e tente novamente");
		problema.setStatus(status.value());
		problema.setDatahora(OffsetDateTime.now());
		problema.setCampos(campos);

		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
}
