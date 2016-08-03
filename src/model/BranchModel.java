package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchModel {
	public static void changeBranch(int branchId, String branchName,String branchAddress, Connection conn) {
		String query ="";
		if(branchId==-1){
			try {
				query = "INSERT INTO tbl_library_branch VALUES (NULL,?,?);";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, branchName);
				pstmt.setString(2, branchAddress);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
			}
		}
		else {
			try {
				query = "UPDATE tbl_library_branch SET branchName=?, branchAddress=? WHERE branchId =?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, branchName);
				pstmt.setString(2, branchAddress);
				pstmt.setInt(3, branchId);
				pstmt.executeUpdate();
				conn.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}
	public static void deleteBranch(int branchId, Connection conn) {
		try {
			String query = "delete from tbl_library_branch where tbl_library_branch.branchId = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, branchId);
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ResultSet getBranch(String branchName, Connection conn){
		ResultSet rs = null;
		try {
			if(branchName.equals("N/A")){
				String query = "select * from tbl_library_branch";
				PreparedStatement pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				conn.commit();
			}
			else {
				String query = "select * from tbl_library_branch where tbl_library_branch.branchName like ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+branchName+"%");
				rs = pstmt.executeQuery();
				conn.commit();
				return rs;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static int updateBranch (int branch_id,String name, String address,Connection conn){
		try {
			String query;
			PreparedStatement pstmt;
			if (name.equals("N/A")){
				if(address.equals("N/A")){
					System.out.println("you didn't update anything");
					return 0;
				}
				else {
					System.out.println("Only update the address");
					query = "UPDATE tbl_library_branch SET branchAddress=? WHERE branchId=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, address);
					pstmt.setInt(2, branch_id);
				}
			}
			else {
				if(address.equals("N/A")){
					System.out.println("Only update the name");
					query = "UPDATE tbl_library_branch SET branchName=? WHERE branchId=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, name);
					pstmt.setInt(2, branch_id);
				}
				else {
					System.out.println("update success");
					query = "UPDATE tbl_library_branch SET branchName=?,branchAddress=? WHERE branchId=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, name);
					pstmt.setString(2, address);
					pstmt.setInt(3, branch_id);
				}
			}
			pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
}
