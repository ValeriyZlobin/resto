package org.example.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int orderCounter = 1;

    private int id;
    private int tableNumber;  // Номер стола
    private LocalDateTime orderTime;  // Время заказа
    private List<Dish> dishes;  // Список блюд
    private boolean isCompleted;  // Статус заказа

    // Конструктор заказа, который автоматически получает номер стола
    public Order(int nextAvailableTable) {
        this.tableNumber = TableManager.getNextAvailableTable();  // Получаем номер стола автоматически
        this.orderTime = LocalDateTime.now();
        this.dishes = new ArrayList<>();
        this.isCompleted = false;
        id = orderCounter++;
    }

    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void addDish(Dish dish) {
        if (dish == null) {
            throw new IllegalArgumentException("Блюдо не может быть null");
        }
        dishes.add(dish);

    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public boolean removeDish(int dishId) {
        return dishes.removeIf(dish -> dish.getId() == dishId);
    }

    public void completeOrder() {
        this.isCompleted = true;
        TableManager.freeTable(this.tableNumber);  // Освобождаем стол после завершения заказа
    }
}
