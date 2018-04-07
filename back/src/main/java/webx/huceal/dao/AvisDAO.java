package webx.huceal.dao;

import webx.huceal.DataSource;
import webx.huceal.domains.Avis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AvisDAO {

	/**
	 * Renvoie la liste des avis d'un film.
	 * @param id - Identifiant du film
	 * @return la liste des avis du film
	 */
	public List<Avis> findAllAvisByFilmId(String id) {
		// TODO
		return null;
	}

	public void addAvis(String filmID, int note, String commentaire) {
		Connection con = null;
		PreparedStatement stmt = null;
		String tableCreateQuery = "INSERT INTO Avis VALUES(?, ?, ?)";
		try {
			con = DataSource.getDBConnection();
			stmt = con.prepareStatement(tableCreateQuery);
			stmt.setString(1, filmID);
			stmt.setInt(2, note);
			stmt.setString(3, commentaire);
			stmt.executeUpdate();
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

}
