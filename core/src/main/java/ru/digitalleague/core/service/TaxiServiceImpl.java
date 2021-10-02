package ru.digitalleague.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import ru.digitalleague.core.mapper.TaxiInfoMapper;
import ru.digitalleague.core.model.OrderDetails;
import ru.digitalleague.core.api.TaxiService;

@Slf4j
@Service
public class TaxiServiceImpl implements TaxiService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TaxiInfoMapper taxiInfoMapper;

    @Override
    public String notifyTaxi(OrderDetails orderDetails) {
        String message = null;

        try {
            message = objectMapper.writeValueAsString(orderDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String queueByCity = taxiInfoMapper.getQueueByCity(orderDetails.getCity());

        if (ObjectUtils.isEmpty(queueByCity))
            return "Заказ не принят, город не известен";

        amqpTemplate.convertAndSend(queueByCity, message);

        return "Заказ принят";
    }
}