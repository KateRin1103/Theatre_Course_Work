package server;

import account.Account;
import account.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import theatre.Booking;
import theatre.Seance;
import theatre.Spectacle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static dataBase.DataBase.*;

public class ServerWorking extends Thread {

    private static Socket cl = null;
    private static final String checkAdmin = "checkAdmin";
    private static final String checkSameUser = "checkSameUser";
    private static final String addNewAccountUser = "addNewAccountUser";
    private static final String addNewAccountAdmin = "addNewAccountAdmin";
    private static final String addNewSpectacle = "addNewSpectacle";
    private static final String addNewSeances = "addNewSeances";
    private static final String checkUser = "checkUser";
    private static final String getAllAccount = "getAllAccount";
    private static final String getAllSeances = "getAllSeances";
    private static final String getAllSpectacles = "getAllSpectacles";
    private static final String getAllAccountUser = "getAllAccountUser";
    private static final String getAllBookings = "getAllBookings";
    private static final String deleteSelectedAccount = "deleteSelectedAccount";
    private static final String deleteSelectedSeances = "deleteSelectedSeances";
    private static final String deleteSelectedSpectacle = "deleteSelectedSpectacle";
    private static final String editPassword = "editPassword";
    private static final String editLogin = "editLogin";
    private static final String editSurname = "editSurname";
    private static final String editName = "editName";
    private static final String editMail = "editMail";
    private static final String end = "end";
    private static final String addSpectacle = "addSpectacle";

    private static final String buyTickets = "buyTickets";
    private static final String showBoughtTickets = "showBoughtTickets";
    private static final String showUsersBoughtTickets = "showUsersBoughtTickets";
    //private static final String getProfit = "getProfit";
    //private static final String accReport = "accReport";

    static Logger logger = Logger.getLogger(ServerWorking.class);

    public ServerWorking(Socket cl) {
        this.cl = cl;
    }

    BufferedReader bufferedReader;
    PrintStream printStream;
    ArrayList<String> resultSplit;

