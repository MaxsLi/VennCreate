package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ShapeSceneController implements Initializable {

	@FXML
	private AnchorPane mainScene;

	@FXML
	private StackPane stackPane;

	@FXML
	private Circle leftCircle;

	@FXML
	private Circle rightCircle;

	@FXML
	private Button addBttn;

	@FXML
	private Button editBttn;

	@FXML
	private Button removeButton;

	@FXML
	private TextField diagramText;

	@FXML
	private TextField appTitle;

	@FXML
	private ColorPicker leftColorPicker;

	@FXML
	private ColorPicker rightColorPicker;

	@FXML
	private ContextMenu textFieldContext;

	private MainApp mainApp;

	private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;

	private ArrayList<TextField> current;

	public ShapeSceneController() {
		current = new ArrayList<>();
	}

	/**
	 * On click, creates a textArea which can be dragged into Respective Circle
	 */
	public void addTextToDiagram() {
		if (this.diagramText.getText().isEmpty() || this.diagramText.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Empty TextField");
			alert.setContentText("Please enter some Text to the TextField under the Venn Diagram");
			alert.showAndWait();

		} else {
			String newText = this.diagramText.getText();

			TextField newTextBox = new TextField(newText);
			newTextBox.setEditable(false);
			newTextBox.resizeRelocate(leftCircle.getCenterX(), leftCircle.getCenterY(), 1, 1);

			newTextBox.setMaxWidth(newText.length() + 50);

			stackPane.getChildren().add(newTextBox);
			current.add(newTextBox);
			addDragEvent(newTextBox);
			addContext(newTextBox);
		}

	}
	
	public void handle(KeyEvent e) {
		if( e.getCode() == KeyCode.ENTER) {
			addTextToDiagram();
		}
		
		e.consume();
		}

	private void addDragEvent(TextField newTextBox) {
		newTextBox.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = newTextBox.getTranslateX();
			orgTranslateY = newTextBox.getTranslateY();

			newTextBox.toFront();
		});

		newTextBox.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			newTextBox.setTranslateX(newTranslateX);
			newTextBox.setTranslateY(newTranslateY);

		});
	}
	
		

	public void addContext(TextField text) {
		ContextMenu context = new ContextMenu();
		MenuItem delete = new MenuItem("Delete");
		MenuItem edit = new MenuItem("Edit");
		context.getItems().add(delete);
		context.getItems().add(edit);
		text.setContextMenu(context);

		delete.setOnAction((event) -> {
			stackPane.getChildren().remove(text);
		});

		edit.setOnAction((event) -> {
			text.setEditable(true);
		});

	}

	public void loadVenn() {

		try {
			FileReader fr = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\application\\save.csv");
			BufferedReader br = new BufferedReader(fr);
			String[] parts;
			String s;
			TextField tf;
			while ((s = br.readLine()) != null) {
				parts = s.split(", ");
				tf = new TextField();
				tf.setText(parts[0]);
				tf.setEditable(false);
				tf.resizeRelocate(0, 0, 1, 1);
				tf.resize(50, 50);
				tf.setMinWidth(50);
				tf.setPrefWidth(50);
				tf.setMaxWidth(400);
				tf.setTranslateX(Double.parseDouble(parts[1]));
				tf.setTranslateY(Double.parseDouble(parts[2]));
				stackPane.getChildren().add(tf);
				current.add(tf);
				addDragEvent(tf);
			}
			fr.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// for tester
	public void setStackPane(StackPane sp) {
		this.stackPane = sp;

	}

	public ArrayList<TextField> getTextFields() {
		return current;
	}

	public void saveVenn(ArrayList<TextField> write) {
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\application\\save.csv",
					false);

			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			for (TextField textField : write) {

				pw.write(textField.getText() + ", " + textField.getTranslateX() + ", " + textField.getTranslateY()
						+ "\n");
				pw.flush();
			}
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeLeftColor() {
		leftCircle.setFill(leftColorPicker.getValue());
	}

	public void changeRightColor() {
		rightCircle.setFill(rightColorPicker.getValue());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
