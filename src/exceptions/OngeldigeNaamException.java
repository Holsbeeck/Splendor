package exceptions;

public class OngeldigeNaamException extends RuntimeException
	{
	    public OngeldigeNaamException()
	    {
	    }
	    
	    public OngeldigeNaamException(String message)
	    {
	        super(message);
	    }

	    public OngeldigeNaamException(String message, Throwable cause)
	    {
	        super(message, cause);
	    }

	    public OngeldigeNaamException(Throwable cause)
	    {
	        super(cause);
	    }

	    public OngeldigeNaamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	    {
	        super(message, cause, enableSuppression, writableStackTrace);
	    }
	}


