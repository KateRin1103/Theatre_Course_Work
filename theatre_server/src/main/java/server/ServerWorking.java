package server;

import account.Account;
import account.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import statistics.Damages;
import statistics.Notification;
import statistics.Rating;
import statistics.Statistics;
import theatre.Booking;
import theatre.Film;
import theatre.Seance;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static dataBase.DataBase.*;
import static java.lang.Integer.parseInt;

public class ServerWorking extends Thread {

    private static Socket cl = null;
    private static final String checkAdmin = "checkAdmin";
    private static final String checkSameUser = "checkSameUser";
    private static final String addNewAccountUser = "addNewAccountUser";
    private static final String addNewAccountAdmin = "addNewAccountAdmin";
    private static final String addNewFilm = "addNewFilm";
    private static final String addNewSeances = "addNewSeances";
    private static final String checkUser = "checkUser";
    private static final String getAllSeances = "getAllSeances";
    private static final String getAllFilms = "getAllFilms";
    private static final String getAllAccountUser = "getAllAccountUser";
    private static final String getAllBookings = "getAllBookings";
    private static final String getAllBookingsToDel = "getAllBookingsToDel";
    private static final String getFilmTitles = "getFilmTitles";
    private static final String deleteSelectedAccount = "deleteSelectedAccount";
    private static final String deleteSelectedSeances = "deleteSelectedSeances";
    private static final String deleteSelectedFilm = "deleteSelectedFilm";
    private static final String deleteSelectedBooking = "deleteSelectedBooking";
    private static final String editPassword = "editPassword";
    private static final String editLogin = "editLogin";
    private static final String editSurname = "editSurname";
    private static final String editName = "editName";
    private static final String editMail = "editMail";
    private static final String end = "end";
    private static final String getAllBookingsByLogin = "getAllBookingsByLogin";
    private static final String getFreeTime = "getFreeTime";
    private static final String editDuration = "editDuration";
    private static final String editTitle = "editTitle";
    private static final String editRentalCost = "editRentalCost";
    private static final String editTime = "editTime";
    private static final String getAllNotifications = "getAllNotifications";
    private static final String deleteSelectedNotification = "deleteSelectedNotification";
    private static final String addRating = "userAddNewRating";
    private static final String getAllRatings = "getAllRatings";
    private static final String getAllRatingsByLogin = "getAllRatingsByLogin";
    private static final String deleteRating = "deleteRating";
    private static final String getAvgRating = "getAvgRating";
    private static final String getDamages = "getDamages";
    private static final String getAllNotificationsByLogin = "getAllNotificationsByLogin";
    private static final String setRequestedDelete = "setRequestedDelete";

    private static final String getSeancePlaces = "getSeancePlaces";

