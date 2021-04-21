package serverProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerWorking extends Thread {

    private static Socket cl = null;
    private static final String checkAdmin = "checkAdmin";
    private static final String checkSameUser = "checkSameUser";
    private static final String addNewAccount = "addNewAccount";
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

    public ServerWorking(Socket cl) {
        this.cl = cl;
    }

    BufferedReader bufferedReader;
    PrintStream printStream;

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
                        System.out.println("Клиент отключился");
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
                            // CheckSameUs();
                        }
                        if (input.equals(checkUser)) {
                            // userAutorization();
                        }
                        if (input.equals(addNewAccount)) {
                            // addAcc();
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


}
