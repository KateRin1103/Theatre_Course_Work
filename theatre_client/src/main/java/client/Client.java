package client;

import account.Account;
import account.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import statistics.Statistics;
import theatre.Booking;
import theatre.Seance;
import theatre.Spectacle;

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
                cl = new Socket(InetAddress.getLocalHost().getLocalHost(), 8002);
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

    public static void addNewSpectacle(Spectacle acc) {
        String userStatement = "addNewSpectacle";
        clout.println(userStatement);
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addNewSeances(Seance acc) {
        String userStatement = "addNewSeances";
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

    public static boolean checkSameUser(String login) {
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

    public static ArrayList<Seance> getAllSeances() throws IOException { //
        String GetNuserStatement = new String("getAllSeances");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<Seance> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<Seance>>() {
                }.getType());
        return arrayList;
    }

    public static ArrayList<Booking> getAllBookings() throws IOException { //
        String GetNuserStatement = new String("getAllBookings");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<Booking> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<Booking>>() {
                }.getType());
        return arrayList;
    }

    public static ArrayList<Spectacle> getAllSpectacles() throws IOException { //
        String GetNuserStatement = new String("getAllSpectacles");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<Spectacle> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<Spectacle>>() {
                }.getType());
        return arrayList;
    }

    public static ArrayList<Statistics> getStatistics() throws IOException { //
        String GetNuserStatement = new String("getStatistics");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<Statistics> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<Statistics>>() {
                }.getType());
        return arrayList;
    }

    public static ArrayList<String> getSpectacleTitles() throws IOException { //
        String GetNuserStatement = new String("getSpectacleTitles");
        clout.println(GetNuserStatement);
        String receive = clin.readLine();
        ArrayList<String> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<String>>() {
                }.getType());
        return arrayList;
    }


    public static void deleteSelectedAccount(String str) { //
        String delSelectedAcc = new String("deleteSelectedAccount");
        clout.println(delSelectedAcc);
        clout.println(str);
    }

    public static void deleteSelectedBooking(Booking booking) { //
        String delSelectedAcc = new String("deleteSelectedBooking");
        clout.println(delSelectedAcc);
        String str = new Gson().toJson(booking);
        clout.println(str);
    }

    public static void deleteSelectedSpectacle(String str) { //
        String delSelectedSp = new String("deleteSelectedSpectacle");
        clout.println(delSelectedSp);
        clout.println(str);
    }

    public static void deleteSelectedSeance(Seance seance) { //
        String delSelectedSeance = new String("deleteSelectedSeances");
        clout.println(delSelectedSeance);
        String str = new Gson().toJson(seance);
        clout.println(str);
    }

    public static ArrayList<Integer> getSeancePlaces(Seance seance) throws IOException{
        String GetNuserStatement = new String("getSeancePlaces");
        clout.println(GetNuserStatement);
        String gson = new Gson().toJson(seance);
        clout.println(gson);
        String receive = clin.readLine();
        ArrayList<Integer> arrayList = new Gson()
                .fromJson(receive, new TypeToken<ArrayList<Integer>>() {
                }.getType());
        return arrayList;
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

    public static void getReport() {
        clout.println("getReport");
    }
}
