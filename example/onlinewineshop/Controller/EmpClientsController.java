package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Client;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class EmpClientsController implements Initializable {
    @FXML
    private Button button_invio;
    @FXML
    private Label UserName;


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
    int CheckAdmin = 0;

    Integer index;
    Client ClientSelected = null;
    ObservableList<Client> listC = null;

    public void Delete(){
        try {
            ei.getEmployeSession().sendMessage("GestoreClient|Delete|"+tf_UserName.getText()+"|"+tf_Password.getText()+"|"+tf_Nome.getText()+"|"+tf_Cognome.getText()+"|"+tf_CodiceFiscale.getText()+"|"+tf_Phone.getText()+"|"+tf_Indirizzo.getText()+"|"+tf_Email.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(100);
            String messageFromServer = ei.getEmployeSession().getmsg();
            System.out.println(messageFromServer);
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Cliente eliminato");
                alert.setHeaderText("Cliente eliminato");
                alert.setContentText("Cliente eliminato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Cliente Non eliminato");
                alert.setHeaderText("Cliente Non eliminato");
                alert.setContentText("Cliente Non eliminato");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListClient");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listC = returnList(messageFromServer);
                table_Client.setItems(listC);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }


        tf_Nome.clear();
        tf_Cognome.clear();
        tf_UserName.clear();
        tf_Password.clear();
        tf_CodiceFiscale.clear();
        tf_Phone.clear();
        tf_Indirizzo.clear();
        tf_Email.clear();

    }
    @FXML
    public void Add(){
        try {
            ei.getEmployeSession().sendMessage("GestoreClient|Add|"+tf_UserName.getText()+"|"+tf_Password.getText()+"|"+tf_Nome.getText()+"|"+tf_Cognome.getText()+"|"+tf_CodiceFiscale.getText()+"|"+tf_Phone.getText()+"|"+tf_Indirizzo.getText()+"|"+tf_Email.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Cliente aggiunto");
                alert.setHeaderText("Cliente aggiunto");
                alert.setContentText("Cliente aggiunto");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Cliente Non aggiunto");
                alert.setHeaderText("Cliente Non aggiunto");
                alert.setContentText("Cliente Non aggiunto");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListClient");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listC = returnList(messageFromServer);
                table_Client.setItems(listC);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }


        tf_Nome.clear();
        tf_Cognome.clear();
        tf_UserName.clear();
        tf_Password.clear();
        tf_CodiceFiscale.clear();
        tf_Phone.clear();
        tf_Indirizzo.clear();
        tf_Email.clear();
    }
    @FXML
    public void Update(){
        try {
            ei.getEmployeSession().sendMessage("GestoreClient|Update|"+tf_UserName.getText()+"|"+tf_Password.getText()+"|"+tf_Nome.getText()+"|"+tf_Cognome.getText()+"|"+tf_CodiceFiscale.getText()+"|"+tf_Phone.getText()+"|"+tf_Indirizzo.getText()+"|"+tf_Email.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            System.out.println(messageFromServer);
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Cliente aggiornato");
                alert.setHeaderText("Cliente aggiornato");
                alert.setContentText("Cliente aggiornato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Cliente Non aggiornato");
                alert.setHeaderText("Cliente Non aggiornato");
                alert.setContentText("Cliente Non aggiornato");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListClient");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(10);
                messageFromServer = ei.getEmployeSession().getmsg();
                listC = returnList(messageFromServer);
                table_Client.setItems(listC);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }


        tf_Nome.clear();
        tf_Cognome.clear();
        tf_UserName.clear();
        tf_Password.clear();
        tf_CodiceFiscale.clear();
        tf_Phone.clear();
        tf_Indirizzo.clear();
        tf_Email.clear();
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
    public ObservableList<Client> returnList(String msgList){
        ObservableList<Client> listCtmp = FXCollections.observableArrayList();
        String[] tmp = msgList.split(";");
        for(String i: tmp){
            String[] msg = i.split("[|]");
            listCtmp.add(new Client(msg[0],msg[1],msg[2],msg[3],msg[4],msg[5],msg[6],msg[7]));
        }
        return listCtmp;
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
                ei.getEmployeSession().sendMessage("ViewListClient");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                String messageFromServer = ei.getEmployeSession().getmsg();
                listC = returnList(messageFromServer);
                table_Client.setItems(listC);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }
            flag=1;

        }
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

        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ei.setAdmin(0);
                ei.setNome(null);
                ei.setCognome(null);
                EmployeSession.changeScene(event,"Log in!","login.fxml",ei);
            }
        });
        button_Wines.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"Wines Management","EmpWines.fxml",ei);
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

