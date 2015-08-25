/**
 * interface for View model 
 * Author:Zhang Junwen
 */
package com.Wx.control;


import java.util.LinkedList;
import java.sql.SQLException;
import com.Wx.model.ExpressFormatException;
import com.Wx.model.User;
import com.Wx.model.Contact;
import com.Wx.model.Imodel;
import com.Wx.model.UserNotFoundOrPasswordErrorException;
import com.Wx.model.RelogonException;

public interface Icontroler
{
    /// function to submit the operation for the database operation
    /// @operation is a String that describe the operation 
    ///  just like this :
    ///            "User = zjwdmlmx;Password = 123456"
    /// all the keyworld is list below:
    ///     User                the user's name                         not null
    ///     Password            for the special User 's password        not null
    ///     Contact             the name of a link people               not null
    ///     Sex                 the sex of the link people              not null
    ///     Tellphone           the phone number for the link people    not null
    ///     Classes             the classes of the link people          can null
    ///     Age                 the age of the link people              not null
    ///     QQ                  the qq number of link people            can null
    ///     MSN                 the MSN number of link people           can null
    ///     Birthday            the birthday of the link people         can null
    ///     Address             the address of the link people          can null
    ///     Note                the notes of the link people            can null
    ///     Email               the email address of the link people    can null
    ///     Hobby               the hobby of link people                can null
    ///     Postcode            the Postcode of the link people         can null
    ///     Pic                 the picture of the link people          can null
    /// @type is a operationType enum type whitch describe the operation
    ///  type of the operation string just like:operationType.adduser
    public void SubmitOperation(User user,Contact contact,operationType type);
    
    
    /// logon the addressbook system with a user whos name and password is gaven
    /// if the username is not found in the addressbook database system UserNotFoundException 
    /// will be throw
    /// if the user's password is not currect PasswordException will be throw
    /// if an user is already logon ,throw RelogonException
    public void logon(User user)throws ExpressFormatException,SQLException,UserNotFoundOrPasswordErrorException,RelogonException;
    
    /// logout the system for next logon and only one user can logon at one times
    /// so if you want to logon anthoer user logout first
    public void logout();
    
    
    /// try to get the data for a while if not get the data throw a DataNotReadyException exception
    /// if the thread is interrupted throw a InterruptedException exception if the DataNotReadException
    /// is not throwed the data is readyed now then get the it
    public LinkedList<Contact> TryToGetContacts(long time)throws InterruptedException,DataNotReadyException;
    public Imodel.OperationError TryToGetErrorMark(long time)throws InterruptedException,DataNotReadyException;
    
    
    /// wait the database operation finished and the data is all right the retrun the 
    /// contacts in a Linkedlist and reutrn the error mark
    public Imodel.OperationError WaitToGetErrorMark()throws InterruptedException;
    public LinkedList<Contact> WaitToGetContacts()throws InterruptedException;
    
    public void Shutdown();
}