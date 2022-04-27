package com.gxdxy.curso.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.gxdxy.curso.service.exception.AuthorizationException;
import com.gxdxy.curso.service.exception.DataIntegrityException;
import com.gxdxy.curso.service.exception.FileException;
import com.gxdxy.curso.service.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest req) {

		StandardError erro = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);

	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest req) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest req) {

		ValidationError erro = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				System.currentTimeMillis());

		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			erro.addError(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorizationException(AuthorizationException e, HttpServletRequest req) {

		StandardError erro = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);

	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest req) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest req) {

		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());

		StandardError erro = new StandardError(code.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(code).body(erro);

	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest req) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest req) {

		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

}
