package view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connectionBackend.BackendMain;
import model.BookModel;
import model.BorrowerModel;
import model.BranchModel;

public class BorrowerMain {
	public static void checkCardNo(Connection conn){
		boolean vaildCard= false;
		int cardNo =-1;
		while(!vaildCard){
			try {
				System.out.println("Enter the your Card Number:");
				Scanner scanner = new Scanner(System.in);
				cardNo = scanner.nextInt();
				ResultSet rs = BorrowerModel.getBorrower(cardNo, conn);
				
				if (!rs.next()){
					System.out.println("invalid!");
					System.out.println();
				}else {
					vaildCard = true;
					System.out.println("valid");
//					scanner.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("that's not good.");
			}
		}
		borrowerMain(cardNo,conn);
	}
	public static void borrowerMain(int borrower_id, Connection conn){
		System.out.println("1) Check out a book\n2) Return a Book\n3) Quit to Previous");
		try {
			Scanner sc = new Scanner(System.in);
			int branchNo = sc.nextInt();
			switch (branchNo){
				case 1:
					checkBook(borrower_id, conn);
					borrowerMain(borrower_id,conn);
					break;
				case 2:
					returnBook(borrower_id, conn);
					borrowerMain(borrower_id,conn);
					break;
				case 3:
					System.out.println("aaa");
					BackendMain.main(null);
					break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	public static void checkBook(int borrower_id, Connection conn) {
		System.out.println("Pick the Branch you want to check out from:");
		try {
			ResultSet rs =BranchModel.getBranch("N/A",conn);
			int i =1;
			while (rs.next()){
				System.out.println(i+") "+rs.getString("branchName"));
				i++;
			}
			System.out.println(i+") "+"Quit to previous");
			Scanner sc = new Scanner(System.in);
			int branch_id= sc.nextInt();
			if(branch_id == i)
				borrowerMain(borrower_id,conn);
			else{
				rs.absolute(branch_id);
				branch_id = rs.getInt("branchId");
				//System.out.println(rs.getInt("branchId")+" "+rs.getString("branchName"));
				rs = BookModel.getBook(branch_id,conn);
				i =1;
				while (rs.next()){
					System.out.println(i+") "+rs.getString("title"));
					i++;
				}
			}
			System.out.println(i+") "+"Quit to previous");
			int book_id= sc.nextInt();
			if(book_id == i)
				borrowerMain(borrower_id,conn);
			else{
				rs.absolute(book_id);
				book_id = rs.getInt("bookId");
				BorrowerModel.checkBook(borrower_id,book_id,branch_id,conn);
			} 
//			BranchMenu (branch_id,conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void returnBook(int borrower_id,Connection conn){
		System.out.println("Pick the Book you want to return");
		try {
			ResultSet rs =BorrowerModel.getLoans(-1,borrower_id,conn);
			int i =1;
			while (rs.next()){
				if (rs.getString("tbl_book_loans.dateIn")==null){
					System.out.println(i+") "+rs.getString("title")+" you borrowed in "+rs.getString("tbl_library_branch.branchName"));
				}
				else {
					//rs.deleteRow();
					continue;
				}
				i++;
			}
			System.out.println(i+") "+"Quit to previous");
			Scanner sc = new Scanner(System.in);
			int bookId= sc.nextInt();
			if(bookId == i)
				borrowerMain(borrower_id,conn);
			else{
				rs.absolute(bookId);
				bookId = rs.getInt("bookId");
				int branchId = rs.getInt("tbl_book_loans.branchId");
				BorrowerModel.returnBook(bookId,branchId,borrower_id,conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	} 
}
