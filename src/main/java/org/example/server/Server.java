package org.example.server;

import io.javalin.Javalin;
import org.example.entity.Table;
import org.example.entity.Order;
import org.example.service.teble.imp.*;
import org.example.service.teble.TableService;

public class Server {
    public static void main(String[] args) {
        // Инициализация TableService
        TableService tableService = DefaultTableService.getInstance();

        // Запуск Javalin-сервера
        Javalin app = Javalin.create().start(7000);

        // Эндпоинт: Получить первый свободный стол
        app.get("/table", ctx -> {
            Table table = tableService.getFirstFreeTable();
            ctx.json(table);
        });

        // Эндпоинт: Получить меню
        app.get("/menu", ctx -> {
            ctx.json(tableService.getMenu());
        });

        // Эндпоинт: Добавить заказ на стол
        app.post("/order/{tableId}", ctx -> {
            int tableId = Integer.parseInt(ctx.pathParam("tableId"));
            Order order = ctx.bodyAsClass(Order.class);
            Table table = tableService.getAllTables().stream()
                    .filter(t -> t.getId() == tableId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Стол не найден!"));
            table.addOrder(order);
            ctx.status(201).result("Заказ добавлен.");
        });

        // Эндпоинт: Рассчитать счет
        app.get("/bill/{tableId}", ctx -> {
            int tableId = Integer.parseInt(ctx.pathParam("tableId"));
            Table table = tableService.getAllTables().stream()
                    .filter(t -> t.getId() == tableId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Стол не найден!"));
            double total = table.calculateTotalRevenue();
            ctx.result("Общая сумма: " + total);
        });

        // Эндпоинт: Оплатить счет и освободить стол
        app.post("/pay/{tableId}", ctx -> {
            int tableId = Integer.parseInt(ctx.pathParam("tableId"));
            tableService.clearTable(tableId);
            ctx.result("Счет оплачен. Стол освобожден.");
        });
    }
}
