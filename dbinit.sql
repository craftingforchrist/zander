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
INSERT INTO playerdata (uuid, username) VALUES ('f78a4d8d-d51b-4b39-98a3-230f2de0c670', 'CONSOLE');

CREATE TABLE gamesessions (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  player_id INT NOT NULL DEFAULT 0,
  sessionstart TIMESTAMP NOT NULL DEFAULT NOW(),
  sessionend TIMESTAMP NULL,
  ipaddress VARCHAR(45),
  FOREIGN KEY (player_id) REFERENCES playerdata (id)
);
create index gamesessions_player_id    on gamesessions (player_id);
create index gamesessions_sessionstart on gamesessions (sessionstart);
create index gamesessions_sessionend   on gamesessions (sessionend);

CREATE TABLE punishments (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  punisheduser_id INT NOT NULL DEFAULT 0,
  punisher_id INT NOT NULL DEFAULT 0,
  punishtype ENUM('KICK', 'BAN', 'TEMP BAN', 'MUTE'),
  platform ENUM('SERVER', 'DISCORD', 'WEBSITE'),
  reason TEXT,
  punishtimestamp TIMESTAMP NOT NULL DEFAULT NOW(),
  FOREIGN KEY (punisheduser_id) REFERENCES playerdata (id),
  FOREIGN KEY (punisher_id) REFERENCES playerdata (id)
);

CREATE TABLE webusers (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  username VARCHAR(16),
  email VARCHAR(150),
  password BINARY(60),
  registered TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS `sessions` (
  `session_id` varchar(128) COLLATE utf8mb4_bin NOT NULL,
  `expires` int(11) unsigned NOT NULL,
  `data` mediumtext COLLATE utf8mb4_bin,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB
