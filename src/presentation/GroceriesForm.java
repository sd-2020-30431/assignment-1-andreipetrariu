package presentation;

import business.WLClient;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GroceriesForm extends Stage{

	public GroceriesForm(WLClient client) {
		Label title = new Label("    Please enter the details of the item");
		Button createBtn = new Button("Create");
		VBox vbox = new VBox();
		VBox labels = new VBox();
		VBox textfields = new VBox();
		createBtn.setScaleX(1.4);
		createBtn.setScaleY(1.4);
		createBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
			String name = ((TextField)textfields.getChildren().get(0)).getText();
			String calories = ((TextField)textfields.getChildren().get(2)).getText();
			String days = ((TextField)textfields.getChildren().get(4)).getText();
			if(name.length()>0 && calories.length()>0 && days.length()>0) {
				int caloriesInt = Integer.parseInt(calories);
				int daysInt = Integer.parseInt(days);
				client.createGroceries(name,caloriesInt,daysInt);
			}
			else {
				Alert failedLogin = new Alert(AlertType.ERROR);
				failedLogin.setHeaderText("Item creation failed!");
				failedLogin.setContentText("All fields are necessary!");
				failedLogin.showAndWait();
			}
		});
		title.setFont(new Font(21));
		HBox hbox = new HBox();
		Label spaces0 = new Label("     ");
		Label spaces1 = new Label("     ");
		Label spaces2 = new Label("     ");
		Label spaces3 = new Label("     ");
		Label spaces4 = new Label("     ");
		Label l1 = new Label("Name   ");
		Label l2 = new Label("Calories   ");
		Label l3 = new Label("Days until expiration   ");
		l1.setFont(new Font(16));
		l2.setFont(new Font(16));
		l3.setFont(new Font(16));
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		labels.getChildren().addAll(l1,spaces0,l2,spaces1,l3);
        labels.setAlignment(Pos.BASELINE_CENTER);
		textfields.getChildren().addAll(tf1,spaces2,tf2,spaces3,tf3);
		hbox.getChildren().addAll(labels,textfields);
		BorderPane theLayout = new BorderPane();
		vbox.getChildren().addAll(hbox,spaces4,createBtn);
        vbox.setAlignment(Pos.BASELINE_CENTER);
		vbox.setStyle("-fx-padding: 20px");
		theLayout.setCenter(vbox);
		theLayout.setTop(title);
		Scene theScene = new Scene(theLayout,400,250);
		this.setScene(theScene);
		this.show();
	}

}
