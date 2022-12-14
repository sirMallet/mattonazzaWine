package com.example.onlinewineshop.Controller;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.*;
import java.sql.*;
import java.io.IOException;
import com.example.onlinewineshop.classes.*;

public class DBUtils {
    // Database credentials (change it to your own)
    public static void changeScene(ActionEvent event,String title,String fxmlFile, String username){
        Parent root = null;
        if(username != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                LoggedInController loggedInController = loader.getController(); // get the controller
                loggedInController.setUserInformation(username); // set the user information
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else    {
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the stage from the event source (the button) and get the scene from the stage and get the window from the scene
        stage.setTitle(title);
        if(title.equals("Logged in!")){
            stage.setScene(new Scene(root,1000,500)); // set the scene
        }
        else{
            stage.setScene(new Scene(root,600,400)); // set the scene
        }

        stage.show();
    }

    public static void signUpUsers(ActionEvent event, String username, String password, String ft_nomeText, String ft_cognomeText, String ft_cfText, String ft_numCellText, String ft_emailText, String ft_indirizzoText){
        Connection connection = null; // initialize the connection
        PreparedStatement psInsert = null; // prepared statement for insert
        PreparedStatement psCheckusersExists = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            psCheckusersExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?"); // check if the user already exists
            psCheckusersExists.setString(1,username); // set the username
            resultSet = psCheckusersExists.executeQuery(); // execute the query
            if(resultSet.isBeforeFirst()){ // if the result set is not empty the user already exists
                Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                alert.setTitle("Error");
                alert.setHeaderText("Username already exists!");
                alert.setContentText("Please choose another username!");
                alert.show();
            }
            else{
                psInsert = connection.prepareStatement("INSERT INTO users(username,password,name,surname,fiscalCode,phone,address,email) VALUES(?,?,?,?,?,?,?,?)"); // insert the user
                psInsert.setString(1,username); // set the username
                psInsert.setString(2,password); // set the password
                psInsert.setString(3,ft_nomeText); // set the name
                psInsert.setString(4,ft_cognomeText); // set the surname
                psInsert.setString(5,ft_cfText); // set the fiscal code
                psInsert.setString(6,ft_numCellText); // set the phone
                psInsert.setString(7,ft_indirizzoText); // set the address
                psInsert.setString(8,ft_emailText); // set the email
                psInsert.executeUpdate(); // execute the update

                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Success");
                alert.setHeaderText("User created!");
                alert.setContentText("You can now log in!");
                alert.show();
                changeScene(event,"Logged in!","logged-in.fxml",username); // change the scene
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckusersExists != null){
                try{
                    psCheckusersExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
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
    public static void logInUser(ActionEvent event, String username,String password){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?"); // check if the user already exists
            preparedStatement.setString(1,username); // set the username
            resultSet = preparedStatement.executeQuery(); // execute the query
            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                alert.setContentText("provide a valid username!");
                alert.show();
            }
            else{
                while(resultSet.next()){
                    String retrivePassword = resultSet.getString("password");
                    if(retrivePassword.equals(password)){
                        System.out.println("User found in the database!");
                        changeScene(event,"Logged in!","logged-in.fxml",username); // change the scene
                    }
                    else{
                        System.out.println("Wrong password!");
                        Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                        alert.setContentText("provide a valid password!");
                        alert.show();
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
    public static ObservableList<Wine> ViewList(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        ObservableList<Wine> wineList = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT * FROM wine"); // select all the wines
            resultSet = preparedStatement.executeQuery(); // execute the query
            while (resultSet.next()) {
                    //(String nome, String region, int vintage, String varietal, int Valutazione, double prezzo)
                    wineList.add(new Wine(resultSet.getString("nome"), resultSet.getString("region"), resultSet.getInt("vintage"), resultSet.getString("varietal"), resultSet.getInt("Valutazione"),resultSet.getDouble("prezzo")));

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
}
