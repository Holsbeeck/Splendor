package exceptions;

public class OngeldigeAfbeeldingException extends RuntimeException {
	
	public OngeldigeAfbeeldingException()
    {
    }
    
    public OngeldigeAfbeeldingException(String message)
    {
        super(message);
    }

    public OngeldigeAfbeeldingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OngeldigeAfbeeldingException(Throwable cause)
    {
        super(cause);
    }

    public OngeldigeAfbeeldingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
