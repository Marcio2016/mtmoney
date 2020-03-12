package com.mt.mtmoney.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MtmoneyExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemDev = ex.getCause().toString();
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
		return handleExceptionInternal(ex, new Erro(mensagemDev,mensagemUsuario), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	public static class Erro {
		String mensagemDev;
		String mensagemUsuario;
		
		public Erro(String mensagemDev,String mensagemUsuario) {
			this.mensagemDev = mensagemDev;
			this.mensagemUsuario = mensagemUsuario;
		}

		public String getMensagemDev() {
			return mensagemDev;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
	}
}
