CREATE TABLE games (
    name varchar(255) PRIMARY KEY ,
    current_status varchar(255)
);

CREATE TABLE players (
    name varchar(255) PRIMARY KEY
);

CREATE TABLE games_players (
  id INTEGER primary key,
  player_id varchar(255) REFERENCES players (name),
  game_id varchar(255) REFERENCES games (name)
);