create database zander;
use zander;
CREATE USER 'zander'@'%' IDENTIFIED WITH mysql_native_password BY 'Passwordzander321';
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
FLUSH PRIVILEGES;
GRANT SELECT ON zander.* TO crutech@'%';
GRANT INSERT ON zander.* TO crutech@'%';
GRANT UPDATE ON zander.* TO crutech@'%';

CREATE TABLE surveydata (
  uuid VARCHAR(36) not null primary key,
  username TEXT,
);
