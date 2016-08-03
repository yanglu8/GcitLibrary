package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenereModel {

	public static void changeGenre(){	
	}
	public static ResultSet getGenre(String genreName, Connection conn) {
		ResultSet rs = null;	
		try {
			if(genreName.equals("N/A")){
				String query = "select * from tbl_genre";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
			else {
				String query = "select * from tbl_genre where tbl_genre.genreName like ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+genreName+"%");
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void deleteGenere(){}
	public static void changegenere(int i, String genereName, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	public static ResultSet getgenere(String next, Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void deletegenere(int idDelete, Connection conn) {
		// TODO Auto-generated method stub
		
	}
}
