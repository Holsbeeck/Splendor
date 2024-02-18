package exceptions;

public class SpelerReedsInSpelException extends RuntimeException {
	public SpelerReedsInSpelException()
    {
    }
    
    public SpelerReedsInSpelException(String message)
    {
        super(message);
    }

    public SpelerReedsInSpelException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SpelerReedsInSpelException(Throwable cause)
    {
        super(cause);
    }

    public SpelerReedsInSpelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
