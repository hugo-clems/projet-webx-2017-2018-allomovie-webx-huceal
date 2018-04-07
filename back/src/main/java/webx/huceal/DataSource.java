package webx.huceal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/allomovie";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static DataSource dataSource;

    private DataSource() {

    }

    public void initDatabase() throws SQLException {
        Connection con = null;
        Statement stmt = null;
        String tableCreateQuery = "CREATE TABLE IF NOT EXISTS Avis (FilmID VARCHAR(10) PRIMARY KEY, Note INTEGER(1), Commentaire VARCHAR(500))";
        try {
            con = getDBConnection();
            stmt = con.createStatement();
            stmt.execute(tableCreateQuery);
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            if (con != null && stmt != null) {
                con.close();
                stmt.close();
            }
        }
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
