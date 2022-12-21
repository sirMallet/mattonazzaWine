package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Wine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class LoggedInController implements Initializable {
    @FXML
    private Button button_invio;
    @FXML
    private Label fx_Select;
    @FXML
    private TextField tf_wine;
    @FXML
    private ChoiceBox<String>   cb_region;
    private String UserName;
    private String[] reg = {"","Italy","France","South America","California","Australia"};
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
    private TableColumn<Wine, Float> col_Prezzo;
    private String name = "";
    private String region = "";
    int prizeMin = 0;
    int prizeMax = 1000;
    Integer index;
    Wine WineSelected = null;
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


        fx_Select.setText("Selected: " + WineSelected.getNome());
        System.out.println(WineSelected.toString());
        DBUtilsClient.changeSceneMouse(event,"order!","order.fxml",WineSelected,UserName);

    }
    @FXML
    void getCart(MouseEvent event) {
        System.out.println("carrello");
        DBUtilsClient.changeSceneCart(event,"Cart","cart.fxml",UserName);
    }



    // controllo del prezzo con gli slider
    @FXML
    private Slider sd_prizeMin;
    @FXML
    private Slider sd_prizeMax;
    @FXML
    private Label fx_Prize;
    @FXML
    private Label fx_NumArt;
    @FXML
    private Label fx_CostoTot;
    ObservableList<Wine> listW;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        WineSelected = new Wine();
        // settare la tabella
        // set the table view with the wine list from the database (DBUtils) and the columns of the table view (Wine class)
        col_Nome.setCellValueFactory(new PropertyValueFactory<Wine, String>("Nome"));
        col_Vintage.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Vintage"));
        col_Regione.setCellValueFactory(new PropertyValueFactory<Wine, String>("region"));
        col_Varietal.setCellValueFactory(new PropertyValueFactory<Wine, String>("Varietal"));
        col_Val.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Valutazione"));
        col_Prezzo.setCellValueFactory(new PropertyValueFactory<Wine, Float>("Prezzo"));
        // prendere i dati dal database
        listW = DBUtilsClient.ViewList(name,region,prizeMin,prizeMax);
        // aggiungere la lista alla tabella
        table_Wine.setItems(listW);
        // aggiungere le regioni alla choice box
        cb_region.getItems().addAll(reg);
        cb_region.setOnAction(this::Regione);
        // aggiungere il listener al prezzo

        fx_Prize.setText("da" + prizeMin + " a " + prizeMax);
        sd_prizeMin.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                prizeMin = (int) sd_prizeMin.getValue();
                fx_Prize.setText("da "+Integer.toString((int)sd_prizeMin.getValue()) + " a " + Integer.toString((int)sd_prizeMax.getValue()));
            }
        });
        sd_prizeMax.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                prizeMax = (int) sd_prizeMax.getValue();
                fx_Prize.setText("da "+Integer.toString((int)sd_prizeMin.getValue()) + " a " + Integer.toString((int)sd_prizeMax.getValue()));

            }
        });


        button_invio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                name = tf_wine.getText();
                listW = DBUtilsClient.ViewList(name,region,prizeMin,prizeMax);
                // aggiungere la lista alla tabella
                table_Wine.setItems(listW);
            }

        });

        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Log in!","login.fxml",null);
            }
        });

    }
    public void setUserInformation(String username){
        UserName = username;
        fx_Label.setText("Benvenuto " + username + "!");
    }
    public void Regione(ActionEvent event){
        region = cb_region.getValue();
    }

    public void setCart(int count,float prize){
        fx_NumArt.setText("n° articoli: "+count);
        fx_CostoTot.setText("Costo totale: " + prize);

    }




}

