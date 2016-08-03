package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entity.Author;
import entity.Book;
import entity.Borrower;

public class BorrowerDAO extends BaseDAO{

	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public Integer addBorrowerWithID(Borrower borrower) throws SQLException {
		return saveWithID("insert into tbl_borrower (name,address,phone) values (?,?,?)", new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone() });
	}
	public void updateBorrower(Borrower borrower) throws SQLException {
		save("update tbl_borrower set name = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getCardNo() });
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException {
		save("delete from tbl_borrower where cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	/*
	public void addBookBorrower(Borrower borrower) throws SQLException {
		for(Book b: borrower.getBookLoans()){
			save("insert into tbl_book_loans (bookId, authorId) values (?, ?)", new Object[] { author.getAuthorID(), b.getBookId() });
		}
	}
	*/
	@Override
	public <T> List<T> extractData(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> extractDataFirstLevel(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object readBorrowerByID(int cardNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
