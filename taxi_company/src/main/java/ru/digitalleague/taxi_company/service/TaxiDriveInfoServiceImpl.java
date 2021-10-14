package ru.digitalleague.taxi_company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.digitalleague.taxi_company.mapper.TaxiDriveInfoMapper;
import ru.digitalleague.taxi_company.model.OrderDetails;
import ru.digitalleague.taxi_company.model.TaxiDriverInfoModel;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class TaxiDriveInfoServiceImpl implements TaxiDriveInfoService {

    @Autowired
    private TaxiDriveInfoMapper taxiDriveInfoMapper;

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
     * Создаем новую задачу в которой будет храниться внешний ключ driver(id водителя которому она принадлежит),
     * поля для начала(start_trip default null) и завершения поздки(end_trip default null), а также оценка клиента rating
     *
     * @param taxiDriverInfoModel
     */
    @Override
    public int createTrip(TaxiDriverInfoModel taxiDriverInfoModel) {
        return taxiDriveInfoMapper.createTrip(taxiDriverInfoModel.getDriverId());
    }

    @Override
    public int tripStart(TaxiDriverInfoModel taxiDriverInfoModel) {
        return taxiDriveInfoMapper.tripStart(taxiDriverInfoModel.getDriverId());
    }

    @Override
    public void setBusy(Long id, boolean flag) {
        taxiDriveInfoMapper.setBusy(id, flag);
    }

    @Override
    public int tripEnd(TaxiDriverInfoModel taxiDriverInfoModel) {
        return taxiDriveInfoMapper.tripEnd(taxiDriverInfoModel.getDriverId());
    }
}
