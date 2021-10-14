package ru.digitalleague.taxi_company.service;

import ru.digitalleague.taxi_company.model.OrderDetails;
import ru.digitalleague.taxi_company.model.TaxiDriverInfoModel;

public interface TaxiDriveInfoService {

    TaxiDriverInfoModel findDriver(OrderDetails orderDetails);

    int createTrip(TaxiDriverInfoModel taxiDriverInfoModel);

    int tripStart(TaxiDriverInfoModel taxiDriverInfoModel);

    int tripEnd(TaxiDriverInfoModel taxiDriverInfoModel);

    void setBusy(Long id, boolean flag);
}