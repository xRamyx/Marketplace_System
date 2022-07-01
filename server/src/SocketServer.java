import java.net.*;

public class SocketServer {

    private ServerSocket server = null;
    private static SocketServer instance = null;

    private SocketServer(int port)
    {
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static SocketServer startServer(int port){
        if(instance == null){
            instance = new SocketServer(port);
        }
        return instance;
    }

    public ServerSocket getServer() {
        return server;
    }
}