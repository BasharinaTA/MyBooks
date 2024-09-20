CREATE TABLE IF NOT EXISTS users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(20)  NOT NULL CHECK (username <> '') UNIQUE,
    hash_password VARCHAR(100) NOT NULL CHECK (hash_password <> ''),
    created       TIMESTAMP    NOT NULL                                                   DEFAULT CURRENT_TIMESTAMP,
    role          VARCHAR(15)  NOT NULL CHECK (role = 'ROLE_ADMIN' OR role = 'ROLE_USER') DEFAULT 'ROLE_USER',
    status        VARCHAR(15)  NOT NULL CHECK (status = 'ACTIVE' OR status = 'INACTIVE' ) DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS profiles
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(20) NOT NULL CHECK (name <> ''),
    surname VARCHAR(30) NOT NULL CHECK (surname <> ''),
    photo   VARCHAR(20),
    user_id INTEGER     NOT NULL REFERENCES users (id) UNIQUE
);

CREATE TABLE IF NOT EXISTS genres
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL CHECK (name <> '') UNIQUE
);

CREATE TABLE IF NOT EXISTS books
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL CHECK (name <> ''),
    author      VARCHAR(50)  NOT NULL CHECK (author <> ''),
    date_start  DATE,
    date_finish DATE,
    type        VARCHAR(15)  NOT NULL CHECK (type = 'AUDIOBOOK' OR type = 'BOOK' OR type = 'EBOOK'),
    created     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    genre_id    INTEGER      NOT NULL REFERENCES genres (id),
    profile_id  INTEGER      NOT NULL REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id      SERIAL PRIMARY KEY,
    text    VARCHAR(500) NOT NULL CHECK (text <> ''),
    created TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    book_id INTEGER      NOT NULL REFERENCES books (id) UNIQUE
);

INSERT INTO public.genres (name) VALUES ('Бизнес-литература');
INSERT INTO public.genres (name) VALUES ('Детектив');
INSERT INTO public.genres (name) VALUES ('Детская литература');
INSERT INTO public.genres (name) VALUES ('Изучение языков');
INSERT INTO public.genres (name) VALUES ('Искусство и культура');
INSERT INTO public.genres (name) VALUES ('История');
INSERT INTO public.genres (name) VALUES ('Компьютерная литература');
INSERT INTO public.genres (name) VALUES ('Красота и здоровье');
INSERT INTO public.genres (name) VALUES ('Мемуары');
INSERT INTO public.genres (name) VALUES ('Научная литература');
INSERT INTO public.genres (name) VALUES ('Научная фантастика');
INSERT INTO public.genres (name) VALUES ('Научно-популярная литература');
INSERT INTO public.genres (name) VALUES ('Общество');
INSERT INTO public.genres (name) VALUES ('Повесть');
INSERT INTO public.genres (name) VALUES ('Поэзия');
INSERT INTO public.genres (name) VALUES ('Поэма');
INSERT INTO public.genres (name) VALUES ('Приключения');
INSERT INTO public.genres (name) VALUES ('Проза');
INSERT INTO public.genres (name) VALUES ('Психология');
INSERT INTO public.genres (name) VALUES ('Пьеса');
INSERT INTO public.genres (name) VALUES ('Рассказ');
INSERT INTO public.genres (name) VALUES ('Роман');
INSERT INTO public.genres (name) VALUES ('Триллер');
INSERT INTO public.genres (name) VALUES ('Ужасы');
INSERT INTO public.genres (name) VALUES ('Фантастика');
INSERT INTO public.genres (name) VALUES ('Фантастическая повесть');
INSERT INTO public.genres (name) VALUES ('Фэнтези');
INSERT INTO public.genres (name) VALUES ('Хобби и досуг');
INSERT INTO public.genres (name) VALUES ('Комедия');
INSERT INTO public.genres (name) VALUES ('Очерк');
INSERT INTO public.genres (name) VALUES ('Сказка');