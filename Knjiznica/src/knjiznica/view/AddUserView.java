package knjiznica.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import knjiznica.model.AddUserToDatabase;
import knjiznica.model.CheckInputLetters;
import knjiznica.model.FormattingName;
import knjiznica.model.PostalCodeComboThread;
import knjiznica.model.ViewProvider;

public class AddUserView {
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private TextField middleNameField;

	@FXML
	private TextField lastNameField;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField phoneNumberField;
	
	@FXML
	private TextField countryField;
	
	@FXML
	private TextField streetField;
	
	@FXML
	private TextField houseNumberField;
	
	@FXML
	private Button addButton;
	
	@FXML
	private Button backButton;
	
	@FXML
	private ComboBox<String> postalCodeCombo;
	
	@FXML
	private Label errorLabel;
	
	public static boolean isInterrupted = false;
	public static boolean isReached = true;
	
	private String nameCombo = "postalCodeComboAddUser";
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String country;
	private String street;
	private String houseNumber;
	String postalCode;
	private SingleSelectionModel<String> postalCodeSingle;
	private boolean check;
	
	public void initialize() {
		Image imageAddButton = new Image(getClass().getResourceAsStream("../resources/add-button.png"));
		addButton.setGraphic(new ImageView(imageAddButton));
		addButton.setId("transparentButton");
		
		Image imageBackButton = new Image(getClass().getResourceAsStream("../resources/back-button.png"));
		backButton.setGraphic(new ImageView(imageBackButton));
		backButton.setId("transparentButton");
		
		ViewProvider.setView(nameCombo, postalCodeCombo);
		PostalCodeComboThread.setComboData(nameCombo);
	}
	
	@FXML
	private void activateBack() throws IOException {
		BorderPane usersPopup = (BorderPane) ViewProvider.getView("usersPopup");
		((BorderPane) ViewProvider.getView("mainScreen")).setCenter(usersPopup);
	}
	
	@FXML
	private void activateAdd() throws IOException {
		
		isInterrupted = false;
		isReached = true;
		
		errorLabel.setVisible(false);
		postalCodeSingle = postalCodeCombo.getSelectionModel();
		firstName = firstNameField.getText();
		middleName = middleNameField.getText();
		lastName = lastNameField.getText();
		email = emailField.getText();
		phoneNumber = phoneNumberField.getText();
		country = countryField.getText();
		street = streetField.getText();
		houseNumber = houseNumberField.getText();
		
		firstName = firstName.trim();
		middleName = middleName.trim();
		lastName = lastName.trim();
		email = email.trim();
		phoneNumber = phoneNumber.trim();
		country = country.trim();
		street = street.trim();
		houseNumber = houseNumber.trim();
		
		firstNameField.setStyle("");
		middleNameField.setStyle("");
		lastNameField.setStyle("");
		postalCodeCombo.setStyle("");
		emailField.setStyle("");
		phoneNumberField.setStyle("");
		countryField.setStyle("");
		streetField.setStyle("");
		houseNumberField.setStyle("");
		
		final String redBorder ="-fx-border-color: #ff0000;\n";
		
		/*
		 * DOUBLED EMAIL NOT UNIQUE
		 */
		
		check = true;
			
			
		if (firstName.isEmpty()) {
			check = false;
			firstNameField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (lastName.isEmpty()) {
			check = false;
			lastNameField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (postalCodeSingle.isEmpty()) {
			check = false;
			postalCodeCombo.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (email.isEmpty()) {
			check = false;
			emailField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (phoneNumber.isEmpty()) {
			check = false;
			phoneNumberField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (country.isEmpty()) {
			check = false;
			countryField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (street.isEmpty()) {
			check = false;
			streetField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		} if (houseNumber.isEmpty()) {
			check = false;
			houseNumberField.setStyle(redBorder);
			errorLabel.setText("Missing information");
			errorLabel.setVisible(true);
			
		}
		
		if (!firstName.isEmpty() && !CheckInputLetters.check(firstName)) {
			check = false;
			firstNameField.setStyle(redBorder);
			if(!errorLabel.isVisible()) {
				errorLabel.setText("Verify that you have entered the correct information.");
				errorLabel.setVisible(true);
				
			}
			
		}
		
		if (!middleName.isEmpty() && !CheckInputLetters.check(middleName)) {
			check = false;
			middleNameField.setStyle(redBorder);
			if(!errorLabel.isVisible()) {
				errorLabel.setText("Verify that you have entered the correct information.");
				errorLabel.setVisible(true);
				
			}
			
		}
		
		if (!lastName.isEmpty() && !CheckInputLetters.check(lastName)) {
			check = false;
			lastNameField.setStyle(redBorder);
			if(!errorLabel.isVisible()) {
				errorLabel.setText("Verify that you have entered the correct information.");
				errorLabel.setVisible(true);
				
			}
			
		}
		
		if (houseNumber.length() > 6) {
			check = false;
			houseNumberField.setStyle(redBorder);
			if(!errorLabel.isVisible()) {
				errorLabel.setText("Verify that you have entered the correct information.");
				errorLabel.setVisible(true);
				
			}
			
		}
		
		if (phoneNumber.length() > 20) {
			check = false;
			phoneNumberField.setStyle(redBorder);
			if(!errorLabel.isVisible()) {
				errorLabel.setText("Verify that you have entered the correct information.");
				errorLabel.setVisible(true);
				
			}
			
		}
		
		if (check) {
			
			postalCode = postalCodeSingle.getSelectedItem();
			firstName = FormattingName.format(firstName);
			lastName = FormattingName.format(lastName);
			
			errorLabel.setVisible(false);
			
			int postalCodeInt = Integer.parseInt((postalCode.split(" - "))[0]);
			
			AddUserToDatabase.addUser(firstName, middleName, lastName, email, phoneNumber, country, postalCodeInt, street, houseNumber);
			
			if(!isInterrupted && isReached) {
				
				Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Information Dialog");
	    		alert.setHeaderText(null);
	    		alert.setContentText("User successfully added!");
	    		
	    		alert.initModality(Modality.APPLICATION_MODAL);
	    		alert.initOwner((Stage) ViewProvider.getView("primaryStage"));  
	    		
	    		alert.showAndWait();
	    		
				BorderPane addUser = (BorderPane) FXMLLoader.load(getClass().getResource("AddUser-view.fxml"));
		    	((BorderPane) ViewProvider.getView("mainScreen")).setCenter(addUser);
		    	
			} else if (isInterrupted) {
				errorLabel.setText("Something went wrong!");
				errorLabel.setVisible(true);
				
			} else {
				errorLabel.setText("Couldn't reach database!");
				errorLabel.setVisible(true); 
				
			}
			
		}
			
		
	}
}
