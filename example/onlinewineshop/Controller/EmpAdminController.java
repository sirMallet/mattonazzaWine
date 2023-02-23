package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Employee;
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


public class EmpAdminController implements Initializable {
    @FXML
    private Label UserName;
    @FXML
    private TextField tf_Password;
    @FXML
    private TextField tf_Nome;
    @FXML
    private TextField tf_Cognome;
    @FXML
    private TextField tf_FiscalCode;
    @FXML
    private TextField tf_IndirizzoEmail;
    @FXML
    private TextField tf_IndirizzoResidenza;
    @FXML
    private TextField tf_NumTel;
    @FXML
    private TextField tf_isAdmin;

    @FXML
    private Button button_Wines;
    @FXML
    private Button button_Clients;
    @FXML
    private Button button_Orders;
    @FXML
    private Button button_Logout;
    @FXML
    private Button button_History;
    @FXML
    private Button button_Emp;
    @FXML
    private Label fx_Label;
    @FXML
    private TableView<Employee> table_Emp;

    @FXML
    private TableColumn<Employee, String> Col_Nome;
    @FXML
    private TableColumn<Employee, String> Col_Cognome;
    @FXML
    private TableColumn<Employee, String> Col_FiscalCode;
    @FXML
    private TableColumn<Employee, String> Col_IndirizzoEmail;
    @FXML
    private TableColumn<Employee, String> Col_NumTel;
    @FXML
    private TableColumn<Employee, String> Col_IndirizzoResidenza;
    @FXML
    private TableColumn<Employee, Boolean> Col_isAdmin;
    @FXML
    private TableColumn<Employee, String> Col_Password;
    int CheckAdmin = 0;

