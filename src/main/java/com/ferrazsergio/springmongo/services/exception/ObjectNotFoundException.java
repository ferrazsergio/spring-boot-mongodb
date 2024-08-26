package com.ferrazsergio.springmongo.services.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Object notFoundObject;

    public ObjectNotFoundException(String message, Object notFoundObject) {
        super(message);
        this.notFoundObject = notFoundObject;
    }

    public Object getNotFoundObject() {
        return notFoundObject;
    }
}