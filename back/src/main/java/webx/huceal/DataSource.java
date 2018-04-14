package webx.huceal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class DataSource.
 */
public final class DataSource {

    /**
     * Le driver de connection à la BD.
     */
    private static final String DB_DRIVER = "org.h2.Driver";

    /**
     * La connexion à la BD.
     */
    private static String DB_CONNECTION = "jdbc:h2:~/allomovieDB";

    /**
     * La dataSource.
     */
    private static DataSource dataSource;

    /**
     * Constructeur par défaut.
     */
    private DataSource() {
        // Empty
    }

    /**
     * Initialise la table Avis dans la BD.
     */
    private static void initTableAvis() {
        Connection con = null;
        Statement stmt = null;
        String tableCreateQuery = "DROP TABLE Avis";
        try {
            con = getDBConnection();
            stmt = con.createStatement();
            stmt.execute(tableCreateQuery);
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            closeConAndStmt(con, stmt);
        }
        tableCreateQuery = "CREATE TABLE IF NOT EXISTS Avis"
                + "(ID INTEGER AUTO_INCREMENT PRIMARY KEY,"
                + "FilmID VARCHAR(10),"
                + "Note INTEGER(1) CHECK (Note >= -1) AND (Note <= 5),"
                + "Commentaire VARCHAR(500))";
        try {
            con = getDBConnection();
            stmt = con.createStatement();
            stmt.execute(tableCreateQuery);
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } finally {
            closeConAndStmt(con, stmt);
        }
    }

    /**
     * Getter connection.
     * @return la connection à la BD
     */
    public static Connection getDBConnection() {
        if (dataSource == null) {
            dataSource = new DataSource();
            initTableAvis();
        }
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            return DriverManager.getConnection(DB_CONNECTION);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Ferme la connection et le statement.
     * @param con la connection à fermer
     * @param stmt le statement à fermer
     */
    public static void closeConAndStmt(final Connection con, final Statement stmt) {
        if (con != null && stmt != null) {
            try {
                con.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setDbConnection(String newDbConnection) {
        DB_CONNECTION = newDbConnection;
    }

}
