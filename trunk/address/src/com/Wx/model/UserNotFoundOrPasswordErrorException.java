

package com.Wx.model;

public class UserNotFoundOrPasswordErrorException extends Exception
{
	private static final long serialVersionUID=1L;
	public UserNotFoundOrPasswordErrorException(){}
	public UserNotFoundOrPasswordErrorException(String message)
	{
		super(message);
	}
	
	public String toString()
	{
		return super.toString();
	}
}