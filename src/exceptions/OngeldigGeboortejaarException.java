package exceptions;

public class OngeldigGeboortejaarException extends RuntimeException {
	
	public OngeldigGeboortejaarException() {
	}

	public OngeldigGeboortejaarException(String message) {
		super(message);
	}

	public OngeldigGeboortejaarException(String message, Throwable cause) {
		super(message, cause);
	}

	public OngeldigGeboortejaarException(Throwable cause) {
		super(cause);
	}

	public OngeldigGeboortejaarException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

