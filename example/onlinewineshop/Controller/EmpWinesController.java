package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Wine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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
        try {
            ei.getEmployeSession().sendMessage("GestoreWine|Add|"+tf_wine.getText()+"|"+tf_Regione.getText()+"|"+tf_Vintage.getText()+"|"+tf_Varietal.getText()+"|"+tf_Val.getText()+"|"+tf_Prezzo.getText()+"|"+tf_Qta.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Vino aggiunto");
                alert.setHeaderText("Vino aggiunto");
                alert.setContentText("Vino aggiunto");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Vino non aggiunto");
                alert.setHeaderText("Vino non aggiunto");
                alert.setContentText("Vino non aggiunto");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewList"+"|||0|1000");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listW = returnList(messageFromServer);
                table_Wine.setItems(listW);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        tf_wine.clear();
        tf_Varietal.clear();
        tf_Regione.clear();
        tf_Vintage.clear();
        tf_Val.clear();
        tf_Prezzo.clear();
        tf_Qta.clear();

    }

    public void Delete(){
        try {
            ei.getEmployeSession().sendMessage("GestoreWine|Delete|"+tf_wine.getText()+"|"+tf_Regione.getText()+"|"+tf_Vintage.getText()+"|"+tf_Varietal.getText()+"|"+tf_Val.getText()+"|"+tf_Prezzo.getText()+"|"+tf_Qta.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Vino Eliminato");
                alert.setHeaderText("Vino Eliminato");
                alert.setContentText("Vino Eliminato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Vino non Eliminato");
                alert.setHeaderText("Vino non Eliminato");
                alert.setContentText("Vino non Eliminato");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewList"+"|||0|1000");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listW = returnList(messageFromServer);
                table_Wine.setItems(listW);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        tf_wine.clear();
        tf_Varietal.clear();
        tf_Regione.clear();
        tf_Vintage.clear();
        tf_Val.clear();
        tf_Prezzo.clear();
        tf_Qta.clear();

    }
    @FXML
    public void Update(){
        try {
            ei.getEmployeSession().sendMessage("GestoreWine|Update|"+tf_wine.getText()+"|"+tf_Regione.getText()+"|"+tf_Vintage.getText()+"|"+tf_Varietal.getText()+"|"+tf_Val.getText()+"|"+tf_Prezzo.getText()+"|"+tf_Qta.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Vino Modificato");
                alert.setHeaderText("Vino Modificato");
                alert.setContentText("Vino Modificato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Vino non Modificato");
                alert.setHeaderText("Vino non Modificato");
                alert.setContentText("Vino non Modificato");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewList"+"|||0|1000");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listW = returnList(messageFromServer);
                table_Wine.setItems(listW);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        tf_wine.clear();
        tf_Varietal.clear();
        tf_Regione.clear();
        tf_Vintage.clear();
        tf_Val.clear();
        tf_Prezzo.clear();
        tf_Qta.clear();

    }

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
    public EmployeInformation ei;
    int flag=0;
    @FXML
    private void receiveData(MouseEvent event) {
        if(flag == 0) {
            // Step 1
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // Step 2
            ei = (EmployeInformation) stage.getUserData();
            // Step 3
            button_Emp.setDisable(ei.getAdmin()==0);
            UserName.setText(ei.getNome());
            fx_Label.setText("Benvenuto " + ei.getNome()+ "!");
            try {
                ei.getEmployeSession().sendMessage("ViewList"+"|||0|1000");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                String messageFromServer = ei.getEmployeSession().getmsg();
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



        button_Clients.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"Clients Management","EmpClients.fxml",ei);
            }
        });
        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ei.setAdmin(0);
                ei.setNome(null);
                ei.setCognome(null);
                EmployeSession.changeScene(event,"Log in!","login.fxml",ei);
            }
        });
        button_Orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"Orders Management","EmpOrders.fxml",ei);
            }
        });
        button_Emp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"Employee Management","EmpAdmin.fxml",ei);
            }
        });

    }



}

