package com.example.onlinewineshop.Controller;

import com.example.onlinewineshop.classes.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


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
    public void Delete(){
        try{
            String NomeUser = tf_UserName.getText();
            DBUtilsEmployee.Delete(NomeUser,"Order");
            table_Orders.refresh();
            listO = DBUtilsEmployee.ViewListOrder();
            // aggiungere la lista alla tabella
            table_Orders.setItems(listO);

        }
        catch(Exception e){
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

        listO = DBUtilsEmployee.ViewListOrder();
        // aggiungere la lista alla tabella
        table_Orders.setItems(listO);





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
        button_Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsClient.changeScene(event,"Log in!","login.fxml",null);
            }
        });
        button_Emp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtilsEmployee.changeScene(event,"Employee Management","EmpAdmin.fxml",UserName.getText(),CheckAdmin);
            }
        });

    }
    public void setUserInformation(String username, int isAdmin){
        CheckAdmin = isAdmin;
        button_Emp.setDisable(CheckAdmin == 0);
        UserName.setText(username);
        fx_Label.setText("Benvenuto " + UserName.getText() + "!");
    }

    @FXML
    public void Accept(MouseEvent mouseEvent) {
    }
}

