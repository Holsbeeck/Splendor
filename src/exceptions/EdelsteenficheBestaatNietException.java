package exceptions;

public class EdelsteenficheBestaatNietException extends RuntimeException {
	public EdelsteenficheBestaatNietException()
    {
    }
    
    public EdelsteenficheBestaatNietException(String message)
    {
        super(message);
    }

    public EdelsteenficheBestaatNietException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdelsteenficheBestaatNietException(Throwable cause)
    {
        super(cause);
    }

    public EdelsteenficheBestaatNietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