    public void run() {
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                printStream = new PrintStream(cl.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                String input = null;//получаем информацию о команде
                try {
                    try {
                        input = bufferedReader.readLine();
                    } catch (SocketException ex) {
                        logger.info("Клиент отключился");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    assert input != null;
                    if (input.equals(end)) {
                        break;
                    } else {
                        if (input.equals(checkAdmin)) {
                            adminAutorization();
                        }
                        if (input.equals(checkSameUser)) {
                            checkSameUser();
                        }
                        if (input.equals(checkUser)) {
                            userAutorization();
                        }
                        if (input.equals(addNewAccountUser)) {
                            addAccUser();
                        }
                        if (input.equals(getAllSeances)) {
                            getAllSeances();
                        }
                        if (input.equals(addNewAccountAdmin)) {
                            addAccAdmin();
                        }
                        if (input.equals(addNewSpectacle)) {
                            addNewSpectacle();
                        }
                        if (input.equals(addNewSeances)) {
                            addNewSeances();
                        }
                        if (input.equals(getAllBookings)) {
                            getAllBookings();
                        }
                        if (input.equals(getAllAccount)) {
                            //  getAcc();
                        }
                        if (input.equals(getAllAccountUser)) {
                            getAccUser();
                        }
                        if (input.equals(deleteSelectedAccount)) {
                            deleteAcc();
                        }
                        if (input.equals(deleteSelectedSeances)) {
                            deleteSeance();
                        }
                        if (input.equals(editPassword)) {
                            editPass();
                        }
                        if (input.equals(editLogin)) {
                            editLog();
                        }
                        if (input.equals(deleteSelectedSpectacle)) {
                            delSpect();
                        }
                        if (input.equals(editSurname)) {
                            editSur();
                        }
                        if (input.equals(editName)) {
                            editN();
                        }
                        if (input.equals(editMail)) {
                            editMail();
                        }
                        if (input.equals(addSpectacle)) {
                            //addSpectacle();
                        }
                        if (input.equals(getAllSpectacles)) {
                            getAllSpectacles();
                        }
                        if (input.equals(showBoughtTickets)) {
                            //  showBoughtTickets();
                        }
                        if (input.equals(buyTickets)) {
                            //   buyTickets();
                        }
                        if (input.equals(showUsersBoughtTickets)) {
                            //   showUsersBoughtTickets();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                bufferedReader.close();
                printStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Server.connectionNumber--;
        }
    }

    private void getAllSpectacles() throws SQLException {
        String sqlst = new String("SELECT * FROM mytheatre.spectacle");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Spectacle> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Spectacle spectacle = new Spectacle();
            spectacle.setTitle(resultSet.getString("title"));
            spectacle.setDuration(resultSet.getInt("duration"));
            arrayList.add(spectacle);
        }
        String sent = new Gson().toJson(arrayList);
        printStream.println(sent);
    }

    private void getAllSeances() throws SQLException {
        String sqlst = String.format("SELECT s.title, se.date, se.time " +
                "FROM seance se INNER JOIN spectacle s on se.spectacle_id = s.id");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Seance> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Seance seance = new Seance();
            seance.setSpectacle(resultSet.getString("title"));
            seance.setDate(resultSet.getDate("date").toLocalDate());
            seance.setTime(resultSet.getTime("time").toLocalTime());
            arrayList.add(seance);
        }
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd");
        Gson gson = gb.create();
        String sent = gson.toJson(arrayList);
        printStream.println(sent);
    }

    private void getAllBookings() throws SQLException {
        String sqlst = String.format("SELECT u.login AS `log`, sp.title, se.time, se.date, p.place, p.row\n" +
                "FROM booking b\n" +
                "         INNER JOIN place p on b.place_id = p.id\n" +
                "         INNER JOIN user u on b.user_id = u.id\n" +
                "         INNER JOIN seance se on b.seance_id = se.id\n" +
                "         INNER JOIN spectacle sp on se.spectacle_id = sp.id");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Booking> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Booking seance = new Booking();
            seance.setTitle(resultSet.getString("title"));
            seance.setLogin(resultSet.getString("log"));
            seance.setDate(resultSet.getDate("date").toLocalDate());
            seance.setTime(resultSet.getTime("time").toLocalTime());
            seance.setRow(resultSet.getInt("row"));
            seance.setPlace(resultSet.getInt("place"));
            arrayList.add(seance);
        }
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd");
        Gson gson = gb.create();
        String sent = gson.toJson(arrayList);
        printStream.println(sent);
    }

    private void beginSplitStringAcc(String beginString) {
        int flag = beginString.lastIndexOf("&&");
        splitStringAcc(beginString, 0, flag);
    }

    private int splitStringAcc(String acc, int id, int flagIndex) {
        int tmp = id;
        int mid = 0;
        mid = acc.indexOf("&&", id);
        String first = acc.substring(tmp, mid);
        this.resultSplit.add(first);
        if (mid != flagIndex) {
            return (splitStringAcc(acc, (mid + 2), flagIndex));
        } else {
            return 0;
        }
    }

    private void beginSplitStringSeance(String beginString) {
        int flag = beginString.lastIndexOf("&&");
        splitStringSeance(beginString, 0, flag);
    }

    private int splitStringSeance(String acc, int id, int flagIndex) {
        int tmp = id;
        int mid = 0;
        mid = acc.indexOf("&&", id);
        String first = acc.substring(tmp, mid);
        this.resultSplit.add(first);
        if (mid != flagIndex) {
            return (splitStringSeance(acc, (mid + 2), flagIndex));
        } else {
            return 0;
        }
    }

    void adminAutorization() throws SQLException {
        String outline = null;
        this.resultSplit = new ArrayList<>();
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        beginSplitStringAcc(get);
        Account obj = new Account(this.resultSplit.get(0), this.resultSplit.get(1));
        openDatabase();
        ResultSet admins = getDatabase("SELECT * FROM mytheatre.admin");
        try {
            if (!admins.isBeforeFirst()) {
                outline = "false";
            } else {
                while (admins.next()) {
                    if (obj.getLogin().equals(admins.getString("login"))
                            && obj.getPassword().equals(admins.getString("password"))
                    ) {
                        outline = "true";
                        break;
                    } else {
                        outline = "false";
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        printStream.println(outline);
    }

    void addAccUser() {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<User>() {
        }.getType();
        User user = g.fromJson(get, Tip);
        String query = String.format("INSERT INTO mytheatre.user " +
                        "(login, password, firstname, lastname, phone) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s')",
                user.getAccount().getLogin(), user.getAccount().getPassword(),
                user.getFirstname(), user.getLastname(), user.getPhone());
        openDatabase();
        execute(query);
    }

    void addAccAdmin() {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<Account>() {
        }.getType();
        Account acc = g.fromJson(get, Tip);
        String query = String.format("INSERT INTO mytheatre.admin (login, password) " +
                "VALUES ('%s', '%s')", acc.getLogin(), acc.getPassword());
        openDatabase();
        execute(query);
    }

    void addNewSpectacle() {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<Spectacle>() {
        }.getType();
        Spectacle acc = g.fromJson(get, Tip);
        String query = String.format("INSERT INTO mytheatre.spectacle (title, duration) " +
                "VALUES ('%s', '%d')", acc.getTitle(), acc.getDuration());
        openDatabase();
        execute(query);
    }

    void addNewSeances() throws SQLException {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<Seance>() {
        }.getType();
        Seance seance = g.fromJson(get, Tip);
        openDatabase();
        ResultSet resultSet = getDatabase(String.format("SELECT id FROM spectacle WHERE title='%s'", seance.getSpectacle()));
        resultSet.next();
        int id = resultSet.getInt(1);
        String sqlst = new String(String.format("INSERT INTO seance (spectacle_id,date,time) VALUES ('%d','%s','%s:00')",
                id, seance.getDate().toString(), seance.getTime().toString()));
        execute(sqlst);
    }

    void returnSpectacles(){

    }

    void checkSameUser() {
        String outline = "false";
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDatabase();
        ResultSet users = getDatabase("SELECT * FROM mytheatre.user");
        try {
            while (users.next()) {
                if (get.equals(users.getString("login"))) {
                    outline = new String("true");
                    break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        printStream.println(outline);
    }

    void userAutorization() {
        String outline = null;
        this.resultSplit = new ArrayList<>();
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        beginSplitStringAcc(get);
        Account obj = new Account(this.resultSplit.get(0), this.resultSplit.get(1));
        openDatabase();
        ResultSet users = getDatabase("SELECT * FROM mytheatre.user");
        try {
            if (!users.isBeforeFirst()) {
                outline = "false";
            } else {
                while (users.next()) {
                    if (obj.getLogin().equals(users.getString("login"))
                            && obj.getPassword().equals(users.getString("password"))
                    ) {
                        outline = "true";
                        break;
                    } else {
                        outline = "false";
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        printStream.println(outline);
    }

    void deleteAcc() {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("DELETE FROM mytheatre.user WHERE (login = '%s')", get));
        openDatabase();
        execute(sqlst);
    }

    void delSpect() {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("DELETE FROM mytheatre.spectacle WHERE (title = '%s')", get));
        openDatabase();
        execute(sqlst);
    }

    void deleteSeance() throws SQLException {
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();

        Type Tip = new TypeToken<Seance>() {
        }.getType();
        Seance seance = g.fromJson(get, Tip);
        openDatabase();
        ResultSet resultSet = getDatabase(String.format("SELECT id FROM spectacle WHERE title='%s'", seance.getSpectacle()));
        resultSet.next();
        int id = resultSet.getInt(1);
        String sqlst = new String(String.format("DELETE FROM seance WHERE spectacle_id = %d AND date = '%s' AND time = '%s:00'",
                id, seance.getDate().toString(), seance.getTime().toString()));
        execute(sqlst);
    }

    void getAccUser() throws SQLException {
        String sqlst = new String("SELECT * FROM mytheatre.user");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<User> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Account acc = new Account();
            User user = new User();
            acc.setLogin(resultSet.getString("login"));
            acc.setPassword(resultSet.getString("password"));
            user.setAccount(acc);
            user.setLastname(resultSet.getString("lastname"));
            user.setFirstname(resultSet.getString("firstname"));
            user.setPhone(resultSet.getString("phone"));
            arrayList.add(user);
        }
        String sent = new Gson().toJson(arrayList);
        printStream.println(sent);
    }

    void editPass() {
        String getl = new String();
        String getp = new String();
        try {
            getl = bufferedReader.readLine();
            getp = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("UPDATE mytheatre.user SET password='%s' WHERE ( login ='%s')", getp, getl));
        openDatabase();
        execute(sqlst);
    }

    void editLog() {
        String getl = new String();
        String getp = new String();
        try {
            getl = bufferedReader.readLine();
            getp = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("UPDATE mytheatre.user SET login='%s' WHERE ( login ='%s')", getp, getl));
        openDatabase();
        execute(sqlst);
    }

    void editSur() {
        String getl = new String();
        String gets = new String();
        try {
            getl = bufferedReader.readLine();
            gets = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("UPDATE mytheatre.user SET lastname='%s' WHERE ( login ='%s')", gets, getl));
        openDatabase();
        execute(sqlst);
    }

    void editN() {
        String getl = new String();
        String getn = new String();
        try {
            getl = bufferedReader.readLine();
            getn = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("UPDATE mytheatre.user SET firstname='%s' WHERE ( login ='%s')", getn, getl));
        openDatabase();
        execute(sqlst);
    }

    void editMail() {
        String getl = new String();
        String getn = new String();
        try {
            getl = bufferedReader.readLine();
            getn = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = new String(String.format("UPDATE mytheatre.user SET phone='%s' WHERE ( login ='%s')", getn, getl));
        openDatabase();
        execute(sqlst);
    }


}
