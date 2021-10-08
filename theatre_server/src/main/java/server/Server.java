package server;

import dataBase.DataBase;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class Server {

    private static ServerSocket serverSocket = null;
    private static Socket cl = null;
    public static int connectionNumber = 0;
    private static String stateInfo;
    static int port;

     static Logger logger = Logger.getLogger(Server.class);

    public Server() throws IOException {
        Properties property = new Properties();
        property.load(DataBase.class.getClassLoader().getResourceAsStream("config.properties"));
        port = parseInt(property.getProperty("port"));
    }

    public static void setStateInfo(String stateInfo) {
        Server.stateInfo = stateInfo;
    }

    public static String getStateInfo() {
        return stateInfo;
    }

    public static void launchOptions() {
        setStateInfo("Active");
        logger.info("Server state" + getStateInfo());
    }

    public static void createLink() throws IOException {
        try {
            cl = new Socket();
            cl = serverSocket.accept();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setStateInfo("Wait...");
    }

    public static void connectionOptions() {
        logger.info("Server is on...");
        logger.info("Connected to server:" + connectionNumber);
    }

    public static void afterLinking() {
        logger.info("Server state:" + getStateInfo());
        connectionNumber++;
        logger.info(connectionNumber + " client-connected " + "; " + "Port address:" + cl.getLocalPort() + "; " + "ip address:" + cl.getInetAddress());
    }

    public static void start() {
        Thread t = new Thread(new ServerWorking(cl));
        t.start();
    }

    public static void startServer() throws IOException {
        try {
            launchOptions();
            new Server();
            serverSocket = new ServerSocket(port);
            while (true) {
                connectionOptions();
                createLink();
                afterLinking();
                start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            serverSocket.close();
            cl.close();
        }
    }
}


