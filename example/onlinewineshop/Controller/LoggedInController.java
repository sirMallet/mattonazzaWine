package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Wine;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_Logout;
    @FXML
    private Label fx_Label;
    @FXML
    private TableView<Wine> table_Wine;
    @FXML
    private TableColumn<Wine, String> col_Nome;
    @FXML
    private TableColumn<Wine, Integer> col_Vintage;
    @FXML
    private TableColumn<Wine, String> col_Regione;
    @FXML
    private TableColumn<Wine, String> col_Varietal;
    @FXML
    private TableColumn<Wine, Integer> col_Val;
    @FXML
    private TableColumn<Wine, Double> col_Prezzo;

    ObservableList<Wine> listW;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // settare la tabella
        col_Nome.setCellValueFactory(new PropertyValueFactory<Wine, String>("Nome"));
        col_Vintage.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Vintage"));
        col_Regione.setCellValueFactory(new PropertyValueFactory<Wine, String>("region"));
        col_Varietal.setCellValueFactory(new PropertyValueFactory<Wine, String>("Varietal"));
        col_Val.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Valutazione"));
        col_Prezzo.setCellValueFactory(new PropertyValueFactory<Wine, Double>("Prezzo"));
        // prendere i dati dal database
        listW = DBUtils.ViewList();
        // aggiungere la lista alla tabella
        table_Wine.setItems(listW);

        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"Log in!","login.fxml",null);
            }
        });

    }
    public void setUserInformation(String username){
        fx_Label.setText("Benvenuto " + username + "!");
    }
}

