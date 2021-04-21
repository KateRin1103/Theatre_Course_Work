package serverProcess;

import dataBase.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {
    private static ServerSocket serverSocket = null;
    private static Socket cl = null;
    public static int connectionNumber = 0;
    private static String stateInfo = new String ();
    static int port ;


    public Server() throws IOException
    {
        Properties property = new Properties();
        property.load(DataBase.class.getClassLoader().getResourceAsStream("config.properties"));
        port = Integer.valueOf(property.getProperty("port"));
    }

    public static void setStateInfo(String stateInfo) {
        Server.stateInfo = stateInfo;
    }

    public static String getStateInfo() {
        return stateInfo;
    }

    public static void launchOptions() {
        setStateInfo ( "Активен" );
        System.out.println ( "Состояние сервера:" + getStateInfo () );
    }

    public static void createLink() throws IOException {
        try {
            cl = new Socket();
            cl = serverSocket.accept ();
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
        setStateInfo ( "Wait" );
    }

    public static void connectionOptions() {
        System.out.println ( "Сервер включен..." );
        System.out.println ( "Подключено к серверу:" + connectionNumber );
    }

    public static void afterLinking() {
        System.out.println ( "Состояние сервера:" + getStateInfo () );
        connectionNumber++;
        System.out.println ( connectionNumber + " подключился к серверу " + "\n" + "Адрес порта:" + cl.getLocalPort () + "\n" + "ip адрес:" + cl.getInetAddress () );
    }

    public static void start() {
       // Thread t = new Thread ( new ServerWorking ( cl ) );
        //t.start ();
    }

    public static void startServer() throws IOException {
        try {
            launchOptions ();
            new Server();
            serverSocket = new ServerSocket( port );
            while (true) {
                connectionOptions ();
                createLink ();
                afterLinking ();
                start ();
            }
        } catch (IOException ex) {
            ex.printStackTrace ();
        } finally {
            serverSocket.close ();
            cl.close ();
        }
    }
}


