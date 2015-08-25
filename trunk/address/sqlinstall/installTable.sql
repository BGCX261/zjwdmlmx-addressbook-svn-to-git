use msdb
/* check is the addressbook exists and if not create the database*/
if (not exists (select * from sys.databases
                where (name = 'addressbook')))
begin
  create database addressbook;
end


use addressbook;
/* check is the table Uuser in the addressbook if not create it*/
if (not exists (select * from sys.tables
                where (name = 'Uuser')))
begin
  create table Uuser
    (username char(40) not null,
     password char(40) not null,
     primary key(username));
end
/* check is the table contact in the addressbook if not create is*/
if (not exists (select * from sys.tables
                where (name = 'contact')))
begin
  create table contact
    (contactname char(40) not null,
     sex         char(4)  not null check(sex in ('man','lady')),
     age         char(4)  not null,
     tellphone   char(20) not null,
     qq          char(20),
     msn         char(20),
     birthday    char(20),
     address     nvarchar(1000),
     hobby       nvarchar(1000),
     postcode    char(10),
     note        nvarchar(1000),
     pic         nvarchar(255),
     email       nvarchar(255),
     Cgroup      char(40),
     Cuser       char(40),
     primary key(Cuser,contactname));
end
go

use msdb
/* create a login name for addressbook operation */
if (not exists (select * from sys.syslogins 
                where name = 'addressbookadmin'))
begin
  create login addressbookadmin 
  with password = '2V3x9HjiJxD87Rit';
end
go

use addressbook

if (not exists (select * from sys.sysusers
                where name = 'addbook'))
begin
  create user addbook for login addressbookadmin
end
go

use addressbook

grant insert,update,delete,select on Uuser,contact to addbook
go



