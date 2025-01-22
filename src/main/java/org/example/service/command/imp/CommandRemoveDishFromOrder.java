package org.example.service.command.imp;

import org.example.entity.Order;
import org.example.entity.Dish;
import org.example.service.command.AbstractCommand;
import org.example.service.order.imp.DefaultOrderService;
import org.example.util.UtilInput;

public class CommandRemoveDishFromOrder extends AbstractCommand {

    public static final CommandRemoveDishFromOrder INSTANCE = new CommandRemoveDishFromOrder();

    protected CommandRemoveDishFromOrder() {
        super("Удалить блюдо из заказа");
    }

    @Override
    public boolean execute() {
        System.out.println("Введите ID заказа:");
        int orderId = UtilInput.getRequiredIntFromUser();

        System.out.println("Введите ID блюда для удаления:");
        int dishId = UtilInput.getRequiredIntFromUser();

        // Находим заказ и удаляем блюдо
        DefaultOrderService.INSTANCE.removeDishFromOrder(orderId, dishId);
        System.out.println("Блюдо с ID " + dishId + " удалено из заказа " + orderId);
        return true;
    }
}
