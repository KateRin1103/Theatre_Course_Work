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
import java.time.LocalDate;
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
    private static final String getSpectacleTitles = "getSpectacleTitles";
    private static final String deleteSelectedAccount = "deleteSelectedAccount";
    private static final String deleteSelectedSeances = "deleteSelectedSeances";
    private static final String deleteSelectedSpectacle = "deleteSelectedSpectacle";
    private static final String deleteSelectedBooking = "deleteSelectedBooking";
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
    private static final String updateStatistics = "updateStatistics";
    private static final String getStatistics = "getStatistics";
    private static final String getResult = "getResult";
    private static final String accReport = "accReport";

    static Logger logger = Logger.getLogger(ServerWorking.class);

    public ServerWorking(Socket cl) {
        ServerWorking.cl = cl;
    }


    public ServerWorking() {
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
                String input = null;//???????????????? ???????????????????? ?? ??????????????
                try {
                    try {
                        input = bufferedReader.readLine();
                    } catch (SocketException ex) {
                        logger.info("???????????? ????????????????????");
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
                        if (input.equals(getSpectacleTitles)) {
                            getSpectacleTitles();
                        }
                        if (input.equals(deleteSelectedAccount)) {
                            deleteAcc();
                        }
                        if (input.equals(deleteSelectedSeances)) {
                            deleteSeance();
                        }
                        if (input.equals(deleteSelectedBooking)) {
                            deleteSelectedBooking();
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
        String sqlst = "SELECT * FROM mytheatre.spectacle";
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
        String sqlst = String.format("SELECT s.title, se.date, se.time, se.price " +
                "FROM seance se INNER JOIN spectacle s on se.spectacle_id = s.id");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Seance> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Seance seance = new Seance();
            seance.setSpectacle(resultSet.getString("title"));
            seance.setDate(resultSet.getDate("date").toLocalDate());
            seance.setTime(resultSet.getTime("time").toLocalTime());
            seance.setPrice(resultSet.getInt("price"));
            arrayList.add(seance);
        }
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd");
        Gson gson = gb.create();
        String sent = gson.toJson(arrayList);
        printStream.println(sent);
    }

    private void getSpectacleTitles() throws SQLException {
        String sqlst = String.format("SELECT title FROM spectacle ORDER BY title");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<String> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(resultSet.getString("title"));
        }
        Gson gson = new Gson();
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

    void adminAutorization() throws SQLException {
        String outline = null;
        this.resultSplit = new ArrayList<>();
        String get = "";
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
        String get = "";
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
        String get = "";
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
        String get = "";
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
        String get = "";
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
        String sqlst = String.format("INSERT INTO seance (spectacle_id,date,time,price) VALUES ('%d','%s','%s:00','%d')",
                id, seance.getDate().toString(), seance.getTime().toString(), seance.getPrice());
        execute(sqlst);
    }

    void checkSameUser() {
        String outline = "false";
        String get = "";
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
                    outline = "true";
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
        String get = "";
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
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("DELETE FROM mytheatre.user WHERE (login = '%s')", get);
        openDatabase();
        execute(sqlst);
    }

    void delSpect() {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("DELETE FROM mytheatre.spectacle WHERE (title = '%s')", get);
        openDatabase();
        execute(sqlst);
    }

    void deleteSeance() throws SQLException {
        String get = "";
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
        String sqlst = String.format("DELETE FROM seance WHERE spectacle_id = %d AND date = '%s' AND time = '%s:00'",
                id, seance.getDate().toString(), seance.getTime().toString());
        execute(sqlst);
    }

    void deleteSelectedBooking() throws SQLException {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();

        Type Tip = new TypeToken<Booking>() {
        }.getType();
        Booking booking = g.fromJson(get, Tip);
        openDatabase();

        ResultSet resultSet = getDatabase(String.format("SELECT id FROM spectacle WHERE title='%s'", booking.getTitle()));
        resultSet.next();
        int id = resultSet.getInt(1);

        ResultSet resultSetPlace = getDatabase(String.format("SELECT id FROM `place` WHERE `place`='%d' AND `row`='%d'",
                booking.getPlace(), booking.getRow()));
        resultSetPlace.next();
        int placeId = resultSetPlace.getInt(1);

        ResultSet resultSetDate = getDatabase(String.format("SELECT id FROM `seance` WHERE `date`='%s:00' AND `time`='%s' AND `spectacle_id`=%d",
                booking.getDate().toString(), booking.getTime().toString(), id));
        resultSetDate.next();
        int seanceId = resultSetDate.getInt(1);

        ResultSet resultSetUser = getDatabase(String.format("SELECT id FROM `user` WHERE `login`='%s'", booking.getLogin()));
        resultSetUser.next();
        int userId = resultSetUser.getInt(1);

        String sqlst = String.format("DELETE FROM booking WHERE seance_id = %d AND user_id = %d AND place_id = %d",
                seanceId, userId, placeId);

        execute(sqlst);
    }

    void getAccUser() throws SQLException {
        String sqlst = "SELECT * FROM mytheatre.user";
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
        String getl = "";
        String getp = "";
        try {
            getl = bufferedReader.readLine();
            getp = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.user SET password='%s' WHERE ( login ='%s')",
                getp, getl);
        openDatabase();
        execute(sqlst);
    }

    void editLog() {
        String getl = "";
        String getp = "";
        try {
            getl = bufferedReader.readLine();
            getp = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.user SET login='%s' WHERE ( login ='%s')",
                getp, getl);
        openDatabase();
        execute(sqlst);
    }

    void editSur() {
        String getl = "";
        String gets = "";
        try {
            getl = bufferedReader.readLine();
            gets = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.user SET lastname='%s' WHERE ( login ='%s')", gets, getl);
        openDatabase();
        execute(sqlst);
    }

    void editN() {
        String getl = "";
        String getn = "";
        try {
            getl = bufferedReader.readLine();
            getn = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.user SET firstname='%s' WHERE ( login ='%s')",
                getn, getl);
        openDatabase();
        execute(sqlst);
    }

    void editMail() {
        String getl = "";
        String getn = "";
        try {
            getl = bufferedReader.readLine();
            getn = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.user SET phone='%s' WHERE ( login ='%s')", getn, getl);
        openDatabase();
        execute(sqlst);
    }

    void updateStatistics() throws SQLException { //???????????????????? ???????????? ???? ??????????????
        openDatabase();
        execute("TRUNCATE TABLE statistics");
        execute("TRUNCATE TABLE results");
        LocalDate date = null;
        int day = LocalDate.now().getDayOfMonth();

        //?????????????????? ???????????? id ????????????????????
        String selectSQLOnTitle = "select distinct s.id, s.title " +
                "from seance " +
                "inner join spectacle s on seance.spectacle_id = s.id";
        ResultSet result = getDatabase(selectSQLOnTitle);

        if (result == null)
            return;
        try {
            while (result.next()) {
                for (int i = 1; i < 4; i++) {
                    String currentDate = date.now().plusDays(-day + 1).plusMonths(-i).toString();

                    //?????????? ???????????????????? ?????????????? ?????????????????????? ?????????????????? ???? ???????????????????????? ??????????,
                    // ???????????????? ?? ?????????????????? ???????????????? ?????????????????? ??????????????
                    ResultSet resNumber = getDatabase(String.format("select COUNT((MONTH(date))) " +
                                    "from seance " +
                                    "where month(date)=month('%s') and spectacle_id=%d",
                            currentDate, result.getInt("id")));
                    resNumber.next();

                    //?????????? ???????????????????? ?????????????????? ?????????????? ?????????????????????? ?????????????????? ???? ???????????????????????? ??????????
                    ResultSet res = getDatabase(String.format("select count(booking.id) " +
                                    "from booking " +
                                    "inner join seance s on s.id = booking.seance_id " +
                                    "inner join spectacle s2 on s.spectacle_id = s2.id " +
                                    "where month(s.date)=month('%s') and s.id=booking.seance_id and s.spectacle_id=s2.id " +
                                    "AND title='%s'",
                            currentDate, result.getString("s.title")));
                    res.next();

                    execute(String.format("insert into statistics (spectacle_id, date, number_of_shows, goal, result) " +
                                    "value (%d, '%s', %d, %d, %d) ",
                            result.getInt("id"), currentDate,
                            resNumber.getInt(1), resNumber.getInt(1) * 20,
                            res.getInt(1)));

                }
            }
            //???????????????????? ??????????????????????????
            ResultSet resEf = getDatabase("select ifnull(result/goal, 0.0) as eff from statistics");
            int i = 1;
            while (resEf.next()) {
                // System.out.println(resEf.getDouble(1));
                execute("update statistics set efficiency=" + resEf.getDouble(1) + " where id=" + i);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ResultSet result2 = getDatabase(selectSQLOnTitle);
        ResultSet parametres = getDatabase("select efficiency from statistics");
        while (result2.next()) {
            ResultSet sp = getDatabase(String.format("select sum(efficiency) from statistics where spectacle_id=%d",
                    result2.getInt(1)));
            sp.next();

            StringBuilder str = new StringBuilder();
            str.append("insert into results (spectacle_id,first_param,second_param,third_param,specific_efficiency,risk) value (");
            str.append(result2.getInt("id"));
            str.append(",");
            parametres.next();
            double one = parametres.getDouble(1);

            str.append(parametres.getDouble(1));
            str.append(",");
            parametres.next();
            double two = parametres.getDouble(1);

            str.append(parametres.getDouble(1));
            str.append(",");
            parametres.next();
            double three = parametres.getDouble(1);

            str.append(parametres.getDouble(1));
            str.append(",");
            double sumDivThree = (one + two + three) / 3;
            str.append(sumDivThree);
            str.append(",");
            str.append(((Math.pow(one - sumDivThree, 2)
                    + Math.pow(two - sumDivThree, 2)
                    + Math.pow(three - sumDivThree, 2)) / 3));
            str.append(")");
            execute(str.toString());
        }
    }
}
