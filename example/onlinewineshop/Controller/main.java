package com.example.onlinewineshop.Controller;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class main extends Application {
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Online Wine Shop");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        // Socket nel try
        try{
            Socket socket = new Socket("localhost", 4445);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        Application.launch(args);
    }
}

