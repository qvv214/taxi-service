package ru.digitalleague.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxiDriverInfoModel {

    private Long driverId;
    /**
     * Фамилия.
     */
    private String lastName;
    /**
     * Имя.
     */
    private String firstName;
    /**
     * Уровень.
     */
    private int level;
    /**
     * Рейтинг
     */
    private double rating;
    /**
     * Занят водитель или нет
     */
    private boolean busy;
    /**
     * Модель авто
     */
    private int carModel;
    /**
     * Город
     */
    private int city;
    /**
     * Дата создания.
     */
    private OffsetDateTime createDttm;
}
