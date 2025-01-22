package org.example.entity;

import java.util.HashSet;
import java.util.Set;

public class TableManager {

    private static final Set<Integer> occupiedTables = new HashSet<>();
    private static final int TOTAL_TABLES = 10;  // Общее количество столов

    // Метод для получения следующего доступного стола
    public static int getNextAvailableTable() {
        for (int i = 1; i <= TOTAL_TABLES; i++) {
            if (!occupiedTables.contains(i)) {
                occupiedTables.add(i);  // Маркируем стол как занятый
                return i;  // Возвращаем первый свободный стол
            }
        }
        throw new IllegalStateException("Все столы заняты");
    }

    // Метод для освобождения стола, если заказ завершен
    public static void freeTable(int tableNumber) {
        occupiedTables.remove(tableNumber);
    }
}
