package ru.digitalleague.core.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import ru.digitalleague.core.mapper.TaxiInfoMapper;
import ru.digitalleague.core.model.OrderDetails;

import java.io.IOException;

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
    public ResponseEntity<String> notifyTaxi(OrderDetails orderDetails) {
        try {
            String message = objectMapper.writeValueAsString(orderDetails);
            String queueByCity = taxiInfoMapper.getQueueByCity(orderDetails.getCity());

            if (ObjectUtils.isEmpty(queueByCity)) {
                return new ResponseEntity("Город не найден", HttpStatus.NOT_FOUND);
            }

            amqpTemplate.convertAndSend(queueByCity, message);

            return new ResponseEntity("Город найден", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}