package com.hbm.main;

public class DeserializationException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1520762653771266447L;

	public DeserializationException()
	{
		// TODO Auto-generated constructor stub
		this("Something occurred during the deserialization of an object.");
	}

	public DeserializationException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DeserializationException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DeserializationException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DeserializationException(
			String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
	)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
