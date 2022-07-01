import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread{

    private int clientNo;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ServerThread(Socket socket, int clientNo) {
        this.clientNo = clientNo;
        try {
            this.socket = socket;
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String socketReceive(){
        try {
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void socketSend(String data){
        try {
            output.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject socketReceiveJSON(){
        JSONParser parser = new JSONParser();
        JSONObject receivedJson = new JSONObject();
        try {
            receivedJson = (JSONObject) parser.parse(socketReceive());
            return (JSONObject) receivedJson.get("req");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void socketSendJSON(JSONObject json){
        JSONObject sentJson = new JSONObject();
        sentJson.put("res", json);
        socketSend(sentJson.toJSONString());
    }

    public JSONObject handleRequest(JSONObject req){
        ServerFunctionalities sf = new ServerFunctionalities();
        JSONObject res = new JSONObject();
        String dist = (String) req.get("@dist");
        switch (dist){
            case "login":
                res = sf.login(req);
                break;
            case "sign-up":
                res = sf.signUp(req);
                break;
            case "show-user-info":
                res = sf.show_user_info(req);
                break;
            case "purchase-items":
                res = sf.purchaseItems(req);
                break;
            case "get-all-products":
                res = sf.getAllProducts();
                break;
            case "deposit":
                sf.deposit(req);
                break;
            case "Get-History":
                res = sf.getHistory(req);
                break;
            case "dashboard-users":
                res = sf.dashboardUsers();
                break;
            case "dashboard-products":
                res = sf.dashboardProducts();
                break;
            case "dashboard-transactions":
                res = sf.dashboardTransactions();
                break;

        }
        return res;
    }

    @Override
    public void run(){
        System.out.println("Started serving client # " + this.clientNo);
        String line = "";
        while (true)
        {
            try
            {
                JSONObject json = socketReceiveJSON();
                JSONObject outJson = new JSONObject();

                if(json.get("@end") != null){    // close server
                    outJson.put("@end", "end");
                    socketSendJSON(outJson);
                    break;
                }

                outJson = handleRequest(json);
                socketSendJSON(outJson);
            }
            catch(Exception e)
            {
                System.out.println("Connection Error with client # " + this.clientNo);
                line = "CLOSE";
            }
        }
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed with client # " + this.clientNo);
    }
}