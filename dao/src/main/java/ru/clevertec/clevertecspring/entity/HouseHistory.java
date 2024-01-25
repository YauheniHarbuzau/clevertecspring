package ru.clevertec.clevertecspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.clevertecspring.entity.enums.Ownership;

import java.time.LocalDateTime;

/**
 * Сущность для реализации истории отношений между {@link Person} и {@link House} владелец-арендатор
 *
 * @see House
 * @see Person
 * @see Ownership
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house_history")
public class HouseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "ownership", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Ownership type;
}
