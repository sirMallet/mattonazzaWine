package com.example.onlinewineshop.Controller;
import com.example.onlinewineshop.classes.OrderManagement;
import com.example.onlinewineshop.classes.Wine;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class EmployeSession {
    private Socket socket;
    private BufferedReader bufferReader;
    private BufferedWriter bufferWriter;
    private String returnMsg;

    public EmployeSession(Socket socket){
        try{
            this.socket = socket;
            this.bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        }catch (IOException e){
            closeEverything(socket, bufferReader, bufferWriter);
        }
    }
    public void sendMessage(String messageToSend) throws IOException {

        bufferWriter.write(messageToSend);
        bufferWriter.newLine();
        bufferWriter.flush();
    }
    public void listenForMessage(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               String messageFromServer;
                   while(socket.isConnected()){
                       try{
                           messageFromServer = bufferReader.readLine();
                           setmsg(messageFromServer);


                       }catch (IOException e){
                           closeEverything(socket, bufferReader, bufferWriter);
                       }
                   }

           }
       }).start();
    }


    public void setmsg(String messageFromServer) {
        returnMsg = messageFromServer;

    }
    public String getmsg(){
        return returnMsg;

    }

    public void closeEverything(Socket socket, BufferedReader bufferReader, BufferedWriter bufferWriter){
        try{
            if(bufferReader != null){
                bufferReader.close();
            }
            if(bufferWriter != null){
                bufferWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // cambio scene
    public static void changeScene(ActionEvent event, String title, String fxmlFile, EmployeInformation ei) {
        Parent root = null;
        if(ei.getNome() != null){
            try {
                FXMLLoader loader = new FXMLLoader(com.example.onlinewineshop.Controller.DBUtilsEmployee.class.getResource(fxmlFile));    //carico il file fxml
                root = loader.load(); // load the fxml file


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else    {
            try{
                root = FXMLLoader.load(com.example.onlinewineshop.Controller.DBUtilsEmployee.class.getResource(fxmlFile));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // get the stage from the event source (the button) and get the scene from the stage and get the window from the scene
        stage.setTitle(title);
        stage.setUserData(ei);

        if(title.equals("Log in!")){
            stage.setScene(new Scene(root,600,400)); // set the scene
        }
        else{
            stage.setScene(new Scene(root,1000,500));
        }
        stage.show();
    }



}
