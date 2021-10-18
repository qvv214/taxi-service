package ru.digitalleague.taxi_company.service;

import ru.digitalleague.core.model.OrderDetails;
import ru.digitalleague.core.model.TaxiDriverInfoModel;

public interface TaxiDriveInfoService {

    TaxiDriverInfoModel findDriver(OrderDetails orderDetails);

    int createTrip(Long driver_id, Long client_id);

    int tripStart(TaxiDriverInfoModel taxiDriverInfoModel);

    int tripEnd(TaxiDriverInfoModel taxiDriverInfoModel);

    int setBusy(Long id, boolean flag);

    int getPayment(Long id);

    int updateRatingForTripById(Long id, int rating);

    TaxiDriverInfoModel getDriverFromTripInfoById(Long id);

    int updateRatingById(Long id, Double rating);
}