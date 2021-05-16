package client;

import account.Account;
import account.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public static Client client;
    private static Socket cl = null;
    private static PrintStream clout;
    private static BufferedReader clin;

    public static String toString(String s1, String s2) { //перегрузка метода toString()
        String s = null;
        s = s1 + "&&" + s2 + "&&";
        return s;
    }

    public static Client getInstanceClient() { //установление соединения
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

    public static void addNewAccountUser(User acc) { //добавление юзера в бд
        String userStatement = "addNewAccountUser";
        clout.println(userStatement);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addNewAccountAdmin(Account acc) { //добавление админа в бд
        String userStatement = "addNewAccountAdminUser";
        clout.println(userStatement);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static boolean adminCheck(String login, String password) {
        String checkAdmin = new String("checkAdmin");
        String str = new String(toString(login, password));
        String check = new String();
        try {
            clout.println(checkAdmin);
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

    public static boolean userCheck(String login, String password) { //проверка существования юзера
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

    public static boolean checkSameUser(String login) { //проверка юзера в базе данных для авторизации
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

    public static ArrayList<Account> getAllAccount() throws IOException { //
        String GetNuserStatement = new String("getAllAccount");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<Account> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<Account>>() {
                }.getType());
        return arrayList;
    }

    public static ArrayList<User> getAllAccountUser() throws IOException { //
        String GetNuserStatement = new String("getAllAccountUser");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<User> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<User>>() {
                }.getType());
        return arrayList;
    }

    public static void deleteSelectedAccount(String str) { //
        String delSelectedAcc = new String("deleteSelectedAccount");
        clout.println(delSelectedAcc);
        clout.println(str);
    }

    public static void editPassword(String str, String log) { //
        String editPassword = new String("editPassword");
        clout.println(editPassword);
        clout.println(log);
        clout.println(str);
    }

    public static void editLogin(String str, String log) { //
        String editLogin = new String("editLogin");
        clout.println(editLogin);
        clout.println(log);
        clout.println(str);
    }

    public static void editSurname(String str, String log) { //
        String editSurname = new String("editSurname");
        clout.println(editSurname);
        clout.println(log);
        clout.println(str);
    }

    public static void editName(String str, String log) { //
        String editName = new String("editName");
        clout.println(editName);
        clout.println(log);
        clout.println(str);
    }

    public static void editMail(String str, String log) { //
        String editMail = new String("editMail");
        clout.println(editMail);
        clout.println(log);
        clout.println(str);
    }

}
