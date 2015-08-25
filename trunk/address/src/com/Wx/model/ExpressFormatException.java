/**
 * Exception base class to mark the expressiong's syntax error
 * Author:Zhang Junwen 
 */

package com.Wx.model;

public final class ExpressFormatException extends Exception
{
	private static final long serialVersionUID = 1L;
	public ExpressFormatException(){}
	public ExpressFormatException(String message)
	{
		super(message);
	}
	
	public String toString()
	{
		return super.toString();
	}
}