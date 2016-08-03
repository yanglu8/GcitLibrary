package connectionBackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import view.AdminMain;
import view.BorrowerMain;
import view.LibrarianMain;
import model.BorrowerModel;

public class BackendMain {
		private static String driver = "";
		private static String url = "jdbc:mysql://localhost/library";
		private static String user = "root";
		private static String pass = "";
		public static void main(String[] args) throws SQLException {
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url, user, pass);
				conn.setAutoCommit(false);
				BorrowerModel.getBorrower(1, conn);
				TerminalUI(conn);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				conn.rollback();
			} finally{
				conn.close();
			}			
		}
		public static void TerminalUI(Connection conn){
			System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you?\n"+" 1) Librarian\n 2) Administrator\n 3) Borrower");
			Scanner sc = new Scanner(System.in);
			int type = sc.nextInt();
			switch (type){
			case 1:
				LibrarianMain.MainUI(conn);
				break;
			case 2:
				AdminMain.adminMain(conn);
				break;
			case 3:
				BorrowerMain.checkCardNo(conn);
				break;
			default:
				System.out.println("No, no , no , no, no");
				System.exit(0) ; 
			}
		}
}

