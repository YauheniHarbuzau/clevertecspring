package ru.clevertec.clevertecspring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.dao.entity.Passport;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.entity.Sex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getLong("id"));
        person.setUuid(UUID.fromString(resultSet.getString("uuid")));
        person.setName(resultSet.getString("name"));
        person.setSurname(resultSet.getString("surname"));
        person.setSex(Sex.valueOf(resultSet.getString("sex")));
        person.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());
        person.setUpdateDate(resultSet.getTimestamp("update_date").toLocalDateTime());

        Passport passport = new Passport();
        passport.setSeries(resultSet.getString("passport_series"));
        passport.setNumber(resultSet.getString("passport_number"));

        return person;
    }
}
