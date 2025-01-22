package org.example.service.teble;

import org.example.entity.Dish;
import org.example.entity.Order;
import org.example.entity.Table;

import java.util.List;
import java.util.Optional;

public interface TableService {

    List<Table> getAllTables();

    void clearTable(int tableId);

    Table getFirstFreeTable();

    List<Dish> getMenu();
}
