package com.barleyyy.orders.repository;

import com.barleyyy.orders.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
