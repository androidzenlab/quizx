mysql website victorli8@yahoo.co.nz/abcd6789,   instalation root/abcd6789

1. Download and unzip MySql 5
2. Copy mysql jar to jboss-home/server/default/lib


1. run mysql using the "mysql -u root -p" to go to the sql UI.
2. run the following to create db and user:
create database mydatabase character set utf8 collate utf8_bin;
create user 'victor'@'localhost' identified by 'pass1';
GRANT ALL ON mydatabase.* to 'victor'@'localhost'; 

create database fsnet character set utf8 collate utf8_bin;
create user 'fsnet'@'localhost' identified by 'pass1';
GRANT ALL ON fsnet.* to 'fsnet'@'localhost'; 

3.login sql by "root" user: mysql -u root -p mydatabase  (password is "abcd6789")

4.login sql by "victor" or "fsnet" user:   
mysql -u victor -p mydatabase (password is "pass1")
mysql -u fsnet -p mydatabase  (password is "pass1")

5. deploy application with 'create' from persistence.xml in EJB/META-INF/persistence.xml


6. useful mySQL sql commands:
use databaseName;
show tables;
drop table tableName;
desc tableName;

select max(categoryVersion) from quiz where category_id=1;
select max(categoryVersion) from categoryVersion where category_id=1;
