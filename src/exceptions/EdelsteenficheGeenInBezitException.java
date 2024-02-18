package exceptions;

public class EdelsteenficheGeenInBezitException extends RuntimeException {
	public EdelsteenficheGeenInBezitException()
    {
    }
    
    public EdelsteenficheGeenInBezitException(String message)
    {
        super(message);
    }

    public EdelsteenficheGeenInBezitException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdelsteenficheGeenInBezitException(Throwable cause)
    {
        super(cause);
    }

    public EdelsteenficheGeenInBezitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
