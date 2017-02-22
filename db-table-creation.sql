create table games ( game_id SERIAL NOT NULL, result CHAR(1) NOT NULL, opponent_type VARCHAR(50) NOT NULL, PRIMARY KEY (game_id));
create table moves ( move_id SERIAL NOT NULL, move_number INT NOT NULL, index_played INT NOT NULL, board_hash VARCHAR(50) NOT NULL, game_id BIGINT UNSIGNED NOT NULL, FOREIGN KEY (game_id) REFERENCES games(game_id));
