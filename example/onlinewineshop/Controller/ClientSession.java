package com.example.onlinewineshop.Controller;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSession {
    private Socket socket;
    private BufferedReader bufferReader;
    private BufferedWriter bufferWriter;
    private String username;
    public ClientSession(Socket socket, String username){
        try{
            this.socket = socket;
            this.bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        }catch (IOException e){
            closeEverything(socket, bufferReader, bufferWriter);
        }
    }
    public void sendMessage(){
        try{
            bufferWriter.write(username);
            bufferWriter.newLine();
            bufferWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferWriter.write(username+": "+messageToSend);
                bufferWriter.newLine();
                bufferWriter.flush();
            }

        }catch (IOException e){
            closeEverything(socket, bufferReader, bufferWriter);
        }
    }
    public void listenForMessage(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               String messageFromServer;

                   while(socket.isConnected()){
                       try{
                           messageFromServer = bufferReader.readLine();
                            System.out.println(messageFromServer);
                       }catch (IOException e){
                           closeEverything(socket, bufferReader, bufferWriter);
                       }
                   }

           }
       }).start();
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
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 4445);
        ClientSession clientSession = new ClientSession(socket,username);
        clientSession.listenForMessage();
        clientSession.sendMessage();
    }
}
