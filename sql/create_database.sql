CREATE DATABASE queststore;

CREATE TABLE User_ (
    login VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email VARCHAR(40),
    type VARCHAR(9)
);

CREATE TABLE Mentor (
    id SERIAL PRIMARY KEY,
    user_login VARCHAR(20) REFERENCES User_(login)
);

CREATE TABLE Class_ (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE Mentor_class (
    mentor_id INTEGER REFERENCES Mentor(id),
    class_id INTEGER REFERENCES Class_(id)
);

CREATE TABLE Level_ (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    min_coolcoins INTEGER
);

CREATE TABLE Codecooler (
    id SERIAL PRIMARY KEY,
    user_login VARCHAR(20) REFERENCES User_(login),
    class_id INTEGER REFERENCES Class_(id),
    level_id INTEGER REFERENCES Level_(id),
    wallet INTEGER DEFAULT 0
);

CREATE TABLE Artifact (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    description VARCHAR(100),
    price INTEGER,
    category VARCHAR(20)
);

CREATE TABLE Codecooler_articact (
    id SERIAL PRIMARY KEY,
    codecooler_id INTEGER REFERENCES Codecooler(id),
    artifact_id INTEGER REFERENCES Artifact(id),
    purchase_date TIMESTAMP,
    use_date TIMESTAMP
);

CREATE TABLE Quest (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    description VARCHAR(100),
    value INTEGER,
    category VARCHAR(20)
);

CREATE TABLE Codecooler_quest (
    id SERIAL PRIMARY KEY,
    codecooler_id INTEGER REFERENCES Codecooler(id),
    quest_id INTEGER REFERENCES Quest(id),
    mark_date TIMESTAMP
);

CREATE TABLE Team (
    id SERIAL PRIMARY KEY,
    artefact_id INTEGER REFERENCES Artifact(id),
    leader_id INTEGER REFERENCES Codecooler(id),
    status VARCHAR(10) DEFAULT 'Pending'
);

CREATE TABLE Codecooler_team (
    codecooler_id INTEGER REFERENCES Codecooler(id),
    group_id INTEGER REFERENCES Team(id),
    coolcoins INTEGER
);