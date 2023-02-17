package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Wine;
import com.sun.jdi.event.ThreadStartEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.stage.*;

import java.io.IOException;
import java.net.Socket;



public class LoggedInController implements Initializable {
    @FXML
    private Button button_invio;
    @FXML
    private Label fx_Select;
    @FXML
    private TextField tf_wine;
    @FXML
    private ChoiceBox<String>   cb_region;
    public UserInformation ui;
    private final String[] reg = {"","Italy","France","South America","California","South Africa","Australia"};
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
        for(Wine w : listW){
            if(w.getNome().equals(WineSelected.getNome())){
                WineSelected.setQta(w.getQta());
            }
        }


        fx_Select.setText("Selected: " + WineSelected.getNome());
        System.out.println(WineSelected.toString());
        ClientSession.changeSceneMouse(event,"order!","order.fxml",WineSelected,ui);

    }
    @FXML
    void getCart(MouseEvent event) {
        ClientSession.changeSceneCart(event,"Cart","cart.fxml",ui);
    }
    int flag = 0;





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



    public ObservableList<Wine> returnList(String msgList){
        ObservableList<Wine> listWtmp = FXCollections.observableArrayList();
        String[] tmp = msgList.split(";");
        for(String i: tmp){
            String[] msg = i.split("[|]");
            listWtmp.add(new Wine(msg[0],msg[1],Integer.parseInt(msg[2]),msg[3],Integer.parseInt(msg[4]),Float.parseFloat(msg[5]),Integer.parseInt(msg[6])));
        }
        return listWtmp;
    }

    @FXML
    private void receiveData(MouseEvent event) {
        if(flag == 0) {
            // Step 1
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // Step 2
            ui = (UserInformation) stage.getUserData();

            System.out.println("Username: " + ui.getUsername());
            fx_Label.setText("Benvenuto " + ui.getUsername() + "!");
            try {
                ui.getClientSession().sendMessage("ViewList"+"|||0|1000");
                ui.getClientSession().listenForMessage();
                Thread.sleep(250);
                String messageFromServer = ui.getClientSession().getmsg();
                listW = returnList(messageFromServer);
                table_Wine.setItems(listW);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag=1;
        }
    }



    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // inizializzo listW
        WineSelected = new Wine();
        // settare la tabella
        // set the table view with the wine list from the database (DBUtils) and the columns of the table view (Wine class)
        col_Nome.setCellValueFactory(new PropertyValueFactory<Wine, String>("Nome"));
        col_Vintage.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Vintage"));
        col_Regione.setCellValueFactory(new PropertyValueFactory<Wine, String>("region"));
        col_Varietal.setCellValueFactory(new PropertyValueFactory<Wine, String>("Varietal"));
        col_Val.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Valutazione"));
        col_Prezzo.setCellValueFactory(new PropertyValueFactory<Wine, Float>("Prezzo"));
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
                try {
                    ui.getClientSession().sendMessage("ViewList"+"|"+name+"|"+region+"|"+prizeMin+"|"+prizeMax);
                    ui.getClientSession().listenForMessage();
                    Thread.sleep(1000);
                    String messageFromServer = ui.getClientSession().getmsg();
                    System.out.println(messageFromServer);
                    ObservableList<Wine> listWtmp = FXCollections.observableArrayList();
                    listW = returnList(messageFromServer);
                    table_Wine.getItems().removeAll();
                    // aggiungere la lista alla tabella
                    table_Wine.setItems(listW);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        });

        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ui.setUsername(null);
                ClientSession.changeScene(event,"Log in!","login.fxml",ui);
            }
        });

    }

    public void Regione(ActionEvent event){
        region = cb_region.getValue();
    }

    public void setCart(int count,float prize){
        fx_NumArt.setText("n° articoli: "+count);
        fx_CostoTot.setText("Costo totale: " + prize);

    }



}

