CREATE DATABASE invader;

-- \c mydatabase;

-- Player 테이블 생성
-- player_id = 사용자의 고유 아이디 
-- created_at = 사용자가 만든 시간
-- username = 사용자 이름
-- password = 사용자의 비밀번호
CREATE TABLE Player (
    player_id VARCHAR(12),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(10) NOT NULL,
    PRIMARY KEY (player_id, created_at)
);

-- Score 테이블 생성
-- player_id = 사용자의 고유 아이디 
-- created_at = 사용자가 게임을 한 시간을 의미
-- game_id = pvp와 1p 게임을 게임 아이디로 구별하고자 추가
-- point = 1p 게임이 끝난 상황에서 플레이어의 점수를 기록
-- result = pvp 게임이 끝난 상황에서 플레이어의 승패를 문자열로 기록
CREATE TABLE Score (
    player_id VARCHAR(12),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    game_id VARCHAR(6) NOT NULL,
    point INTEGER NOT NULL,
    result VARCHAR(4) NOT NULL,
    PRIMARY KEY (player_id, game_id),
    FOREIGN KEY (player_id) REFERENCES Player(player_id)
);

-- Rank 테이블 생성
-- player_id = 사용자의 고유 아이디 
-- game_id = 사용자가 게임을 한 시간을 의미
-- player_point = pvp와 1p 게임을 게임 아이디로 구별하고자 추가
-- rank = 1p 게임이 끝난 상황에서 플레이어의 점수를 기록
CREATE TABLE Rank (
    player_id VARCHAR(12),
    username VARCHAR(255) NOT NULL,
    game_id VARCHAR(6),
    player_point INTEGER NOT NULL,
    rank INTEGER NOT NULL,
    PRIMARY KEY (player_id, game_id),
    FOREIGN KEY (player_id) REFERENCES Player(player_id),
    FOREIGN KEY (game_id) REFERENCES Score(game_id)
);