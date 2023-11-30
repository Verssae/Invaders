CREATE DATABASE invader;

\c invader;


CREATE TABLE Player (
    login_id VARCHAR(10) NOT NULL,
    login_password VARCHAR(10) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);
CREATE TABLE easy_score(
    username VARCHAR(255) NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (username),
    foreign key (username) references Player(username)
);
CREATE TABLE normal_score(
    username VARCHAR(255) NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (username),
    foreign key (username) references Player(username)
);
CREATE TABLE hard_score(
    username VARCHAR(255) NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (username),
    foreign key (username) references Player(username)
);
CREATE TABLE hardcore_score(
    username VARCHAR(255) NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (username),
    foreign key (username) references Player(username)

);
CREATE TABLE pvp_score(
    username VARCHAR(255) NOT NULL,
    score INTEGER NOT NULL,
    PRIMARY KEY (username),
    foreign key (username) references Player(username)

);



