--liquibase formatted sql
--changeset Author:create_house_history_table
CREATE TYPE ownership_type AS ENUM ('OWNER', 'TENANT');

CREATE TABLE IF NOT EXISTS house_history
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    house_id  BIGINT                NOT NULL,
    person_id BIGINT                NOT NULL,
    date      TIMESTAMP             NOT NULL,
    ownership ownership_type        NOT NULL
);