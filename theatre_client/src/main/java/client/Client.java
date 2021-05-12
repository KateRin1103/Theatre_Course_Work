package client;

import account.Account;
import account.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static Client client;
    private static Socket cl = null;
    private static PrintStream clout;
    private static BufferedReader clin;

    public static String toString(String s1, String s2) {
        String s = null;
        s = new String(s1 + "&&" + s2 + "&&");
        return s;
    }

    public static Client getInstanceClient() {
        if (client == null) {
            try {
                cl = new Socket(InetAddress.getLocalHost().getLocalHost(), 8000);
                clout = new PrintStream(cl.getOutputStream());
                clin = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return client;
        } else {
            return client;
        }
    }

    public static void addNewAccountUser(User acc) {
        String userStatement = "addNewAccountUser";
        clout.println(userStatement);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addNewAccountAdmin(Account acc) {
        String userStatement = "addNewAccountAdminUser";
        clout.println(userStatement);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static boolean userCheck(String login, String password) {
        String checkUser = new String("checkUser");
        String str = new String(toString(login, password));
        String check = new String();
        try {
            clout.println(checkUser);
            clout.println(str);
            check = clin.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (check.equals("true"))
            return true;
        else
            return false;
    }

    public static boolean checkSameUser(String login) { //
        String adst = new String("checkSameUser");
        String str = new String(login);
        String check = new String();
        try {
            clout.println(adst);
            clout.println(str);
            check = clin.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (check.equals("true"))
            return true;
        else
            return false;
    }

    public static void addNewAccount(Account acc) {
        String userStatement = new String("addNewAccount");
        clout.println(userStatement);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static boolean checkCorrect(String str) {
        for (var i = 0; i < str.length(); i++) {
            if (!(str.charAt(0) >= 'А' && str.charAt(0) <= 'Я'))
                return false;
            if (!(str.charAt(i) >= 'а' && str.charAt(i) <= 'я') && !(str.charAt(i) >= 'А' && str.charAt(i) <= 'Я')
                    && !(str.charAt(i) == ' ') && !(str.charAt(i) == '-'))
                return false;
        }
        return true;
    }

    public static boolean checkCorrectAccount(String name, String surname) {
        if (checkCorrect(name) && checkCorrect(surname))
            return true;
        else
            return false;
    }
}
