package model;

public class Item {
    private int id;
    private String name;
    private ItemType itemType;
    private double price;

    public Item(int id, String name, ItemType itemType, double price) {
        this.id = id;
        this.name = name;
        this.itemType = itemType;
        this.price = price;
    }

    public int getId() {
        return id;
    }


    public ItemType getItemType() {
        return itemType;
    }

    public double getPrice() {
        return price;
    }
}
