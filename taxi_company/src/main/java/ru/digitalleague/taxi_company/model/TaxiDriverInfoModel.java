package ru.digitalleague.taxi_company.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@ApiModel(value = "TaxiDriverInfoModel", description = "Информация о водителях")
public class TaxiDriverInfoModel {

    @ApiModelProperty(notes = "Уникальный идентификатор", example = "1", position = 0)
    private Long driverId;
    /**
     * Фамилия.
     */
    @ApiModelProperty(notes = "Фамилия", example = "Иванов", position = 1)
    private String lastName;
    /**
     * Имя.
     */
    @ApiModelProperty(notes = "Имя", example = "Иван", position = 2)
    private String firstName;
    /**
     * Уровень.
     */
    @ApiModelProperty(notes = "Уровень", example = "2", position = 3)
    private int level;
    /**
     * Рейтинг
     */
    @ApiModelProperty(notes = "Рейтинг", example = "4.3" ,position = 4)
    private double rating;
    /**
     * Занят водитель или нет
     */
    @ApiModelProperty(notes = "Занят водитель или нет", example = "true", position = 5)
    private boolean busy;
    /**
     * Модель авто
     */
    @ApiModelProperty(notes = "Модель авто", example = "1", position = 6)
    private int carModel;
    /**
     * Город
     */
    @ApiModelProperty(notes = "Город", example = "1", position = 7)
    private int city;
    /**
     * Дата создания.
     */
    @ApiModelProperty(notes = "Дата создания", example = "now()", position = 8)
    private OffsetDateTime createDttm;
}
