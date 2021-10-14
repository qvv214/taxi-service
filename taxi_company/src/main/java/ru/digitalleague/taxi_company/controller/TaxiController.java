package ru.digitalleague.taxi_company.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.taxi_company.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.service.TaxiDriveInfoServiceImpl;

/**
 * Контроллер получающий информацию о поездке.
 */
@Slf4j
@Api
@RestController
public class TaxiController {

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
    @PostMapping("trip-start")
    public ResponseEntity<TaxiDriverInfoModel> startTrip(@RequestBody TaxiDriverInfoModel taxiDriverInfoModel) {
        if (taxiDriveInfoService.tripStart(taxiDriverInfoModel) > 0) {
            taxiDriveInfoService.setBusy(taxiDriverInfoModel.getDriverId(), true);
            return new ResponseEntity(taxiDriverInfoModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(taxiDriverInfoModel, HttpStatus.BAD_REQUEST);
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
    @PostMapping("/trip-complete")
    public ResponseEntity<TaxiDriverInfoModel> completeTrip(@RequestBody TaxiDriverInfoModel taxiDriverInfoModel) {
        if (taxiDriveInfoService.tripEnd(taxiDriverInfoModel) > 0) {
            taxiDriveInfoService.setBusy(taxiDriverInfoModel.getDriverId(), false);
            return new ResponseEntity(taxiDriverInfoModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(taxiDriverInfoModel, HttpStatus.BAD_REQUEST);
    }
}
