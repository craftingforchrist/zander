DROP DATABASE IF EXISTS zander;
CREATE DATABASE IF NOT EXISTS zander;
USE zander;

CREATE USER 'zander'@'%' IDENTIFIED WITH mysql_native_password BY 'Passwordzander321';
FLUSH PRIVILEGES;
GRANT SELECT ON zander.* TO zander@'%';
GRANT INSERT ON zander.* TO zander@'%';
GRANT UPDATE ON zander.* TO zander@'%';

CREATE TABLE playerdata (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  uuid VARCHAR(36),
  username VARCHAR(16),
  joined TIMESTAMP NOT NULL DEFAULT NOW(),
  deaths int DEFAULT 0,
  bedlocation POINT NULL
 );
create index playerdata_username on playerdata (username);

CREATE TABLE sessions (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  player_id INT NOT NULL DEFAULT 0,
  sessionstart TIMESTAMP NOT NULL DEFAULT NOW(),
  sessionend   TIMESTAMP NULL,
  ipaddress VARCHAR(45),
  FOREIGN KEY (player_id) REFERENCES playerdata (id)
);
create index sessions_player_id    on sessions (player_id);
create index sessions_sessionstart on sessions (sessionstart);
create index sessions_sessionend   on sessions (sessionend);

CREATE TABLE punishments (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  punisheduser_id INT NOT NULL DEFAULT 0,
  punisher_id INT NOT NULL DEFAULT 0,
  punishtype ENUM('KICK', 'BAN', 'TEMP BAN'),
  reason TEXT,
  punishtimestamp TIMESTAMP NOT NULL DEFAULT NOW(),
  FOREIGN KEY (punisheduser_id) REFERENCES playerdata (id),
  FOREIGN KEY (punisher_id) REFERENCES playerdata (id)
);
