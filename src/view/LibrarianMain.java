package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.BookModel;
import model.BranchModel;
import connectionBackend.BackendMain;

public class LibrarianMain {
	static Scanner sc = new Scanner (System.in);
	public static void MainUI(Connection conn){
		System.out.println("1) Enter Branch you manage\n2) Quite to previous");
		int menuReturn= sc.nextInt();
		if (menuReturn == 1){
			chooseBranch(conn);
		}else {BackendMain.TerminalUI(conn);}
	}
	public static void chooseBranch(Connection conn){
		try {
			ResultSet rs =BranchModel.getBranch("N/A",conn);
			int i =1;
			while (rs.next()){
				System.out.println(i+") "+rs.getString("branchName")+","+rs.getString("branchAddress"));
				i++;
			}
			System.out.println(i+") "+"Quit to previous");
			int branch_id= sc.nextInt();
			if(branch_id == i)
				MainUI(conn);
			rs.absolute(branch_id);
			branch_id= rs.getInt("branchId");
			BranchMenu (branch_id,conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void BranchMenu(int branch_id, Connection conn){
		System.out.println("1) Update the details of the Library\n"+"2) Add copies of Book to the Branch\n"+"3) Quit to previous");
		int type = sc.nextInt();
		switch (type){
		case 1:
			UpdateLibrary(branch_id,conn);
			break;
		case 2:
			addBook(branch_id,conn);
			BranchMenu(branch_id,conn);
			break;
		case 3:
			chooseBranch(conn);
			break;
		}
		
	}
	public static void UpdateLibrary (int branch_id, Connection conn){
		System.out.println("You have chosen to update the Branch with Branch Id: X and Branch Name: ABCD. Enter ¡®quit¡¯ at any prompt to cancel operation.\nEnter ¡®quit¡¯ at any prompt to cancel operation.\n"
				+ "Please enter new branch name or enter N/A for no change:\n");
		String name = sc.next();
		name +=sc.nextLine();
		if(name.equals("quit")){
			BranchMenu (branch_id,conn);
		}
		System.out.println("Please enter new branch address or enter N/A for no change:");
		String address = sc.next();
		address +=sc.nextLine();
		if(address.equals("quit")){
			BranchMenu (branch_id,conn);
		}
		int i = BranchModel.updateBranch(branch_id, name, address, conn);
		BranchMenu(branch_id,conn);
	}
	public static void addBook (int branch_id, Connection conn){
		try {
			ResultSet rs = BookModel.getBook(conn);
			int i =1;
			//System.out.println(rs.getInt(1));
			while (rs.next()){
				System.out.println(i+") "+rs.getString("title")+" by "+rs.getString("authorName"));
				i++;				
			}
			int type = sc.nextInt();
			rs.absolute(type);
			int book_id = rs.getInt("bookId");
			int currentCopy = BookModel.getBook(branch_id,book_id, conn);
			System.out.println("Existing number of copies: "+currentCopy);
			System.out.println("Enter new number of copies:");
			int copyNum =sc.nextInt();
			BookModel.addBook(branch_id,book_id,copyNum,conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
