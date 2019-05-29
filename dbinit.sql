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
  joins int,
  deaths int,
  status TEXT,
  lastseen TEXT,
  isVerified TEXT,
  discordid VARCHAR(18),
  discorddiscriminator TEXT
);

CREATE TABLE punishments (
  id int AUTO_INCREMENT not null primary key,
  punisheduseruuid VARCHAR(36),
  punishedusername TEXT,
  punisheruuid VARCHAR(36),
  punisherusername TEXT,
  punishtype TEXT,
  reason TEXT,
  punishtimestamp TEXT
);
