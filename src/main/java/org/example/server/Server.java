package org.example.server;

import io.javalin.Javalin;
import org.example.entity.Table;
import org.example.entity.Order;
import org.example.service.teble.imp.*;
import org.example.service.teble.TableService;
import org.example.entity.Dish;

import java.util.Arrays;
import java.util.List;

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

        // Эндпоинт: Добавить заказ на стол с выбором блюд
        app.post("/order/{tableId}", ctx -> {
            int tableId = Integer.parseInt(ctx.pathParam("tableId"));

            // Получаем JSON от клиента
            var requestBody = ctx.bodyAsClass(Order.class);

            // Проверяем, что стол существует
            Table table = tableService.getAllTables().stream()
                    .filter(t -> t.getId() == tableId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Стол не найден!"));

            // Создаем заказ и добавляем блюда
            Order order = new Order(tableId);
            for (var dishRequest : requestBody.getDishes()) {
                // Проверяем, что блюдо есть в меню
                var dish = tableService.getMenu().stream()
                        .filter(d -> d.getId() == dishRequest.getId())
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Блюдо с ID " + dishRequest.getId() + " не найдено в меню!"));
                order.addDish(dish);
            }

            // Добавляем заказ на стол
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

        app.get("/", ctx -> {
            String html = """
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Меню ресторана</title>
    </head>
    <body>
        <h1>Меню ресторана</h1>
        <div id="menu"></div>
        
        <h2>Ваш заказ</h2>
        <div id="order"></div>
        
        <button id="send-order">Отправить заказ</button>
        
        <script>
            let order = [];

            // Получаем меню с сервера
            fetch('/menu')
                .then(response => response.json())
                .then(data => {
                    let menuDiv = document.getElementById('menu');
                    data.forEach(dish => {
                        let item = document.createElement('div');
                        item.innerHTML = `
                            <input type="checkbox" data-id="${dish.id}" data-name="${dish.name}" data-price="${dish.price}">
                            ${dish.name} - ${dish.price} руб.
                        `;
                        menuDiv.appendChild(item);
                    });

                    // Обработчик нажатий на чекбоксы
                    menuDiv.addEventListener('change', (event) => {
                        let checkbox = event.target;
                        if (checkbox.checked) {
                            order.push({
                                id: checkbox.getAttribute('data-id'),
                                name: checkbox.getAttribute('data-name'),
                                price: checkbox.getAttribute('data-price')
                            });
                        } else {
                            order = order.filter(dish => dish.id !== checkbox.getAttribute('data-id'));
                        }

                        updateOrder();
                    });
                });

            // Обновление отображения текущего заказа
            function updateOrder() {
                let orderDiv = document.getElementById('order');
                orderDiv.innerHTML = '';
                order.forEach(dish => {
                    let item = document.createElement('div');
                    item.innerHTML = `${dish.name} - ${dish.price} руб.`;
                    orderDiv.appendChild(item);
                });
            }

            // Отправка заказа на сервер
            document.getElementById('send-order').addEventListener('click', () => {
                fetch('/order', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(order)
                }).then(response => {
                    if (response.ok) {
                        alert('Заказ отправлен успешно!');
                        order = [];
                        updateOrder();
                    } else {
                        alert('Ошибка при отправке заказа.');
                    }
                });
            });
        </script>
    </body>
    </html>
    """;
            ctx.html(html);
        });

        app.post("/order", ctx -> {
            // Получение заказа из тела запроса
            List<Dish> dishes = Arrays.asList(ctx.bodyAsClass(Dish[].class));

            // Проверяем наличие свободного стола
            Table table = tableService.getFirstFreeTable();
            if (table == null) {
                ctx.status(400).result("Нет свободных столов.");
                return;
            }

            // Создаем заказ и связываем его со столом
            Order order = new Order(table.getId());
            for (Dish dish : dishes) {
                order.addDish(dish);
            }
            table.addOrder(order);

            ctx.status(201).result("Заказ успешно добавлен к столу " + table.getId());
        });




    }
}
