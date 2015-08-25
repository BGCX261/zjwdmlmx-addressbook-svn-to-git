/**
  *This class is the main class of Control module whitch in the MVC pattern
  * and sent message to the View and Model to do operations
  *Author:Zhang Junwen
  **/

package com.Wx.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;
import java.sql.SQLException;

import com.Wx.model.Imodel;
import com.Wx.model.User;
import com.Wx.model.Contact;
import com.Wx.model.ExpressFormatException;
import com.Wx.model.RelogonException;
import com.Wx.model.UserNotFoundOrPasswordErrorException;

final public class controler implements Icontroler
{
    /// the thread pool to do tasks with a new thread
    private ExecutorService exec = Executors.newCachedThreadPool();

    /// referenct to the model main class
    private Imodel model;

    /// to submit the operation with a Runnable base class
    private Submiter submiter = new Submiter();
    /// the current user who log on successly
    private User currentUser = null;  
    
    public controler(Imodel model)throws NullPointerException
    {
        if (model == null) 
        {
            throw new NullPointerException();
        }
        this.model = model;
    }
    /// logon the addressbook system with a user whos name and password is gaven
    /// if the username is not found in the addressbook database system UserNotFoundException 
    /// will be throw
    /// if the user's password is not currect PasswordException will be throw
    /// if an user is already logon ,throw RelogonException
    public synchronized void logon(User user)throws ExpressFormatException,SQLException,UserNotFoundOrPasswordErrorException,RelogonException
    {
        if (currentUser != null) throw new RelogonException();
        this.currentUser = model.getUser(user);
    }
    ///
    /// logout the system for next logon and only one user can logon at one times
    /// so if you want to logon anthoer user logout first
    public synchronized void logout()
    {
        this.currentUser = null;
    }
    
    /// 
    public synchronized Imodel.OperationError TryToGetErrorMark(long time)throws InterruptedException,DataNotReadyException
    {
        return submiter.getErrorMark(time);
    }
    
    ///
    public synchronized LinkedList<Contact> TryToGetContacts(long time)throws InterruptedException,DataNotReadyException
    {
        return submiter.getContacts(time);
    }
    public synchronized Imodel.OperationError WaitToGetErrorMark()throws InterruptedException
    {
    	return submiter.getErrorMark();
    }
    public synchronized LinkedList<Contact> WaitToGetContacts()throws InterruptedException
    {
    	return submiter.getContacts();
    }
    /// function to submit the operation for the database operation
    /// @operation is a String that describe the operation 
    ///  just like this :
    ///            "User = zjwdmlmx;Password = 123456"
    /// @type is a operationType enum type whitch describe the operation
    ///  type of the operation string just like:operationType.adduser 
    public synchronized void SubmitOperation(User user,Contact contact,operationType type)
    {
        submiter.setOperation(user,contact, type);
        exec.execute(submiter);
    }
    
    /// To shutdown the control with shutdowning thread pool
    public synchronized void Shutdown()
    {
        exec.shutdown();
        System.out.println("controler has shutdown now");
    }
    /**
     * a inside class implement from Runnable to execute the operation 
     * whitch will operate the database 
     **/
    final class Submiter implements Runnable
    {
        private operationType type;
        private User user;
        private Contact contact;
        private LinkedList<Contact> contacts = new LinkedList<Contact>();
        private Imodel.OperationError error = Imodel.OperationError.all_right;
        
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        
        private boolean is_data_ready = false;
        
        /// try to get the Contacts if the lock is locked and tryLock for 0.1 second
        /// if not locked throw a DataNotReadyException else return the contacts object
        public LinkedList<Contact> getContacts(long time)throws InterruptedException,DataNotReadyException
        {
        	boolean locked = lock.tryLock(time,TimeUnit.MILLISECONDS);
        	try
        	{
        		if (locked)
        		{
        			if (this.is_data_ready) return contacts;
        			else throw new DataNotReadyException();
        		}
        		else
        		{
        			throw new DataNotReadyException();
        		}
        	}
        	finally
        	{
        		if (locked) lock.unlock();
        	}
        }
        public LinkedList<Contact> getContacts()throws InterruptedException
        {
        	lock.lock();
        	try
        	{
                while(is_data_ready == false)
                    this.condition.await();
        		return contacts;
        	}
        	finally
        	{
        		lock.unlock();
        	}
        }
        public Imodel.OperationError getErrorMark(long time)throws InterruptedException,DataNotReadyException
        {
        	boolean locked = lock.tryLock(time,TimeUnit.MILLISECONDS);
        	try
        	{
        		if (locked)
        		{
        			if (this.is_data_ready) return error;
        			else throw new DataNotReadyException();
        		}
        		else
        		{
        			throw new DataNotReadyException();
        		}
        	}
        	finally
        	{
        		if (locked) lock.unlock();
        	}
        }
        public Imodel.OperationError getErrorMark()throws InterruptedException
        {
        	lock.lock();
        	try
        	{
                while(is_data_ready == false)
                    this.condition.await();
        		return error;
        	}
        	finally
        	{
        		lock.unlock();
        	}
        }
        public void setOperation(User user,Contact contact,operationType type)
        {
        	lock.lock();
        	try
        	{
                this.user = user;
                this.contact = contact;
                this.type = type;
        	}
        	finally
        	{
        		lock.unlock();
        	}
        }
        /// The thread will run at here,with each value of type chose 
        ///  defferent operation from model
        /// for each operation there will be a thread to call this run function and lock 
        /// the object and the resault will be write to contacts if call Imodel.getContact
        /// the error mark will be write to error if operation failed 
        public void run()
        {
            lock.lock();
            this.is_data_ready = false;
            try
            {
                switch (type)
                {
                case addUser:  
                    error = model.addUser(user);
                    System.out.println("inside " + error);
                    break;
                case deleteUser:
                    error = model.deleteUser(user);
                    break;
                case addContact:
                    error = model.addContact(currentUser,contact);
                    break;
                case deleteContact:
                    error = model.deleteContact(currentUser,contact);
                    break;
                case updateUser:
                    error = model.updateUser(currentUser,user);
                    break;
                case updateContact:
                    error = model.updateContact(currentUser,contact);
                    break;
                case getContact:
                    try
                    {
                        contacts = model.getContact(currentUser,contact);
                    }
                    catch (UserNotFoundOrPasswordErrorException eu)
                    {
                        error = Imodel.OperationError.user_not_exists_or_password_error;
                    }
                    catch (Exception e)
                    {
                        error = Imodel.OperationError.all_right;
                    }
                    break;
                }    // switch
                this.is_data_ready = true;
                condition.signal();
            }    // try
            finally
            {
                lock.unlock();
            }
        }
    }    // class Submiter

   
}    // class controler