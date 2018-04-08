package webx.huceal;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:tcp://localhost/~/allomovieDB";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static DataSource dataSource;

    private DataSource() {

    }

    private static void initTableAvis() {
        Connection con = null;
        Statement stmt = null;
        String tableCreateQuery = "CREATE TABLE IF NOT EXISTS Avis (ID INTEGER AUTO_INCREMENT PRIMARY KEY, FilmID VARCHAR(10), Note INTEGER(1), Commentaire VARCHAR(500))";
        try {
            con = getDBConnection();
            stmt = con.createStatement();
            stmt.execute(tableCreateQuery);
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            if (con != null && stmt != null) {
                try {
                    con.close();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getDBConnection() {
        if (dataSource == null) {
            dataSource = new DataSource();
            initTableAvis();
        }
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Server embeddedServer = Server.createTcpServer(new String[] { "-tcpAllowOthers", "-web" }).start();
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
