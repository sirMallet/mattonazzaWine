package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtilsEmployee {

        // Database credentials (change it to your own)
        public static void changeScene(ActionEvent event, String title, String fxmlFile, String username,int isAdmin) {
            Parent root = null;
            if(username != null){
                try {
                    FXMLLoader loader = new FXMLLoader(com.example.onlinewineshop.Controller.DBUtilsEmployee.class.getResource(fxmlFile));    //carico il file fxml
                    root = loader.load(); // load the fxml file
                    switch (fxmlFile) {
                        case "EmpWines.fxml" -> {
                            EmpWinesController empWinesController = loader.getController(); // get the controller

                        }
                        case "EmpClients.fxml" -> {
                            EmpClientsController empClientsController = loader.getController(); // get the controller
                            empClientsController.setUserInformation(username,isAdmin); // set the username
                        }
                        case "EmpOrders.fxml" -> {
                            EmpOrdersController empOrdersController = loader.getController(); // get the controller
                            empOrdersController.setUserInformation(username, isAdmin); // set the username
                        }
                        case "EmpAdmin.fxml" -> {
                            EmpAdminController empAdminController = loader.getController(); // get the controller
                            empAdminController.setUserInformation(username, isAdmin); // set the username
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else    {
                try{
                    root = FXMLLoader.load(com.example.onlinewineshop.Controller.DBUtilsEmployee.class.getResource(fxmlFile));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the stage from the event source (the button) and get the scene from the stage and get the window from the scene
            stage.setTitle(title);
            stage.setScene(new Scene(root,1000,500)); // set the scene


            stage.show();
        }




        public static void Delete(String identifier, String field){
            Connection connection = null; // initialize the connection
            PreparedStatement preparedStatement = null; // check if the user already exists
            ResultSet resultSet = null;
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
                switch (field) {
                    case "Wine" -> {
                        preparedStatement = connection.prepareStatement("DELETE FROM wine WHERE Nome = ?"); // check if the user already exists
                        preparedStatement.setString(1, identifier); // set the username
                        preparedStatement.execute(); // execute the query
                    }
                    case "Client" -> {
                        preparedStatement = connection.prepareStatement("DELETE FROM users WHERE username = ?"); // check if the user already exists
                        preparedStatement.setString(1, identifier); // set the username
                        preparedStatement.execute(); // execute the query
                    }
                    case "Order" -> {
                        preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE user = ?"); // check if the user already exists
                        preparedStatement.setString(1, identifier); // set the username
                        preparedStatement.execute(); // execute the query
                    }
                    case "Employee" -> {
                        preparedStatement = connection.prepareStatement("DELETE FROM dipendente WHERE FiscalCode = ?"); // check if the user already exists
                        preparedStatement.setString(1, identifier); // set the username
                        preparedStatement.execute(); // execute the query
                    }
                }

                if (preparedStatement != null) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                    alert.setTitle(field+" eliminato");
                    alert.setHeaderText(field+" eliminato");
                    alert.setContentText(field+" eliminato");
                    alert.show();

                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                    alert.setTitle(field+" non trovato");
                    alert.setHeaderText(field+" non trovato");
                    alert.setContentText(field+" non trovato");
                    alert.show();
                }



            }catch (SQLException e){
                e.printStackTrace();
            }
            finally {

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
        }

        public static void logInDip(ActionEvent event, String Nome,String Cognome,String password){
            Connection connection = null; // initialize the connection
            PreparedStatement preparedStatement = null; // check if the user already exists
            ResultSet resultSet = null;
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
                preparedStatement = connection.prepareStatement("SELECT password,isAdmin FROM dipendente WHERE Nome = ? AND Cognome = ?"); // check if the user already exists
                preparedStatement.setString(1,Nome); // set the username
                preparedStatement.setString(2,Cognome);
                resultSet = preparedStatement.executeQuery(); // execute the query
                if(!resultSet.isBeforeFirst()){
                    System.out.println("Employeee not found in the database!");
                    Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                    alert.setContentText("provide a valid Name or Surname!");
                    alert.show();
                }
                else{
                    while(resultSet.next()){
                        String retrivePassword = resultSet.getString("password");
                        int retriveIsAdmin = resultSet.getInt("isAdmin");
                        if(Nome.length()!=0 || Cognome.length()!=0){
                            if(retrivePassword.equals(password)){
                                System.out.println("Employeee found in the database!");
                                changeScene(event,"Logged in!","EmpWines.fxml",Nome,retriveIsAdmin); // change the scene
                            }
                            else{
                                System.out.println("Wrong password!");
                                Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                                alert.setContentText("provide a valid password!");
                                alert.show();
                            }
                        }
                        else{
                            break;
                        }

                    }

                }
            }catch (SQLException e){
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

        }
        public static ObservableList<Wine> ViewListWines(){
            Connection connection = null; // initialize the connection
            PreparedStatement preparedStatement = null; // check if the user already exists
            ResultSet resultSet = null;
            ObservableList<Wine> wineList = FXCollections.observableArrayList();
            try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
                preparedStatement = connection.prepareStatement("SELECT * FROM wine"); // check if the user already exists

                resultSet = preparedStatement.executeQuery(); // execute the query
                while (resultSet.next()) {
                    //(String nome, String region, int vintage, String varietal, int Valutazione, double prezzo)
                    wineList.add(new Wine(resultSet.getString("nome"), resultSet.getString("region"), resultSet.getInt("vintage"), resultSet.getString("varietal"), resultSet.getInt("Valutazione"),resultSet.getFloat("prezzo"),resultSet.getInt("quantita")));

                }
                return wineList;
            }
            catch (SQLException e){
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

            return null;
        }

    public static ObservableList<Client> ViewListClient(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        ObservableList<Client> Clientlist = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT * FROM users"); // check if the user already exists

            resultSet = preparedStatement.executeQuery(); // execute the query
            while (resultSet.next()) {
                //(String nome, String region, int vintage, String varietal, int Valutazione, double prezzo)
                Clientlist.add(new Client(resultSet.getString("userName"),resultSet.getString("password"),resultSet.getString("name"),resultSet.getString("surname"), resultSet.getString("fiscalCode"), resultSet.getString("phone"), resultSet.getString("address"), resultSet.getString("email")));
            }
            return Clientlist;
        }
        catch (SQLException e){
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

        return null;
    }
    public static ObservableList<Order> ViewListOrder(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        ObservableList<Order> Orderlist = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT user,SUM(qta) as qta,SUM(price) as price FROM orders group by user;"); // check if the user already exists

            resultSet = preparedStatement.executeQuery(); // execute the query
            while (resultSet.next()) {
                Orderlist.add(new Order(resultSet.getString("user"),resultSet.getInt("qta"),resultSet.getFloat("price")));
            }
            return Orderlist;
        }
        catch (SQLException e){
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

        return null;
    }

    public static ObservableList<Employee> ViewListEmployee(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        ObservableList<Employee> Employeelist = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT * FROM dipendente"); // check if the user already exists

            resultSet = preparedStatement.executeQuery(); // execute the query
            while (resultSet.next()) {
                //(String nome, String region, int vintage, String varietal, int Valutazione, double prezzo)
                Employeelist.add(new Employee(resultSet.getString("Nome"),resultSet.getString("Cognome"),resultSet.getString("FiscalCode"),resultSet.getString("IndirizzoEmail"), resultSet.getString("NumTel"), resultSet.getString("IndirizzoResidenza"), resultSet.getBoolean("isAdmin"), resultSet.getString("Password")));
            }
            return Employeelist;
        }
        catch (SQLException e){
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

        return null;
    }
    public static ObservableList<Order> ViewListOrders(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT * FROM orders"); // check if the user already exists

            resultSet = preparedStatement.executeQuery(); // execute the query
            while (resultSet.next()) {
                //(String nome, String region, int vintage, String varietal, int Valutazione, double prezzo)
                orderList.add(new Order(resultSet.getString("username"), resultSet.getInt("qta"), resultSet.getFloat("prezzo")));

            }
            return orderList;
        }
        catch (SQLException e){
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

        return null;
    }
}


