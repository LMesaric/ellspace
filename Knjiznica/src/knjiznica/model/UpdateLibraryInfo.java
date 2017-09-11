package knjiznica.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import javafx.scene.control.CheckBox;
import knjiznica.resources.ConnectionData;
import knjiznica.view.UpdateLibraryView;
import knjiznica.view.UpdateUserView;

public class UpdateLibraryInfo implements Runnable {
	
	private static String firstName;
	private static String phoneNumber;
	private static String email;
	private static String information;
	private static String country;
	private static String street;
	private static String houseNumber;
	private static int postalCode;
	private static ArrayList<String> beginTime;
	private static ArrayList<String> endTime;
	private static ArrayList<CheckBox> checkBoxList;
	private static Time timeBegin;
	private static Time timeEnd;
	private static boolean onlineLibraryCheck;
	private static int addressID;
	private static int libraryID;
	
	@Override
	public void run() {
		
		PreparedStatement pstmtAddress = null;
		PreparedStatement pstmtUser = null;
		
		try {
			Connection con = DriverManager.getConnection(
					ConnectionData.getLink(), ConnectionData.getUsername(), ConnectionData.getPassword());
		
			String queryAddress = "UPDATE public.\"Address\" "
					+ "SET \"Country\"=?, \"PostalCode\"=?, \"Street\"=?, \"HouseNumber\"=? " 
					+ "WHERE \"AddressID\"=?;";
			
			pstmtAddress = con.prepareStatement(queryAddress);
		
			pstmtAddress.setString(1, country);
			pstmtAddress.setInt(2, postalCode);
			pstmtAddress.setString(3, street);
			pstmtAddress.setString(4, houseNumber);
			pstmtAddress.setInt(5, addressID);
			
			pstmtAddress.executeUpdate();
			
			String queryUser = "UPDATE public.\"Library\" " 
					+ "SET \"LibraryName\"=?, \"AddressID\"=?, \"PhoneNumber\"=?, \"Email\"=?, \"Information\"=?" 
					+ "WHERE \"LibraryID\"=?";
			
			pstmtUser = con.prepareStatement(queryUser);
			
			pstmtUser.setString(1, firstName);
			pstmtUser.setInt(2, addressID);
			pstmtUser.setString(3, phoneNumber);
			pstmtUser.setString(4, email); 
			pstmtUser.setString(5, information);
			pstmtUser.setInt(6, libraryID);
			
			pstmtUser.executeUpdate();
			
		} catch (PSQLException e) {
			UpdateUserView.isReached = false;
			
		} catch (SQLException e) {
			UpdateUserView.isReached = false;
		} finally {
			try {
				pstmtAddress.close();
				pstmtUser.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void updateLibrary(String firstNameIn, String phoneNumberIn, String emailIn, String informationIn, String countryIn, String streetIn, String houseNumberIn, int postalCodeIn, ArrayList<String> beginTimeIn, ArrayList<String>endTimeIn, ArrayList<CheckBox> checkBoxListIn, boolean onlineLibraryCheckIn) {
		firstName = firstNameIn;
		phoneNumber = phoneNumberIn;
		email = emailIn;
		information = informationIn;
		country = countryIn;
		street = streetIn;
		houseNumber = houseNumberIn;
		postalCode = postalCodeIn;
		beginTime = beginTimeIn;
		endTime = endTimeIn;
		checkBoxList = checkBoxListIn;
		onlineLibraryCheck = onlineLibraryCheckIn;
		
		Thread t = new Thread(new UpdateLibraryInfo());
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			UpdateLibraryView.isInterrupted = true;
		}
	}
}
