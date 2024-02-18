package exceptions;

public class OntwikkelingskaartBestaatNietException extends RuntimeException {
	public OntwikkelingskaartBestaatNietException()
    {
    }
    
    public OntwikkelingskaartBestaatNietException(String message)
    {
        super(message);
    }

    public OntwikkelingskaartBestaatNietException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OntwikkelingskaartBestaatNietException(Throwable cause)
    {
        super(cause);
    }

    public OntwikkelingskaartBestaatNietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
