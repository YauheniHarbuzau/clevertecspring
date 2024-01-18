--liquibase formatted sql
--changeset Author:create_houses_owners_table
CREATE TABLE IF NOT EXISTS houses_owners
(
    houses_id BIGINT NOT NULL REFERENCES houses(id),
    owners_id BIGINT NOT NULL REFERENCES persons(id),
    PRIMARY KEY (houses_id, owners_id)
);