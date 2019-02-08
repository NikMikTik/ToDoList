package com.todo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ToDoException extends RuntimeException {
	
	Logger logger = LoggerFactory.getLogger(ToDoException.class);

	 public ToDoException(String message) {
		super(message);
		logger.info(message);

	}
}
