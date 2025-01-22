package org.example.service.order;

import org.example.entity.Dish;
import org.example.entity.Order;

import java.util.Optional;

public interface OrderService {

    Order createOrder(Order order);

    void addDishToOrder(int orderId, Dish dish);

    void removeDishFromOrder(int orderId, int dishId);

    Optional<Order> getOrderById(int orderId);

    void completeOrder(int orderId);
}
