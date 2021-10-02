package ru.digitalleague.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.core.api.TaxiService;
import ru.digitalleague.core.model.OrderDetails;

@Slf4j
@RestController
public class Controller {

    @Autowired
    private TaxiService taxiService;

    @PostMapping("/order-taxi")
    public ResponseEntity<String> receive(@RequestBody OrderDetails orderDetails) {
        log.info("Received message from postman" + orderDetails);

        String result = taxiService.notifyTaxi(orderDetails);

        return ResponseEntity.ok("test");
    }
}
