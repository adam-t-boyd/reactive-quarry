DROP TABLE IF EXISTS users;

CREATE TABLE user_details
(
    id             SERIAL,
    first_name     VARCHAR(100) NOT NULL,
    last_name      VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    username       VARCHAR(50) NOT NULL UNIQUE,
    password       VARCHAR(250) NOT NULL,
    user_role      VARCHAR(50) NOT NULL,
--    avatar         VARCHAR,
--    secret         VARCHAR,
--    account_enabled BOOLEAN NOT NULL DEFAULT true,
--    account_expired BOOLEAN NOT NULL DEFAULT false,
--    account_locked BOOLEAN NOT NULL DEFAULT false,
--    credentials_expired BOOLEAN NOT NULL DEFAULT false,
--    use_2fa BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT user_details_pk PRIMARY KEY (id)
);

INSERT INTO users (username, password, user_role, first_name, last_name, email) VALUES ('adam123', '$2a$12$ax/tHBEraPIk5lCBy079ueyu8M5IN5Wglt1skhXvbwkFVTtP180nW', 'ADMIN', 'Adam', 'Boyd', 'adam@gmail.com');
INSERT INTO users (username, password, user_role, first_name, last_name, email) VALUES ('bob123', '$2a$12$kGiwKDP9mLGpCDtGYIplkOB26fnjEfm8lkPNJYIGlifvjW72xmZRy', 'ADMIN', 'Bob', 'Doe', 'bob@doe.com');
INSERT INTO users (username, password, user_role, first_name, last_name, email) VALUES ('alice123', '$2a$12$E3wv3qAD74hUNeRQEVLql.WUWY3UASAsFEIB/KeuXOZLjgol.amRG', 'ADMIN', 'Alice', 'Doe', 'alice@doe.com');