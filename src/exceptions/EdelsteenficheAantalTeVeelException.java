package exceptions;

public class EdelsteenficheAantalTeVeelException extends RuntimeException {
	public EdelsteenficheAantalTeVeelException()
    {
    }
    
    public EdelsteenficheAantalTeVeelException(String message)
    {
        super(message);
    }

    public EdelsteenficheAantalTeVeelException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdelsteenficheAantalTeVeelException(Throwable cause)
    {
        super(cause);
    }

    public EdelsteenficheAantalTeVeelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
