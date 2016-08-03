package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Publisher;
import entity.Book;

public class PublisherDAO extends BaseDAO {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public void addPublisher(Publisher publisher) throws SQLException {
		save("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)", new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone() });
	}
	
	public Integer addPublisherWithID(Publisher publisher) throws SQLException {
		return saveWithID("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)", new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone() });
	}
	/*
	public void updateBookPublisher(Publisher publisher) throws SQLException {
		for(Book b: publisher.getBook()){
			save("insert into tbl_book_publishers (bookId, publisherId) values (?, ?)", new Object[] { publisher.getPublisherId(), b.getBookId() });
		}
	}
	*/
	public void updatePublisher(Publisher publisher) throws SQLException {
		save("update tbl_publisher set publisherName = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers() throws SQLException {
		return read("select * from tbl_publisher", null);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<Publisher>();
		BookDAO bdao = new BookDAO(conn);
		try {
			while (rs.next()) {
				Publisher a = new Publisher();
				a.setPublisherId(rs.getInt("publisherId"));
				a.setPublisherName(rs.getString("publisherName"));
				a.setBooks(bdao.readFirstLevel(
						"select * from tbl_publisher where pId = ?",
						new Object[] { a.getPublisherId()}));
				publishers.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<Publisher>();
		try {
			while (rs.next()) {
				Publisher a = new Publisher();
				a.setPublisherId(rs.getInt("publisherId"));
				a.setPublisherName(rs.getString("publisherName"));
				publishers.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publishers;
	}
}
