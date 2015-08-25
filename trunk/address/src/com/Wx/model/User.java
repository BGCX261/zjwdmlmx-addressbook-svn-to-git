/**
   this class point to the user table in database 
   
   Author:Zhang Junwen
 */

package com.Wx.model;

public final class User
{
    private String username;
    private String password;
    
    public String getUsername()
    {
        String res = (this.username == null)?"":username;
        return res;
    }
    public String getPassword()
    {
        String res = (this.password == null)?"":password;
        return res;
    }
    
    public void setUsername(final String username)
    {
        if (username == null)
            throw new NullPointerException();
        this.username = username;
    }
    
    public void setPassword(final String password)
    {
        if (password == null)
            throw new NullPointerException();
        this.password = password;
    }
}    // class User

