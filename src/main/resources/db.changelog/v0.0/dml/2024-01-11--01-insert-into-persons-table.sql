--liquibase formatted sql
--changeset Harbuzau:insert_into_persons_table
INSERT INTO persons (uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, residency_id)
VALUES ('6e19a7ae-78f5-475f-92cd-8e838bbc269e', 'Ivan', 'Ivanov', 'MALE', 'HB', '1111111', NOW(), NOW(), 1),
       ('36245727-058e-455b-9d42-accc28c05177', 'Petr', 'Petrov', 'MALE', 'HB', '1112222', NOW(), NOW(), 1),
       ('fb085176-7794-416b-8548-887ece39e8f1', 'Sidr', 'Sidorov', 'MALE', 'HB', '1113333', NOW(), NOW(), 2),
       ('b2c5641c-293c-457e-9b21-a2b6dbe47216', 'Svetlana', 'Svetlanova', 'FEMALE', 'HB', '2211111', NOW(), NOW(), 2),
       ('4563844b-c79a-499e-95bd-9ddca776e391', 'Vera', 'Verova', 'FEMALE', 'HB', '3311111', NOW(), NOW(), 3),
       ('12f98707-0cdc-4c4d-96b4-45a8c5676eed', 'Alex', 'Alov', 'MALE', 'HB', '1133444', NOW(), NOW(), 3),
       ('ab14f5c8-1e57-492c-ac31-cc518f40d59d', 'Julia', 'Juliava', 'FEMALE', 'HB', '4441112', NOW(), NOW(), 4),
       ('c2994a09-1211-49d2-858f-9d79744fb2f3', 'Semen', 'Semenov', 'MALE', 'HB', '5511771', NOW(), NOW(), 4),
       ('79c594fe-7ed4-49ed-aada-fe165a57e640', 'Stanislav', 'Stanislavov', 'MALE', 'HB', '7711118', NOW(), NOW(), 5),
       ('7aa2b539-cab9-4a6c-8860-dda2c8d7f8d5', 'Anatolij', 'Anatolevov', 'MALE', 'HB', '5533112', NOW(), NOW(), 5);