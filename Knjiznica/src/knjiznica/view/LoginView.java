package knjiznica.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class LoginView {

	@FXML
	private TextField usernameText;
	
	@FXML
	private PasswordField passwordText;
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private ImageView backgroundImage;
	
	@FXML 
	private BorderPane root;
	
	public void login() {

	}
	
	public void initialize() {
		backgroundImage.fitWidthProperty().bind(root.widthProperty());
		//backgroundImage.fitHeightProperty().bind(root.heightProperty());
	}
	
	@FXML
	private void activateLogin() {

		String username = usernameText.getText();
		String password = passwordText.getText();
		
		System.out.println(username + '\t' + password);
		
		errorLabel.setText("Successful login!");
		errorLabel.setVisible(true);
		
		//clear passwordText ako faila
		
		//close window / view
		
	}
	
}