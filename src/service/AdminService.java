package service;

import java.sql.Connection;
import java.sql.SQLException;

import model.BookModel;
import dao.AuthorDAO;
import dao.BookDAO;
import dao.PublisherDAO;
import entity.Author;
import entity.Book;
import entity.Publisher;

public class AdminService {
	
	public void addAuthor(Author author) throws SQLException{
		Connection conn =  ConnectionUtil.getConnection();
		try {
			AuthorDAO adao = new AuthorDAO(conn);
			Integer authorID = adao.addAuthorWithID(author);
			author.setAuthorID(authorID);
			adao.addBookAuthor(author);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	public void deleteAuthor(Author author) throws SQLException{
		Connection conn =  ConnectionUtil.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	public void updateAuthor (Author author) throws SQLException{
		Connection conn =ConnectionUtil.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			adao.updateAuthor(author);
			conn.commit();
		}catch(Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	public void deletePublisher(Publisher publisher) throws SQLException{
		Connection conn =  ConnectionUtil.getConnection();
		try{
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(publisher);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			//conn.rollback();
		}finally{
			conn.close();
		}
	}
	public void addPublisher(Publisher publisher) throws SQLException {
		Connection conn =  ConnectionUtil.getConnection();
		try {
			PublisherDAO adao = new PublisherDAO(conn);
			Integer publisherID = adao.addPublisherWithID(publisher);
			publisher.setPublisherId(publisherID);
			//adao.addBookAuthor(publisher);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	public void updatePublisher(Publisher publisher) throws SQLException {
		Connection conn =ConnectionUtil.getConnection();
		try{
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.updatePublisher(publisher);
			conn.commit();
		}catch(Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}	
	}
	public void deleteBook(Book book) throws SQLException{
		Connection conn =  ConnectionUtil.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			//conn.rollback();
		}finally{
			conn.close();
		}
	}
	public void addBook(Book book)throws SQLException, ClassNotFoundException{
		Connection conn =  ConnectionUtil.getConnection();
		try {
			BookDAO bdao = new BookDAO(conn);
			Integer bookID = bdao.addBookWithID(book);
			book.setBookId(bookID);
			bdao.addAuthorBook(book);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
}
