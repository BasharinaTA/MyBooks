CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(20)  NOT NULL CHECK (username <> '') UNIQUE,
    password VARCHAR(100) NOT NULL CHECK (password <> ''),
    created  DATE         NOT NULL                      DEFAULT CURRENT_TIMESTAMP,
    role     VARCHAR(15)  NOT NULL CHECK (role <> '')   DEFAULT 'ROLE_USER',
    status   VARCHAR(15)  NOT NULL CHECK (status <> '') DEFAULT 'ACTIVE'
);

CREATE TABLE profiles
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(20) NOT NULL CHECK (name <> ''),
    surname VARCHAR(30) NOT NULL CHECK (surname <> ''),
    photo   VARCHAR(20),
    user_id INTEGER REFERENCES users (id)
);

CREATE TABLE genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL CHECK (name <> '') UNIQUE
);

CREATE TABLE books
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL CHECK (name <> ''),
    author      VARCHAR(50)  NOT NULL CHECK (author <> ''),
    date_start  DATE,
    date_finish DATE,
    type        VARCHAR(15),
    created     DATE         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    genre_id    INTEGER REFERENCES genres (id),
    profile_id  INTEGER REFERENCES profiles (id)
);

CREATE TABLE comments
(
    id      SERIAL PRIMARY KEY,
    text    VARCHAR(500),
    rate    BOOLEAN,
    created DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    book_id INTEGER REFERENCES books (id)
--     profile_id INTEGER REFERENCES profiles (id)
);