package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Order;
import com.example.onlinewineshop.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class CartController implements Initializable {
    @FXML
    private Button button_Logout;
    @FXML
    private TableView<Order> table_Wine;
    @FXML
    private Button button_Back;
    @FXML
    private Label fx_Label;
    @FXML
    private Label fx_NumArt;
    @FXML
    private Label fx_CostoTot;

    @FXML
    private TableColumn<Order, String> col_Nome;
    @FXML
    private TableColumn<Order, Integer> col_Quantity;
    @FXML
    private TableColumn<Order, Float> col_Prezzo;
    @FXML
    private TextField fx_NomeWine;
    @FXML
    private TextField fx_Qta;
    Order order = null;
    Order orderSelected = null;
    Integer index;
    static ObservableList<Order> listO = FXCollections.observableArrayList();
    @FXML
    void getSelected(MouseEvent event) {
        try{
            index = table_Wine.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            orderSelected.setWineOrder(col_Nome.getCellData(index));
            orderSelected.setQta(col_Quantity.getCellData(index));
            orderSelected.setPrice(col_Prezzo.getCellData(index));


            fx_NomeWine.setText(orderSelected.getWineOrder());
            fx_Qta.setText(String.valueOf(orderSelected.getQta()));
        }
        catch(Exception e){
            orderSelected = null;
        }

        //OrderManagement.removeOrder(CartController.getOrders(),orderSelected);

    }
    public void Delete(){
        try{
            String NomeWine = fx_NomeWine.getText();
            int Qta = Integer.parseInt(fx_Qta.getText());
            table_Wine.setItems(OrderManagement.removeOrder(CartController.getOrders(),NomeWine));
            table_Wine.refresh();
            fx_NomeWine.setText("");
            fx_Qta.setText("");
        }
        catch(Exception e){
        }

    }
    public void Update(){
        try{
            String NomeWine = fx_NomeWine.getText();
            int Qta = Integer.parseInt(fx_Qta.getText());
            table_Wine.setItems(OrderManagement.updateQta(CartController.getOrders(),NomeWine,Qta));
            table_Wine.refresh();
            fx_NomeWine.setText("");
            fx_Qta.setText("");
        }
        catch(Exception e){

        }


    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        order = new Order();
        orderSelected = new Order();
        //inizializzo la lista

        col_Nome.setCellValueFactory(new PropertyValueFactory<Order, String>("wineOrder"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<Order, Integer>("qta"));
        col_Prezzo.setCellValueFactory(new PropertyValueFactory<Order, Float>("price"));
        // prendere i dati dal database

        // aggiungere la lista alla tabella
        table_Wine.setItems(listO);
        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Login","Login.fxml",null);
            }
        });
        button_Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Logged in!","logged-in.fxml",fx_Label.getText());
            }
        });

    }
    public void setUserName(String UserName){
        System.out.println(UserName);
        fx_Label.setText(UserName);
    }
    public static ObservableList<Order> getOrders(){
        return listO;
    }
    public static void changeSceneMouse(MouseEvent event, String title, String fxmlFile, String username){
        Parent root = null;
        if(username!= null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtilsClient.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file
                OrderController orderController = loader.getController(); // get the controller
                orderController.setUserName(username);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else    {
            try{
                root = FXMLLoader.load(CartController.class.getResource(fxmlFile));
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
    public void setCart(int count,float prize){
        fx_NumArt.setText("n° articoli: "+count);
        fx_CostoTot.setText("Costo totale: " + prize);

    }

}
