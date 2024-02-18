package exceptions;

public class OngeldigeKleurException extends RuntimeException {
	public OngeldigeKleurException()
    {
    }
    
    public OngeldigeKleurException(String message)
    {
        super(message);
    }

    public OngeldigeKleurException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OngeldigeKleurException(Throwable cause)
    {
        super(cause);
    }

    public OngeldigeKleurException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
