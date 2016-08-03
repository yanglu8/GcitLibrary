package service;

import java.sql.Connection;
import java.sql.SQLException;

import model.BookModel;
import dao.AuthorDAO;
import dao.BookDAO;
import dao.BorrowerDAO;
import dao.PublisherDAO;
import entity.Author;
import entity.Book;
import entity.Branch;
import entity.Publisher;

public class BorrowerService {
	public void checkBook(Book book,Branch branch){		
	}
	public boolean checkCardNo(int cardNo) throws SQLException{
		Connection conn = ConnectionUtil.getConnection();
		boolean valid = false;
		try{
			BorrowerDAO bdao = new BorrowerDAO(conn);
			if(bdao.readBorrowerByID(cardNo) != null)
				valid = true;
			
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		
		return valid;
	}
	public void returnBook(){}

}
