package com.cooksys.assessment1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException  {

	private static final long serialVersionUID = 5778158275303317542L;
	
	private String message;
}
