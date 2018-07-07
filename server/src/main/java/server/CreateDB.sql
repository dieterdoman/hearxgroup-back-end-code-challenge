CREATE TABLE games (
    game_name varchar(255),
    game_status varchar(255),
    PRIMARY KEY (game_name)
);

CREATE TABLE players (
    player_name varchar(255),
    PRIMARY KEY (player_name)
);

CREATE TABLE games_players (
  player_name varchar(255) REFERENCES players (player_name),
  game_name varchar(255) REFERENCES games (game_name),
  PRIMARY KEY (player_name, game_name)
);