package exceptions;

public class OngeldigAantalException extends RuntimeException {
	public OngeldigAantalException()
    {
    }
    
    public OngeldigAantalException(String message)
    {
        super(message);
    }

    public OngeldigAantalException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OngeldigAantalException(Throwable cause)
    {
        super(cause);
    }

    public OngeldigAantalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
