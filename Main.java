package presentation;

import business.WLClient;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	private Stage loginGUI;
	private WLClient client;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void setClient(WLClient client) {
		this.client = client;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		loginGUI = new LoginGUI(client);
		loginGUI.show();
	}
}
