package ru.clevertec.clevertecspring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.clevertec.clevertecspring.dao.entity.House;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class HouseRowMapper implements RowMapper<House> {

    @Override
    public House mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        House house = new House();

        house.setId(resultSet.getLong("id"));
        house.setUuid(UUID.fromString(resultSet.getString("uuid")));
        house.setArea(resultSet.getString("area"));
        house.setCountry(resultSet.getString("country"));
        house.setCity(resultSet.getString("city"));
        house.setStreet(resultSet.getString("street"));
        house.setNumber(resultSet.getString("number"));
        house.setCreateDate(resultSet.getTimestamp("create_date").toLocalDateTime());

        return house;
    }
}
