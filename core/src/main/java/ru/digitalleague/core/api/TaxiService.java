package ru.digitalleague.core.api;

import org.springframework.http.ResponseEntity;
import ru.digitalleague.core.model.OrderDetails;

/**
 * Сервис отправки информации о заказе.
 */
public interface TaxiService {

    /**
     * Информируем такси о поступлении заказа.
     */
    ResponseEntity<String> notifyTaxi(OrderDetails orderDetails);

}