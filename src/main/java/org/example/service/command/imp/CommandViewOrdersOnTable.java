package org.example.service.command.imp;

import org.example.service.command.AbstractCommand;
import org.example.service.teble.imp.DefaultTableService;
import org.example.entity.Table;
import org.example.util.UtilInput;

public class CommandViewOrdersOnTable extends AbstractCommand {

    public static final CommandViewOrdersOnTable INSTANCE = new CommandViewOrdersOnTable();

    protected CommandViewOrdersOnTable() {
        super("Посмотреть заказы на столе");
    }

    @Override
    public boolean execute() {
        System.out.println("Введите ID стола для просмотра заказов:");
        int tableId = UtilInput.getRequiredIntFromUser();

        Table table = DefaultTableService.getInstance().getAllTables().stream()
                .filter(t -> t.getId() == tableId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Стол с ID " + tableId + " не найден"));

        System.out.println("Заказы для стола #" + tableId + ":");
        table.getOrders().forEach(order -> System.out.println(order.toString()));

        return true;
    }
}
