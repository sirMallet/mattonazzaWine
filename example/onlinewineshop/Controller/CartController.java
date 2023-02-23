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
    private Button button_Order;
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
    static ObservableList<Order> listOTemp = FXCollections.observableArrayList();
    int flag =0;
    UserInformation ui;
    @FXML
    private void receiveData(MouseEvent event) {
        if( flag == 0) {
            // Step 1
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // Step 2
            ui = (UserInformation) stage.getUserData();

            System.out.println("Username: " + ui.getUsername());
            fx_Label.setText("Benvenuto " + ui.getUsername() + "!");

            flag=1;
        }
    }
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
        String NomeWine = fx_NomeWine.getText();
        int Qta = Integer.parseInt(fx_Qta.getText());
        table_Wine.setItems(OrderManagement.removeOrder(CartController.getOrders(),NomeWine));
        table_Wine.refresh();
        fx_NomeWine.setText("");
        fx_Qta.setText("");

    }
    public void Update(){
        String NomeWine = fx_NomeWine.getText();
        int Qta = Integer.parseInt(fx_Qta.getText());
        table_Wine.setItems(OrderManagement.updateQta(CartController.getOrders(),NomeWine,Qta));
        table_Wine.refresh();
        fx_NomeWine.setText("");
        fx_Qta.setText("");
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
                ui.setUsername(null);
                ClientSession.changeScene(event,"Login","Login.fxml",ui);
            }
        });
        button_Back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientSession.changeScene(event,"Logged in!","logged-in.fxml",ui);
            }
        });
        button_Order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // chiamo una funzione che invia ordine con la lista di ordini
                listOTemp = listO;
                String listOrderWine = "";
                for (Order order : listOTemp) {
                    listOrderWine = listOrderWine + order.getUsername() + "/" + order.getWineOrder()+ "/" +order.getQta()+ "/"+order.getPrice()+ ";";
                }
                try {
                    ui.getClientSession().sendMessage("SendOrder"+"|"+listOrderWine);
                    ui.getClientSession().listenForMessage();
                    Thread.sleep(250);
                    String messageFromServer = ui.getClientSession().getmsg();
                    if(messageFromServer.equals("true")){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // create an alert
                        alert.setContentText("ordine inviato!");
                        alert.show();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                        alert.setContentText("errore nell'invio dell'ordine!");
                        alert.show();
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }



                ClientSession.changeScene(event,"Logged in!","logged-in.fxml",ui);

                listO.clear();
            }
        });
    }
    public static ObservableList<Order> getOrders(){
        return listO;
    }

    public void setCart(int count,float prize){
        fx_NumArt.setText("n° articoli: "+count);
        fx_CostoTot.setText("Costo totale: " + prize);

    }

}
