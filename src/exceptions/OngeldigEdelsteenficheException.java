package exceptions;

public class OngeldigEdelsteenficheException extends RuntimeException {
	public OngeldigEdelsteenficheException()
    {
    }
    
    public OngeldigEdelsteenficheException(String message)
    {
        super(message);
    }

    public OngeldigEdelsteenficheException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OngeldigEdelsteenficheException(Throwable cause)
    {
        super(cause);
    }

    public OngeldigEdelsteenficheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
