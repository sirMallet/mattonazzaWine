package com.example.onlinewineshop.Controller;
import com.example.onlinewineshop.classes.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


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
    private Wine WineSelected;

    int flag = 0;
    public UserInformation ui;
    @FXML
    private void receiveData(MouseEvent event) {
        if(flag == 0) {
            // Step 1
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // Step 2
            ui = (UserInformation) stage.getUserData();

            System.out.println("Username: " + ui.getUsername());
            fx_username.setText("Benvenuto " + ui.getUsername() + "!");

            flag=1;
        }
    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        Order order = new Order();
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
        button_Cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                order.setWineOrder(WineSelected.getNome());
                System.out.println("Ordine: "+ WineSelected.getNome());
                order.setQta(Integer.parseInt(fx_qta.getText()));
                order.setPrice(WineSelected.getPrezzo());
                order.setUsername(ui.getUsername());

                if(order.getQta()>= WineSelected.getQta()){

                    System.out.println("quantità non superiore al magazzino!");
                    Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                    alert.setContentText("quantità non presente in magazzino!");
                    alert.show();
                }
                else{
                    order.setPrice(WineSelected.getPrezzo()*order.getQta());
                    order.toStringOrder();

                    CartController.getOrders().add(new Order(order.getUsername(),order.getWineOrder(),order.getQta(),order.getPrice()));
                    System.out.println(order.getUsername()+" ha aggiunto al carrello!");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // create an alert
                    alert.setContentText("aggiunto al carrello!");
                    alert.show();

                }
                ClientSession.changeScene(event,"Logged in!","logged-in.fxml",ui);

            }
        });

    }

    public void setWine(Wine wine){
        WineSelected = wine;
        fx_TitleWine.setText(wine.getNome());
        fx_qtaRimasta.setText("n° prodotti rimasti: "+wine.getQta());
    }



}



