package org.example.service.teble.imp;

import org.example.entity.Dish;
import org.example.entity.Table;
import org.example.service.teble.TableService;

import java.util.ArrayList;
import java.util.List;

public class DefaultTableService implements TableService {

    public static DefaultTableService INSTANCE;

    private final List<Table> tables = new ArrayList<>();
    private final List<Dish> menu = new ArrayList<>();

    public DefaultTableService() {
        // Инициализация столов
        for (int i = 0; i <= 10; i++) {
            tables.add(new Table()); // Создаем 10 столов
        }

        // Инициализация меню
        menu.add(new Dish("Sushi", 10.99));
        menu.add(new Dish("Ramen", 8.99));
        menu.add(new Dish("Tempura", 12.49));
        menu.add(new Dish("Mochi", 4.99));
    }

    public static DefaultTableService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DefaultTableService();
        }
        return INSTANCE;
    }

    @Override
    public List<Table> getAllTables() {
        return tables;
    }

    @Override
    public List<Dish> getMenu() {
        return menu;
    }

    @Override
    public Table getFirstFreeTable() {
        for (Table table : tables) {
            if (!table.isOccupied()) {
                table.setOccupied(true); // Занимаем стол
                return table;
            }
        }
        throw new RuntimeException("Нет свободных столов!");
    }

    @Override
    public void clearTable(int tableId) {
        Table table = tables.stream()
                .filter(t -> t.getId() == tableId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Стол с ID " + tableId + " не найден!"));
        table.clearOrders();
        table.setOccupied(false); // Освобождаем стол
    }
}
