package com.example.onlinewineshop.Controller;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.Socket;
import java.sql.*;

import com.example.onlinewineshop.classes.*;

public class DBUtilsClient implements Runnable {
    // inserisco le informazioni necessarie per ascoltare il client
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public DBUtilsClient(Socket socket){
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void closeEverything(Socket socket, BufferedReader bufferReader, BufferedWriter bufferWriter){

        try{
            if(bufferReader != null){
                bufferReader.close();
            }
            if(bufferWriter != null){
                bufferWriter.close();
            }
            if(socket != null){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendMessage(String messageToSend) throws IOException {
        bufferedWriter.write(messageToSend);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    @Override
    public void run() {
        String messageFromClient;
        String listWine = "";
        String listClient = "";
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                System.out.println(messageFromClient);
                String[] message = messageFromClient.split("[|]"); //serve per dividere la stringa in più parti
                switch (message[0]) {
                    case "logInUser" -> {
                        String result = DBUtilsClient.logInUser(message[1], message[2]) ? "true" : "false";
                        sendMessage(result);
                    }
                    case "logInDip" -> {
                        String result = DBUtilsClient.logInDip(message[1], message[2], message[3]);
                        sendMessage(result);
                    }
                    case "ViewList" -> {
                        ObservableList<Wine> list;
                        if (message[1].equals("") && message[2].equals("") && Integer.parseInt(message[3]) == 0 && Integer.parseInt(message[4]) == 1000) {
                            list = DBUtilsClient.ViewList("", "", 0, 1000);
                        } else {
                            list = DBUtilsClient.ViewList(message[1], message[2], Integer.parseInt(message[3]), Integer.parseInt(message[4]));
                        }
                        assert list != null;
                        listWine = "";
                        for (Wine wine : list) {
                            listWine = listWine + wine.getNome() + "|" + wine.getRegion() + "|" + wine.getVintage() + "|" + wine.getVarietal() + "|" + wine.getValutazione() + "|" + wine.getPrezzo() + "|" + wine.getQta() + ";";
                        }
                        sendMessage(listWine);
                    }
                    case "ViewListEmployee" -> {
                        ObservableList<Employee> list;
                        list = DBUtilsClient.ViewListEmployee();
                        listClient = "";
                        assert list != null;
                        for (Employee employee : list) {
                            listClient = listClient + employee.getNome() + "|" + employee.getCognome() + "|" + employee.getFiscalCode() + "|" + employee.getIndirizzoEmail() + "|" + employee.getNumTel() + "|" + employee.getIndirizzoResidenza() + "|" + employee.getIsAdmin() + "|" + employee.getPassword() + ";";
                        }
                        sendMessage(listClient);
                    }
                    case "ViewListOrder" -> {
                        ObservableList<Order> list;
                        list = DBUtilsClient.ViewListOrder();
                        listClient = "";
                        assert list != null;
                        for (Order order : list) {
                            listClient = listClient + order.getUsername() + "|" + order.getWineOrder() + "|" + order.getQta() + "|" + order.getPrice() + ";";
                        }
                        sendMessage(listClient);
                    }
                    case "ViewListClient" -> {
                        ObservableList<Client> list;
                        list = DBUtilsClient.ViewListClient();
                        listClient = "";
                        assert list != null;
                        for (Client client : list) {
                            listClient = listClient + client.getUserName() + "|" + client.getPassword() + "|" + client.getName() + "|" + client.getSurname() + "|" + client.getFiscalCode() + "|" + client.getPhone() + "|" + client.getAddress() + "|" + client.getEmail() + ";";
                        }
                        sendMessage(listClient);
                    }
                    case "GestoreWine" -> {
                        Wine wine = new Wine(message[2], message[3], Integer.parseInt(message[4]), message[5], Integer.parseInt(message[6]), Float.parseFloat(message[7]), Integer.parseInt(message[8]));
                        String result = DBUtilsClient.GestoreWine(wine, message[1]) ? "true" : "false";
                        sendMessage(result);
                    }
                    case "GestoreEmployee" -> {
                        Employee employee = new Employee(message[2], message[3], message[4], message[5], message[6], message[7], Boolean.parseBoolean(message[8]), message[9]);
                        String result = DBUtilsClient.GestoreEmployee(employee, message[1]) ? "true" : "false";
                        sendMessage(result);
                    }
                    case "GestoreClient" -> {
                        Client client = new Client(message[2], message[3], message[4], message[5], message[6], message[7], message[8], message[9]);
                        String result = DBUtilsClient.GestoreClient(client, message[1]) ? "true" : "false";
                        sendMessage(result);
                    }
                    case "GestoreOrder" -> {

                        String result = DBUtilsClient.GestoreOrder(message[1], message[2],message[3],message[4]) ? "true" : "false";
                        sendMessage(result);
                    }
                    case "CheckAccept" -> {
                        String result = DBUtilsClient.CheckAccept(message[1]) ? "true" : "false";
                        sendMessage(result);
                    }
                    case "SendOrder" -> {
                        ObservableList<Order> listOrderWine = FXCollections.observableArrayList();
                        String[] tmp = message[1].split(";");
                        for (String i : tmp) {
                            String[] msg = i.split("[/]");
                            listOrderWine.add(new Order(msg[0], msg[1], Integer.parseInt(msg[2]), Float.parseFloat(msg[3])));
                        }
                        String result = DBUtilsClient.sendOrder(listOrderWine) ? "true" : "false";
                        sendMessage(result);
                    }
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean CheckAccept(String userName) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        preparedStatement = connection.prepareStatement("SELECT * FROM ordertmp WHERE userName=?");
        preparedStatement.setString(1, userName);
        resultSet = preparedStatement.executeQuery(); // execute the query
        return resultSet.isBeforeFirst();

    }

    private static boolean GestoreOrder(String action,String userName,String NomeDip,String CognomeDip) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        switch(action){
            case "Delete" -> {
                preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE user=?");
                preparedStatement.setString(1, userName);
                preparedStatement.executeUpdate();
                return true;
            }
            case "Accept" -> {
                preparedStatement = connection.prepareStatement("INSERT INTO ordertmp (NomeDip,CognomeDip,userName) VALUES (?,?,?)");
                preparedStatement.setString(1, NomeDip);
                preparedStatement.setString(2, CognomeDip);
                preparedStatement.setString(3, userName);
                preparedStatement.executeUpdate();
                return true;
            }

        }
        return false;
    }

    private static boolean GestoreEmployee(Employee employee, String action) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        switch (action){
            case "Add" -> {
                preparedStatement = connection.prepareStatement("INSERT INTO dipendente (Nome,Cognome,FiscalCode,IndirizzoEmail,NumTel,IndirizzoResidenza,isAdmin,Password) VALUES (?,?,?,?,?,?,?,?)");
                preparedStatement.setString(1, employee.getNome());
                preparedStatement.setString(2, employee.getCognome());
                preparedStatement.setString(3, employee.getFiscalCode());
                preparedStatement.setString(4, employee.getIndirizzoEmail());
                preparedStatement.setString(5, employee.getNumTel());
                preparedStatement.setString(6, employee.getIndirizzoResidenza());
                preparedStatement.setBoolean(7, employee.getIsAdmin());
                preparedStatement.setString(8, employee.getPassword());
                preparedStatement.executeUpdate();
                return true;
            }
            case "Update" -> {
                preparedStatement = connection.prepareStatement("UPDATE dipendente SET Nome=?, Cognome=?, FiscalCode=?, IndirizzoEmail=?, NumTel=?, IndirizzoResidenza=?, isAdmin=?, Password=? WHERE FiscalCode=?");
                preparedStatement.setString(1, employee.getNome());
                preparedStatement.setString(2, employee.getCognome());
                preparedStatement.setString(3, employee.getFiscalCode());
                preparedStatement.setString(4, employee.getIndirizzoEmail());
                preparedStatement.setString(5, employee.getNumTel());
                preparedStatement.setString(6, employee.getIndirizzoResidenza());
                preparedStatement.setBoolean(7, employee.getIsAdmin());
                preparedStatement.setString(8, employee.getPassword());
                preparedStatement.setString(9, employee.getFiscalCode());
                preparedStatement.executeUpdate();
                return true;
            }
            case "Delete" -> {
                preparedStatement = connection.prepareStatement("DELETE FROM dipendente WHERE FiscalCode=?");
                preparedStatement.setString(1, employee.getFiscalCode());
                preparedStatement.executeUpdate();
                return true;
            }
        }
        return false;
    }

    // Database credentials (change it to your own)
    public static void changeScene(ActionEvent event,String title,String fxmlFile, String username){
        Parent root = null;
        if(username != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtilsClient.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                LoggedInController loggedInController = loader.getController(); // get the controller

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

    public static void changeSceneCart(MouseEvent event,String title,String fxmlFile,String username){
        Parent root = null;
        if(username!= null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtilsClient.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                CartController CartController = loader.getController(); // get the controller
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
    public static boolean logInUser(String username,String password){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?"); // check if the user already exists
            preparedStatement.setString(1,username); // set the username
            resultSet = preparedStatement.executeQuery(); // execute the query
            if(!resultSet.isBeforeFirst()){


                return false;
            }
            else{
                while(resultSet.next()){
                    String retrivePassword = resultSet.getString("password");
                    if(retrivePassword.equals(password)){
                        System.out.println("User found in the database!");
                        // deve restituire un valore che mi permetta di passare alla pagina successiva
                        //changeScene(event,"Logged in!","logged-in.fxml",username); // change the scene
                        return true;
                    }
                    else{
                        System.out.println("Wrong password!");
                        Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                        alert.setContentText("provide a valid password!");
                        alert.show();
                        return false;
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
        return false;
    }
    public static String logInDip(String Nome,String Cognome,String password){
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
                System.out.println("Employee not found in the database!");
                return "false|0";
            }
            else{
                while(resultSet.next()){
                    String retrivePassword = resultSet.getString("password");
                    int retriveIsAdmin = resultSet.getInt("isAdmin");
                    if(Nome.length()!=0 || Cognome.length()!=0){
                        if(retrivePassword.equals(password)){
                            System.out.println("Employeee found in the database!");
                            return "true|"+retriveIsAdmin+"";
                        }
                        else{
                            System.out.println("Wrong password!");
                            return "false|0";
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
        return "false|0";
    }
    public static boolean GestoreClient(Client client,String action) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        switch (action){
            case "Add" -> {
                preparedStatement = connection.prepareStatement("INSERT INTO users (userName,password,name,surname,fiscalCode,phone,address,email) VALUES(?,?,?,?,?,?,?,?)"); // check if the user already exists
                preparedStatement.setString(1, client.getUserName()); // set the username
                preparedStatement.setString(2,  client.getPassword()); // set the password
                preparedStatement.setString(3,  client.getName()); // set the name
                preparedStatement.setString(4,  client.getSurname()); // set the surname
                preparedStatement.setString(5,  client.getFiscalCode()); // set the fiscalCode
                preparedStatement.setString(6,  client.getPhone()); // set the phone
                preparedStatement.setString(7,  client.getAddress()); // set the address
                preparedStatement.setString(8,  client.getEmail()); // set the email

                preparedStatement.executeUpdate();
                return true;
            }
            case "Update" -> {
                preparedStatement = connection.prepareStatement("UPDATE users SET userName = ? ,password = ?,name = ?,surname = ?, fiscalCode= ?,phone = ?,address = ?,email=? WHERE userName = ?"); // check if the user already exists
                preparedStatement.setString(1, client.getUserName()); // set the username
                preparedStatement.setString(2,  client.getPassword()); // set the password
                preparedStatement.setString(3,  client.getName()); // set the name
                preparedStatement.setString(4,  client.getSurname()); // set the surname
                preparedStatement.setString(5,  client.getFiscalCode()); // set the fiscalCode
                preparedStatement.setString(6,  client.getPhone()); // set the phone
                preparedStatement.setString(7,  client.getAddress()); // set the address
                preparedStatement.setString(8,  client.getEmail());
                preparedStatement.setString(9,  client.getUserName());
                preparedStatement.executeUpdate();
                return true;
            }
            case "Delete" -> {
                preparedStatement = connection.prepareStatement("DELETE FROM users WHERE userName = ?"); // check if the user already exists
                preparedStatement.setString(1, client.getUserName()); // set the username
                preparedStatement.executeUpdate(); // execute the update
                return true;
            }
        }
        return false;
    }
    public static boolean GestoreWine(Wine wine, String action) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        switch (action) {
            case "Add" -> {
                preparedStatement = connection.prepareStatement("INSERT INTO wine (Nome,vintage,region,varietal,valutazione,prezzo,quantita) VALUES (?,?,?,?,?,?,?)"); // check if the user already exists
                preparedStatement.setString(1, wine.getNome()); // set the username
                preparedStatement.setInt(2, wine.getVintage());
                preparedStatement.setString(3, wine.getRegion());
                preparedStatement.setString(4, wine.getVarietal());
                preparedStatement.setInt(5, wine.getValutazione());
                preparedStatement.setFloat(6, wine.getPrezzo());
                preparedStatement.setInt(7, wine.getQta());
                preparedStatement.executeUpdate();
                return true;
            }
            case "Update" -> {
                preparedStatement = connection.prepareStatement("UPDATE wine SET vintage=?,valutazione=?,prezzo=?,quantita=?  WHERE Nome = ?"); // check if the user already exists
                preparedStatement.setInt(1, wine.getVintage()); // set the username
                preparedStatement.setInt(2, wine.getValutazione());
                preparedStatement.setFloat(3, wine.getPrezzo());
                preparedStatement.setInt(4, wine.getQta());
                preparedStatement.setString(5, wine.getNome());
                preparedStatement.executeUpdate();
                return true;
            }
            case "Delete" -> {
                preparedStatement = connection.prepareStatement("DELETE FROM wine WHERE Nome = ?"); // check if the user already exists
                preparedStatement.setString(1, wine.getNome());
                preparedStatement.executeUpdate();
                return true;
            }
        }
        if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        return false;
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

    public static Boolean sendOrder(ObservableList<Order> listO) {

        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            for(Order o : listO){
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
            return true;
        }
        catch (SQLException e){
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
        return false;
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
