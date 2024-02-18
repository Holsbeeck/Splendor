package exceptions;

public class SpelerBestaatNietException extends RuntimeException {
	public SpelerBestaatNietException()
    {
    }
    
    public SpelerBestaatNietException(String message)
    {
        super(message);
    }

    public SpelerBestaatNietException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SpelerBestaatNietException(Throwable cause)
    {
        super(cause);
    }

    public SpelerBestaatNietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
