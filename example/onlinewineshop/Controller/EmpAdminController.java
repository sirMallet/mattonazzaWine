package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.Employee;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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

    public void Delete(){
        try{
            String FiscalCode = tf_FiscalCode.getText();
            DBUtilsEmployee.Delete(FiscalCode,"Employee");
            table_Emp.refresh();
            listE = DBUtilsEmployee.ViewListEmployee();
            // aggiungere la lista alla tabella
            table_Emp.setItems(listE);

        }
        catch(Exception e){
        }

    }
    @FXML
    public void Add(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("INSERT INTO dipendente (Nome,Cognome,FiscalCode,IndirizzoEmail,NumTel,IndirizzoResidenza,isAdmin,Password)VALUES(?,?,?,?,?,?,?,?)"); // check if the user already exists
            preparedStatement.setString(1, tf_Nome.getText()); // set the Nome
            preparedStatement.setString(2, tf_Cognome.getText()); // set the Cognome
            preparedStatement.setString(3, tf_FiscalCode.getText()); // set the FiscalCode
            preparedStatement.setString(4, tf_IndirizzoEmail.getText()); // set the indirizzo email
            preparedStatement.setString(5, tf_NumTel.getText()); // set the NumTel
            preparedStatement.setString(6, tf_IndirizzoResidenza.getText()); // set the indirizzoResidenza
            if (tf_isAdmin.getText().equals("true")||tf_isAdmin.getText().equals("1")) {
                tf_isAdmin.setText("1");
            } else {
                tf_isAdmin.setText("0");
            }
            preparedStatement.setString(7, tf_isAdmin.getText()); // set the isAdmin
            preparedStatement.setString(8, tf_Password.getText()); // set the password

            preparedStatement.execute(); // execute the query
            tf_Nome.clear();
            tf_Cognome.clear();
            tf_FiscalCode.clear();
            tf_IndirizzoEmail.clear();
            tf_NumTel.clear();
            tf_IndirizzoResidenza.clear();
            tf_isAdmin.clear();
            tf_Password.clear();

            listE = DBUtilsEmployee.ViewListEmployee();
            // aggiungere la lista alla tabella
            table_Emp.setItems(listE);

            Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
            alert.setTitle("Impiegato aggiornato");
            alert.setHeaderText("Impiegato aggiornato");
            alert.setContentText("Impiegato aggiornato");
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
    @FXML
    public void Update(){
        Connection connection = null; // initialize the connection
        PreparedStatement preparedStatement = null; // check if the user already exists
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx wine","root",""); // connect to the database
            preparedStatement = connection.prepareStatement("UPDATE dipendente SET Nome = ? ,Cognome = ?,FiscalCode = ?,IndirizzoEmail = ?, NumTel= ?,IndirizzoResidenza = ?,isAdmin = ?,Password=? WHERE FiscalCode = ?"); // check if the user already exists
            preparedStatement.setString(1, tf_Nome.getText()); // set the username
            preparedStatement.setString(2, tf_Cognome.getText()); // set the password
            preparedStatement.setString(3, tf_FiscalCode.getText()); // set the name
            preparedStatement.setString(4, tf_IndirizzoEmail.getText()); // set the surname
            preparedStatement.setString(5, tf_NumTel.getText()); // set the fiscalCode
            preparedStatement.setString(6, tf_IndirizzoResidenza.getText()); // set the phone
            if (tf_isAdmin.getText().equals("true")||tf_isAdmin.getText().equals("1")) {
                tf_isAdmin.setText("1");
            } else {
                tf_isAdmin.setText("0");
            }
            preparedStatement.setString(7, tf_isAdmin.getText()); // set the address
            preparedStatement.setString(8, tf_Password.getText()); // set the email
            preparedStatement.setString(9,  EmployeeSelected.getFiscalCode()); // set the old username
            preparedStatement.execute(); // execute the query
            tf_Nome.clear();
            tf_Cognome.clear();
            tf_FiscalCode.clear();
            tf_IndirizzoEmail.clear();
            tf_NumTel.clear();
            tf_IndirizzoResidenza.clear();
            tf_isAdmin.clear();
            tf_Password.clear();

            listE = DBUtilsEmployee.ViewListEmployee();
            // aggiungere la lista alla tabella
            table_Emp.setItems(listE);

            Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
            alert.setTitle("Dipendente aggiornato");
            alert.setHeaderText("Dipendente aggiornato");
            alert.setContentText("Dipendente aggiornato");
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
        listE = DBUtilsEmployee.ViewListEmployee();
        // aggiungere la lista alla tabella
        table_Emp.setItems(listE);

        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Log in!","login.fxml",null);
            }
        });
        button_Wines.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Wines Management","EmpWines.fxml",UserName.getText(),CheckAdmin);
            }
        });
        button_Clients.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Clients Management","EmpClients.fxml",UserName.getText(),CheckAdmin);
            }
        });
        button_Orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Orders Management","EmpOrders.fxml",UserName.getText(),CheckAdmin);
            }
        });

    }
    public void setUserInformation(String username, int isAdmin){
        button_Emp.setDisable(CheckAdmin == 0);
        CheckAdmin=isAdmin;
        UserName.setText(username);
        fx_Label.setText("Benvenuto " + UserName.getText() + "!");
    }





}

