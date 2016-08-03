package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class BookModel {
	public static ResultSet getBook(String bookName, Connection conn) {
		ResultSet rs = null;	
		try {
			if(bookName.equals("N/A")){
				String query = "select * from tbl_book";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
			else {
				String query = "select * from tbl_book where tbl_book.title like ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+bookName+"%");
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
	public static int changeBook(int bookId,String bookName,int publisherId, Connection conn){
		String query ="";
		if(bookId== -1){
			try {
				if (publisherId!=-1)
				{
					query = "INSERT INTO tbl_book(title,pubId) VALUES (?,?);";
					PreparedStatement pstmt = conn.prepareStatement(query);
					//pstmt.setInt(1, bookId);
					pstmt.setString(1, bookName);
					
					pstmt.setInt(2, publisherId);
					
					pstmt.executeUpdate();
					query = "SELECT LAST_INSERT_ID();";
					pstmt = conn.prepareStatement(query);
					ResultSet newIndex = pstmt.executeQuery();
					conn.commit();
			        if (newIndex.next()){
			            return newIndex.getInt(1);
			        }
				}
				else{
					query = "INSERT INTO tbl_book(title) VALUES (?);";
					PreparedStatement pstmt = conn.prepareStatement(query);
					//pstmt.setInt(1, bookId);
					pstmt.setString(1, bookName);
					//pstmt.setInt(2, publisherId);
					pstmt.executeUpdate();
					query = "SELECT LAST_INSERT_ID();";
					pstmt = conn.prepareStatement(query);
					ResultSet newIndex = pstmt.executeQuery();
					conn.commit();
			        if (newIndex.next()){
			            return newIndex.getInt(1);
			        }
				}
				return -1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//newIndex.getInt(1)
			}}
		else {
			try {
				query = "UPDATE tbl_book SET title=?, pubId=? WHERE bookId =?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, bookName);
				pstmt.setInt(2, publisherId);
				pstmt.setInt(3, bookId);
				pstmt.executeUpdate();
				conn.commit();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//return 0;
			}
		}
		return bookId;
	}
	public static void deleteBook(int bookId, Connection conn) {
		try {
			String query = "delete from tbl_book_genres where tbl_book_genres.bookId =?;";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
			query = "delete from tbl_book_loans where tbl_book_loans.bookId = ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
			query = "delete from tbl_book_copies where tbl_book_copies.bookId = ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
			query = "delete from tbl_book_authors where tbl_book_authors.bookId = ?;";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
			query = "delete from tbl_book where tbl_book.bookId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int getBook(int branch_id,int book_id, Connection conn){		
		ResultSet rs = null;
		try {
			//String query = "select distinct title,authorName,tbl_book_copies.bookId from tbl_book_copies, tbl_book, tbl_author,tbl_book_authors where tbl_book_copies.branchId != ? and tbl_book_copies.bookId=tbl_book.bookId and tbl_author.authorId= tbl_book_authors.authorId and tbl_book_authors.bookId= tbl_book.bookId ";
			String query = "select tbl_book_copies.noOfCopies from tbl_book_copies where tbl_book_copies.branchId =? and tbl_book_copies.bookId =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, branch_id);
			pstmt.setInt(2, book_id);
			rs = pstmt.executeQuery();
			//System.out.println(rs);
			conn.commit();
			if (rs.next()) {
				return rs.getInt("tbl_book_copies.noOfCopies");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return 0;
	}
	public static ResultSet getBook(int branch_id, Connection conn){
			ResultSet rs = null;
			try {
				String query = "select * from tbl_book_copies, tbl_book where tbl_book_copies.bookId=tbl_book.bookId and tbl_book_copies.branchId =?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, branch_id);
				rs = pstmt.executeQuery();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rs;
		}
	public static int addBook(int branch_id,int book_id,int noOfCopies,Connection conn){
			String query ="";
			try {
				query = "INSERT INTO tbl_book_copies VALUES (?,?,?);";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, book_id);
				pstmt.setInt(2, branch_id);
				pstmt.setInt(3, noOfCopies);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				try {
					query = "UPDATE tbl_book_copies SET noOfCopies=? WHERE branchId=? and bookId =?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(3, book_id);
					pstmt.setInt(2, branch_id);
					pstmt.setInt(1, noOfCopies);
					pstmt.executeUpdate();
					conn.commit();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return 0;
				}
				//e.printStackTrace();
				return 0;
			}
			return 0;
		}
	public static ResultSet getBook(Connection conn){
			ResultSet rs = null;
			try {
				String query = "select distinct tbl_book.bookId, tbl_book.title, tbl_author.authorName  from tbl_book,tbl_author,tbl_book_authors where tbl_book_authors.bookId= tbl_book.bookId and tbl_book_authors.authorId = tbl_author.authorId";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rs;
		}
	public static void addBookGenre(int genreId, int bookId, Connection conn) {
		String query ="";
		try {
			query = "INSERT INTO tbl_book_genres VALUES (?,?);";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, genreId);
			pstmt.setInt(2, bookId);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return 0;
		}
	}
}
