package ru.digitalleague.taxi_company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.digitalleague.core.model.OrderDetails;
import ru.digitalleague.core.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.mapper.TaxiDriveInfoMapper;
import ru.digitalleague.taxi_company.model.TripInfoModel;
import ru.digitalleague.taxi_company.utils.Utils;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class TaxiDriveInfoServiceImpl implements TaxiDriveInfoService {

    @Autowired
    private TaxiDriveInfoMapper taxiDriveInfoMapper;

    @Autowired
    private Utils utils;

    /**
     *
     * Ищем водителей который на данный момент свободны в городе orderDetails.getCity() и соответствую
     * orderDetail.getLevel()
     * После чего сортируем водителей по рейтингу
     * В теории, N количеству водителей с самым высоким рейтингом должно приходить оповещение с предложением
     * принять заказ, но на данный момент мы просто берем самого первого водителя из отсортирового списка
     *
     */
    @Override
    public TaxiDriverInfoModel findDriver(OrderDetails orderDetails) {
        List<TaxiDriverInfoModel> taxiDriverInfoModelList = taxiDriveInfoMapper.findTaxiDriveForTrip(orderDetails.getCity(), orderDetails.getLevel());

        if (ObjectUtils.isEmpty(taxiDriverInfoModelList)) {
            return null;
        }

        taxiDriverInfoModelList.sort(Comparator.comparing(TaxiDriverInfoModel::getRating).reversed());
        return taxiDriverInfoModelList.get(0);
    }

    /**
     * Создаем новую задачу в которой будет храниться внешний ключ driver_id(id водителя которому она принадлежит),
     * client_id (id клиента сделавшего заказ)
     * поля для начала(start_trip default null) и завершения поздки(end_trip default null), а также оценка клиента rating
     *
     */
    @Override
    public int createTrip(Long driver_id, Long client_id) {
        return taxiDriveInfoMapper.createTrip(driver_id, client_id);
    }

    @Override
    public int tripStart(TaxiDriverInfoModel taxiDriverInfoModel) {
        return taxiDriveInfoMapper.tripStart(taxiDriverInfoModel.getDriverId());
    }

    @Override
    public int setBusy(Long id, boolean flag) {
        return taxiDriveInfoMapper.setBusy(id, flag);
    }

    @Override
    public int tripEnd(TaxiDriverInfoModel taxiDriverInfoModel) {
        return taxiDriveInfoMapper.tripEnd(taxiDriverInfoModel.getDriverId());
    }

    /**
     *
     * Поллучаем поездку из таблицы trip_info по id
     * Расчитываем количество минут проведенных в поезде и записывавем стоимость(total) в таблицу trip_info
     */
    @Override
    public int getPayment(Long id) {
        TripInfoModel tripInfoModel = taxiDriveInfoMapper.getTripById(id);
        Long price = taxiDriveInfoMapper.getDriverPriceById(tripInfoModel.getDriver_id());
        Long minutes = utils.getDifferenceTime(tripInfoModel.getStart_trip(), tripInfoModel.getEnd_trip());

        if (minutes > 0) {
            return taxiDriveInfoMapper.setTripTotalById(id, minutes * price);
        } else {
            return taxiDriveInfoMapper.setTripTotalById(id, price);
        }
    }

    @Override
    public int updateRatingForTripById(Long id, int rating) {
        return taxiDriveInfoMapper.updateRatingForTripById(id, rating);
    }

    @Override
    public TaxiDriverInfoModel getDriverFromTripInfoById(Long id) {
        return taxiDriveInfoMapper.getDriverFromTripInfoById(id);
    }

    @Override
    public int updateRatingById(Long id, Double rating) {
        return taxiDriveInfoMapper.updateRatingById(id, rating);
    }
}
