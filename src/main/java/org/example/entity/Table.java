package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private static int tableCounter = 0; // Для уникального идентификатора стола
    private final int id; // Уникальный идентификатор стола
    private boolean isOccupied; // Статус: занят или свободен
    private final List<Order> orders; // Список заказов для стола

    public Table() {

        id = tableCounter++;
        this.isOccupied = false;
        this.orders = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders); // Возвращаем копию списка заказов
    }

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Заказ не может быть null");
        }
        if (order.getTableNumber() != this.id) {
            throw new IllegalArgumentException("Заказ не принадлежит этому столу");
        }
        orders.add(order);
    }

    public void clearOrders() {
        orders.clear();

    }

    public double calculateTotalRevenue() {
        return orders.stream()
                .filter(order -> !order.isCompleted()) // Учитываем только незавершенные заказы
                .flatMap(order -> order.getDishes().stream()) // Преобразуем список заказов в список блюд
                .mapToDouble(Dish::getPrice) // Получаем цену каждого блюда
                .sum();
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", isOccupied=" + isOccupied +
                ", orders=" + orders +
                '}';
    }
}
