package org.openjfx.sio2E4;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * JavaFX App
 */
public class App extends Application {
	
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/sio2E4/loginPage.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Gnotes");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    


	/*private static Scene scene;

	private final TableView<Item> tableView = new TableView<>();
	private final ObservableList<Item> data = FXCollections.observableArrayList(new Item("Mathématiques", "coeff"),
			new Item("Physique", "coeff"));
	final HBox hbox = new HBox();

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws IOException {

		scene = new Scene(new Group());
		stage.setTitle("Vue des matières");
		stage.setMinWidth(640);
		stage.setMinHeight(600);

		final Label label = new Label("Les matières");
		label.setFont(new Font("Arial", 30));

		tableView.setEditable(true);
		tableView.minWidth(600d);
		tableView.minHeight(400d);

		Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory = (
				TableColumn<Item, String> p) -> new EditingCell();

		TableColumn<Item, String> column1 = new TableColumn<>("Colonne 1");
		column1.setMinWidth(200);
		column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
//		column1.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		column1.setCellFactory(cellFactory);
		column1.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumn1(t.getNewValue());
		});

		TableColumn<Item, String> column2 = new TableColumn<>("Colonne 2");
		column2.setMinWidth(200);
		column2.setCellValueFactory(new PropertyValueFactory<>("column2"));
//		column2.setCellFactory(TextFieldTableCell.<Item>forTableColumn());
		column2.setCellFactory(cellFactory);
		column2.setOnEditCommit((CellEditEvent<Item, String> t) -> {
			((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setColumn2(t.getNewValue());
		});

		TableColumn<Item, Button> deleteColumn = new TableColumn<>("Supprimer");
		deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

		tableView.setItems(data);
		tableView.getColumns().addAll(column1, column2, deleteColumn);
		
		final TextField addColumn1 = new TextField();
		addColumn1.setPromptText("column1");
		addColumn1.setMinWidth(column1.getMinWidth());
		final TextField addColumn2 = new TextField();
		addColumn2.setPromptText("column2");
		addColumn2.setMinWidth(column1.getMinWidth());

		final Button newButton = new Button("New");
		newButton.setOnAction((ActionEvent e) -> {
			data.add(new Item(addColumn1.getText(), addColumn2.getText()));
			addColumn1.clear();
			addColumn2.clear();
		});

		hbox.getChildren().addAll(addColumn1, addColumn2, newButton);
		hbox.setSpacing(3);

		Button addButton = new Button("Add");
		addButton.setOnAction(event -> addEmptyRow());

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, addButton, tableView, hbox);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();

//        scene = new Scene(loadFXML("primary"), 640, 480);
//        stage.setScene(scene);
//        stage.show();
	}

	private void addEmptyRow() {
		Item newItem = new Item("", ""); // Create a new item with
											// empty values
		tableView.getItems().add(newItem);
		tableView.setEditable(true);
	}

	public class Item {
		private SimpleStringProperty column1;
		private SimpleStringProperty column2;
		private Button deleteButton;

		public Item(String column1, String column2) {
			this.column1 = new SimpleStringProperty(column1);
			this.column2 = new SimpleStringProperty(column2);
			;

			deleteButton = new Button("Supprimer");
			deleteButton.setOnAction(event -> tableView.getItems().remove(this));
		}

		public String getColumn1() {
			return column1.get();
		}

		public String getColumn2() {
			return column2.get();
		}

		public Button getDeleteButton() {
			return deleteButton;
		}

		public void setColumn1(String column1) {
			this.column1.set(column1);
		}

		public void setColumn2(String column2) {
			this.column2.set(column2);
		}
	}

	class EditingCell extends TableCell<Item, String> {

		private TextField textField;

		public EditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createTextField();
				setText(null);
				setGraphic(textField);
				textField.selectAll();
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText((String) getItem());
			setGraphic(null);
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.focusedProperty()
					.addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
						if (!arg2) {
							commitEdit(textField.getText());
						}
					});
		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}*/

}