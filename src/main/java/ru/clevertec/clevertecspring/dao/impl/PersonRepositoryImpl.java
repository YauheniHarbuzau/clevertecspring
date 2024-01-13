package ru.clevertec.clevertecspring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.clevertecspring.dao.PersonRepository;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.dao.rowmapper.PersonRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с {@link Person}
 */
@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;
    private final PersonRowMapper rowMapper;

    @Override
    public Optional<Person> findByUuid(UUID uuid) {
        var session = sessionFactory.openSession();

        var person = session.createQuery("SELECT p FROM Person p WHERE p.uuid = :uuid", Person.class)
                .setParameter("uuid", uuid)
                .uniqueResult();
        session.close();

        return Optional.ofNullable(person);
    }

    @Override
    public List<Person> findAll(int pageSize, int pageNumber) {
        var session = sessionFactory.openSession();

        var personQuery = session.createQuery("SELECT p FROM Person p", Person.class);
        personQuery.setFirstResult((pageNumber - 1) * pageSize);
        personQuery.setMaxResults(pageSize);

        return personQuery.getResultList();
    }

    @Override
    public Person create(Person personToCreate) {
        var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.persist(personToCreate);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return personToCreate;
    }

    @Override
    public Person update(Person personToUpdate) {
        var session = sessionFactory.openSession();
        var personUpdated = new Person();

        try {
            session.beginTransaction();
            personUpdated = session.merge(personToUpdate);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return personUpdated;
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
    public List<Person> findHouseResidents(UUID houseUuid) {
        String sql = "SELECT * FROM persons WHERE residency_id = (SELECT id FROM houses WHERE uuid = ?)";
        return jdbcTemplate.query(sql, rowMapper, houseUuid);
    }

    @Override
    public List<Person> fullTextSearch(String text) {
        String sql = "SELECT * FROM persons WHERE CONCAT(name, ' ', surname) LIKE '%' || ? || '%'";
        return jdbcTemplate.query(sql, rowMapper, text);
    }
}
