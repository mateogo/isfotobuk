package ar.kennedy.is2011.exception;

/**
 * @author: mlabarinas
 */
public class SessionOperationException extends RuntimeException {

	private static final long serialVersionUID = 6768505943311247379L;

	public SessionOperationException(String message) {
		super(message);
	}
	
	public SessionOperationException(String message, Throwable e) {
		super(message, e);
	}
	
}
