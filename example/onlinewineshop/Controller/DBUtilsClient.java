package com.example.onlinewineshop.Controller;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.*;
import javafx.scene.input.MouseEvent;
import java.sql.*;
import java.io.IOException;
import com.example.onlinewineshop.classes.*;

public class DBUtilsClient {

    // Database credentials (change it to your own)
    public static void changeScene(ActionEvent event,String title,String fxmlFile, String username){
        Parent root = null;
        if(username != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtilsClient.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                LoggedInController loggedInController = loader.getController(); // get the controller
                loggedInController.setUserInformation(username); // set the user information
                loggedInController.setCart(OrderManagement.sumQta( CartController.getOrders()),OrderManagement.sumPrice( CartController.getOrders()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else    {
            try{
                root = FXMLLoader.load(DBUtilsClient.class.getResource(fxmlFile));
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
    public static void changeSceneMouse(MouseEvent event,String title,String fxmlFile, Wine wine,String username){
        Parent root = null;
        if(username!= null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtilsClient.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                OrderController orderController = loader.getController(); // get the controller
                orderController.setUserName(username);
                orderController.setWine(wine);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else    {
            try{
                root = FXMLLoader.load(DBUtilsClient.class.getResource(fxmlFile));
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
    public static void changeSceneCart(MouseEvent event,String title,String fxmlFile,String username){
        Parent root = null;
        if(username!= null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtilsClient.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                CartController CartController = loader.getController(); // get the controller
                CartController.setUserName(username);

                CartController.setCart(OrderManagement.sumQta( CartController.getOrders()),OrderManagement.sumPrice( CartController.getOrders()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else    {
            try{
                root = FXMLLoader.load(DBUtilsClient.class.getResource(fxmlFile));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the stage from the event source (the button) and get the scene from the stage and get the window from the scene
        stage.setTitle(title);
        if(title.equals("Logged in!")|| title.equals("Cart")){
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
    public static void logInDip(ActionEvent event, String username,String password){
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
    // controllo per la quantità del prodotto nel magazzino
    public static void Order(ActionEvent event,Order order){
        Parent root = null;
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT quantita,prezzo FROM wine WHERE Nome LIKE CONCAT( ?,'%')"); // check if the user already exists
            preparedStatement.setString(1,order.getWineOrder()); // set the username

            resultSet = preparedStatement.executeQuery(); // execute the query
            if(!resultSet.isBeforeFirst()){

                System.out.println("quantità non valida !");
                Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                alert.setContentText("quantità non valida !");
                alert.show();
                changeScene(event,"Logged in!","logged-in.fxml", order.getUsername());
            }
            else {

                while (resultSet.next()) {
                    int qta = resultSet.getInt("quantita");
                    float prezzo = resultSet.getFloat("prezzo");

                    if(order.getQta()>= qta){

                        System.out.println("quantità non superiore al magazzino!");
                        Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                        alert.setContentText("quantità non presente in magazzino!");
                        alert.show();
                        break;

                    }
                    else{
                        System.out.println("prezzo CAD: "+prezzo);


                        order.setPrice(prezzo*order.getQta());
                        order.toStringOrder();

                        CartController.getOrders().add(new Order(order.getUsername(),order.getWineOrder(),order.getQta(),order.getPrice()));
                        System.out.println(order.getUsername()+" ha aggiunto al carrello!");
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // create an alert
                        alert.setContentText("aggiunto al carrello!");
                        alert.show();

                        break;

                    }

                }
                changeScene(event,"Logged in!","logged-in.fxml",order.getUsername());
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

    }



    public static ObservableList<Wine> ViewList(String name,String region, int PrizeMin,int  PrizeMax){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        ObservableList<Wine> wineList = FXCollections.observableArrayList();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            if(name.equals("") && region.equals("")){
                preparedStatement = connection.prepareStatement("SELECT * FROM wine WHERE prezzo BETWEEN ? AND ?"); // select all the wines
                preparedStatement.setInt(1,PrizeMin);
                preparedStatement.setInt(2,PrizeMax);
            }
            else if(name.length()==0 && region.length()!=0){
                preparedStatement = connection.prepareStatement("SELECT * FROM wine WHERE region = ? AND prezzo BETWEEN ? and ?"); // select only the wines from the region
                preparedStatement.setString(1,region);
                preparedStatement.setInt(2,PrizeMin);
                preparedStatement.setInt(3,PrizeMax);
            }
            else if(region.equals("") && name.length()!=0){
                preparedStatement = connection.prepareStatement("SELECT * FROM wine WHERE Nome LIKE CONCAT( ?,'%') AND prezzo BETWEEN ? and ?"); // select only the wines from the region
                preparedStatement.setString(1,name);
                preparedStatement.setInt(2,PrizeMin);
                preparedStatement.setInt(3,PrizeMax);
            }
            else{
                preparedStatement = connection.prepareStatement("SELECT * FROM wine WHERE Nome LIKE CONCAT( ?,'%') AND region = ? AND prezzo BETWEEN ? and ?"); // select only the wines from the region
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,region);
                preparedStatement.setInt(3,PrizeMin);
                preparedStatement.setInt(4,PrizeMax);
            }

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

    public static void sendOrder(ObservableList<Order> listO, ActionEvent event, String username) {
        for(int i=0;i<listO.size();i++){
            System.out.println(listO.get(i).getWineOrder());
        }
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            System.out.println(listO.size());
            for(Order o : listO){
                System.out.println(o.getWineOrder()+" "+o.getQta()+" "+o.getPrice()+" "+o.getUsername());
                preparedStatement = connection.prepareStatement("INSERT INTO orders (user, wine, qta, price) VALUES (?,?,?,?)"); // select all the wines
                preparedStatement.setString(1,o.getUsername());
                preparedStatement.setString(2,o.getWineOrder());
                preparedStatement.setInt(3,o.getQta());
                preparedStatement.setFloat(4,o.getPrice());

                preparedStatement.executeUpdate();
                /*
                preparedStatement = connection.prepareStatement("UPDATE wine SET quantita = quantita - ? WHERE nome = ?");
                preparedStatement.setInt(1,o.getQta());
                preparedStatement.setString(2,o.getWineOrder());
                preparedStatement.executeUpdate();
                */
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // create an alert
            alert.setContentText("ordine inviato!");
            alert.show();
            changeScene(event,"Logged in!","logged-in.fxml",username);
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
    }
}
