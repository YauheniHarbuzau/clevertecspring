--liquibase formatted sql
--changeset Author:create_houses_owners_table
CREATE TABLE IF NOT EXISTS houses_owners
(
    house_id BIGINT NOT NULL REFERENCES houses(id),
    owner_id BIGINT NOT NULL REFERENCES persons(id),
    PRIMARY KEY (house_id, owner_id)
);