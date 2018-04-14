package webx.huceal.dao;

import webx.huceal.DataSource;
import webx.huceal.domains.Avis;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La DAO des Avis.
 */
public class AvisDAO {

    /**
     * Ajoute un avis à la base de donnée avec une note et/ou un commentaire.
     * @param filmID l'id du film auquel se rattache l'avis
     * @param note la note donnée, -1 si elle n'est pas renseignée
     * @param commentaire le commentaire donné, peut être vide si note existe
     * @return L'id de l'avis créé
     */
    public long addAvis(final String filmID,
                        final int note,
                        final String commentaire) {
        long id = -1;
        final int filmIDIndex = 1;
        final int noteIndex = 2;
        final int commentaireIndex = 3;
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Avis(FilmID, Note, Commentaire) "
                     + "VALUES(?, ?, ?)";
        try {
            con = DataSource.getDBConnection();
            if (con != null) {
                stmt = con.prepareStatement(query);
                stmt.setString(filmIDIndex, filmID);
                stmt.setInt(noteIndex, note);
                stmt.setString(commentaireIndex, commentaire);
                stmt.executeUpdate();
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConAndStmt(con, stmt);
        }
        return id;
    }

    /**
     * Renvoie tous les commentaires pour un film identifié par son id.
     * @param filmID l'id du film (tt suivi de 7 chiffres)
     * @return La liste des avis du film s'il y en a, une liste vide sinon
     */
    public List<Avis> findAllAvisByFilmID(final String filmID) {
        List<Avis> liste = new ArrayList<>();
        ResultSet res = null;
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "SELECT * FROM Avis WHERE FilmID = ?";
        try {
            con = DataSource.getDBConnection();
            if (con != null) {
                stmt = con.prepareStatement(query);
                stmt.setString(1, filmID);
                res = stmt.executeQuery();
                while (res.next()) {
                    liste.add(new Avis(res.getLong("id"),
                         res.getString("FilmID"),
                         res.getInt("Note"),
                         res.getString("Commentaire")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSource.closeResultSet(res);
            DataSource.closeConAndStmt(con, stmt);
        }
        return liste;
    }

    /**
     * Renvoie l'avis correspondant à l'id donné.
     * @param avisID l'id d'un avis
     * @return L'avis s'il existe, null sinon
     */
    public Avis findAvisByID(final long avisID) {
        Avis avis = null;
        ResultSet res = null;
        Connection con = null;
        Statement stmt = null;
        String query = "SELECT * FROM Avis WHERE id = '" + avisID + "'";
        try {
            con = DataSource.getDBConnection();
            if (con != null) {
                stmt = con.createStatement();
                res = stmt.executeQuery(query);
                if (res.next()) {
                    avis = new Avis(res.getLong("id"),
                         res.getString("FilmID"),
                         res.getInt("Note"),
                         res.getString("Commentaire"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSource.closeResultSet(res);
            DataSource.closeConAndStmt(con, stmt);
        }
        return avis;
    }

    /**
     * Supprime les avis ayant un commentaire contenant le mot-clé donné.
     * @param key le mot-clé à chercher parmi les commentaires.
     * @return Le nombre de commentaires supprimés
     */
    public int deleteAvisByKey(final String key) {
        int affectedRows = 0;
        Connection con = null;
        Statement stmt = null;
        String query = "DELETE FROM Avis WHERE lower(Commentaire)"
                     + " LIKE '%" + key.toLowerCase() + "%'";
        try {
            con = DataSource.getDBConnection();
            if (con != null) {
                stmt = con.createStatement();
                affectedRows = stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConAndStmt(con, stmt);
        }
        return affectedRows;
    }

    /**
     * Supprime l'avis correspondant à l'id donné.
     * @param avisID l'id d'un avis
     */
    public final void deleteAvisByID(final long avisID) {
        Connection con = null;
        Statement stmt = null;
        String query = "DELETE FROM Avis WHERE id = '" + avisID + "'";
        try {
            con = DataSource.getDBConnection();
            if (con != null) {
                stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConAndStmt(con, stmt);
        }
    }

}
