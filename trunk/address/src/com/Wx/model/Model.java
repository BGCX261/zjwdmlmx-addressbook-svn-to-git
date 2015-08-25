package com.Wx.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.LinkedList;


import com.Wx.control.operationType;


public final class Model implements Imodel
{
    private final String  driver   = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String  url      = "jdbc:sqlserver://localhost:1433;DatabaseName=addressbook";
    private final String  user     = "addressbookadmin";
    private final String  password = "2V3x9HjiJxD87Rit";
    private Connection    connect;
    private Statement     statement; 
    private ResultSet     res;
    private SqlTranslater translater = new SqlTranslater();
    
    
    public Model()
    {
        try
        {
            Class.forName(driver);
            connect = DriverManager.getConnection(url,user,password);
            statement = connect.createStatement();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    /// privode function to close the database link manal
    public void close()
    {
        try
        {
            if(res != null)
            {
                res.close(); 
                res = null;
            }
            if(statement != null)
            {
                statement.close(); 
                statement = null;
            }
            if(connect != null)
            {
                connect.close(); 
                connect = null;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        System.out.println("database has closed now");
    }  
    private void printSql(String[] sql)
    {
        for (int i = 0;i < sql.length;i++)
        {
            System.out.println(sql[i]);
        }
        System.out.println();
    }
    @Override
    public OperationError addUser(User user) 
    {
        boolean is_success;
        String[] sql;
        try
        {
            sql = translater.translateToSql(user, null, operationType.addUser);
            printSql(sql);
            is_success = statement.execute(sql[0]);
            if (is_success)
            {
                res = statement.getResultSet();
                if (res.next()) return Imodel.OperationError.user_exists;
            }
            else return OperationError.user_exists;
            is_success = statement.execute(sql[1]);
        }
        catch(Exception e)
        { 
            System.out.println(e);
        }
        return Imodel.OperationError.all_right;
    }

    @Override
    public OperationError deleteUser(User user)
    {
        boolean is_success;
        String[] sql;
        try
        {
           sql = translater.translateToSql(user, null, operationType.deleteUser);
           printSql(sql);
           is_success = statement.execute(sql[0]);
           if (is_success)
           {
               res = statement.getResultSet();
               if (!res.next()) return OperationError.user_not_exists_or_password_error;
           }
           is_success = statement.execute(sql[1]);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return OperationError.all_right;
    }
    
    @Override
    public OperationError addContact(User user, Contact contact) 
    {
        boolean is_success;
        String[] sql;
         try
         {
             sql = translater.translateToSql(user, contact, operationType.addContact);
             printSql(sql);
             is_success = statement.execute(sql[0]);
             if (is_success)
             {
                 res = statement.getResultSet();
                 if (res.next()) return OperationError.contact_exists;
             }
             is_success = statement.execute(sql[1]);
             if (is_success)
             {
                 res = statement.getResultSet();
                 if (!res.next()) return OperationError.user_not_exists_or_password_error;
             }
             is_success = statement.execute(sql[2]);
         }
         catch(Exception e)
         { 
             System.out.println(e);
         }
         return OperationError.all_right;
    }

    @Override
    public OperationError deleteContact(User user, Contact contact)
    {
        boolean is_success;
        String[] sql;
        try
        {
            sql = translater.translateToSql(user, contact, operationType.deleteContact);
            printSql(sql);
            is_success = statement.execute(sql[0]);
            if (is_success)
            {
                res = statement.getResultSet();
                 if (!res.next()) return OperationError.contact_not_exists;
            }
            is_success = statement.execute(sql[1]);
            if (is_success)
            {
                 res = statement.getResultSet();
                 if (!res.next()) return OperationError.user_not_exists_or_password_error;
            }
            is_success = statement.execute(sql[2]);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return OperationError.all_right;
    }

    @Override
    public OperationError updateUser(User oldUser, User newUser)
    {
        boolean is_success;
        String[] sql;
        try
        {
            sql = translater.translateToSql(oldUser,null, operationType.updateUser);
            printSql(sql);
            is_success = statement.execute(sql[0]);
            if (is_success)
            {
                 res = statement.getResultSet();
                 if (!res.next()) return OperationError.user_not_exists_or_password_error;
            }
            is_success = statement.execute(sql[1]);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return OperationError.all_right;
    }
    
    @Override
    public OperationError updateContact(User user, Contact contact)
    {
        boolean is_success;
        String[] sql;
        try
        {
            sql = translater.translateToSql(user, contact, operationType.updateContact);
            printSql(sql);
            is_success = statement.execute(sql[0]);
            if (is_success)
            {
                res = statement.getResultSet();
                if (!res.next()) return OperationError.contact_not_exists;
            }
            is_success = statement.execute(sql[1]);
            if (is_success)
            {
                 res = statement.getResultSet();
                 if (!res.next()) return OperationError.user_not_exists_or_password_error;
            }
            is_success = statement.execute(sql[2]);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return OperationError.all_right ; 
    }

    @Override
    public User getUser(User user) throws SQLException,ExpressFormatException,UserNotFoundOrPasswordErrorException
    {
        boolean is_success;
        String[] sql;
        sql = translater.translateToSql(user,null, operationType.getUser);
        printSql(sql);
        is_success = statement.execute(sql[0]);
        if (is_success)
        {
            res = statement.getResultSet();
            User _user = new User();
            if (res.next())
            {
            	_user.setUsername(res.getString(1));
            	_user.setPassword(res.getString(2));
            }
            else
                throw new UserNotFoundOrPasswordErrorException();
            
            return _user;
        }
        else new SQLException("expression not execute success");
        return null;
    }

    @Override
    public LinkedList<Contact> getContact(User currentUser, Contact queryContact)throws Exception,SQLException,UserNotFoundOrPasswordErrorException
    {
        boolean is_success;
        String[] sql;
        sql = translater.translateToSql(currentUser, queryContact, operationType.getContact);
        printSql(sql);
        is_success = statement.execute(sql[0]);
        if (is_success)
        {
             res = statement.getResultSet();
             if (!res.next()) throw new UserNotFoundOrPasswordErrorException();
        }
        is_success = statement.execute(sql[1]);
        if (is_success)
        {
            LinkedList<Contact> contacts = new LinkedList<Contact>();
            
            res = statement.getResultSet();
            while(res.next())
            {
                Contact _contact = new Contact();
                _contact.setContactname(res.getString("contactname").split(" ")[0]);
                String sex = res.getString("sex");
                if (sex.compareToIgnoreCase("man") == 0)
                    _contact.setSex(Contact.SEX.MAN);
                else if (sex.compareToIgnoreCase("lady") == 0)
                    _contact.setSex(Contact.SEX.LADY);
                _contact.setAge(res.getString("age").split(" ")[0]);
                _contact.setTellphone(res.getString("tellphone").split(" ")[0]);
                String qq = res.getString("qq");
                if (qq == null)
                	_contact.setQQ("");
                else
                    _contact.setQQ(qq.split(" ")[0]);
                String msn = res.getString("msn");
                if (msn == null)
                	_contact.setMSN("");
                else
                    _contact.setMSN(msn.split(" ")[0]);
                String birthday = res.getString("birthday");
                if (birthday == null)
                	_contact.setBirthday("");
                else
                    _contact.setBirthday(birthday.split(" ")[0]);
                String address = res.getString("address");
                if (address == null)
                	_contact.setAddress("");
                else
                    _contact.setAddress(address);
                String postcode = res.getString("postcode");
                if (postcode == null)
                	_contact.setPostcode("");
                else
                    _contact.setPostcode(postcode);
                String note = res.getString("note");
                if (note == null)
                	_contact.setNote("");
                else
                    _contact.setNote(note);
                String pic = res.getString("pic");
                if (pic == null)
                	_contact.setPic("");
                else
                    _contact.setPic(pic.split("[ ]")[0]);
                String email = res.getString("email");
                if (email == null)
                	_contact.setEmail("");
                else
                    _contact.setEmail(email);
                String group = res.getString("Cgroup");
                if (group == null)
                	_contact.setGroup("");
                else
                    _contact.setGroup(group);
                contacts.add(_contact);
            }
            return contacts;
        }
        return null;
    }
    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        this.close();
    }
    /**
     * class SqlTranslater to translate the data object to sql expression
     * 
     */
    final class SqlTranslater
    {
        private final String[] keywords = 
        {
            "username",
            "passowrd",
            "contactname",
            "sex",
            "age",
            "tellphone",
            "qq",
            "msn",
            "birthday",
            "address",
            "hobby",
            "postcode",
            "note",
            "pic",
            "email",
            "Cgroup",
        };
        /// foreach values point to the keywords key value
        private String[] values = 
        {
            "","","","","","","","","","","","","","","",""
        };
        
        /// initial the values to it's values if the operation string not set a value 
        /// then do nothing and the value is "" string
        private void InitValues(User user,Contact contact)throws ExpressFormatException
        {
            if (user != null)
            {
                values[0]  = user.getUsername();
                values[1]  = user.getPassword();
            }
            if (contact != null)
            {
                values[2]  = contact.getContactname();
                values[3]  = contact.getSex();
                values[4]  = contact.getAge();
                values[5]  = contact.getTellphone();
                values[6]  = contact.getQQ();
                values[7]  = contact.getMSN();
                values[8]  = contact.getBirthday();
                values[9]  = contact.getAddress();
                values[10] = contact.getHobby();
                values[11] = contact.getPostcode();
                values[12] = contact.getNote();
                values[13] = contact.getPic();
                values[14] = contact.getEmail();
                values[15] = contact.getGroup();    
            }
        }
        
        private void checkUserFormat()throws ExpressFormatException
        {
            if (0 == values[0].compareToIgnoreCase("") || 0 == values[1].compareToIgnoreCase(""))
                throw new ExpressFormatException();
        }
        
        private void checkContactFormat()throws ExpressFormatException
        {
            for (int i = 2;i <= 5;i++)
            {
                if (values[i].compareToIgnoreCase("") == 0)
                    throw new ExpressFormatException();
            }
        }
        
        /// translate the string expression to a sql expression
        public String[] translateToSql(User user,Contact contact, operationType type)throws ExpressFormatException 
        {
            this.InitValues(user,contact);
            this.checkUserFormat();
            String[] sqlRes = new String[1];
            switch(type)
            {
            case addUser:
                sqlRes = new String[2];
                sqlRes[0] = "select * from Uuser where username = " + "'" + values[0] + "'";
                sqlRes[1] = "insert into Uuser(username,password)values('" + values[0] + "','" + values[1] + "')";
                return sqlRes;
            case deleteUser:
                sqlRes = new String[2];
                sqlRes[0] = "select * from Uuser where password = " + "'" + values[1] + "' and username = '" + values[0] +"'";
                sqlRes[1] = "delete from Uuser where username='" + values[0] + "'";
                return sqlRes;
            case addContact:
                this.checkContactFormat();
                sqlRes = new String[3];
                sqlRes[0] = "select * from contact where Cuser = " + "'" + values[0] + "' and contactname = '" + values[2] +"'";
                sqlRes[1] = "select * from Uuser where password = " + "'" + values[1] + "' and username = '" + values[0] + "'";
                sqlRes[2] = "insert into contact(contactname,sex,age,tellphone,Cuser";
                String valuesSql = new String("values('" + values[2] + "','" + values[3] + "','" + values[4] + "','" + values[5] + "','" + values[0] + "'");
                for (int i = 6;i < values.length;i++)
                {
                    if (values[i].compareToIgnoreCase("") != 0)
                    {
                        sqlRes[2] = sqlRes[2].concat("," + keywords[i]);
                        valuesSql = valuesSql.concat(",'" + values[i] + "'");
                    }
                }
                sqlRes[2] = sqlRes[2].concat(")");
                valuesSql = valuesSql.concat(")");
                sqlRes[2] = sqlRes[2].concat(valuesSql);
                return sqlRes;
            case deleteContact:
                checkContactFormat();
                sqlRes = new String[3];
                sqlRes[0] = "select * from contact where Cuser = " + "'" + values[0] + "' and contactname = '" + values[2] + "'";
                sqlRes[1] = "select * from Uuser where password = " + "'" + values[1] + "' and username = '" + values[0] + "'";
                sqlRes[2] = "delete from contact where contactname = " + "'" + values[2] + "'";
                return sqlRes;
            case updateUser:
                sqlRes = new String[2];
                sqlRes[0] = "select * from Uuser where password = " + "'" + values[1] +"' and username = '" + values[0] + "'";
                sqlRes[1] = "update user set username = '" + values[0] + "'," + "password = '" + values[1] + "'";
                return sqlRes;
            case updateContact:
                sqlRes = new String[3];
                sqlRes[0] = "select* from contact where Cuser = " + "'" + values[0] + "' and contactname = '" + values[2] + "'";
                sqlRes[1] = "select * from Uuser where password = '" + values[1] + "' and username = '" + values[0] + "'";
                sqlRes[2] = "update contact set ";
                for (int i = 2;i < values.length;i++)
                {
                    if (values[i].compareToIgnoreCase("") != 0)
                    {
                        sqlRes[2] = sqlRes[2].concat(keywords[i] + "= '" + values[i] + "',");
                    }
                }
                sqlRes[2] = sqlRes[2].substring(0,sqlRes[2].length()-2);
                return sqlRes;
            case getUser:
                sqlRes = new String[1];
                sqlRes[0] = "select * from Uuser where password = '" + values[1] + "' and username = '" + values[0] + "'";
                break;
            case getContact:
                sqlRes = new String[2];
                sqlRes[0] = "select * from Uuser where password = '" + values[1] + "' and username = '" + values[0] + "'";
                sqlRes[1] = "select * from contact where Cuser = '" + values[0] + "'" + " and contactname like '" + values[2] + "%'";
                break;
            }    // switch
            return sqlRes;
        }
    }    // class SqlTranslater

}   // class model
    