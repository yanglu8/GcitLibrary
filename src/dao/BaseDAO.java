package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO {
	public static Connection conn;
	
	public BaseDAO(Connection conn){
		this.conn = conn;
	}
	
	public void save(String query, Object[] vals) throws SQLException{
			PreparedStatement pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			pstmt.executeUpdate();
	}
	
	public Integer saveWithID(String query, Object[] vals) throws SQLException{
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			if(rs!=null){
				return rs.getInt(1);
			}else{
				return -1;
			}
	}
	
	public <T> List<T> read(String query, Object[] vals) throws SQLException{
			PreparedStatement pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractData(rs);
	}

	public abstract <T> List<T> extractData(ResultSet rs);
	
	public <T> List<T> readFirstLevel(String query, Object[] vals) throws SQLException{
			PreparedStatement pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count = 1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return extractDataFirstLevel(rs);
	}

	public abstract <T> List<T> extractDataFirstLevel(ResultSet rs);
}
