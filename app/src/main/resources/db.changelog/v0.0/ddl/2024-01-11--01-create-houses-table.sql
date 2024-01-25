--liquibase formatted sql
--changeset Author:create_houses_table
CREATE TABLE IF NOT EXISTS houses
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    uuid        UUID                  NOT NULL UNIQUE,
    area        VARCHAR(100)          NOT NULL,
    country     VARCHAR(100)          NOT NULL,
    city        VARCHAR(100)          NOT NULL,
    street      VARCHAR(100)          NOT NULL,
    number      VARCHAR(10)           NOT NULL,
    create_date TIMESTAMP             NOT NULL,
    UNIQUE (area, country, city, street, number)
);