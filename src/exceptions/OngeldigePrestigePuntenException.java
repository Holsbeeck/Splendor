package exceptions;

public class OngeldigePrestigePuntenException extends RuntimeException {
	
	public OngeldigePrestigePuntenException()
    {
    }
    
    public OngeldigePrestigePuntenException(String message)
    {
        super(message);
    }

    public OngeldigePrestigePuntenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OngeldigePrestigePuntenException(Throwable cause)
    {
        super(cause);
    }

    public OngeldigePrestigePuntenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
