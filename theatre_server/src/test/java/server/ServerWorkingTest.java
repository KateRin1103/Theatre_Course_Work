package server;

import junit.framework.TestCase;

import java.io.IOException;
import java.sql.SQLException;

public class ServerWorkingTest extends TestCase {

    public void testUpdateStatistics() throws SQLException {
        ServerWorking serverWorking = new ServerWorking();
        serverWorking.updateStatistics();
    }

    public void testWriteInFile() throws IOException, SQLException {
        ServerWorking serverWorking = new ServerWorking();
        serverWorking.writeInFile();
    }
}