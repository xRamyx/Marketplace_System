package com.example.shoppingcart;

import java.io.*;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SocketClient {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private static SocketClient instance = null;
    private static final String ADDRESS = "192.168.167.5";

    private SocketClient(int port)
    {
        connectSocket(port);
    }

    private void connectSocket(int port){
        try
        {
            socket = new Socket(ADDRESS, port);
            System.out.println("Connected to port " + port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static SocketClient getInstance(){
        return initConnection(5000);
    }

    public static SocketClient initConnection(int port){
        if(instance == null){
            instance = new SocketClient(port);
        }
        return instance;
    }

    private String socketSendReceive(String data){
        try {
            output.writeUTF(data);
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject socketSendReceiveJSON(JSONObject json, String dist){
        JSONParser parser = new JSONParser();
        json.put("@dist", dist);
        try {
            JSONObject sentJson = new JSONObject(), receivedJson = new JSONObject();
            sentJson.put("req", json);
            receivedJson = (JSONObject) parser.parse(socketSendReceive(sentJson.toJSONString()));
            return (JSONObject) receivedJson.get("res");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(){
        try
        {
            JSONObject json = new JSONObject();
            json.put("@end", "end");
            socketSendReceiveJSON(json, "special");
            input.close();
            output.close();
            socket.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}