package server;

import junit.framework.TestCase;

import java.sql.SQLException;

public class ServerWorkingTest extends TestCase {

    public void testUpdateStatistics() throws SQLException {
        ServerWorking serverWorking = new ServerWorking();
        serverWorking.updateStatistics();
    }
}