--liquibase formatted sql
--changeset Harbuzau:insert_into_houses_table
INSERT INTO houses (uuid, area, country, city, street, number, create_date)
VALUES ('7d7369f0-368f-4ed3-9fbb-71e7c7664121', 'Eurasia', 'Belarus', 'Gomel', 'Bazarnaya', '1-A', NOW()),
       ('3d2dba22-783b-4a45-9f20-4457dbef611a', 'Eurasia', 'Belarus', 'Gomel', 'Bazarnaya', '1-B', NOW()),
       ('fc34b864-9b6f-43f3-8f38-2707b34e3c7b', 'Eurasia', 'Belarus', 'Gomel', 'Bazarnaya', '2-A', NOW()),
       ('1733a4f1-f40c-455f-a108-6d457a6286c7', 'Eurasia', 'Belarus', 'Gomel', 'Bazarnaya', '2-B', NOW()),
       ('5f41d967-25bb-44ba-b6ba-687025794cc9', 'Eurasia', 'Belarus', 'Gomel', 'Bazarnaya', '3', NOW());