package com.example.onlinewineshop.Controller;



import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;


    // costruttore
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Client connesso");
                // qui posso decidere che tipo di client sono collegati, se DBEmployee o DBClient
                DBUtilsClient dbUtilsClient = new DBUtilsClient(socket);

                Thread thread = new Thread(dbUtilsClient);
                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try{
            if(socket != null){
                socket.close();
            }
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4445);
        Server server = new Server(serverSocket);
        server.startServer();

    }
}
