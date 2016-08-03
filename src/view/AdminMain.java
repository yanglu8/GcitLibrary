package view;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connectionBackend.BackendMain;
import model.AuthorModel;
import model.BookModel;
import model.BorrowerModel;
import model.BranchModel;
import model.GenereModel;
import model.PublisherModel;
public class AdminMain {
	static Scanner sc = new Scanner(System.in);
	public static void adminMain(Connection conn) {
		System.out.println("1 )Author\n2 )Book\n3 )Publishers\n4 )Library Branches\n5 )Borrowers\n6 )Over-ride Due Date for a Book Loan\n7 )Reselect role");
		int type = sc.nextInt();
		int action = 0;
		switch (type){
		case 1:
			System.out.println("you have chose to modify book, choose options below\n"+"1 )Add\n2 )Update\n3 )Delete\n4 )Return to main menu\n");
			action = sc.nextInt();
			authorAdmin(action,conn);
			break;
		case 2:
			System.out.println("you have chose to modify author, choose options below\n"+"1 )Add\n2 )Update\n3 )Delete\n4 )Return to main menu\n");
			action = sc.nextInt();
			bookAdmin(action,conn);
			break;
		case 3:
			System.out.println("you have chose to modify book, choose options below\n"+"1 )Add\n2 )Update\n3 )Delete\n4 )Return to main menu\n");
			action = sc.nextInt();
			publisherAdmin(action, conn);
			break;
		case 4:
			System.out.println("you have chose to modify branch, choose options below\n"+"1 )Add\n2 )Update\n3 )Delete\n4 )Return to main menu\n");
			action = sc.nextInt();
			branchAdmin(action, conn);
			break;
		case 5:
			System.out.println("you have chose to modify borrowers, choose options below\n"+"1 )Add\n2 )Update\n3 )Delete\n4 )Return to main menu\n");
			action = sc.nextInt();
			borrowerAdmin(action, conn);
			break;
		case 6:
			System.out.println("you have chose to override due date");
			overrideDuedate(conn);
			break;
		case 7:
			try {
				BackendMain.main(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		adminMain(conn);
	}
	public static void authorAdmin(int action,Connection conn) {
		switch (action){
		case 1:
			System.out.println("what author do u want to add? quit to quit");
			String authorName = sc.next();
			authorName += sc.nextLine();
			if (!authorName.equals("quit")){
				//System.out.println(authorName);
				AuthorModel.changeAuthor(-1,authorName,conn);
			}
			break;
		case 2:
			System.out.println("which author do you want to update?  type N/A to list all");
			ResultSet rs = AuthorModel.getAuthor(sc.next(),conn);
			try {
				System.out.println("select an author id to update ");
				System.out.println("0: go back");
				while (rs.next()){
					System.out.println(rs.getInt("authorId")+": "+rs.getString("authorName"));
				}
				int idupdate = sc.nextInt();
				if (idupdate==0)
					break;
				System.out.println("what's the new author name? type quit to quit");
				authorName = sc.next();
				authorName+=sc.nextLine();
				if (!authorName.equals("quit"))
					AuthorModel.changeAuthor(idupdate, authorName, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("which author do you want to delete?  type N/A to list all");
			rs = AuthorModel.getAuthor(sc.next(),conn);
			try {
				System.out.println("select an author id to delete");
				System.out.println("0: I changed my mind take me back");
				while (rs.next()){
					System.out.println(rs.getInt("authorId")+": "+rs.getString("authorName"));
				}
				int idDelete = sc.nextInt();				
				if (idDelete!=0)
				{	System.out.println("delete success!!");
					AuthorModel.deleteAuthor(idDelete, conn);}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			break;
		}
	}
	public static void bookAdmin(int action,Connection conn) {
		switch (action){
		case 1:
			System.out.println("what's the book name do u want to add? enter quit to quit");
			String bookName = sc.next();
			bookName += sc.nextLine();
			if(bookName.equals("quit"))
				break;
			int bookId = BookModel.changeBook(-1, bookName,-1, conn);
			System.out.println("new bookId: "+bookId);
			System.out.println("now we have to initialize the new added book. type skip to skip, type continue to continue");
			String cont = sc.next();
			cont += sc.nextLine();
			if(cont.equals("skip"))
				break;
			System.out.println("select an author for this book");
			ResultSet rs = AuthorModel.getAuthor("N/A",conn);
			try {
				System.out.println("0: there is no author for this book");
				while (rs.next()){
					System.out.println(rs.getInt("authorId")+": "+rs.getString("authorName"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("select one from above");
			int authorId =sc.nextInt();
			if(authorId!=0){
				AuthorModel.addBookAuthor(bookId,authorId,conn);
			}
			System.out.println("select a publisher for this book");
			rs = PublisherModel.getPublisher("N/A",conn);
			try {
				System.out.println("0: there is no publisher for this book");
				while (rs.next()){
					System.out.println(rs.getInt("publisherId")+": "+rs.getString("publisherName"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int publisherId =sc.nextInt();
			if(publisherId!=0){
				BookModel.changeBook(bookId,bookName,publisherId,conn);
			}
			System.out.println("select a genre for this book");
			rs = GenereModel.getGenre("N/A",conn);
			try {
				System.out.println("0: there is no genre for this book");
				while (rs.next()){
					System.out.println(rs.getInt("genre_id")+": "+rs.getString("genre_name"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int genreId =sc.nextInt();
			if(genreId!=0){
				BookModel.addBookGenre(genreId,bookId,conn);
			}
			System.out.println("select a library branch for this book");
			rs = BranchModel.getBranch("N/A",conn);
			try {
				System.out.println("0: there is no library branch for this book");
				while (rs.next()){
					System.out.println(rs.getInt("branchId")+": "+rs.getString("branchName"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int branchId =sc.nextInt();
			if(branchId!=0){
				System.out.println("how many copies does it have?");
				int noCopies=sc.nextInt();
				BookModel.addBook(branchId, bookId, noCopies, conn);
			}
			break;
		case 2:
			System.out.println("which book do you want to update?  type N/A to list all");
			rs = BookModel.getBook(sc.next(),conn);
			try {
				System.out.println("select an book id to update");
				System.out.println("0: return to previous menu");
				while (rs.next()){
					System.out.println(rs.getInt("bookId")+": "+rs.getString("title"));
				}
				int idupdate = sc.nextInt();
				if (idupdate ==0)
					break;
				System.out.println("what's the new book name?");
				bookName = sc.next();
				bookName+= sc.nextLine();
				System.out.println("select a new publisher id");
				rs = PublisherModel.getPublisher("N/A",conn);
				while (rs.next()){
					System.out.println(rs.getInt("publisherId")+": "+rs.getString("publisherName"));
				}
				publisherId = sc.nextInt();
				BookModel.changeBook(idupdate, bookName, publisherId, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("which book do you want to delete?  type N/A to list all");
			rs = BookModel.getBook(sc.next(),conn);
			try {
				System.out.println("select an book id to delete");
				while (rs.next()){
					System.out.println(rs.getInt("bookId")+": "+rs.getString("title"));
				}
				int idupdate = sc.nextInt();
				BookModel.deleteBook(idupdate,conn);
			}catch (Exception e) {e.printStackTrace();}
			break;
		case 4:
			//adminMain(conn);
			break;
		}
		//adminMain(conn);
	}
	public static void publisherAdmin(int action,Connection conn) {
		switch (action){
		case 1:
			System.out.println("what publisher do u want to add?");
			String publisherName = sc.next();
			publisherName+= sc.nextLine();
			System.out.println("what's the publisher address?");
			String publisherAddress = sc.next();
			publisherAddress+= sc.nextLine();
			System.out.println("what's the publisher's phone");
			String publisherPhone = sc.next();
			publisherPhone+= sc.nextLine();
			System.out.println(publisherName);
			System.out.println(publisherAddress);
			System.out.println(publisherPhone);
			PublisherModel.changePublisher(-1, publisherName, publisherAddress, publisherPhone, conn);
			break;
		case 2:
			System.out.println("which publisher do you want to update?  type N/A to list all");
			ResultSet rs = PublisherModel.getPublisher(sc.next(),conn);
			try {
				System.out.println("select an publisher id to update");
				while (rs.next()){
					System.out.println(rs.getInt("publisherId")+": "+rs.getString("publisherName"));
				}
				int idupdate = sc.nextInt();
				System.out.println("what's the new publisher name?");
				publisherName = sc.next();
				publisherName+= sc.nextLine();
				System.out.println("what's the publisher address?");
				publisherAddress = sc.next();
				publisherAddress+= sc.nextLine();
				System.out.println("what's the publisher's phone");
				publisherPhone = sc.next();
				publisherPhone+= sc.nextLine();
				PublisherModel.changePublisher(idupdate, publisherName,publisherAddress,publisherPhone, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("which publisher do you want to delete?  type N/A to list all");
			rs = PublisherModel.getPublisher(sc.next(),conn);
			try {
				System.out.println("select an publisher id to delete");
				while (rs.next()){
					System.out.println(rs.getInt("publisherId")+": "+rs.getString("publisherName"));
				}
				int idDelete = sc.nextInt();
				System.out.println("WARNING Delete publisher is dangerous, It will affact all books in the database!!\n");
				System.out.println("There are two options for you to choose");
				System.out.println("1. Delete all the books the publisher ever published\n"+"2. Keep the book, set book publisher to NULL");
				int deleteType = sc.nextInt();
				
				PublisherModel.deletePublisher(deleteType,idDelete, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			//adminMain(conn);
			break;
		}
		//adminMain(conn);
	}
	public static void branchAdmin(int action,Connection conn) {
		switch (action){
		case 1:
			System.out.println("what branch do u want to add?");
			String branchName = sc.next();
			branchName+= sc.nextLine();
			System.out.println("what's the branch address?");
			String branchAddress = sc.next();
			branchAddress+= sc.nextLine();
			System.out.println(branchName);
			System.out.println(branchAddress);
			BranchModel.changeBranch(-1, branchName, branchAddress, conn);
			break;
		case 2:
			System.out.println("which branch do you want to update?  type N/A to list all");
			ResultSet rs = BranchModel.getBranch(sc.next(),conn);
			try {
				System.out.println("select an branch id to update");
				while (rs.next()){
					System.out.println(rs.getInt("branchId")+": "+rs.getString("branchName"));
				}
				int idupdate = sc.nextInt();
				System.out.println("what's the new branch name?");
				branchName = sc.next();
				branchName+= sc.nextLine();
				System.out.println("what's the branch's address?");
				branchAddress = sc.next();
				branchAddress+= sc.nextLine();
				BranchModel.changeBranch(idupdate, branchName,branchAddress,conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("which branch do you want to delete?  type N/A to list all");
			rs = BranchModel.getBranch(sc.next(),conn);
			try {
				System.out.println("select an branch id to delete");
				while (rs.next()){
					System.out.println(rs.getInt("branchId")+": "+rs.getString("branchName"));
				}
				int idDelete = sc.nextInt();
				BranchModel.deleteBranch(idDelete, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			adminMain(conn);
			break;
		}
		//adminMain(conn);
	}
	public static void borrowerAdmin(int action,Connection conn) {
		switch (action){
		case 1:
			System.out.println("what borrower do u want to add? enter quit to quit");
			String borrowerName = sc.next();
			borrowerName+= sc.nextLine();
			if (borrowerName.equals("quit"))
				break;
			System.out.println("what's the borrower address? enter quit to quit");
			String borrowerAddress = sc.next();
			borrowerAddress+= sc.nextLine();
			if (borrowerAddress.equals("quit"))
				break;
			System.out.println("what's the borrower's phone?");
			String borrowerPhone = sc.next();
			borrowerPhone+= sc.nextLine();
			BorrowerModel.changeBorrower(-1, borrowerName, borrowerAddress,borrowerPhone, conn);
			break;
		case 2:
			System.out.println("which borrower do you want to update?  type N/A to list all");
			ResultSet rs = BorrowerModel.getBorrower(sc.next(), conn);
			
			try {
				System.out.println("select an borrower id to update");
				System.out.println("0: return to previous menu");
				while (rs.next()){
					System.out.println(rs.getInt("cardNo")+": "+rs.getString("Name"));
				}
				
				int idupdate = sc.nextInt();
				if (idupdate ==0)
					break;
				System.out.println("what's the new borrower name?");
				borrowerName = sc.next();
				borrowerName+= sc.nextLine();
				System.out.println("what's the borrower's address?");
				borrowerAddress = sc.next();
				borrowerAddress+= sc.nextLine();
				System.out.println("what's the borrower's phone?");
				borrowerPhone = sc.next();
				borrowerPhone+= sc.nextLine();
				BorrowerModel.changeBorrower(idupdate, borrowerName,borrowerAddress,borrowerPhone,conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("which borrower do you want to delete?  type N/A to list all");
			rs = BorrowerModel.getBorrower(sc.next(),conn);
			try {
				System.out.println("select an borrower id to delete");
				while (rs.next()){
					System.out.println(rs.getInt("cardNo")+": "+rs.getString("name"));
				}
				int idDelete = sc.nextInt();
				BorrowerModel.deleteBorrower(idDelete, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			adminMain(conn);
			break;
		}
		//adminMain(conn);
	}
	public static void overrideDuedate(Connection conn){
		System.out.println("which borrower do you want to change?  type N/A to list all");
		ResultSet rs = BorrowerModel.getBorrower(sc.next(), conn);
		try {
			System.out.println("select an borrower id to update");
			while (rs.next()){
				System.out.println(rs.getInt("cardNo")+": "+rs.getString("Name"));
			}
			int idupdate = sc.nextInt();
			System.out.println("select a record to override");
			rs = BorrowerModel.getLoans(0,idupdate, conn);
			System.out.println("0: Return to previous window");
			int i=1;
			while (rs.next()){
				System.out.println(i+": "+"cardNo "+rs.getInt("cardNo")+"bookTitle "+rs.getString("title")+"branchId: "+rs.getInt("branchId"));
				i++;
			}
			int idoverride = sc.nextInt();
			if (idoverride ==0)
				return;
			rs.absolute(idoverride);
			System.out.println("type the new duedate");
			String date = sc.next();
			date+=sc.nextLine();
			BorrowerModel.overrideDue(rs.getInt("bookId"), rs.getInt("branchId"),date,rs.getInt("cardNo"),conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void genreMain(int action,Connection conn) {
		switch (action){
		case 1:
			System.out.println("what genere do u want to add? quit to quit");
			String genereName = sc.next();
			genereName += sc.nextLine();
			if (!genereName.equals("quit")){
				//System.out.println(genereName);
				GenereModel.changegenere(-1,genereName,conn);
			}
			break;
		case 2:
			System.out.println("which genere do you want to update?  type N/A to list all");
			ResultSet rs = GenereModel.getgenere(sc.next(),conn);
			try {
				System.out.println("select an genere id to update ");
				System.out.println("0: go back");
				while (rs.next()){
					System.out.println(rs.getInt("genereId")+": "+rs.getString("genereName"));
				}
				int idupdate = sc.nextInt();
				if (idupdate==0)
					break;
				System.out.println("what's the new genere name? type quit to quit");
				genereName = sc.next();
				genereName+=sc.nextLine();
				if (!genereName.equals("quit"))
					GenereModel.changegenere(idupdate, genereName, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("which genere do you want to delete?  type N/A to list all");
			rs = GenereModel.getgenere(sc.next(),conn);
			try {
				System.out.println("select an genere id to delete");
				System.out.println("0: I changed my mind take me back");
				while (rs.next()){
					System.out.println(rs.getInt("genereId")+": "+rs.getString("genereName"));
				}
				int idDelete = sc.nextInt();				
				if (idDelete!=0)
				{	System.out.println("delete success!!");
					GenereModel.deletegenere(idDelete, conn);}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			break;
		}

	}
}
