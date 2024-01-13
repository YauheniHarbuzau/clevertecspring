--liquibase formatted sql
--changeset Harbuzau:insert_into_houses_owners_table
INSERT INTO houses_persons (houses_id, persons_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10);