    Integer index;
    Employee EmployeeSelected = null;
    ObservableList<Employee> listE = null;
    public ObservableList<Employee> returnList(String msgList){
        ObservableList<Employee> listEtmp = FXCollections.observableArrayList();
        String[] tmp = msgList.split(";");
        for(String i: tmp){
            String[] msg = i.split("[|]");
            listEtmp.add(new Employee(msg[0],msg[1],msg[2],msg[3],msg[4],msg[5],Boolean.parseBoolean(msg[6]),msg[7]));
        }
        return listEtmp;
    }
    int flag = 0;
    public EmployeInformation ei;
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
                ei.getEmployeSession().sendMessage("ViewListEmployee");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                String messageFromServer = ei.getEmployeSession().getmsg();
                listE = returnList(messageFromServer);
                table_Emp.setItems(listE);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }
            flag=1;

        }
    }
    public void Delete(){
        try {

            ei.getEmployeSession().sendMessage("GestoreEmployee|Delete|"+tf_Nome.getText()+"|"+tf_Cognome.getText()+"|"+tf_FiscalCode.getText()+"|"+tf_IndirizzoEmail.getText()+"|"+tf_NumTel.getText()+"|"+tf_IndirizzoResidenza.getText()+"|"+tf_isAdmin.getText()+"|"+tf_Password.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Dipendente Eliminato");
                alert.setHeaderText("Dipendente Eliminato");
                alert.setContentText("Dipendente Eliminato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Dipendente non Aggiunto");
                alert.setHeaderText("Dipendente non Aggiunto");
                alert.setContentText("Dipendente non Aggiunto");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListEmployee");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listE = returnList(messageFromServer);
                table_Emp.setItems(listE);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        tf_Nome.clear();
        tf_Cognome.clear();
        tf_FiscalCode.clear();
        tf_IndirizzoEmail.clear();
        tf_NumTel.clear();
        tf_IndirizzoResidenza.clear();
        tf_isAdmin.clear();
        tf_Password.clear();

    }

    @FXML
    public void Add(){
        try {
            if (tf_isAdmin.getText().equals("true")||tf_isAdmin.getText().equals("1")) {
                tf_isAdmin.setText("1");
            } else {
                tf_isAdmin.setText("0");
            }
            ei.getEmployeSession().sendMessage("GestoreEmployee|Add|"+tf_Nome.getText()+"|"+tf_Cognome.getText()+"|"+tf_FiscalCode.getText()+"|"+tf_IndirizzoEmail.getText()+"|"+tf_NumTel.getText()+"|"+tf_IndirizzoResidenza.getText()+"|"+tf_isAdmin.getText()+"|"+tf_Password.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Dipendente Aggiunto");
                alert.setHeaderText("Dipendente Aggiunto");
                alert.setContentText("Dipendente Aggiunto");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Dipendente non Aggiunto");
                alert.setHeaderText("Dipendente non Aggiunto");
                alert.setContentText("Dipendente non Aggiunto");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListEmployee");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listE = returnList(messageFromServer);
                table_Emp.setItems(listE);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        tf_Nome.clear();
        tf_Cognome.clear();
        tf_FiscalCode.clear();
        tf_IndirizzoEmail.clear();
        tf_NumTel.clear();
        tf_IndirizzoResidenza.clear();
        tf_isAdmin.clear();
        tf_Password.clear();


    }

    @FXML
    public void Update(){

        try {
            if (tf_isAdmin.getText().equals("true")||tf_isAdmin.getText().equals("1")) {
                tf_isAdmin.setText("1");
            } else {
                tf_isAdmin.setText("0");
            }
            ei.getEmployeSession().sendMessage("GestoreEmployee|Update|"+tf_Nome.getText()+"|"+tf_Cognome.getText()+"|"+tf_FiscalCode.getText()+"|"+tf_IndirizzoEmail.getText()+"|"+tf_NumTel.getText()+"|"+tf_IndirizzoResidenza.getText()+"|"+tf_isAdmin.getText()+"|"+tf_Password.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Dipendente Modificato");
                alert.setHeaderText("Dipendente Modificato");
                alert.setContentText("Dipendente Modificato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Dipendente non Modificato");
                alert.setHeaderText("Dipendente non Modificato");
                alert.setContentText("Dipendente non Modificato");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListEmployee");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listE = returnList(messageFromServer);
                table_Emp.setItems(listE);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
            tf_Nome.clear();
            tf_Cognome.clear();
            tf_FiscalCode.clear();
            tf_IndirizzoEmail.clear();
            tf_NumTel.clear();
            tf_IndirizzoResidenza.clear();
            tf_isAdmin.clear();
            tf_Password.clear();


    }

    @FXML
    void getSelected(MouseEvent event) {
        index = table_Emp.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        EmployeeSelected.setNome(Col_Nome.getCellData(index));
        EmployeeSelected.setCognome(Col_Cognome.getCellData(index));
        EmployeeSelected.setFiscalCode(Col_FiscalCode.getCellData(index));
        EmployeeSelected.setIndirizzoEmail(Col_IndirizzoEmail.getCellData(index));
        EmployeeSelected.setNumTel(Col_NumTel.getCellData(index));
        EmployeeSelected.setIndirizzoResidenza(Col_IndirizzoResidenza.getCellData(index));
        EmployeeSelected.setIsAdmin(Col_isAdmin.getCellData(index));
        EmployeeSelected.setPassword(Col_Password.getCellData(index));
        tf_Nome.setText(EmployeeSelected.getNome());
        tf_Cognome.setText(String.valueOf(EmployeeSelected.getCognome()));
        tf_FiscalCode.setText(String.valueOf(EmployeeSelected.getFiscalCode()));
        tf_IndirizzoEmail.setText(String.valueOf(EmployeeSelected.getIndirizzoEmail()));
        tf_NumTel.setText(String.valueOf(EmployeeSelected.getNumTel()));
        tf_IndirizzoResidenza.setText(String.valueOf(EmployeeSelected.getIndirizzoResidenza()));
        tf_isAdmin.setText(String.valueOf(EmployeeSelected.getIsAdmin()));
        tf_Password.setText(String.valueOf(EmployeeSelected.getPassword()));



    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        UserName.setVisible(false);
        EmployeeSelected = new Employee();
        // settare la tabella
        // set the table view with the wine list from the database (DBUtils) and the columns of the table view (Wine class)
        Col_Nome.setCellValueFactory(new PropertyValueFactory<Employee, String>("Nome"));
        Col_Cognome.setCellValueFactory(new PropertyValueFactory<Employee, String>("Cognome"));
        Col_FiscalCode.setCellValueFactory(new PropertyValueFactory<Employee, String>("FiscalCode"));
        Col_IndirizzoEmail.setCellValueFactory(new PropertyValueFactory<Employee, String>("IndirizzoEmail"));
        Col_NumTel.setCellValueFactory(new PropertyValueFactory<Employee, String>("NumTel"));
        Col_IndirizzoResidenza.setCellValueFactory(new PropertyValueFactory<Employee, String>("IndirizzoResidenza"));
        Col_isAdmin.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("isAdmin"));
        Col_Password.setCellValueFactory(new PropertyValueFactory<Employee, String>("Password"));


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
        button_Clients.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"Clients Management","EmpClients.fxml",ei);
            }
        });
        button_History.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"List History","History.fxml",ei);
            }
        });
        button_Orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmployeSession.changeScene(event,"Orders Management","EmpOrders.fxml",ei);
            }
        });

    }
}

