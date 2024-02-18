package exceptions;

public class EdeleBestaatNietException extends RuntimeException {
	public EdeleBestaatNietException()
    {
    }
    
    public EdeleBestaatNietException(String message)
    {
        super(message);
    }

    public EdeleBestaatNietException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdeleBestaatNietException(Throwable cause)
    {
        super(cause);
    }

    public EdeleBestaatNietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
