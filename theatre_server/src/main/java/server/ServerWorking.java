package server;

import account.Account;
import account.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

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
    private static final String checkUser = "checkUser";
    private static final String getAllAccount = "getAllAccount";
    private static final String deleteSelectedAccount = "deleteSelectedAccount";
    private static final String editPassword = "editPassword";
    private static final String editLogin = "editLogin";
    private static final String editSurname = "editSurname";
    private static final String editName = "editName";
    private static final String end = "end";
    private static final String addSpectacle = "addSpectacle";
    private static final String getAllSpectacles = "getAllSpectacles";
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
                            // adminAutorization();
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
                        if (input.equals(addNewAccountAdmin)) {
                            addAccAdmin();
                        }
                        if (input.equals(getAllAccount)) {
                            //  getAcc();
                        }
                        if (input.equals(deleteSelectedAccount)) {
                            // deleteAcc();
                        }
                        if (input.equals(editPassword)) {
                            // editPass();
                        }
                        if (input.equals(editLogin)) {
                            //  editLog();
                        }
                        if (input.equals(editSurname)) {
                            //  editSur();
                        }
                        if (input.equals(editName)) {
                            // editN();
                        }
                        if (input.equals(addSpectacle)) {
                            //addSpectacle();
                        }
                        if (input.equals(getAllSpectacles)) {
                            // getAllSpectacles();
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
                } catch (NullPointerException ex) {
                }
            }
        } //catch (SQLException e1) {
        // e1.printStackTrace();
        // }
        finally {
            try {
                bufferedReader.close();
                printStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Server.connectionNumber--;
        }
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
        String sqlst = "INSERT INTO mytheatre.user (login, password, firstname, lastname, phone)" +
                " VALUES ('" + user.getAccount().getLogin() + "', '" + user.getAccount().getPassword()
                + "', '" + user.getFirstname() + "', '" +
                user.getLastname() + "', '" + user.getPhone() + "')";
        openDatabase();
        execute(sqlst);
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
        String sqlst = new String("INSERT INTO mytheatre.admin (login, password)" +
                " VALUES ('" + acc.getLogin() + "', '" + acc.getPassword() + "');");
        openDatabase();
        execute(sqlst);
    }

    void checkSameUser() {
        String outline = "false";
        String get = new String();
        try {
            get = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String StatementAdminSql = new String("SELECT * FROM mytheatre.user");
        openDatabase();
        ResultSet users = getDatabase(StatementAdminSql);
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
        String StatementAdminSql = new String("SELECT * FROM mytheatre.user");
        openDatabase();
        ResultSet admins = getDatabase(StatementAdminSql);
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

}
