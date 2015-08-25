/** 
 * An Exception base class to mark the Data operated by another thread is not ready 
 * 
 * Author:Zhang Junwen
 */

package com.Wx.control;

final public class DataNotReadyException extends Exception
{
	private static final long serialVersionUID = 1L;
	public DataNotReadyException(){}
	public DataNotReadyException(String message)
	{
		super(message);
	}
	
    @Override
	public String toString()
	{
		return super.toString();
	}
}