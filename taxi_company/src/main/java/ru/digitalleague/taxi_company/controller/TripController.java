package ru.digitalleague.taxi_company.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalleague.core.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.model.TripInfoModel;
import ru.digitalleague.taxi_company.service.TaxiDriveInfoServiceImpl;

import java.text.DateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * Контроллер получающий информацию о поездке.
 */
@Slf4j
@Api
@RestController
public class TripController {

    @Autowired
    private TaxiDriveInfoServiceImpl taxiDriveInfoService;

    /**
     * Метод начала поездки
     * @param taxiDriverInfoModel
     */
    @ApiOperation(value = "Начало поездки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Поездка успешно стартовала"),
            @ApiResponse(code = 400, message = "Введены неверные параметры")
    })
    @PutMapping("trip-start")
    public ResponseEntity<String> startTrip(@RequestBody TaxiDriverInfoModel taxiDriverInfoModel) {
        if (taxiDriveInfoService.tripStart(taxiDriverInfoModel) > 0) {
            return ResponseEntity.ok("Водитель начал поездку");
        }
        return ResponseEntity.badRequest().body("Поездка не началась");
    }

    /**
     * Метод получает инфо о завершении поездки.
     * @param taxiDriverInfoModel
     * */

    @ApiOperation(value = "Завершение поездки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Поездка успешно завершена"),
            @ApiResponse(code = 400, message = "Введены неверные параметры")
    })
    @PutMapping("/trip-complete")
    public ResponseEntity<String> completeTrip(@RequestBody TaxiDriverInfoModel taxiDriverInfoModel) {
        if (taxiDriveInfoService.tripEnd(taxiDriverInfoModel) > 0) {
            return ResponseEntity.ok("Поездка окончена");
        }
        return ResponseEntity.badRequest().body("Поездка не закончилась");
    }

    /**
     * Получаем и сохраняем оплату по id поездки в таблицу trip_info
     *
     */
    @PutMapping("/payment/{id}")
    public ResponseEntity<String> payment(@PathVariable("id") Long id) {
        if (taxiDriveInfoService.getPayment(id) > 0) {
            return ResponseEntity.ok("Оплата прошла успешно");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Оплата не прошла");
        }
    }
}
