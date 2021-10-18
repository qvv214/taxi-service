package ru.digitalleague.taxi_company.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import ru.digitalleague.core.model.OrderDetails;
import ru.digitalleague.core.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.model.TripInfoModel;
import ru.digitalleague.taxi_company.service.TaxiDriveInfoServiceImpl;

import java.io.IOException;

@Slf4j
@Component
public class OrderListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaxiDriveInfoServiceImpl taxiDriveInfoService;

//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private String port;

    /**
     *
     * Ищем водителя походящего водителя для поездки taxiDriverInfoModel и создаем задачу
     * Если находим, то вызываем endpoin о старте поездки: http://localhost:" + port + "/trip-start"
     *
     */
    @RabbitListener(queues = "#{myQueue3.getName()}")
    public void order(Message message) throws IOException {
        OrderDetails orderDetails = objectMapper.readValue(new String(message.getBody()), OrderDetails.class);
        TaxiDriverInfoModel taxiDriverInfoModel = taxiDriveInfoService.findDriver(orderDetails);

        if (ObjectUtils.isEmpty(taxiDriverInfoModel)) {
            log.info("Нет подходящего таксиста...");
        } else {
            taxiDriveInfoService.createTrip(taxiDriverInfoModel.getDriverId(), orderDetails.getClientNumber());

            log.info(taxiDriverInfoModel.toString());
        }
    }
}
