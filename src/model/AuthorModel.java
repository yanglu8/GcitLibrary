package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorModel {
	public static void changeAuthor(int authorId, String authorName,Connection conn){
		String query ="";
		if(authorId==-1){
			try {
				query = "INSERT INTO tbl_author VALUES (NULL,?);";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, authorName);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		else {
			try {
				query = "UPDATE tbl_author SET authorName=? WHERE authorId =?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, authorName);
				pstmt.setInt(2, authorId);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static ResultSet getAuthor (String authorName,Connection conn){
		ResultSet rs = null;	
		try {
			if(authorName.equals("N/A")){
				String query = "select * from tbl_author";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
			else {
				String query = "select * from tbl_author where tbl_author.authorName like ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+authorName+"%");
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
	public static void deleteAuthor(int authorId, Connection conn) {
		try {
			String query = "delete from tbl_book_authors where tbl_book_authors.authorId = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);
			pstmt.executeUpdate();
			query = "delete from tbl_author where tbl_author.authorId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);
			pstmt.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addBookAuthor(int bookId, int authorId, Connection conn){
		String query ="";
		try {
			query = "INSERT INTO tbl_book_authors VALUES (?,?);";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, authorId);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return 0;
		}
	}
}
