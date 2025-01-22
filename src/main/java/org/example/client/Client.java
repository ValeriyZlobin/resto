package org.example.client;

import com.google.gson.Gson;
import org.example.entity.Order;
import org.example.entity.Dish;
import org.example.entity.TableManager;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        // Получение свободного стола
        URL tableUrl = new URL("http://localhost:7000/table");
        HttpURLConnection tableConnection = (HttpURLConnection) tableUrl.openConnection();
        tableConnection.setRequestMethod("GET");

        int tableId;
        try (var inputStream = tableConnection.getInputStream()) {
            var tableJson = new String(inputStream.readAllBytes());
            var table = gson.fromJson(tableJson, org.example.entity.Table.class);
            tableId = table.getId();
            System.out.println("Получен свободный стол: " + tableId);
        }

        // Получение меню
        URL menuUrl = new URL("http://localhost:7000/menu");
        HttpURLConnection menuConnection = (HttpURLConnection) menuUrl.openConnection();
        menuConnection.setRequestMethod("GET");
        try (var inputStream = menuConnection.getInputStream()) {
            System.out.println("Меню: " + new String(inputStream.readAllBytes()));
        }

        // Создание заказа
        URL orderUrl = new URL("http://localhost:7000/order/" + tableId);
        HttpURLConnection orderConnection = (HttpURLConnection) orderUrl.openConnection();
        orderConnection.setRequestMethod("POST");
        orderConnection.setDoOutput(true);
        orderConnection.setRequestProperty("Content-Type", "application/json");

        Order order = new Order(TableManager.getNextAvailableTable());
        order.addDish(new Dish("Sushi", 10.99));
        order.addDish(new Dish("Ramen", 8.99));

        String orderJson = gson.toJson(order);
        try (OutputStream os = orderConnection.getOutputStream()) {
            os.write(orderJson.getBytes());
        }
        System.out.println("Заказ отправлен!");

        // Получение счета
        URL billUrl = new URL("http://localhost:7000/bill/" + tableId);
        HttpURLConnection billConnection = (HttpURLConnection) billUrl.openConnection();
        billConnection.setRequestMethod("GET");
        try (var inputStream = billConnection.getInputStream()) {
            System.out.println("Счет: " + new String(inputStream.readAllBytes()));
        }

        // Оплата счета
        URL payUrl = new URL("http://localhost:7000/pay/" + tableId);
        HttpURLConnection payConnection = (HttpURLConnection) payUrl.openConnection();
        payConnection.setRequestMethod("POST");
        System.out.println("Счет оплачен и стол освобожден.");
    }
}
