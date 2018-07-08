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

CREATE TABLE rounds (
  id integer,
  round_number integer,
  game_name varchar(255) REFERENCES games(game_name),
  number1 integer,
  number2 integer,
  answer integer,
  PRIMARY KEY (id)
);

CREATE TABLE scores (
  id integer,
  score integer,
  response_time integer,
  round integer REFERENCES rounds (id),
  player_name varchar(255) REFERENCES players (player_name),
  PRIMARY KEY (id)
);