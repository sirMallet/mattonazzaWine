package com.example.onlinewineshop.Controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.* ;
import javafx.stage.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server  extends Application {
    private int clientNo = 0;
    private TextArea ta = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {
        ta.setEditable(false);

        // scrollpane with text area in it to show the text area
        ScrollPane scroll = new ScrollPane(ta);
        Scene scene = new Scene(scroll, 450, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();
        new Thread(() -> {
            try{
                ServerSocket serverSocket = new ServerSocket(4445);
                Platform.runLater(() -> {
                    ta.appendText("Server started at " + new Date() + '\n');
                });
                while (true){
                    Socket socket = serverSocket.accept();
                    clientNo++;
                    Platform.runLater(() -> {

                        InetAddress inetAddress = socket.getInetAddress();
                        ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');
                        ta.appendText("Client " + clientNo + "'s IP Address is " + socket.getInetAddress() + '\n');
                    });
                    new Thread( new ThreadClient(socket)).start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

    }
    class ThreadClient implements Runnable{
        private Socket socket;
        public ThreadClient(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            try{
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                while (true){
                    Platform.runLater(() -> {
                        try{
                            ta.appendText("Client " + clientNo + " says: " + fromClient.readUTF() + '\n');

                        }catch (Exception e){
                            e.printStackTrace();}
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Application.launch(args);
    }


}
