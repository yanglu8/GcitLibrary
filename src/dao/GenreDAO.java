package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entity.Genre;

public class GenreDAO extends BaseDAO {
	public GenreDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public void addGenre(Genre genre) throws SQLException{
		save("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}
	public Integer addGenreWithID(Genre genre) throws SQLException {
		return saveWithID("insert into tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}
	
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

	
	
}
