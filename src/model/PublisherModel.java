package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherModel {
	public static void changePublisher(int publisherId, String publisherName, String publisherAddress, String publisherPhone, Connection conn) {
		String query ="";
		if(publisherId==-1){
			try {
				query = "INSERT INTO tbl_publisher VALUES (NULL,?,?,?);";
				PreparedStatement pstmt = conn.prepareStatement(query);
				//pstmt.setInt(1, publisherId);
				pstmt.setString(1, publisherName);
				pstmt.setString(2, publisherAddress);
				pstmt.setString(3, publisherPhone);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
		else {
			try {
				query = "UPDATE tbl_publisher SET publisherName=?, publisherAddress=?, publisherPhone=? WHERE publisherId =?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, publisherName);
				pstmt.setString(2, publisherAddress);
				pstmt.setString(3, publisherPhone);
				pstmt.setInt(4, publisherId);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//return 0;
			}
		}		
	}
	public static void deletePublisher(int deleteType, int publisherId, Connection conn) {
		try {
			String query = "select * from tbl_book where tbl_book.pubId = ?;";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, publisherId);
			ResultSet rs = pstmt.executeQuery();
			if (deleteType ==1){
				while(rs.next()){
					BookModel.deleteBook(rs.getInt("bookId"), conn);
				}
			}
			else if (deleteType ==2){
				while(rs.next()){
					BookModel.changeBook(rs.getInt("bookId"), rs.getString("title"), -1, conn);
				}
			}
			query = "delete from tbl_publisher where tbl_publisher.publisherId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, publisherId);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ResultSet getPublisher(String publisherName, Connection conn) {
		ResultSet rs = null;	
		try {
			if(publisherName.equals("N/A")){
				String query = "select * from tbl_publisher";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
			else {
				String query = "select * from tbl_publisher where tbl_publisher.publisherName like ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+publisherName+"%");
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
}
