package business;

import data_access.WLDataAccess;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import presentation.*;

public class WLClient {
	private MainGUI mainGUI;
	private WLDataAccess dataAccess;
	
	public WLClient() {
		dataAccess = new WLDataAccess();
	}
	
	public void login(String username, String password) {
		if(dataAccess.login(username, password)==true) {
			mainGUI = new MainGUI(this,username,password);
			mainGUI.initGroceries(dataAccess.readAllGroceries());
			mainGUI.initItems(dataAccess.getItems(username));
		}
		else {
			Alert failedLogin = new Alert(AlertType.ERROR);
			failedLogin.setHeaderText("Login failed!");
			failedLogin.setContentText("Username and password combination not found.");
			failedLogin.showAndWait();
		}
		
	}
	
	public void logout(String username,String password) {
		dataAccess.logout(username,password);
		mainGUI.close();
	}
	
}
