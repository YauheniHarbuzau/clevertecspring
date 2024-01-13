--liquibase formatted sql
--changeset Harbuzau:create_houses_persons_table
CREATE TABLE houses_persons
(
    houses_id  BIGINT NOT NULL REFERENCES houses(id),
    persons_id BIGINT NOT NULL REFERENCES persons(id),
    PRIMARY KEY (houses_id, persons_id)
);