    private static final String addBooking = "addBooking";
    private static final String updateStatistics = "updateStatistics";
    private static final String getStatistics = "getStatistics";
    private static final String getReport = "getReport";

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
                        switch (input) {
                            case checkAdmin: {
                                adminAutorization();
                                break;
                            }
                            case checkSameUser: {
                                checkSameUser();
                                break;
                            }
                            case checkUser: {
                                userAutorization();
                                break;
                            }
                            case addNewAccountUser: {
                                addAccUser();
                                break;
                            }
                            case getAllBookingsByLogin: {
                                getAllBookingsByLogin();
                                break;
                            }
                            case getAllRatingsByLogin: {
                                getAllRatingsByLogin();
                                break;
                            }
                            case getDamages: {
                                getDamages();
                                break;
                            }
                            case deleteRating: {
                                deleteRating();
                                break;
                            }
                            case getAllSeances: {
                                getAllSeances();
                                break;
                            }
                            case addNewAccountAdmin: {
                                addAccAdmin();
                                break;
                            }
                            case getStatistics: {
                                getStatistics();
                                break;
                            }
                            case addNewFilm: {
                                addNewFilm();
                                break;
                            }
                            case getSeancePlaces: {
                                getSeancePlaces();
                                break;
                            }
                            case addNewSeances: {
                                addNewSeances();
                                break;
                            }
                            case getReport: {
                                writeInFile();
                                break;
                            }
                            case setRequestedDelete:{
                                setRequestedDelete();
                                break;
                            }
                            case addBooking: {
                                addBooking();
                                break;
                            }
                            case addRating: {
                                addRating();
                                break;
                            }
                            case getAllNotificationsByLogin: {
                                getAllNotificationsByLogin();
                                break;
                            }
                            case getAllBookingsToDel: {
                                getAllBookingsToDel();
                                break;
                            }
                            case getAllRatings: {
                                getAllRatings();
                                break;
                            }
                            case getAvgRating: {
                                getAvgRating();
                                break;
                            }
                            case getAllAccountUser: {
                                getAccUser();
                                break;
                            }
                            case getFilmTitles: {
                                getFilmTitles();
                                break;
                            }
                            case deleteSelectedAccount: {
                                deleteAcc();
                                break;
                            }
                            case deleteSelectedNotification: {
                                deleteSelectedNotification();
                                break;
                            }
                            case deleteSelectedSeances: {
                                deleteSeance();
                                break;
                            }
                            case deleteSelectedBooking: {
                                deleteSelectedBooking();
                                break;
                            }
                            case getAllNotifications: {
                                getAllNotifications();
                                break;
                            }
                            case getAllBookings: {
                                getAllBookings();
                                break;
                            }
                            case editPassword: {
                                editPass();
                                break;
                            }
                            case editLogin: {
                                editLog();
                                break;
                            }
                            case editDuration: {
                                editDuration();
                                break;
                            }
                            case editRentalCost: {
                                editRentalCost();
                                break;
                            }
                            case editTitle: {
                                editTitle();
                                break;
                            }
                            case deleteSelectedFilm: {
                                delSpect();
                                break;
                            }
                            case editSurname: {
                                editSur();
                                break;
                            }
                            case editName: {
                                editN();
                                break;
                            }
                            case editMail: {
                                editMail();
                                break;
                            }
                            case getAllFilms: {
                                getAllFilms();
                                break;
                            }
                            case getFreeTime: {
                                getFreeTime();
                                break;
                            }
                            case editTime: {
                                editTime();
                                break;
                            }
                        }
                    }
                } catch (SQLException | IOException e) {
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

    private void getAllFilms() throws SQLException {
        String sqlst = "SELECT * FROM mytheatre.film";
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Film> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Film film = new Film();
            film.setTitle(resultSet.getString("title"));
            film.setDuration(resultSet.getInt("duration"));
            film.setRent(resultSet.getInt("rental_price_per_month"));
            arrayList.add(film);
        }
        String sent = new Gson().toJson(arrayList);
        printStream.println(sent);
    }

    private void getAllSeances() throws SQLException {
        String sqlst = String.format("SELECT s.title, se.date, se.time, se.price " +
                "FROM seance se INNER JOIN film s on se.film_id = s.id");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Seance> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Seance seance = new Seance();
            seance.setFilm(resultSet.getString("title"));
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

    private void getFilmTitles() throws SQLException {
        String sqlst = String.format("SELECT title FROM film ORDER BY title");
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

    private void getAllBookingsToDel() throws SQLException {
        openDatabase();
        ResultSet resultSet = getDatabase(String.format("SELECT u.login AS `log`, sp.title, se.time, se.date, p.place, p.row\n" +
                "FROM booking b\n" +
                "         INNER JOIN place p on b.place_id = p.id\n" +
                "         INNER JOIN user u on b.user_id = u.id\n" +
                "         INNER JOIN seance se on b.seance_id = se.id\n" +
                "         INNER JOIN film sp on se.film_id = sp.id WHERE `return`=1"));
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

    private void getAllBookings() throws SQLException {
        openDatabase();
        ResultSet resultSet = getDatabase(String.format("SELECT u.login AS `log`, sp.title, se.time, se.date, p.place, p.row\n" +
                "FROM booking b\n" +
                "         INNER JOIN place p on b.place_id = p.id\n" +
                "         INNER JOIN user u on b.user_id = u.id\n" +
                "         INNER JOIN seance se on b.seance_id = se.id\n" +
                "         INNER JOIN film sp on se.film_id = sp.id"));
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

    private void getAllNotifications() throws SQLException {
        openDatabase();
        ResultSet res = getDatabase("select login, changed, date, time from notifications join user u on u.id = notifications.user_id");
        ArrayList<Notification> arrayList = new ArrayList<>();
        while (res.next()) {
            Notification notification = new Notification();
            notification.setLogin(res.getString("login"));
            notification.setChanged(res.getString("changed"));
            notification.setDate(res.getDate("date").toLocalDate());
            notification.setTime(res.getTime("time").toLocalTime());
            arrayList.add(notification);
        }
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd");
        Gson gson = gb.create();
        printStream.println(gson.toJson(arrayList));
    }

    private void getAllNotificationsByLogin() throws SQLException {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDatabase();
        ResultSet res = getDatabase("select login, changed, date, time from notifications join user u on u.id = notifications.user_id where login = '" + get + "'");
        ArrayList<Notification> arrayList = new ArrayList<>();
        while (res.next()) {
            Notification notification = new Notification();
            notification.setLogin(res.getString("login"));
            notification.setChanged(res.getString("changed"));
            notification.setDate(res.getDate("date").toLocalDate());
            notification.setTime(res.getTime("time").toLocalTime());
            arrayList.add(notification);
        }
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd");
        Gson gson = gb.create();
        printStream.println(gson.toJson(arrayList));
    }

    private void getDamages() throws SQLException {
        openDatabase();
        ResultSet res = getDatabase("select title, sum(price) as sold, sum(price) - rental_price_per_month as income\n" +
                "from booking\n" +
                "         inner join seance s on booking.seance_id = s.id\n" +
                "         inner join film f on s.film_id = f.id\n" +
                "where date >= current_date - interval 1 month\n" +
                "group by title");
        ArrayList<Damages> arrayList = new ArrayList<>();
        while (res.next()) {
            Damages dam = new Damages();
            dam.setTitle(res.getString("title"));
            dam.setSold(res.getInt("sold"));
            dam.setIncome(res.getInt("income"));
            arrayList.add(dam);
        }
        Gson gson = new Gson();
        String sent = gson.toJson(arrayList);
        printStream.println(sent);
    }

    private void getAllBookingsByLogin() throws SQLException {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("SELECT u.login AS `log`, sp.title, se.time, se.date, p.place, p.row " +
                "FROM booking b " +
                "INNER JOIN place p on b.place_id = p.id " +
                "INNER JOIN user u on b.user_id = u.id " +
                "INNER JOIN seance se on b.seance_id = se.id " +
                "INNER JOIN film sp on se.film_id = sp.id " +
                "WHERE u.login='" + get + "'");
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

    private void getStatistics() throws SQLException {
        updateStatistics();
        String sqlst = String.format("select * " +
                "from statistics " +
                "         inner join film s on statistics.film_id = s.id");
        openDatabase();
        ResultSet resultSet = getDatabase(sqlst);
        ArrayList<Statistics> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            Statistics seance = new Statistics();
            seance.setTitle(resultSet.getString("title"));
            seance.setDate(resultSet.getDate("date").toLocalDate());
            seance.setGoal(resultSet.getInt("goal"));
            seance.setNumberOfShows(resultSet.getInt("number_of_shows"));
            seance.setResult(resultSet.getInt("result"));
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
                            && obj.getPassword().equals(admins.getString("password"))) {
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

    void addNewFilm() {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<Film>() {
        }.getType();
        Film acc = g.fromJson(get, Tip);
        String query = String.format("INSERT INTO mytheatre.film (title, duration,rental_price_per_month) " +
                "VALUES ('%s',%d,%d)", acc.getTitle(), acc.getDuration(), acc.getRent());
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
        ResultSet resultSet = getDatabase(String.format("SELECT id FROM film WHERE title='%s'", seance.getFilm()));
        resultSet.next();
        int id = resultSet.getInt(1);
        String sqlst = String.format("INSERT INTO seance (film_id,date,time,price) VALUES ('%d','%s','%s:00','%d')",
                id, seance.getDate().toString(), seance.getTime().toString(), seance.getPrice());
        execute(sqlst);
    }

    void addBooking() throws SQLException {
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
        ResultSet resultSet = getDatabase(String.format("SELECT id FROM user WHERE login='%s'", booking.getLogin()));
        resultSet.next();
        int id = resultSet.getInt(1);

        String str = String.format("SELECT seance.id FROM seance inner join film s on seance.film_id = s.id" +
                        " WHERE date='%s' AND time='%s:00' AND title='%s'",
                booking.getDate().toString(), booking.getTime().toString(), booking.getTitle());
        ResultSet resultSeance = getDatabase(str);
        resultSeance.next();
        int idSeance = resultSeance.getInt(1);

        ResultSet resultPlace = getDatabase(String.format("SELECT id FROM place WHERE `row`=%d AND `place`=%d",
                booking.getRow(), booking.getPlace()));
        resultPlace.next();
        int idPlace = resultPlace.getInt(1);

        String sqlst = String.format("INSERT INTO booking (seance_id,user_id,place_id) VALUES ('%d','%d','%d')",
                idSeance, id, idPlace);
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

    void delSpect() throws SQLException {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultSet users = getDatabase(String.format("select distinct u.id from booking\n" +
                "join user u on u.id = booking.user_id\n" +
                "join seance s on s.id = booking.seance_id\n" +
                "join film f on f.id = s.film_id\n" +
                "where title = '" + get + "'"));
        String message = "ОТМЕНА! К сожалению прокат фильма "
                + get + " прекращён. Забронированные билеты возвращены. Приносим свои извинения!";
        while (users.next()) {
            execute(String.format("INSERT INTO notifications (user_id, changed, `date`, `time`) values (%d,'%s',CURRENT_DATE(),CURRENT_TIME())",
                    users.getInt("u.id"), message));
        }
        String sqlst = String.format("DELETE FROM mytheatre.film WHERE (title = '%s')", get);
        openDatabase();
        execute(sqlst);
    }

    private void addRating() {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<Rating>() {
        }.getType();
        Rating rating = g.fromJson(get, Tip);
        String str = String.format("INSERT INTO rating (user_id,film_id,rating,comment) " +
                        "VALUES ((SELECT id FROM user WHERE login='%s')," +
                        " (SELECT id FROM film WHERE title='%s'), %d, '%s')",
                rating.getLogin(), rating.getFilm(), (int) rating.getRating(), rating.getComment());
        execute(str);
    }

    private void getAllRatings() throws SQLException {
        openDatabase();
        ResultSet res = getDatabase("select login, title, comment, rating\n" +
                "from rating\n" +
                "         inner join film f on rating.film_id = f.id\n" +
                "         inner join user u on rating.user_id = u.id");
        ArrayList<Rating> ratings = new ArrayList<>();
        while (res.next()) {
            ratings.add(new Rating(res.getString("login"), res.getString("title"),
                    res.getFloat("rating"), res.getString("comment")));
        }
        printStream.println(new Gson().toJson(ratings));
    }

    private void getAllRatingsByLogin() throws SQLException {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDatabase();
        ResultSet res = getDatabase("select login, title, comment, rating\n" +
                "from rating\n" +
                "         inner join film f on rating.film_id = f.id\n" +
                "         inner join user u on rating.user_id = u.id where login='" + get + "'");
        ArrayList<Rating> ratings = new ArrayList<>();
        while (res.next()) {
            ratings.add(new Rating(res.getString("login"), res.getString("title"),
                    res.getFloat("rating"), res.getString("comment")));
        }
        printStream.println(new Gson().toJson(ratings));
    }

    private void deleteRating() {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();
        Type Tip = new TypeToken<Rating>() {
        }.getType();
        Rating rating = g.fromJson(get, Tip);
        execute(String.format("DELETE FROM rating WHERE " +
                        "user_id=(SELECT id FROM user WHERE login='%s') " +
                        "AND film_id=(SELECT id FROM film WHERE title='%s')" +
                        "AND rating=%d AND comment='%s'",
                rating.getLogin(), rating.getFilm(), rating.getRating(), rating.getComment()));
    }

    private void deleteSelectedNotification() {
        String get = "";
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson g = new Gson();

        Type Tip = new TypeToken<Notification>() {
        }.getType();
        Notification notif = g.fromJson(get, Tip);
        openDatabase();
        execute(String.format("delete from notifications where time='%s' and date='%s'",
                notif.getTime().toString(), notif.getDate().toString()));
    }

    private void getAvgRating() throws SQLException {
        openDatabase();
        ResultSet res = getDatabase("SELECT title, AVG(rating) as rating\n" +
                "FROM rating\n" +
                "join film f on f.id = rating.film_id\n" +
                "GROUP BY title;");
        ArrayList<Rating> ratings = new ArrayList<>();
        while (res.next()) {
            ratings.add(new Rating("", res.getString("title"),
                    res.getFloat("rating"), ""));
        }
        printStream.println(new Gson().toJson(ratings));
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
        String str = String.format("select distinct u.id from booking " +
                        "join user u on u.id = booking.user_id " +
                        "join seance s on s.id = booking.seance_id " +
                        "join film f on f.id = s.film_id " +
                        "where s.id = (select id FROM seance WHERE f.title = '%s' AND date = '%s' AND time = '%s:00')"
                , seance.getFilm(), seance.getDate().toString(), seance.getTime().toString());
        ResultSet users = getDatabase(str);
        String message = "ОТМЕНА сеанса фильма " + seance.getFilm()
                + ", который должен был пройти " + seance.getDate().toString()
                + " в " + seance.getTime().toString()
                + ". Приносим извинения за неудобства!";
        while (users.next()) {
            execute(String.format("insert into notifications (user_id, changed, date, time) values ('%d','%s',CURRENT_DATE(),CURRENT_TIME())",
                    users.getInt("u.id"), message));
        }
        ResultSet resultSet = getDatabase(String.format("SELECT id FROM film WHERE title='%s'", seance.getFilm()));
        resultSet.next();
        int id = resultSet.getInt(1);
        String sqlst = String.format("DELETE FROM seance WHERE film_id = %d AND date = '%s' AND time = '%s:00'",
                id, seance.getDate().toString(), seance.getTime().toString());
        execute(sqlst);
    }

    void setRequestedDelete() {
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
        String string = "UPDATE booking SET `return`=1 WHERE user_id=(SELECT id FROM user WHERE login = '" + booking.getLogin() + "')" +
                " AND place_id=(SELECT id FROM place WHERE `row`="+booking.getRow()+" AND `place`="+booking.getPlace()+") " +
                " AND seance_id=(SELECT id FROM seance WHERE date='"+booking.getDate()+"' " +
                " AND time='"+booking.getTime()+":00')";
        execute(string);
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

        String message = "ВОЗВРАТ билета на сеанс " + booking.getTitle()
                + " , который должен был пройти " + booking.getDate().toString()
                + " в " + booking.getTime().toString()
                + " оформлен. Ждём вас снова!";

        ResultSet resultSet = getDatabase(String.format("SELECT id FROM film WHERE title='%s'", booking.getTitle()));
        resultSet.next();
        int id = resultSet.getInt(1);

        ResultSet resultSetPlace = getDatabase(String.format("SELECT id FROM `place` WHERE `place`='%d' AND `row`='%d'",
                booking.getPlace(), booking.getRow()));
        resultSetPlace.next();
        int placeId = resultSetPlace.getInt(1);

        ResultSet resultSetDate = getDatabase(String.format("SELECT id FROM `seance` WHERE `date`='%s:00' AND `time`='%s' AND `film_id`=%d",
                booking.getDate().toString(), booking.getTime().toString(), id));
        resultSetDate.next();
        int seanceId = resultSetDate.getInt(1);

        ResultSet resultSetUser = getDatabase(String.format("SELECT id FROM `user` WHERE `login`='%s'", booking.getLogin()));
        resultSetUser.next();
        int userId = resultSetUser.getInt(1);

        String sqlst = String.format("DELETE FROM booking WHERE seance_id = %d AND user_id = %d AND place_id = %d",
                seanceId, userId, placeId);

        execute(String.format("insert into notifications (user_id, changed, date, time) values ('%d','%s',CURRENT_DATE(),CURRENT_TIME())",
                userId, message));

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

    private void getFreeTime() throws SQLException {
        String getDate = "";
        try {
            getDate = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDatabase();
        ResultSet resultSet = getDatabase("SELECT time\n" +
                "FROM seance_time\n" +
                "WHERE time NOT IN (SELECT TIME\n" +
                "               FROM seance\n" +
                "                        JOIN film f ON f.id = seance.film_id\n" +
                "               WHERE date = '" + getDate + "');");
        ArrayList<LocalTime> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(resultSet.getTime("time").toLocalTime());
        }
        String sent = new Gson().toJson(arrayList);
        printStream.println(sent);
    }

    void getSeancePlaces() throws SQLException {
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

        String str = "SELECT seance.id FROM seance inner join film s on seance.film_id = s.id" +
                " WHERE date='" + seance.getDate() + "' AND time='" + seance.getTime() + ":00' AND title='" + seance.getFilm() + "'";
        ResultSet resultSet = getDatabase(str);
        resultSet.next();
        int seanceID = resultSet.getInt(1);

        ResultSet placesID = getDatabase("select place_id " +
                "from booking " +
                "inner join place p on p.id = booking.place_id " +
                "where seance_id = " + seanceID);
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (placesID.next()) {
            arrayList.add(placesID.getInt(1));
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

    void editDuration() {
        String getl = "";
        String gets = "";
        try {
            getl = bufferedReader.readLine();
            gets = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.film SET duration=%d WHERE ( title ='%s')", parseInt(gets), getl);
        openDatabase();
        execute(sqlst);
    }

    void editRentalCost() {
        String getl = "";
        String gets = "";
        try {
            getl = bufferedReader.readLine();
            gets = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.film SET rental_price_per_month=%d WHERE ( title ='%s')", parseInt(gets), getl);
        openDatabase();
        execute(sqlst);
    }

    void editTitle() {
        String getl = "";
        String gets = "";
        try {
            getl = bufferedReader.readLine();
            gets = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE mytheatre.film SET title='%s' WHERE ( title ='%s')", gets, getl);
        openDatabase();
        execute(sqlst);
    }

    void editTime() throws SQLException {
        String getl = "";
        String getn = "";
        try {
            getl = bufferedReader.readLine();
            getn = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sqlst = String.format("UPDATE seance SET time='%s' WHERE ( time ='%s')",
                getn, getl);
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

    void updateStatistics() throws SQLException { //обновление данных за квартал
        openDatabase();
        execute("TRUNCATE TABLE statistics");
        execute("TRUNCATE TABLE results");
        LocalDate date = null;
        int day = LocalDate.now().getDayOfMonth();

        //получение списка id спектаклей
        String selectSQLOnTitle = "select distinct s.id, s.title " +
                "from seance " +
                "inner join film s on seance.film_id = s.id";
        ResultSet result = getDatabase(selectSQLOnTitle);

        if (result == null)
            return;
        try {
            while (result.next()) {
                for (int i = 1; i < 4; i++) {
                    String currentDate = date.now().plusDays(-day + 1).plusMonths(-i).toString();

                    //выбор количества сеансов конкретного спектакля за определённый месяц,
                    // целевого и итогового значения купленных билетов
                    ResultSet resNumber = getDatabase(String.format("select COUNT((MONTH(date))) " +
                                    "from seance " +
                                    "where month(date)=month('%s') and film_id=%d",
                            currentDate, result.getInt("id")));
                    resNumber.next();

                    //выбор количества купленных билетов конкретного спектакля за определённый месяц
                    ResultSet res = getDatabase(String.format("select count(booking.id) " +
                                    "from booking " +
                                    "inner join seance s on s.id = booking.seance_id " +
                                    "inner join film s2 on s.film_id = s2.id " +
                                    "where month(s.date)=month('%s') and s.id=booking.seance_id and s.film_id=s2.id " +
                                    "AND title='%s'",
                            currentDate, result.getString("s.title")));
                    res.next();

                    execute(String.format("insert into statistics (film_id, date, number_of_shows, goal, result) " +
                                    "value (%d, '%s', %d, %d, %d) ",
                            result.getInt("id"), currentDate,
                            resNumber.getInt(1), resNumber.getInt(1) * 20,
                            res.getInt(1)));

                }
            }
            //обновление эффективности ПРИБЫЛИ
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
            ResultSet sp = getDatabase(String.format("select sum(efficiency) from statistics where film_id=%d",
                    result2.getInt(1)));
            sp.next();

            StringBuilder str = new StringBuilder();
            str.append("insert into results (film_id,first_param,second_param,third_param,specific_efficiency,risk) value (");
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
                    + Math.pow(three - sumDivThree, 2)) / 2));
            str.append(")");
            execute(str.toString());
        }
    }

    void writeInFile() throws SQLException, IOException {
        openDatabase();
        List<String> data = new ArrayList<>();
        List<String> risks = new ArrayList<>();
        ResultSet res = getDatabase("select *, title " +
                "from statistics " +
                "inner join film s on statistics.film_id = s.id");
        while (res.next()) {
            String title = res.getString("title");
            String date = res.getDate("date").toLocalDate().toString();
            int number = res.getInt("number_of_shows");
            int goal = res.getInt("goal");
            int result = res.getInt("result");
            double efficiency = res.getDouble("efficiency");
            data.add(String.format("|%25s|%15s|%15d|%10d|%10d|%16f|%27s|",
                    title, date, number, goal, result, efficiency, 1 - efficiency));
        }
        ResultSet report = getDatabase("select *, title " +
                "from results " +
                "inner join film s on results.film_id = s.id");
        while (report.next()) {
            String title = report.getString("title");
            double efficiency = report.getDouble("specific_efficiency");
            double risk = report.getDouble("risk");
            risks.add(String.format("|%31s|%31f|%60f|", title, efficiency, risk));
        }
        File file = new File("D:/Report.txt");
        file.delete();
        if (file.delete()) {
            file.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
        out.flush();
        out.write(String.format("\n           ОТЧЕТ ПРЕДЫДУЩЕГО КВАРТАЛА ПО ПРОДАЖАМ БИЛЕТОВ ОТ %s\n\n",
                LocalDate.now().toString()));
        String tableStr = "+----------------------------------------------------------------------------------------------------------------------------+\n";
        out.write(tableStr);
        out.write(String.format("|%25s|%15s|%15s|%10s|%10s|%16s|%27s|\n", "Название", "Месяц", "Число показов", "Цель", "Итог", "Уд.прибыль", "Уд.убытки правообладателя"));
        out.write(tableStr);
        for (Object o : data) {
            out.write(o.toString());
            out.newLine();
        }
        out.write(tableStr);

        out.write("  По результатам анализа сведений об эффективности каждого фильма в прошлом была\nоценена удельная прибыль каждой единицы.\n");
        out.write("  В качестве оценки эффективности решений (фильмов для показа) была использована\n" +
                "средняя удельная эффективность. Найдена оценка эффективности показов:");
        out.newLine();

        out.write(String.format("\n           ОЦЕНКА РИСКА ПО ПРОДАЖАМ БИЛЕТОВ НА ФИЛЬМЫ ОТ %s",
                LocalDate.now().toString()));
        out.newLine();
        out.write(tableStr);
        out.write(String.format("|%31s|%31s|%60s|\n", "Название", "Удельная эффективность", "Оценка риска"));
        out.write(tableStr);
        for (Object o : risks) {
            out.write(o.toString());
            out.newLine();
        }
        out.write(tableStr);

        out.write("По данным отчёта можно оценить эффективность проведения заданного количества\n " +
                "показов ранеее для дальнейшего принятия решений об увеличении, уменьшении либо сохранении \n" +
                "количества показов представленных спектаклей.");
        ResultSet max = getDatabase("select title\n" +
                "from results\n" +
                "inner join film s on results.film_id = s.id\n" +
                "where risk=(select max(risk)from results)");
        max.next();
        String maxTitle = max.getString(1);

        ResultSet min = getDatabase("select title\n" +
                "from results\n" +
                "inner join film s on results.film_id = s.id\n" +
                "where risk=(select min(risk)from results)");
        min.next();
        String minTitle = min.getString(1);

        out.newLine();
        out.newLine();
        out.newLine();
        out.write("                                  ВЫВОД\n");
        out.newLine();
        out.write("    По результатам оценки рисков для повышения эффективности " +
                "работы\nучреждения рекомендовано сокращение ежемесячного количества" +
                "показов\nспектакля '" + maxTitle + "' и увеличение количества показов спектакля '" + minTitle + "'.");

        out.close();
        Desktop d = Desktop.getDesktop();
        d.open(file);
    }
}
