package ru.clevertec.clevertecspring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.clevertecspring.dao.HouseRepository;
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.dao.rowmapper.HouseRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с {@link House}
 */
@Repository
@RequiredArgsConstructor
public class HouseRepositoryImpl implements HouseRepository {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;
    private final HouseRowMapper rowMapper;

    @Override
    public Optional<House> findByUuid(UUID uuid) {
        var session = sessionFactory.openSession();

        var house = session.createQuery("SELECT h FROM House h WHERE h.uuid = :uuid", House.class)
                .setParameter("uuid", uuid)
                .uniqueResult();
        session.close();

        return Optional.ofNullable(house);
    }

    @Override
    public List<House> findAll(int pageSize, int pageNumber) {
        var session = sessionFactory.openSession();

        var houseQuery = session.createQuery("SELECT h FROM House h", House.class);
        houseQuery.setFirstResult((pageNumber - 1) * pageSize);
        houseQuery.setMaxResults(pageSize);

        return houseQuery.getResultList();
    }

    @Override
    public House create(House houseToCreate) {
        var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.persist(houseToCreate);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return houseToCreate;
    }

    @Override
    public House update(House houseToUpdate) {
        var session = sessionFactory.openSession();
        var houseUpdated = new House();

        try {
            session.beginTransaction();
            houseUpdated = session.merge(houseToUpdate);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return houseUpdated;
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.remove(findByUuid(uuid).get());
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
    }

    @Override
    public List<House> fullTextSearch(String text) {
        String sql = "SELECT * FROM houses WHERE CONCAT(area, ' ', country, ' ', city, ' ', street, ' ', number) LIKE '%' || ? || '%'";
        return jdbcTemplate.query(sql, rowMapper, text);
    }
}
