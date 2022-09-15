package com.barleyyy.orders.controllers;

import com.barleyyy.orders.dto.ResponseData;
import com.barleyyy.orders.entities.Order;
import com.barleyyy.orders.services.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ResponseData<Iterable<Order>>> index() {
        ResponseData<Iterable<Order>> responseData = new ResponseData<>();
        responseData.setPayload(orderService.getAllOrders());
        responseData.setStatus(true);
        responseData.getMessages().add("Get All Orders Success!");
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
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setPayload(payload);
            responseData.setStatus(false);
            responseData.getMessages().add("Order Not Found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseData<Order>> addOrder(@Valid @RequestBody Order order) {
        ResponseData<Order> responseData = new ResponseData<>();
        responseData.setPayload(orderService.store(order));
        responseData.setStatus(true);
        responseData.getMessages().add("Add Order Success!");
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ResponseData<Order>> deleteOrder(@PathVariable("id") int id) {
        ResponseData<Order> responseData = new ResponseData<>();
        if (orderService.delete(id)) {
            responseData.setStatus(true);
            responseData.getMessages().add("Delete Order Success!");
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Delete Order Fail! ID Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }
}
