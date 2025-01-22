package org.example.service.order.imp;

import org.example.entity.Dish;
import org.example.entity.Order;
import org.example.exception.OrderNotValidException;
import org.example.service.order.OrderService;

import java.util.*;

public class DefaultOrderService implements OrderService {
    public static final DefaultOrderService INSTANCE = new DefaultOrderService();

    private final Map<Integer, Order> orders;

    public DefaultOrderService() {
        orders = new HashMap<>();
    }

    @Override
    public Order createOrder(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public void addDishToOrder(int orderId, Dish dish) {
        Order order = getOrderById(orderId).orElseThrow(() -> new OrderNotValidException("Заказ не найден"));
        order.addDish(dish);
    }

    @Override
    public void removeDishFromOrder(int orderId, int dishId) {
        Order order = getOrderById(orderId).orElseThrow(() -> new OrderNotValidException("Заказ не найден"));
        boolean removed = order.removeDish(dishId);
        if (!removed) {
            throw new OrderNotValidException("Блюдо не найдено в заказе");
        }
    }

    @Override
    public Optional<Order> getOrderById(int orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public void completeOrder(int orderId) {
        Order order = getOrderById(orderId).orElseThrow(() -> new OrderNotValidException("Заказ не найден"));
        order.setCompleted(true);
    }
}
