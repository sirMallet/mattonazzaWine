package com.example.onlinewineshop.classes;

public class Order {
    private String wineOrder;
    private int qta;
    private float  price;
    private String username;

    public Order(){
        this.wineOrder = "";
        this.qta = 0;
        this.price = 0;
        this.username = "";

    }

    public Order(String wineOrder, int qta, float price, String username){
        this.wineOrder = wineOrder;
        this.qta = qta;
        this.price = price;
        this.username = username;
    }
    public Order(String username,int qta, float price){
        this.qta = qta;
        this.price = price;
        this.username = username;
    }
   public String getWineOrder() {
        return wineOrder;
    }
    public void setWineOrder(String wineOrder) {
        this.wineOrder = wineOrder;
    }
    public int getQta() {
        return qta;
    }
    public void setQta(int qta) {
        this.qta = qta;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void toStringOrder() {
        System.out.println("Wine: " + wineOrder + "\tQuantity: " + qta + "\tPrice: " + price + "\tUsername: " + username);
    }



}
