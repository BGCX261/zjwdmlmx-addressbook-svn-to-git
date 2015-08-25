/**
   this class content the item in the Contace table from database
                                                                 
   Author:Zhang Junwen                                           
 */

package com.Wx.model;


public final class Contact
{
    public enum SEX
    {
        MAN,
        LADY,
        OTHER
    }
    
    private String   contactname;
    private String   sex;
    private String   age;
    private String   tellphone;
    private String   qq;
    private String   msn;
    private String   birthday;
    private String   address;
    private String   hobby;
    private String   postcode;
    private String   note;
    private String   pic;
    private String   email;
    private String   group;
    
    /**
     * functions to get parement and if the name is not setted 
     * return a null string
     * @return 
     */
    public String getContactname()
    {
        String res = (this.contactname == null)?"":contactname;
        return res;
    }
    public String getSex() 
    {
        String res = (this.sex == null)?"":sex;
        return res;
    }
    public String getAge() 
    {
        String res = (this.age == null)?"":age;
        return res;
    }
    public String getTellphone()
    {
        String res = (this.tellphone == null)?"":tellphone;
        return res;
    }
    public String getQQ()
    {
        String res = (this.qq == null)?"":qq;
        return res;
    }
    public String getMSN()
    {
        String res = (this.msn == null)?"":msn;
        return res;
    }
    public String getBirthday()
    {
        String res = (this.birthday == null)?"":birthday;
        return res;
    }
    public String getAddress()
    {
        String res = (this.address == null)?"":address;
        return res;
    }
    public String getHobby()
    {
        String res = (this.hobby == null)?"":hobby;
        return res;
    }
    public String getPostcode()
    {
        String res = (this.postcode == null)?"":postcode;
        return res;
    }
    public String getNote()
    {
        String res = (this.note == null)?"":note;
        return res;
    }
    public String getPic()
    {
        String res = (this.pic == null)?"":pic;
        return res;
    }
    public String getEmail()
    {
        String res = (this.email == null)?"":email;
        return res;
    }
    public String getGroup()
    {
        String res = (this.group == null)?"":group;
        return res;
    }
    /**
     * 
     * functions to set the paremtns in this class 
     * and if the arguments are null throw NullPointerException
     * @param name
     * @throws Exception
     * @throws NullPointerException 
     */
    public void setContactname(final String name)throws Exception,NullPointerException
    {
        if (name == null)
            throw new NullPointerException();
        this.contactname = name;
    }
    public void setSex(final SEX sex)throws Exception
    {
        if (sex == SEX.MAN) this.sex = "man";
        else if (sex == SEX.LADY) this.sex = "lady";
        else if (sex == SEX.OTHER) this.sex = "other";
    }
    
    public void setAge(final String age)throws Exception,NullPointerException
    {
        if (age.matches("^[0-9]*$")) 
            this.age = age;
        else throw new Exception("age must zero or big than zero");
    }
    
    public void setTellphone(final String tellphone)throws Exception,NullPointerException
    {
        if (tellphone.matches("^[0-9]*$"))
            this.tellphone = tellphone;
        else throw new Exception("Is not s tellphone number");
    }
    
    public void setQQ(final String qq)throws Exception,NullPointerException
    {
        if (qq.matches("^[0-9]*$"))
            this.qq = qq;
        else throw new Exception("Is not a qq number");
    }
    
    public void setMSN(final String msn)throws Exception,NullPointerException
    {
        if (msn.matches("^[0-9]*$"))
            this.msn = msn;
        else throw new Exception("Is not a msn number");
    }
    
    public void setBirthday(final String birthday)throws Exception,NullPointerException
    {
        if (birthday.matches("^[0-9]*$"))
            this.birthday = birthday;
        else throw new Exception("Is not a birthday");
    }
    
    public void setAddress(final String address)throws Exception,NullPointerException
    {
        if (address == null)
            throw new NullPointerException();
        this.address = address;
    }
    
    public void setHobby(final String hobby)throws Exception,NullPointerException
    {
        if (hobby == null)
            throw new NullPointerException();
        this.hobby = hobby;
    }
    
    public void setPostcode(final String postcode)throws Exception,NullPointerException
    {
        if (postcode == null)
            throw new NullPointerException();
        this.postcode = postcode;
    }
    
    public void setNote(final String notes)throws Exception,NullPointerException
    {
        if (notes == null)
            throw new NullPointerException();
        this.note = notes;
    }
    
    public void setPic(final String pic)throws Exception,NullPointerException
    {
        if (pic == null)
            throw new NullPointerException();
        this.pic = pic;
    }
    
    public void setEmail(final String email)throws Exception,NullPointerException
    {
        if (email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))
            this.email = email;
        else throw new Exception("is not a Email address format");
    }

    public void setGroup(final String group)throws Exception,NullPointerException
    {
        if (group == null)
            throw new NullPointerException();
        this.group = group;
    }
    
    @Override
    public String toString()
    {
        String res = (this.contactname == null)?"":contactname;
        return res;
    }
    
}

