package ru.digitalleague.taxi_company.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.core.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.service.TaxiDriveInfoServiceImpl;

@Slf4j
@RestController
public class DriverController {

    @Autowired
    private TaxiDriveInfoServiceImpl taxiDriveInfoService;

    /**
     * Метод для смены статуса водлителя (свободен или занят)
     */
    @PostMapping("/driver-free")
    public ResponseEntity<String> createTrip(@RequestBody TaxiDriverInfoModel taxiDriverInfoModel) {
        if (taxiDriveInfoService.setBusy(taxiDriverInfoModel.getDriverId(), taxiDriverInfoModel.isBusy()) > 0) {
            return ResponseEntity.ok("Водитель изменил статус");
        } else {
            return ResponseEntity.badRequest().body("Водитель не найден");
        }
    }
}
