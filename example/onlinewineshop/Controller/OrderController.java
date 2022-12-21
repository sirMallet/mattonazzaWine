package com.example.onlinewineshop.Controller;
import com.example.onlinewineshop.classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.*;


public class OrderController implements Initializable {
    @FXML
    private Button button_Logout;
    @FXML
    private Button button_Back;
    @FXML
    private TextField fx_qta;
    @FXML
    private Label fx_username;
    @FXML
    private Button button_Cart;
    @FXML
    private Label fx_TitleWine;
    @FXML
    private Label fx_qtaRimasta;
    private Wine wine1;
    private Order order1;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        Wine wine = new Wine();
        Order order = new Order();
        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Login","Login.fxml",null);
            }
        });
        button_Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Logged in!","logged-in.fxml",fx_username.getText());
            }
        });
        button_Cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                order.setWineOrder(wine1.getNome().toString());
                System.out.println("Ordine: "+wine1.getNome());
                order.setQta(Integer.parseInt(fx_qta.getText()));
                order.setPrice(wine1.getPrezzo());
                order.setUsername(fx_username.getText());
                DBUtilsClient.Order(event,order);

            }
        });

    }
    public void setUserName(String UserName){
        System.out.println(UserName);
        fx_username.setText(UserName);
    }
    public void setWine(Wine wine){
        wine1 = wine;
        fx_TitleWine.setText(wine.getNome());
        wine.setQta(QtaPrecisa(wine.getNome()));
        fx_qtaRimasta.setText("n° prodotti rimasti: "+wine.getQta());
    }
    public int QtaPrecisa(String nome){
        int qta = 0;
            Connection connection = null; // initialize the connection
            PreparedStatement preparedStatement = null; // check if the user already exists
            ResultSet resultSet = null;
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
                preparedStatement = connection.prepareStatement("SELECT quantita FROM wine WHERE Nome LIKE CONCAT( ?,'%')"); // check if the user already exists
                preparedStatement.setString(1,nome); // set the username

                resultSet = preparedStatement.executeQuery(); // execute the query
                if(!resultSet.isBeforeFirst()){
                    System.out.println("No data");
                }
                else{
                    while(resultSet.next()){
                        qta = resultSet.getInt("quantita");
                        return qta;
                    }
                }

            }catch (SQLException e ){
                e.printStackTrace();
            }
            finally {
                if(resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(preparedStatement != null){
                    try{
                        preparedStatement.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                if(connection != null){
                    try{
                        connection.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
            return qta;
        }
    }



