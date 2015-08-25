/**
   This file if one part of model model
   This interface of model supply the function to add/delete user add/delete
   Contact update user and contact the main class of model which operation 
   the database must inherit this model
   
   Author:Zhang Junwen
 */

package com.Wx.model;

import java.util.LinkedList;
import java.sql.SQLException;


public interface Imodel
{
	/**
	 * For the below function to return the error mark
	 * [user_not_exists]
	 *     when you delete a user update or contact operation is done,and found
	 *     the user is not exists in the address database system return this
	 * [user_exists]
	 *     when you add a new user and the user is already exists return this
	 * [password_error]
	 *     when you delete a user or add/delete contact or update contact and user and
	 *     found the password is not currect return this
	 * [contact_exists]
	 *      when you add contact and the contact you showed is exists return this
	 * [contact_not_exists]
	 *      when you delete contact and update contact and you found contact not exists
	 *      return this 
	 */
	public enum OperationError
	{
		user_not_exists_or_password_error,
		user_exists,
		password_error,
		contact_exists,
		contact_not_exists,
		all_right
	}
    /// function to add a user to the address book                      
    ///      [username]                        not null                   
    ///      [userpassword]                    not null                   
    public OperationError addUser(User user);

    /// function to delete a user from the address book   
    ///       [username]                       not null                  
    ///       [userpassword]                   not null                  
    public OperationError deleteUser(User user);

    /// function to add Contact to the address book     
    ///        [username]                       not null
    ///        [userpassword]                   not null
    ///        [contactname]                    not null
    ///        [sex]                            not null
    ///        [age]                            not null
    ///        [tellnumber]                     not null
    /// for other keyworld if it is not null then set it else do nothing
    public OperationError addContact(User user,Contact contact);

    /// function to delete Contact from the address book        
    ///        [username]                       not null
    ///        [userpasswold]                   not null
    ///        [contactname]                    not null
    public OperationError deleteContact(User user,Contact contact);

    /// function to update a User 's information from the address book
    ///        [username]        the user's current name        not null
    ///        [userpassword]    the user's current password    not null
    ///        [newuserpassword] the user's new password        can be null
    ///        [newusername]     the user's new name            can be null
    public OperationError updateUser(User oldUser,User newUser);

    /// function to update a Contact in the address book            
    ///        [username]                       not null
    ///        [userpassword]                   not null
    ///        [contactname]                    not null
    ///        [sex]                            not null
    ///        [age]                            not null
    ///        [tellnumber]                     not null
    /// for other keyworld if it is not null then set it else do nothing
    public OperationError updateContact(User user,Contact contact);
    
    /// function to get the User's information
    /// argument user contant the username and password and if the username and password 
    /// are currect return the user object
    public User getUser(User user)throws SQLException,ExpressFormatException,UserNotFoundOrPasswordErrorException;
    
    
    /// function to get the Contact witch matched the queryContect
    /// arguments currentUser is the current user who loged on
    /// arguments queryContact is the query contect object
    ///     in whitch if the field set then this field is a query option if not it isn't
    public LinkedList<Contact> getContact(User currentUser,Contact queryContect)throws Exception,SQLException,UserNotFoundOrPasswordErrorException;
       
}
