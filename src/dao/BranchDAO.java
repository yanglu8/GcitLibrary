package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import entity.Author;
import entity.Branch;
import entity.Book;

public class BranchDAO extends BaseDAO {

	public BranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public Integer addBranchWithID(Branch branch) throws SQLException{
		return saveWithID("insert into tbl_library_branch (branchName,branchAddress) values (?,?)", new Object[] { branch.getBranchName(),branch.getBranchAddress() });		
	}
	public void updateBranch(Branch branch) throws SQLException{
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(),branch.getBranchId() });
	}
	public void deleteAuthor(Branch branch) throws SQLException {
		save("delete from tbl_library_branch where branchId = ?", new Object[] { branch.getBranchId()});
	}
	public Map<Book,Integer> readAllLoans() throws SQLException {
		//return read("select * from tbl_book_copies", null);
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
