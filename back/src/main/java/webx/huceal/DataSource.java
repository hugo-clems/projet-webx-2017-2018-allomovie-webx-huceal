package webx.huceal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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
    private static String dbConnection = "jdbc:h2:~/allomovieDB";

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
     * + première ligne avis des films affichés (front)
     */
    private static void initTableAvis() {
        Connection con = null;
        Statement stmt = null;
        String tableCreateQuery = "CREATE TABLE IF NOT EXISTS Avis"
                + "(ID INTEGER AUTO_INCREMENT PRIMARY KEY,"
                + "FilmID VARCHAR(10) CHECK(REGEXP_LIKE"
                + "(FilmID, '^tt[0-9][0-9][0-9][0-9][0-9][0-9][0-9]$')),"
                + "Note INTEGER(1) CHECK (Note >= -1) AND (Note <= 5),"
                + "Commentaire VARCHAR(500),"
                + "CONSTRAINT avis_not_empty CHECK"
                + "(note != -1 OR Commentaire != ''))";
        String avisCreateQuery = "MERGE INTO Avis VALUES "
                + "(1, 'tt0076759', 5, 'Meilleur Star Wars !'),"
                + "(2, 'tt0076759', -1, 'Souvenir vraiment cool.'),"
                + "(3, 'tt0076759', 3, ''),"
                + "(4, 'tt0080684', 3, 'Bof bof...'),"
                + "(5, 'tt0080684', 4, 'Bon film familial.'),"
                + "(6, 'tt0086190', 5, 'Je retournerai le voir !'),"
                + "(7, 'tt2488496', 1, 'Une véritable perte de temps"
                + "... Remboursez-moi.'),"
                + "(8, 'tt2488496', 2, 'Je ne pense pas avoir tout"
                + " compris, bizarre et long.'),"
                + "(9, 'tt0120915', 5, 'LE film que je souhaitais"
                + " voir depuis des années.')";
        try {
            con = getDBConnection();
            if (con != null) {
                stmt = con.createStatement();
                stmt.execute(tableCreateQuery);
                if (!dbConnection.contains("Test")) {
                    stmt.execute(avisCreateQuery);
                }
            }
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
            return DriverManager.getConnection(dbConnection);
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
    public static void closeConAndStmt(final Connection con,
                                       final Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ferme le resultSet.
     * @param res le resultSet à fermer
     */
    public static void closeResultSet(final ResultSet res) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Setter dbConnection.
     * @param newDbConnection le nouveau dbConnection
     */
    public static void setDbConnection(final String newDbConnection) {
        dbConnection = newDbConnection;
    }

}
