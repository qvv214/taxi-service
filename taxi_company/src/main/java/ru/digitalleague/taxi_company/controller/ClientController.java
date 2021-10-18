package ru.digitalleague.taxi_company.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import ru.digitalleague.core.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.model.TripInfoModel;
import ru.digitalleague.taxi_company.service.TaxiDriveInfoServiceImpl;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    private TaxiDriveInfoServiceImpl taxiDriveInfoService;

    /**
     * Присваиваем рейтинг к поездке в таблице trip_info по trip_id
     * после чего обновляем общий рейтинг водителя в таблице taxi_drive_info
     */
    @PutMapping("/trip-rating")
    public ResponseEntity<String> addRatingForDriver(@RequestBody TripInfoModel tripInfoModel) {
        if (taxiDriveInfoService.updateRatingForTripById(tripInfoModel.getTrip_id(), tripInfoModel.getRating()) > 0) {
            TaxiDriverInfoModel taxiDriverInfoModel = taxiDriveInfoService.getDriverFromTripInfoById(tripInfoModel.getTrip_id());

            if (ObjectUtils.isEmpty(taxiDriverInfoModel.getRating())) {
                taxiDriveInfoService.updateRatingById(taxiDriverInfoModel.getDriverId(), (double) tripInfoModel.getRating());
            } else {
                Double newRating = (taxiDriverInfoModel.getRating() + tripInfoModel.getRating()) / 2;
                int i = taxiDriveInfoService.updateRatingById(taxiDriverInfoModel.getDriverId(), newRating);
            }

            return ResponseEntity.ok("Рейтинг обновлен");
        } else {
            return ResponseEntity.badRequest().body("Рейтинг не обновлен");
        }
    }
}
