package org.example.entity;

public class Dish {

    private final int id;
    private static int dishCouner = 0;
    private String name;
    private double price;


    public Dish(String name, double price) {
        id = dishCouner++;
        this.name = name;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }


}
