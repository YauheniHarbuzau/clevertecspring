--liquibase formatted sql
--changeset Author:create_house_history_trigger
CREATE OR REPLACE FUNCTION person_create_house_func() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    INSERT INTO house_history (house_id, person_id, date, ownership)
    VALUES (NEW.residency_id, NEW.id, CURRENT_DATE, 'TENANT');
    RETURN NEW;
END;
$$;

CREATE TRIGGER person_create_house
    AFTER INSERT
    ON persons
    FOR EACH ROW
EXECUTE FUNCTION person_create_house_func();

CREATE OR REPLACE FUNCTION person_change_house_func() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    INSERT INTO house_history (house_id, person_id, date, ownership)
    VALUES (NEW.residency_id, NEW.id, CURRENT_DATE, 'TENANT');
    RETURN NEW;
END;
$$;

CREATE TRIGGER person_change_house
    AFTER UPDATE OF residency_id
    ON persons
    FOR EACH ROW
EXECUTE FUNCTION person_change_house_func();

CREATE OR REPLACE FUNCTION house_owner_change_func() RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO house_history (house_id, person_id, date, ownership)
        VALUES (NEW.house_id, NEW.owner_id, CURRENT_DATE, 'OWNER');
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO house_history (house_id, person_id, date, ownership)
        VALUES (OLD.house_id, OLD.owner_id, CURRENT_DATE, 'OWNER');
        RETURN OLD;
    END IF;
END;
$$;

CREATE TRIGGER house_owner_change
    AFTER INSERT OR DELETE
    ON houses_owners
    FOR EACH ROW
EXECUTE FUNCTION house_owner_change_func();