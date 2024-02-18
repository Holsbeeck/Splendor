package exceptions;

public class EdelsteenficheNietGenoegVoorraadException extends RuntimeException {
	public EdelsteenficheNietGenoegVoorraadException()
    {
    }
    
    public EdelsteenficheNietGenoegVoorraadException(String message)
    {
        super(message);
    }

    public EdelsteenficheNietGenoegVoorraadException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdelsteenficheNietGenoegVoorraadException(Throwable cause)
    {
        super(cause);
    }

    public EdelsteenficheNietGenoegVoorraadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
