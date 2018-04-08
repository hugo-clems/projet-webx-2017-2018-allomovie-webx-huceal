package webx.huceal.dao;

import webx.huceal.DataSource;
import webx.huceal.domains.Avis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisDAO {

	public long addAvis(String filmID, int note, String commentaire) {
		long id = -1;
		Connection con = null;
		PreparedStatement stmt = null;
		String query = "INSERT INTO Avis(FilmID, Note, Commentaire) VALUES(?, ?, ?)";
		try {
			con = DataSource.getDBConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, filmID);
			stmt.setInt(2, note);
			stmt.setString(3, commentaire);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return id;
	}

	public List<Avis> findAllAvisByFilmID(String filmID) {
		List<Avis> liste = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		String query = "SELECT * FROM Avis WHERE FilmID = '" + filmID + "'";
		try {
			con = DataSource.getDBConnection();
			stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(query);
			while (res.next()) {
				liste.add(new Avis(res.getLong("id"), res.getString("FilmID"),
						res.getInt("Note"), res.getString("Commentaire")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return liste;
	}

}
