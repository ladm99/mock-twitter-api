package com.cooksys.assessment1.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cooksys.assessment1.dtos.ExceptionDto;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.exceptions.NotAuthorizedException;
import com.cooksys.assessment1.exceptions.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = { "com.cooksys.assessment1.controllers" })
@ResponseBody
public class Assessment1ControllerAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ExceptionDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException) {
		return new ExceptionDto(notFoundException.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ExceptionDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException) {
		return new ExceptionDto(badRequestException.getMessage());
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NotAuthorizedException.class)
	public ExceptionDto handleNotAuthorizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException) {
		return new ExceptionDto(notAuthorizedException.getMessage());
	}

}
