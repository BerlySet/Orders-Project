package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.dto.SearchData;
import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.services.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {
    private static final Logger log = LogManager.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ResponseData<Iterable<Order>>> index() {
        ResponseData<Iterable<Order>> responseData = new ResponseData<>();
        responseData.setPayload(orderService.getAllOrders());
        responseData.setStatus(true);
        responseData.getMessages().add("Get All Orders Success!");
        log.info("Return response body (Get All Orders)");
        log.info(responseData.toString());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ResponseData<Optional<Order>>> findById(@PathVariable("id") int id) {
        ResponseData<Optional<Order>> responseData = new ResponseData<>();
        Optional<Order> payload = orderService.getSpecifiedOrder(id);

        if (payload.isPresent()) {
            responseData.setPayload(payload);
            responseData.setStatus(true);
            responseData.getMessages().add("Get Order Success!");
            log.info("Return response body (Get Specified Order)");
            log.info(responseData.toString());
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setPayload(payload);
            responseData.setStatus(false);
            responseData.getMessages().add("Order Not Found!");
            log.warn("Fail return response body (Get Specified Order)");
            log.info(responseData.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseData<Order>> addOrder(@Valid @RequestBody Order order) {
        ResponseData<Order> responseData = new ResponseData<>();
        responseData.setPayload(orderService.store(order));
        responseData.setStatus(true);
        responseData.getMessages().add("Add Order Success!");
        log.info("Return response body (Add New Order)");
        log.info(responseData.toString());
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ResponseData<Order>> deleteOrder(@PathVariable("id") int id) {
        ResponseData<Order> responseData = new ResponseData<>();
        if (orderService.delete(id)) {
            responseData.setStatus(true);
            responseData.getMessages().add("Delete Order Success!");
            log.info("Return response body (Delete Specified Order)");
            log.info(responseData.toString());
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Delete Order Fail! ID Not Found");
            log.warn("Fail return response body (Delete Specified Order)");
            log.info(responseData.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PutMapping("/orders")
    public ResponseEntity<ResponseData<Order>> update(@Valid @RequestBody Order order) {
        ResponseData<Order> responseData = new ResponseData<>();
        responseData.setPayload(orderService.store(order));
        responseData.setStatus(true);
        responseData.getMessages().add("Update Order Success!");
        log.info("Return response body (Update Specified Order)");
        log.info(responseData.toString());
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/orders/search")
    public ResponseEntity<ResponseData<List<Order>>> getOrderByName(@RequestBody SearchData searchData) {
        ResponseData<List<Order>> responseData = new ResponseData<>();
        responseData.setPayload(orderService.findByName(searchData.getSearchKey()));
        responseData.setStatus(true);
        responseData.getMessages().add("Search Order Success!");
        log.info("Return response body (Search Orders)");
        log.info(responseData.toString());
        return ResponseEntity.ok(responseData);
    }
}
