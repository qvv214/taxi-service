package ru.digitalleague.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.core.model.OrderDetails;

@Slf4j
@RestController
public class Controller {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/order-taxi")
    public void receive(@RequestBody OrderDetails orderDetails) {
        String message = null;

        try {
            message = objectMapper.writeValueAsString(orderDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("order", message);
    }

//    @PostMapping("/trip-over")
//    public ResponseEntity<String> rideFinished(){
//        return ResponseEntity.ok("Trip is over, cost is: ... ");
//
//    }
}
