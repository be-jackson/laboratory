DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    username   VARCHAR(20) NOT NULL,
    github_url VARCHAR(255)         DEFAULT NULL,
    created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (id),
    CONSTRAINT unq_username UNIQUE (username)
);