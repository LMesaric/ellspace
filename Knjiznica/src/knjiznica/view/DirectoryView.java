package knjiznica.view;

import java.util.concurrent.Executor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

import org.controlsfx.control.MaskerPane;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import knjiznica.model.Book;
import knjiznica.model.GlobalCollection;
import knjiznica.model.SelectBooks;
import knjiznica.model.ViewProvider;

public class DirectoryView {
	
	@FXML
	private TextField searchField;
	
	@FXML
	private TableView<Book> tableBookList;
	
	@FXML
	private TableColumn<Book, String> ISBNCol;
	
	@FXML
	private TableColumn<Book, String> titleCol;
	
	@FXML
	private TableColumn<Book, String> authorsCol;
	
	@FXML
	private TableColumn<Book, String> publishersCol;
	
	@FXML
	private TableColumn<Book, String> genresCol;
	
	@FXML
	private TableColumn<Book, String> languagesCol;
	
	@FXML
	private TableColumn<Book, String> informationCol;
	
	@FXML
	private TableColumn<Book, Integer> numberCol;
	
	@FXML
	private TableColumn<Book, String> yearCol;
	
	@FXML
	private TableColumn<Book, Integer> pagesCol;
	
	@FXML
	private TableColumn<Book, String> ownerCol;
	
	@FXML
	private TableColumn<Book, String> locationCol;
	
	@FXML
	private TableColumn<Book, Boolean> availableCol;
	
	@FXML
	private TableColumn<Book, Date> returnDateCol;
	
	private static StackPane sp = (StackPane) ViewProvider.getView("stackPane");
	private static Executor exec;
	
	public void initialize() {
		
		exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });
		
		sp.getChildren().add((MaskerPane) ViewProvider.getView("mask"));
		
		ArrayList<Book> books = new ArrayList<Book>();
		
		Task<ArrayList<Book>> getBooksTableTask = new Task<ArrayList<Book>>() {
            @Override
            public ArrayList<Book> call() throws Exception {
            	
    			Thread.sleep(600);
    			
    			return SelectBooks.select();  
            }
		};
		
		getBooksTableTask.setOnSucceeded(e -> {
			sp.getChildren().remove((MaskerPane) ViewProvider.getView("mask"));
			books.addAll(getBooksTableTask.getValue());
			populateTable(books);
		});
		
		//TODO after thread fails populate users table
		
		exec.execute(getBooksTableTask);
	}
	
	public void populateTable(ArrayList<Book> books) {
		
		GlobalCollection.emptyList();
		
		for (int i = 0; i < books.size(); ++i) {
			GlobalCollection.getBooksList().add(books.get(i));
		}
		
		tableBookList. setItems(GlobalCollection.getBooksList());
		FilteredList<Book> filteredData = new FilteredList<Book>(GlobalCollection.getBooksList(), e -> true);
		searchField.setOnKeyReleased(e -> {
			searchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Book>) book -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					
					String lowerCaseFilter = newValue.toLowerCase();
					String[] splitStr = lowerCaseFilter.split(" ");
					ArrayList<String> splittedFilter = new ArrayList<String>();
					ArrayList<String> splittedBookData = new ArrayList<String>();
					
					for (int i = 0; i < splitStr.length; ++i) {
						splittedFilter.add(splitStr[i]);
					}
					
					splittedBookData.add(book.getAuthorsName().toLowerCase());
					splittedBookData.add(book.getCurrentLocationName().toLowerCase());
					splittedBookData.add(Integer.toString(book.getEditionNumber()));
					splittedBookData.add(Integer.toString(book.getEditionNumberOfPages()));
//					splittedBookData.add(book.getEditionYear());
					splittedBookData.add(book.getGenresName().toLowerCase());
//					splittedBookData.add(book.getInformation().toLowerCase());
					splittedBookData.add(book.getISBN());
					splittedBookData.add(book.getLanguagesName().toLowerCase());
					splittedBookData.add(book.getOwnerName().toLowerCase());
					splittedBookData.add(book.getPublishersName().toLowerCase());
//					splittedBookData.add(book.getReturnDate().toString(). toLowerCase());
//					splittedBookData.add(book.getSummary(). toLowerCase());
					splittedBookData.add(book.getTitle(). toLowerCase());
					
					int i;
					for (i = 0; i < splittedFilter.size(); ++i) {
						int j;
						for (j = 0; j < splittedBookData.size(); ++j) {
							if (splittedBookData.get(j).contains(splittedFilter.get(i))) {
								break;
							}
						}
						
						if (j == splittedBookData.size()) {
							break;
						}
					}
					
					if (i == splittedFilter.size()) {
						return true;
					}
					
					return false;
				});
			});
			
			SortedList<Book> sortedData = new SortedList<Book>(filteredData);
			sortedData.comparatorProperty().bind(tableBookList.comparatorProperty());
			tableBookList.setItems(sortedData);
		});
		ISBNCol.  	   setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN"));
		ISBNCol.  	   setStyle("-fx-alignment: CENTER;");
		titleCol.      setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		titleCol. 	   setStyle("-fx-alignment: CENTER;");
		authorsCol.    setCellValueFactory(new PropertyValueFactory<Book, String>("authorsName"));
		authorsCol.    setStyle("-fx-alignment: CENTER;");
		publishersCol. setCellValueFactory(new PropertyValueFactory<Book, String>("publishersName"));
		publishersCol. setStyle("-fx-alignment: CENTER;");
		genresCol.     setCellValueFactory(new PropertyValueFactory<Book, String>("genresName"));
		genresCol.     setStyle("-fx-alignment: CENTER;");
		languagesCol.  setCellValueFactory(new PropertyValueFactory<Book, String>("languagesName"));
		languagesCol.  setStyle("-fx-alignment: CENTER;");
		informationCol.setCellValueFactory(new PropertyValueFactory<Book, String>("information"));
		informationCol.setStyle("-fx-alignment: CENTER;");
		numberCol.     setCellValueFactory(new PropertyValueFactory<Book, Integer>("editionNumber"));
		numberCol.     setStyle("-fx-alignment: CENTER;");
		yearCol.       setCellValueFactory(new PropertyValueFactory<Book, String>("editionYear")); 
		yearCol.       setStyle("-fx-alignment: CENTER;");
		pagesCol.      setCellValueFactory(new PropertyValueFactory<Book, Integer>("editionNumberOfPages"));
		pagesCol.      setStyle("-fx-alignment: CENTER;");
		ownerCol.      setCellValueFactory(new PropertyValueFactory<Book, String>("ownerName"));
		ownerCol.      setStyle("-fx-alignment: CENTER;");
		locationCol.   setCellValueFactory(new PropertyValueFactory<Book, String>("currentLocationName"));
		locationCol.   setStyle("-fx-alignment: CENTER;");
		availableCol.  setCellValueFactory(new PropertyValueFactory<Book, Boolean>("available"));
		availableCol.  setStyle("-fx-alignment: CENTER;");
		returnDateCol. setCellValueFactory(new PropertyValueFactory<Book, Date>("returnDate"));
		returnDateCol. setStyle("-fx-alignment: CENTER;");
		
		tableBookList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					//TODO click event
				}
			}
		});
		
		
	}
}
