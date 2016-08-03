package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowerModel {
	@SuppressWarnings("finally")
	public static ResultSet getBorrower(int b_id, Connection conn){
		ResultSet rs = null;
		try {
			String query = "select * from tbl_borrower where cardNo = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, b_id);
			rs = pstmt.executeQuery();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static void checkBook(int borrower_id, int book_id, int branch_id, Connection conn){
		String query ="";
		try {
			query = "INSERT INTO tbl_book_loans VALUES (?,?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 7 DAY ),NULL)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, book_id);//3
			pstmt.setInt(2, branch_id);//2
			pstmt.setInt(3, borrower_id);//1
			System.out.println(book_id+' '+branch_id+ " "+borrower_id);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("You have alreay borrowed this book from this branch before!!!");
			//e.printStackTrace();
		}
	}
	/*
	public static ResultSet checkBook(Connection conn) {
		ResultSet rs = null;
		try {
			String query = "select * from tbl_library_branch";
			PreparedStatement pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			//System.out.println(rs);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	*/
	public static void changeBorrower(int cardNo, String name, String address, String phone, Connection conn) {
		String query ="";
		if(cardNo==-1){
			try {
				query = "INSERT INTO tbl_borrower VALUES (NULL,?,?,?);";
				PreparedStatement pstmt = conn.prepareStatement(query);
				//pstmt.setInt(1, cardNo);
				pstmt.setString(1, name);
				pstmt.setString(2, address);
				pstmt.setString(3, phone);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				query = "UPDATE tbl_borrower SET name=?, address=?, phone=? WHERE cardNo =?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, address);
				pstmt.setString(3, phone);
				pstmt.setInt(4, cardNo);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//return 0;
			}
		}
	
	}
	public static void deleteBorrower(int cardNo, Connection conn) {
		try {
			//String query = "select distinct title,authorName,tbl_book_copies.bookId from tbl_book_copies, tbl_book, tbl_author,tbl_book_authors where tbl_book_copies.branchId != ? and tbl_book_copies.bookId=tbl_book.bookId and tbl_author.authorId= tbl_book_authors.authorId and tbl_book_authors.bookId= tbl_book.bookId ";
			String query = "delete from tbl_borrower where tbl_borrower.cardNo = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cardNo);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ResultSet getBorrower(String borrowerName, Connection conn) {
		ResultSet rs = null;	
		try {
			if(borrowerName.equals("N/A")){
				String query = "select * from tbl_borrower";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
			else {
				String query = "select * from tbl_borrower where tbl_borrower.name like ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+borrowerName+"%");
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
	public static ResultSet getLoans(int type,int cardNo, Connection conn) {
		ResultSet rs = null;	
		try {
			switch (type) {
			case -1:
				String query = "select cardNo, tbl_book.bookId, dueDate, title, tbl_book_loans.branchId ,tbl_library_branch.branchName, tbl_book_loans.dateIn from tbl_book_loans, tbl_book, tbl_library_branch where tbl_book_loans.cardNo = ? and tbl_book_loans.bookId = tbl_book.bookId and tbl_book_loans.branchId= tbl_library_branch.branchId  and tbl_book_loans.dateIn is NULL";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, cardNo);	
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			case 0:
				query = "select cardNo, tbl_book.bookId, dueDate, title, tbl_book_loans.branchId ,tbl_library_branch.branchName, tbl_book_loans.dateIn from tbl_book_loans, tbl_book, tbl_library_branch where tbl_book_loans.cardNo = ? and tbl_book_loans.bookId = tbl_book.bookId and tbl_book_loans.branchId= tbl_library_branch.branchId";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, cardNo);	
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			case 1:
				query = "select cardNo, tbl_book.bookId, dueDate, title, tbl_book_loans.branchId ,tbl_library_branch.branchName, tbl_book_loans.dateIn from tbl_book_loans, tbl_book, tbl_library_branch where tbl_book_loans.cardNo = ? and tbl_book_loans.bookId = tbl_book.bookId and tbl_book_loans.branchId= tbl_library_branch.branchId  and tbl_book_loans.dateIn is not NULL";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, cardNo);	
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ResultSet getLoans(int cardNo, String dueDate, Connection conn){
		// TODO Auto-generated catch block
		return null;}
	public static void overrideDue(int bookId, int branchId,String dueDate, int cardNo,Connection conn){
		try {
			String query = "UPDATE tbl_book_loans SET dueDate = ? WHERE bookId=? and branchId=? and cardNo =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dueDate);
			pstmt.setInt(2, bookId);
			pstmt.setInt(3, branchId);
			pstmt.setInt(4, cardNo);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println("The new due date format is wrong!!");
		}
	}
	public static void returnBook(int bookId, int branchId, int borrowerId, Connection conn) {
		System.out.println(bookId+" "+branchId+" "+borrowerId+" ");
		
		try {
			String query = "UPDATE tbl_book_loans SET dateIn =CURDATE() WHERE bookId=? and branchId=? and cardNo =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			pstmt.setInt(2, branchId);
			pstmt.setInt(3, borrowerId);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
