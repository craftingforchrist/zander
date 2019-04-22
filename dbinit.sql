create database zander;
use zander;
CREATE USER 'zander'@'%' IDENTIFIED WITH mysql_native_password BY 'Passwordzander321';
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
FLUSH PRIVILEGES;
GRANT SELECT ON zander.* TO zander@'%';
GRANT INSERT ON zander.* TO zander@'%';
GRANT UPDATE ON zander.* TO zander@'%';

CREATE TABLE playerdata (
  uuid VARCHAR(36) not null primary key,
  username TEXT,
  joins TEXT,
  leaves TEXT,
  deaths TEXT,
  lastseen TEXT,
  ipaddress TEXT
);
