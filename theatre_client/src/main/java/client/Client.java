package client;

import account.Account;
import account.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import statistics.Damages;
import statistics.Notification;
import statistics.Rating;
import statistics.Statistics;
import theatre.Booking;
import theatre.Film;
import theatre.Seance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Client {
    public static Client client;
    private static Socket cl = null;
    private static PrintStream clout;
    private static BufferedReader clin;

    public static String toString(String s1, String s2) { //перегрузка метода toString()
        String s;
        s = s1 + "&&" + s2 + "&&";
        return s;
    }

    public static Client getInstanceClient() { //установление соединения
        if (client == null) {
            try {
                cl = new Socket(InetAddress.getLocalHost().getLocalHost(), 3036);
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
        clout.println("addNewAccountUser");
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addNewFilm(Film acc) {
        clout.println("addNewFilm");
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addNewSeances(Seance acc) {
        clout.println("addNewSeances");
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static void addBooking(Booking booking) {
        clout.println("addBooking");
        String str = new Gson().toJson(booking);
        clout.println(str);
    }

    public static void userAddNewRating(Rating rating) {
        clout.println("userAddNewRating");
        String str = new Gson().toJson(rating);
        clout.println(str);
    }


    public static void addNewAccountAdmin(Account acc) {
        clout.println("addNewAccountAdminUser");
        String str = new Gson().toJson(acc);
        clout.println(str);
    }

    public static boolean adminCheck(String login, String password) {
        String checkAdmin = "checkAdmin";
        String str = toString(login, password);
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
        String checkUser = "checkUser";
        String str = toString(login, password);
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
        String adst = "checkSameUser";
        String str = login;
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
        clout.println("addNewAccount");
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
        return checkCorrect(name) && checkCorrect(surname);
    }

    public static ArrayList<User> getAllAccountUser() throws IOException { //
        clout.println("getAllAccountUser");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<User>>() {
        }.getType());
    }

    public static ArrayList<Seance> getAllSeances() throws IOException { //
        clout.println("getAllSeances");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Seance>>() {
        }.getType());
    }

    public static ArrayList<Booking> getAllBookings() throws IOException { //
        clout.println("getAllBookings");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Booking>>() {
        }.getType());
    }

    public static ArrayList<Notification> getAllNotifications() throws IOException {
        clout.println("getAllNotifications");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Notification>>() {
        }.getType());
    }

    public static void deleteSelectedNotification(Notification not) {
        clout.println("deleteSelectedNotification");
        clout.println(new Gson().toJson(not));
    }

    public static void deleteSelectedRating(Rating rating) {
        clout.println("deleteRating");
        clout.println(new Gson().toJson(rating));
    }

    public static ArrayList<Rating> getAllRatings() throws IOException {
        clout.println("getAllRatings");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Rating>>() {
        }.getType());
    }

    public static ArrayList<Rating> getAllRatingsByLogin(String login) throws IOException {
        clout.println("getAllRatingsByLogin");
        clout.println(login);
        return new Gson().fromJson(clin.readLine(), new TypeToken<ArrayList<Rating>>() {
        }.getType());
    }

    public static ArrayList<Notification> getAllNotificationsByLogin(String login) throws IOException{
        clout.println("getAllNotificationsByLogin");
        clout.println(login);
        return new Gson().fromJson(clin.readLine(), new TypeToken<ArrayList<Notification>>() {
        }.getType());
    }

    public static ArrayList<Rating> getAvgRatings() throws IOException {
        clout.println("getAvgRating");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Rating>>() {
        }.getType());
    }

    public static ArrayList<Damages> getDamages() throws IOException {
        clout.println("getDamages");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Damages>>() {
        }.getType());
    }


    public static ArrayList<Booking> getAllBookingsToDel() throws IOException { //
        clout.println("getAllBookingsToDel");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Booking>>() {
        }.getType());
    }

    public static ArrayList<Booking> getAllBookingsByLogin(String str) throws IOException { //
        clout.println("getAllBookingsByLogin");
        clout.println(str);
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Booking>>() {
        }.getType());
    }

    public static ArrayList<Film> getAllFilms() throws IOException { //
        clout.println("getAllFilms");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Film>>() {
        }.getType());
    }

    public static ArrayList<Statistics> getStatistics() throws IOException { //
        clout.println("getStatistics");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Statistics>>() {
        }.getType());
    }

    public static ArrayList<String> getFilmTitles() throws IOException { //
        clout.println("getFilmTitles");
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<String>>() {
        }.getType());
    }


    public static void deleteSelectedAccount(String str) { //
        clout.println("deleteSelectedAccount");
        clout.println(str);
    }

    public static void deleteSelectedBooking(Booking booking) {
        clout.println("deleteSelectedBooking");
        String str = new Gson().toJson(booking);
        clout.println(str);
    }

    public static void deleteSelectedFilm(String str) { //
        clout.println("deleteSelectedFilm");
        clout.println(str);
    }

    public static void deleteSelectedSeance(Seance seance) { //
        clout.println("deleteSelectedSeances");
        String str = new Gson().toJson(seance);
        clout.println(str);
    }

    public static ArrayList<Integer> getSeancePlaces(Seance seance) throws IOException {
        clout.println("getSeancePlaces");
        String gson = new Gson().toJson(seance);
        clout.println(gson);
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<Integer>>() {
        }.getType());
    }

    public static ArrayList<LocalTime> getFreeTime(LocalDate date) throws IOException {
        clout.println("getFreeTime");
        clout.println(date);
        String receive = clin.readLine();
        return new Gson().fromJson(receive, new TypeToken<ArrayList<LocalTime>>() {
        }.getType());
    }

    public static void editDuration(int str, String log) {
        clout.println("editDuration");
        clout.println(log);
        clout.println(str);
    }

    public static void editTitle(String str, String log) {
        clout.println("editTitle");
        clout.println(log);
        clout.println(str);
    }

    public static void editRentalCost(int str, String log) {
        clout.println("editRentalCost");
        clout.println(log);
        clout.println(str);
    }

    public static void editPassword(String str, String log) {
        clout.println("editPassword");
        clout.println(log);
        clout.println(str);
    }

    public static void editTime(LocalTime str, LocalTime log) {
        clout.println("editTime");
        clout.println(log.toString());
        clout.println(str.toString());
    }

    public static void editLogin(String str, String log) {
        clout.println("editLogin");
        clout.println(log);
        clout.println(str);
    }

    public static void editSurname(String str, String log) {
        clout.println("editSurname");
        clout.println(log);
        clout.println(str);
    }

    public static void editName(String str, String log) {
        clout.println("editName");
        clout.println(log);
        clout.println(str);
    }

    public static void editMail(String str, String log) {
        clout.println("editMail");
        clout.println(log);
        clout.println(str);
    }

    public static void getReport() {
        clout.println("getReport");
    }
}
