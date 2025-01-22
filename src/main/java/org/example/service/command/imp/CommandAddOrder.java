package org.example.service.command.imp;

import org.example.entity.Order;
import org.example.entity.TableManager;
import org.example.service.command.AbstractCommand;
import org.example.util.UtilInput;

public class CommandAddOrder extends AbstractCommand {

    public static final CommandAddOrder INSTANCE = new CommandAddOrder();

    protected CommandAddOrder() {
        super("Создать заказ");
    }

    public boolean execute() {
        System.out.println("Введите ID стола для нового заказа:");
        int tableId = UtilInput.getRequiredIntFromUser();

        Order order = new Order(tableId); // Создаем новый заказ
        getOrderService().createOrder(order); // Сохраняем заказ через OrderService

        System.out.println("Заказ для стола #" + tableId + " успешно создан.");
        return true;
    }
}
