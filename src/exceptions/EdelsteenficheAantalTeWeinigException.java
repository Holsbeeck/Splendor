package exceptions;

public class EdelsteenficheAantalTeWeinigException extends RuntimeException {
	public EdelsteenficheAantalTeWeinigException()
    {
    }
    
    public EdelsteenficheAantalTeWeinigException(String message)
    {
        super(message);
    }

    public EdelsteenficheAantalTeWeinigException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdelsteenficheAantalTeWeinigException(Throwable cause)
    {
        super(cause);
    }

    public EdelsteenficheAantalTeWeinigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
