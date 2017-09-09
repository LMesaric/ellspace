package knjiznica.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import knjiznica.model.GlobalCollection;
import knjiznica.model.User;

public class ListUsersView {
	
	@FXML
	private TableView<User> tableUserList;
	
	@FXML
	private TableColumn<User, String> firstNameCol;
	
	@FXML
	private TableColumn<User, String> middleNameCol;
	
	@FXML
	private TableColumn<User, String> lastNameCol;
	
	@FXML
	private TableColumn<User, String> countryCol;
	
	@FXML
	private TableColumn<User, Integer> postalCodeCol;
	
	@FXML
	private TableColumn<User, String> streetCol;
	
	@FXML
	private TableColumn<User, String> houseNumberCol;
	
	@FXML
	private TableColumn<User, String> phoneNumberCol;
	
	public void initialize() {
		
		for(int i = 0; i < 30; ++i) {
			GlobalCollection.getList().add(new User("Luka", "", "Mesaric", "Croatia", 10000, "Dugi dol", "13", "019827"));
		}
		
		tableUserList.setItems(GlobalCollection.getList());
		firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		middleNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("middleName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
		postalCodeCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("postalCode"));
		streetCol.setCellValueFactory(new PropertyValueFactory<User, String>("street"));
		houseNumberCol.setCellValueFactory(new PropertyValueFactory<User, String>("houseNumber"));
		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
		
		
	}
}
