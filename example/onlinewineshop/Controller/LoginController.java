package com.example.onlinewineshop.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.*;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.Socket;


public class LoginController implements Initializable {

    @FXML
    private TextField tf_UserName;
    @FXML
    private Label fx_Nome;
    @FXML
    private Label fx_UserName;
    @FXML
    private TextField tf_Nome;
    @FXML
    private Button button_login;
    @FXML
    private RadioButton button_ChoiceC, button_ChoiceE;
    private int flag = 0;
    // faccio gestire la scelta del tipo di utente al controller
    @FXML
    public void getLogin(ActionEvent event) {
        if (button_ChoiceC.isSelected()) {
            fx_UserName.setText("UserName:");
            button_Signup.setDisable(false);
            fx_LabelSignUp.setDisable(false);
            tf_Nome.setDisable(true);
            tf_Nome.setVisible(false);
            fx_Nome.setDisable(true);
            fx_Nome.setVisible(false);
            flag = 0;
        } else if (button_ChoiceE.isSelected()) {
            button_Signup.setDisable(true);
            fx_LabelSignUp.setDisable(true);
            tf_Nome.setDisable(false);
            tf_Nome.setVisible(true);
            fx_Nome.setDisable(false);
            fx_Nome.setVisible(true);
            fx_UserName.setText("Cognome:");
            flag = 1;
        }
    }
    @FXML
    private Button button_Signup;
    @FXML
    private Button button_Logout;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Label fx_LabelSignUp;
    private ClientSession clientSession;
    private EmployeSession employeSession;
    public UserInformation ui;
    public EmployeInformation ei;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        try{
            Socket socket = new Socket("localhost", 4445);
            clientSession = new ClientSession(socket);
            employeSession = new EmployeSession(socket);
        }catch (IOException e){
            e.printStackTrace();
        }
        tf_Nome.setDisable(true);
        tf_Nome.setVisible(false);
        fx_Nome.setDisable(true);
        fx_Nome.setVisible(false);
        ui = new UserInformation(null,clientSession);
        ei = new EmployeInformation(null,null,employeSession,999);
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(flag==0){
                    try {
                        clientSession.sendMessage("logInUser"+"|"+tf_UserName.getText()+"|"+tf_password.getText());
                        clientSession.listenForMessage();
                        Thread.sleep(1000);
                        String messageFromServer = clientSession.getmsg();

                        if(messageFromServer!=null && messageFromServer.equals("true")){
                            ui.setUsername(tf_UserName.getText());
                            ClientSession.changeScene(event,"Logged in!","logged-in.fxml",ui);
                        }
                        else{
                            System.out.println("User not found in the database!");
                            Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                            alert.setContentText("provide a valid username!");
                            alert.show();
                        }
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    try {
                        employeSession.sendMessage("logInDip|"+tf_Nome.getText()+"|"+tf_UserName.getText()+"|"+tf_password.getText());
                        employeSession.listenForMessage();
                        Thread.sleep(1000);
                        String messageFromServer = employeSession.getmsg();
                        System.out.println(messageFromServer);
                        String[] message = messageFromServer.split("[|]");
                        // dovrebbe inviare il boolean e anche se è admin o no
                        if(message[0].equals("true")){
                            ei.setCognome(tf_UserName.getText());
                            ei.setNome(tf_Nome.getText());
                            ei.setAdmin(Integer.parseInt(message[1]));
                            System.out.println(ei.getNome()+" "+ei.getCognome()+" "+ei.getAdmin());
                            EmployeSession.changeScene(event,"Logged in!","EmpWines.fxml",ei);
                        }
                        else{
                            System.out.println("User not found in the database!");
                            Alert alert = new Alert(Alert.AlertType.ERROR); // create an alert
                            alert.setContentText("provide a valid name or password!");
                            alert.show();
                        }
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        button_Signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientSession.changeScene(event,"Sign up!","SignUp.fxml",ui);
            }
        });

    }

}
