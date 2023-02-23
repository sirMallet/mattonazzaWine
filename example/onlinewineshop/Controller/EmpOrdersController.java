package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.*;
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


public class EmpOrdersController implements Initializable {
    @FXML
    private Button button_invio;
    @FXML
    private Label UserName;
    @FXML
    private TextField tf_UserName;
    @FXML
    private TextField tf_Prezzo;
    @FXML
    private TextField tf_Qta;
    @FXML
    private Button button_Emp;
    @FXML
    private Button button_Wines;
    @FXML
    private Button button_Clients;
    @FXML
    private Button button_Orders;
    
    @FXML
    private Button button_Logout;
    @FXML
    private ImageView button_Accept;
    @FXML
    private ImageView button_Delete;
    @FXML
    private Label fx_Label;
    @FXML
    private TableView<Order> table_Orders;
    @FXML
    private TableColumn<Order, String> Col_UserName;
    @FXML
    private TableColumn<Order, Integer> Col_Qta;
    @FXML
    private TableColumn<Order, Float> Col_Prezzo;

    int CheckAdmin =0;
    Integer index;
    Order OrderSelected = null;
    ObservableList<Order> listO = null;

    @FXML
    void getSelected(MouseEvent event) {
        index = table_Orders.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        OrderSelected.setUsername(Col_UserName.getCellData(index));

        OrderSelected.setQta(Col_Qta.getCellData(index));
        OrderSelected.setPrice(Col_Prezzo.getCellData(index));
        tf_UserName.setText(Col_UserName.getCellData(index));
        tf_Qta.setText(Col_Qta.getCellData(index).toString());
        tf_Prezzo.setText(Col_Prezzo.getCellData(index).toString());
    }
    @FXML
    public void Accept(){

        try {
            ei.getEmployeSession().sendMessage("GestoreOrder|Accept|"+tf_UserName.getText()+"|"+ei.getNome()+"|"+ei.getCognome());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("In attesa della Conferma o Annullamento");
                alert.setHeaderText("in Attesa");
                alert.setContentText("in Attesa");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Errore in fase di accettazione");
                alert.setHeaderText("Errore in fase di accettazione");
                alert.setContentText("Errore in fase di accettazione");
                alert.show();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);


        }

    }
    @FXML
    public void Delete(){
        try {
            ei.getEmployeSession().sendMessage("GestoreOrder|Delete|"+tf_UserName.getText());
            ei.getEmployeSession().listenForMessage();
            Thread.sleep(250);
            String messageFromServer = ei.getEmployeSession().getmsg();
            if(messageFromServer.equals("true")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Ordine Eliminato");
                alert.setHeaderText("Ordine Eliminato");
                alert.setContentText("Ordine Eliminato");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION); // create an alert
                alert.setTitle("Ordine non Eliminato");
                alert.setHeaderText("Ordine non Eliminato");
                alert.setContentText("Ordine non Eliminato");
                alert.show();
            }
            try {
                ei.getEmployeSession().sendMessage("ViewListOrder");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                messageFromServer = ei.getEmployeSession().getmsg();
                listO = returnList(messageFromServer);
                table_Orders.setItems(listO);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        tf_UserName.clear();
        tf_Prezzo.clear();
        tf_Qta.clear();

    }
    public ObservableList<Order> returnList(String msgList){
        ObservableList<Order> listOtmp = FXCollections.observableArrayList();
        String[] tmp = msgList.split(";");
        for(String i: tmp){
            String[] msg = i.split("[|]");
            listOtmp.add(new Order(msg[0],msg[1],Integer.parseInt(msg[2]),Float.parseFloat(msg[3])));
        }
        return listOtmp;
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
                ei.getEmployeSession().sendMessage("ViewListOrder");
                ei.getEmployeSession().listenForMessage();
                Thread.sleep(250);
                String messageFromServer = ei.getEmployeSession().getmsg();
                listO = returnList(messageFromServer);
                table_Orders.setItems(listO);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);

            }
            flag=1;

        }
    }
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        UserName.setVisible(false);
        button_Orders.setDisable(true);
        OrderSelected = new Order();
        // settare la tabella
        // set the table view with the wine list from the database (DBUtils) and the columns of the table view (Wine class)
        Col_UserName.setCellValueFactory(new PropertyValueFactory<Order, String>("username"));
        Col_Qta.setCellValueFactory(new PropertyValueFactory<Order, Integer>("qta"));
        Col_Prezzo.setCellValueFactory(new PropertyValueFactory<Order, Float>("price"));
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
        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ei.setNome(null);
                ei.setCognome(null);
                ei.setAdmin(0);
                EmployeSession.changeScene(event,"Log in!","login.fxml",ei);
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

