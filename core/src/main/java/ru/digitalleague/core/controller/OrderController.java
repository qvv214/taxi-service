package ru.digitalleague.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.core.model.OrderDetails;
import ru.digitalleague.core.api.TaxiServiceImpl;
import ru.digitalleague.core.model.TaxiDriverInfoModel;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private TaxiServiceImpl taxiService;

    @PostMapping("/order-taxi")
    public ResponseEntity<String> receive(@RequestBody OrderDetails orderDetails) {
        log.info("Received message from postman" + orderDetails);
        ResponseEntity<String> result = taxiService.notifyTaxi(orderDetails);
        return result;
    }
}
