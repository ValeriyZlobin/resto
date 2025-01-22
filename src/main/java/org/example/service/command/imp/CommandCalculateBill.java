package org.example.service.command.imp;

import org.example.service.command.AbstractCommand;
import org.example.service.teble.imp.DefaultTableService;
import org.example.entity.Table;
import org.example.util.UtilInput;

public class CommandCalculateBill extends AbstractCommand {

    public static final CommandCalculateBill INSTANCE = new CommandCalculateBill();

    protected CommandCalculateBill() {
        super("Рассчитать счет");
    }

    @Override
    public boolean execute() {
        System.out.println("Введите ID стола для расчета счета:");
        int tableId = UtilInput.getRequiredIntFromUser();

        Table table = DefaultTableService.getInstance().getAllTables().stream()
                .filter(t -> t.getId() == tableId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Стол с ID " + tableId + " не найден"));

        double totalBill = table.calculateTotalRevenue();
        System.out.println("Общая сумма для стола #" + tableId + ": " + totalBill);
        return true;
    }
}
