package ru.digitalleague.core.service;

import ru.digitalleague.core.model.TaxiDriverInfoModel;

public interface TaxiDriverInfoService {

    int insert(TaxiDriverInfoModel record);

    TaxiDriverInfoModel selectByPrimaryKey(Long driverId);

    int updateByPrimaryKey(TaxiDriverInfoModel record);

    int getByIdAndUpdateLevel(Long driverId);

    boolean twoSelectByIdWithSleep();
}