package ru.clevertec.clevertecspring.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.clevertecspring.dao.entity.enums.PassportSeries;

/**
 * Сущность Passport
 *
 * @see PassportSeries
 * @see Person
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"passport_series", "passport_number"}))
public class Passport {

    @Column(name = "passport_series", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PassportSeries series;

    @Column(name = "passport_number", nullable = false)
    private String number;
}
