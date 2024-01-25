--liquibase formatted sql
--changeset Author:create_persons_table
CREATE TABLE IF NOT EXISTS persons
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    uuid            UUID                  NOT NULL UNIQUE,
    name            VARCHAR(100)          NOT NULL,
    surname         VARCHAR(100)          NOT NULL,
    sex             VARCHAR(10)           NOT NULL,
    passport_series VARCHAR(10)           NOT NULL,
    passport_number VARCHAR(10)           NOT NULL,
    create_date     TIMESTAMP             NOT NULL,
    update_date     TIMESTAMP             NOT NULL,
    residency_id    BIGINT REFERENCES houses (id),
    UNIQUE (passport_series, passport_number)
);