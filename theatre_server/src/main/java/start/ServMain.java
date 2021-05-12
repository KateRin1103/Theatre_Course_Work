package start;

import dataBase.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class ServMain {
    public static void main(String[] args) {
        try{
            Properties property = new Properties();
            property.load(DataBase.class.getClassLoader().getResourceAsStream("config.properties"));
            String url = property.getProperty("db.url");
            String username = property.getProperty("db.login");
            String password = property.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();

                int rows = statement.executeUpdate("INSERT admin(login, password) VALUES ('test', 'test')");
                System.out.printf("Added rows", rows);
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
}

