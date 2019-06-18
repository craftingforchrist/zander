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
  joins int NOT NULL DEFAULT 0,
  deaths int NOT NULL DEFAULT 0,
  status ENUM('Currently Online', 'Offline'),
  lastseen TIMESTAMP,
  initaljoin TIMESTAMP,
  totalplaytime BIGINT,
  isVerified BOOLEAN DEFAULT false,
  discordid VARCHAR(18),
  discorddiscriminator VARCHAR(35),
  ipaddress VARCHAR(45),
  bedlocation TEXT
 );

 CREATE TABLE mobdeath (
  player_id int NOT NULL DEFAULT 0,
  mobdeath_player int NOT NULL DEFAULT 0,
  mobdeath_creeper int NOT NULL DEFAULT 0,
  mobdeath_enderdragon int NOT NULL DEFAULT 0,
  mobdeath_wither int NOT NULL DEFAULT 0,
  mobdeath_elderguardian int NOT NULL DEFAULT 0,
  mobdeath_witherskeleton int NOT NULL DEFAULT 0,
  mobdeath_shulker int NOT NULL DEFAULT 0,
  mobdeath_evoker int NOT NULL DEFAULT 0,
  mobdeath_bat int NOT NULL DEFAULT 0,
  FOREIGN KEY (player_id) REFERENCES playerdata (id)
);

CREATE TABLE punishments (
  id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
  punisheduser_id int NOT NULL DEFAULT 0,
  punisher_id int NOT NULL DEFAULT 0,
  punishplatform ENUM('SERVER', 'DISCORD'),
  punishtype ENUM('KICK', 'BAN', 'TEMP BAN'),
  reason TEXT,
  punishtimestamp TIMESTAMP,
  FOREIGN KEY (punisheduser_id) REFERENCES playerdata (id),
  FOREIGN KEY (punisher_id) REFERENCES playerdata (id)
);
