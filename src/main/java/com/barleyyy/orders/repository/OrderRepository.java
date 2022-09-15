package com.barleyyy.orders.repository;

import com.barleyyy.orders.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT p FROM Order p WHERE p.name LIKE :name")
    public List<Order> findOrderByName(@PathParam("name") String name);
}
