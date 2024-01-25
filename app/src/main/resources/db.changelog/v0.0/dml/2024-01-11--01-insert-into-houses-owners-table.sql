--liquibase formatted sql
--changeset Author:insert_into_houses_owners_table
INSERT INTO houses_owners (house_id, owner_id)
VALUES (1, 1),
       (2, 1),
       (3, 5),
       (4, 7),
       (5, 9),
       (5, 10);