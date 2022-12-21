package com.example.onlinewineshop.classes;


import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.*;
import javafx.scene.input.MouseEvent;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;

import com.example.onlinewineshop.classes.*;

import com.example.onlinewineshop.Controller.*;


public class OrderManagement {

    // gestisce un observable list di ordini
    public static ObservableList<Order> addList(Order order,ObservableList<Order> orderList){
        //inizializzo la lista
        orderList.add(order);
        return orderList;
    }
    // funzione che mi la somma della quantita degli ordini
    public static int sumQta(ObservableList<Order> orderList){
        int sum = 0;
        for (Order order : orderList) {
            sum += order.getQta();
        }
        return sum;
    }
    // funzione che mi la somma del prezzo degli ordini
    public static float sumPrice(ObservableList<Order> orderList){
        float sum = 0;
        for (Order order : orderList) {
            sum += order.getPrice();
        }
        return sum;
    }
    // funzione che rimuoove un ordine dalla lista
    public static ObservableList<Order> removeOrder(ObservableList<Order> orderList, String NomeOrder){
        for (Order order : orderList) {
            if(NomeOrder.equals(order.getWineOrder())){
                orderList.remove(order);
                break;
            }

        }
        //cambia anche il prezzo totale e quello dell'ordine

        return orderList;
    }

    // funzione che permette di modificare la quantita di un ordine
    public static ObservableList<Order> updateQta(ObservableList<Order> orderList, String NomeOrder, int qta){
        for (Order order : orderList) {
            if(NomeOrder.equals(order.getWineOrder())){
                int oldQta= order.getQta();
                order.setQta(qta);
                order.setPrice(order.getPrice()*qta/oldQta);

                System.out.println( order.getPrice()+" qta aggiornata");
            }

        }
        //cambia anche il prezzo totale e quello dell'ordine

        return orderList;
    }


// aggiunge ordini
// rimuove ordini
}
