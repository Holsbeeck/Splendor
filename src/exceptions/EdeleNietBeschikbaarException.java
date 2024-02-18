package exceptions;

public class EdeleNietBeschikbaarException extends RuntimeException {
	public EdeleNietBeschikbaarException()
    {
    }
    
    public EdeleNietBeschikbaarException(String message)
    {
        super(message);
    }

    public EdeleNietBeschikbaarException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EdeleNietBeschikbaarException(Throwable cause)
    {
        super(cause);
    }

    public EdeleNietBeschikbaarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
