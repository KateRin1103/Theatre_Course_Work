package dataBase;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataBase {
    private static DataBase database;
    private static Connection connection;
    private static Statement statement;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public DataBase() throws IOException {
        Properties property = new Properties();
        property.load(DataBase.class.getClassLoader().getResourceAsStream("config.properties"));
        URL = property.getProperty("db.url");
        USER = property.getProperty("db.login");
        PASSWORD = property.getProperty("db.password");
    }

    public static DataBase openDatabase()// singleton-паттерн
    {
        if (database == null) {
            try {
                new DataBase();
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.isClosed();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex){
                System.out.println(ex);
            }
        }
        return database;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static ResultSet getDatabase(String str) {
        ResultSet res = null;
        try {
            statement = connection.createStatement();
            res = statement.executeQuery(str);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static void execute(String str)//Выполнить скрипт, для добавления и удаления
    {
        try {
            statement = connection.createStatement();
            statement.execute(str);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
