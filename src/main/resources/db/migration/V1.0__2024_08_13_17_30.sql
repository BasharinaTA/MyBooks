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
    created     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    genre_id    INTEGER REFERENCES genres (id),
    profile_id  INTEGER REFERENCES profiles (id)
);

CREATE TABLE comments
(
    id      SERIAL PRIMARY KEY,
    text    VARCHAR(500),
    created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    book_id INTEGER REFERENCES books (id)
);

INSERT INTO genres (name)
VALUES ('Бизнес-литература');
INSERT INTO genres (name)
VALUES ('Детектив');
INSERT INTO genres (name)
VALUES ('Детская литература');
INSERT INTO genres (name)
VALUES ('Изучение языков');
INSERT INTO genres (name)
VALUES ('Искусство и культура');
INSERT INTO genres (name)
VALUES ('История');
INSERT INTO genres (name)
VALUES ('Компьютерная литература');
INSERT INTO genres (name)
VALUES ('Красота и здоровье');
INSERT INTO genres (name)
VALUES ('Мемуары');
INSERT INTO genres (name)
VALUES ('Научная литература');
INSERT INTO genres (name)
VALUES ('Научная фантастика');
INSERT INTO genres (name)
VALUES ('Научно-популярная литература');
INSERT INTO genres (name)
VALUES ('Общество');
INSERT INTO genres (name)
VALUES ('Повесть');
INSERT INTO genres (name)
VALUES ('Поэзия');
INSERT INTO genres (name)
VALUES ('Поэма');
INSERT INTO genres (name)
VALUES ('Приключения');
INSERT INTO genres (name)
VALUES ('Проза');
INSERT INTO genres (name)
VALUES ('Психология');
INSERT INTO genres (name)
VALUES ('Пьеса');
INSERT INTO genres (name)
VALUES ('Рассказ');
INSERT INTO genres (name)
VALUES ('Роман');
INSERT INTO genres (name)
VALUES ('Триллер');
INSERT INTO genres (name)
VALUES ('Ужасы');
INSERT INTO genres (name)
VALUES ('Фантастика');
INSERT INTO genres (name)
VALUES ('Фантастическая повесть');
INSERT INTO genres (name)
VALUES ('Фэнтези');
INSERT INTO genres (name)
VALUES ('Хобби и досуг');