package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Client;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;


public class EmpClientsController implements Initializable {
    @FXML
    private Button button_invio;
    @FXML
    private Label UserName;
    @FXML
    private Label fx_Select;
    @FXML
    private TextField tf_wine;
    @FXML
    private TextField tf_UserName;
    @FXML
    private TextField tf_Password;
    @FXML
    private TextField tf_Nome;
    @FXML
    private TextField tf_Cognome;
    @FXML
    private TextField tf_Indirizzo;
    @FXML
    private TextField tf_Email;
    @FXML
    private TextField tf_Phone;
    @FXML
    private TextField tf_CodiceFiscale;

    @FXML
    private Button button_Wines;
    @FXML
    private Button button_Clients;
    @FXML
    private Button button_Orders;
    @FXML
    private Button button_Logout;
    @FXML
    private Button button_Emp;
    @FXML
    private Label fx_Label;
    @FXML
    private TableView<Client> table_Client;
    @FXML
    private TableColumn<Client, String> Col_UserName;
    @FXML
    private TableColumn<Client, String> Col_Password;
    @FXML
    private TableColumn<Client, String> Col_Nome;
    @FXML
    private TableColumn<Client, String> Col_Cognome;
    @FXML
    private TableColumn<Client, String> Col_CodiceFiscale;
    @FXML
    private TableColumn<Client, String> Col_Indirizzo;
    @FXML
    private TableColumn<Client, String> Col_Phone;
    @FXML
    private TableColumn<Client, String> Col_Email;


    Integer index;
    Client ClientSelected = null;
    ObservableList<Client> listC = null;

    public void Delete(){
        try{
            String UserName = tf_UserName.getText();
            DBUtilsEmployee.Delete(UserName,"Client");
            table_Client.refresh();
            listC = DBUtilsEmployee.ViewListClient();
            // aggiungere la lista alla tabella
            table_Client.setItems(listC);

        }
        catch(Exception e){
        }

    }
    @FXML
    public void Add(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("INSERT INTO users (userName,password,name,surname,fiscalCode,phone,address,email)VALUES(?,?,?,?,?,?,?,?)"); // check if the user already exists
            preparedStatement.setString(1, tf_UserName.getText()); // set the username
            preparedStatement.setString(2, tf_Password.getText()); // set the password
            preparedStatement.setString(3, tf_Nome.getText()); // set the name
            preparedStatement.setString(4, tf_Cognome.getText()); // set the surname
            preparedStatement.setString(5, tf_CodiceFiscale.getText()); // set the fiscalCode
            preparedStatement.setString(6, tf_Phone.getText()); // set the phone
            preparedStatement.setString(7, tf_Indirizzo.getText()); // set the address
            preparedStatement.setString(8, tf_Email.getText()); // set the email

            preparedStatement.execute(); // execute the query
            tf_Nome.clear();
            tf_Cognome.clear();
            tf_UserName.clear();
            tf_Password.clear();
            tf_CodiceFiscale.clear();
            tf_Phone.clear();
            tf_Indirizzo.clear();
            tf_Email.clear();

            listC = DBUtilsEmployee.ViewListClient();
            // aggiungere la lista alla tabella
            table_Client.setItems(listC);

            Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
            alert.setTitle("Cliente aggiornato");
            alert.setHeaderText("Cliente aggiornato");
            alert.setContentText("Cliente aggiornato");
            alert.show();



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


        System.out.println("Add");
    }
    @FXML
    public void Update(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("UPDATE users SET userName = ? ,password = ?,name = ?,surname = ?, fiscalCode= ?,phone = ?,address = ?,email=? WHERE username = ?"); // check if the user already exists
            preparedStatement.setString(1, tf_UserName.getText()); // set the username
            preparedStatement.setString(2, tf_Password.getText()); // set the password
            preparedStatement.setString(3, tf_Nome.getText()); // set the name
            preparedStatement.setString(4, tf_Cognome.getText()); // set the surname
            preparedStatement.setString(5, tf_CodiceFiscale.getText()); // set the fiscalCode
            preparedStatement.setString(6, tf_Phone.getText()); // set the phone
            preparedStatement.setString(7, tf_Indirizzo.getText()); // set the address
            preparedStatement.setString(8, tf_Email.getText()); // set the email
            preparedStatement.setString(9,  ClientSelected.getUserName()); // set the old username
            preparedStatement.execute(); // execute the query
            tf_Nome.clear();
            tf_Cognome.clear();
            tf_UserName.clear();
            tf_Password.clear();
            tf_CodiceFiscale.clear();
            tf_Phone.clear();
            tf_Indirizzo.clear();
            tf_Email.clear();

            listC = DBUtilsEmployee.ViewListClient();
            // aggiungere la lista alla tabella
            table_Client.setItems(listC);

            Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
            alert.setTitle("Cliente aggiornato");
            alert.setHeaderText("Cliente aggiornato");
            alert.setContentText("Cliente aggiornato");
            alert.show();



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

    @FXML
    void getSelected(MouseEvent event) {
        index = table_Client.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        ClientSelected.setUserName(Col_UserName.getCellData(index));
        ClientSelected.setName(Col_Nome.getCellData(index));
        ClientSelected.setSurname(Col_Cognome.getCellData(index));
        ClientSelected.setPassword(Col_Password.getCellData(index));
        ClientSelected.setFiscalCode(Col_CodiceFiscale.getCellData(index));
        ClientSelected.setAddress(Col_Indirizzo.getCellData(index));
        ClientSelected.setPhone(Col_Phone.getCellData(index));
        ClientSelected.setEmail(Col_Email.getCellData(index));
        tf_UserName.setText(ClientSelected.getUserName());
        tf_Password.setText(String.valueOf(ClientSelected.getPassword()));
        tf_Nome.setText(String.valueOf(ClientSelected.getName()));
        tf_Cognome.setText(String.valueOf(ClientSelected.getSurname()));
        tf_CodiceFiscale.setText(String.valueOf(ClientSelected.getFiscalCode()));
        tf_Phone.setText(String.valueOf(ClientSelected.getPhone()));
        tf_Indirizzo.setText(String.valueOf(ClientSelected.getAddress()));
        tf_Email.setText(String.valueOf(ClientSelected.getEmail()));



    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        UserName.setVisible(false);
        button_Clients.setDisable(true);
        ClientSelected = new Client();
        // settare la tabella
        // set the table view with the wine list from the database (DBUtils) and the columns of the table view (Wine class)
        Col_Nome.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        Col_Cognome.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        Col_UserName.setCellValueFactory(new PropertyValueFactory<Client, String>("UserName"));
        Col_Password.setCellValueFactory(new PropertyValueFactory<Client, String>("password"));
        Col_CodiceFiscale.setCellValueFactory(new PropertyValueFactory<Client, String>("fiscalCode"));
        Col_Indirizzo.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        Col_Phone.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
        Col_Email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        listC = DBUtilsEmployee.ViewListClient();
        // aggiungere la lista alla tabella
        table_Client.setItems(listC);

        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Log in!","login.fxml",null);
            }
        });
        button_Wines.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Wines Management","EmpWines.fxml",UserName.getText());
            }
        });
        button_Orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Orders Management","EmpOrders.fxml",UserName.getText());
            }
        });

    }
    public void setUserInformation(String username){
        UserName.setText(username);
        fx_Label.setText("Benvenuto " + UserName.getText() + "!");
    }





}

