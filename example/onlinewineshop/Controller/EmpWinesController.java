package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Wine;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.*;


public class EmpWinesController implements Initializable {
    public ImageView button_Trash;
    @FXML
    private Button button_Wines;
    @FXML
    private Label fx_Select;
    @FXML
    private TextField tf_wine;
    @FXML
    private TextField tf_Regione;
    @FXML
    private TextField tf_Varietal;

    @FXML
    private TextField tf_Vintage;
    @FXML
    private TextField tf_Val;
    @FXML
    private TextField tf_Prezzo;
    @FXML
    private TextField tf_Qta;
    @FXML
    private Label UserName;

    @FXML
    private Button button_Logout;
    @FXML
    private Button button_Emp;
    @FXML
    private Label fx_Label;
    @FXML
    private TableView<Wine> table_Wine;
    @FXML
    private TableColumn<Wine, String> col_Nome;
    @FXML
    private TableColumn<Wine, Integer> col_Qta;
    @FXML
    private TableColumn<Wine, Integer> col_Vintage;
    @FXML
    private TableColumn<Wine, String> col_Regione;
    @FXML
    private TableColumn<Wine, String> col_Varietal;
    @FXML
    private TableColumn<Wine, Integer> col_Val;
    @FXML
    private TableColumn<Wine, Float> col_Prezzo;

    int CheckAdmin = 0;
    Integer index;
    Wine WineSelected = null;
    ObservableList<Wine> listW = null;
    @FXML
    private Button button_Clients;
    @FXML
    private Button button_Orders;
    @FXML
    private Button button_Modifica;
    @FXML
    public void Add(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("INSERT INTO wine (Nome,vintage,region,varietal,valutazione,prezzo,quantita)VALUES(?,?,?,?,?,?,?)"); // check if the user already exists
            preparedStatement.setString(1, tf_wine.getText()); // set the name wine
            preparedStatement.setString(2, tf_Vintage.getText()); // set the varietal
            preparedStatement.setString(3, tf_Regione.getText()); // set the region
            preparedStatement.setString(4, tf_Varietal.getText()); // set the vintage
            preparedStatement.setString(5, tf_Val.getText()); // set the value
            preparedStatement.setString(6, tf_Prezzo.getText()); // set the price
            preparedStatement.setString(7, tf_Qta.getText()); // set the qta


            preparedStatement.execute(); // execute the query
            tf_wine.clear();
            tf_Varietal.clear();
            tf_Regione.clear();
            tf_Vintage.clear();
            tf_Val.clear();
            tf_Prezzo.clear();
            tf_Qta.clear();


            listW = DBUtilsEmployee.ViewListWines();
            // aggiungere la lista alla tabella
            table_Wine.setItems(listW);

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

    public void Delete(){
        try{
            String NomeWine = tf_wine.getText();
            DBUtilsEmployee.Delete(NomeWine,"Wine");
            table_Wine.refresh();
            listW = DBUtilsEmployee.ViewListWines();
            // aggiungere la lista alla tabella
            table_Wine.setItems(listW);

        }
        catch(Exception e){
        }

    }
    @FXML
    public void Update(){


        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("UPDATE wine SET vintage=?,valutazione=?,prezzo=?,quantita=?  WHERE Nome = ?"); // check if the user already exists
            preparedStatement.setString(1, tf_Vintage.getText()); // set the username
            preparedStatement.setString(2, tf_Val.getText()); // set the Valutazione
            preparedStatement.setString(3, tf_Prezzo.getText()); // set the price
            preparedStatement.setString(4, tf_Qta.getText()); // set the quantita
            preparedStatement.setString(5, WineSelected.getNome()); // set the password
            preparedStatement.execute(); // execute the query
            tf_wine.clear();
            tf_Vintage.clear();
            tf_Val.clear();
            tf_Prezzo.clear();
            tf_Qta.clear();

            listW = DBUtilsEmployee.ViewListWines();
            // aggiungere la lista alla tabella
            table_Wine.setItems(listW);

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
        index = table_Wine.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        WineSelected.setNome(col_Nome.getCellData(index));

        WineSelected.setVintage(col_Vintage.getCellData(index));
        WineSelected.setRegion(col_Regione.getCellData(index));
        WineSelected.setVarietal(col_Varietal.getCellData(index));
        WineSelected.setValutazione(col_Val.getCellData(index));
        WineSelected.setPrezzo(col_Prezzo.getCellData(index));
        WineSelected.setQta(col_Qta.getCellData(index));
        WineSelected.setRegion(col_Regione.getCellData(index));
        WineSelected.setVintage(col_Vintage.getCellData(index));
        tf_wine.setText(WineSelected.getNome());
        tf_Vintage.setText(String.valueOf(WineSelected.getVintage()));
        tf_Val.setText(String.valueOf(WineSelected.getValutazione()));
        tf_Prezzo.setText(String.valueOf(WineSelected.getPrezzo()));
        tf_Qta.setText(String.valueOf(WineSelected.getQta()));
        tf_Regione.setText(WineSelected.getRegion());
        tf_Varietal.setText(WineSelected.getVarietal());


    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {


        UserName.setVisible(false);
        WineSelected = new Wine();
        button_Wines.setDisable(true);
        // settare la tabella
        // set the table view with the wine list from the database (DBUtils) and the columns of the table view (Wine class)
        col_Nome.setCellValueFactory(new PropertyValueFactory<Wine, String>("Nome"));
        col_Vintage.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Vintage"));
        col_Regione.setCellValueFactory(new PropertyValueFactory<Wine, String>("region"));
        col_Varietal.setCellValueFactory(new PropertyValueFactory<Wine, String>("Varietal"));
        col_Val.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Valutazione"));
        col_Prezzo.setCellValueFactory(new PropertyValueFactory<Wine, Float>("Prezzo"));
        col_Qta.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Qta"));
        // prendere i dati dal database
        listW = DBUtilsEmployee.ViewListWines();
        // aggiungere la lista alla tabella
        table_Wine.setItems(listW);

        button_Clients.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Clients Management","EmpClients.fxml",UserName.getText(),CheckAdmin);
            }
        });
        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Log in!","login.fxml",null);
            }
        });
        button_Orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Orders Management","EmpOrders.fxml",UserName.getText(),CheckAdmin);
            }
        });
        button_Emp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Employee Management","EmpAdmin.fxml",UserName.getText(),CheckAdmin);
            }
        });

    }
    public void setUserInformation(String username, int isAdmin){
        CheckAdmin = isAdmin;
        button_Emp.setDisable(CheckAdmin == 0);
        UserName.setText(username);
        fx_Label.setText("Benvenuto " + UserName.getText() + "!");
    }

}

