package presentation;

import business.WLClient;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginGUI extends Stage{
	
	private WLClient client;
	private Button loginBtn;
	private Button registerBtn;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Label usernameLabel,passwordLabel;
	private Scene loginScene;
	
	public LoginGUI() {
		usernameLabel = new Label("Username");
		usernameLabel.setTranslateX(-100);
		usernameLabel.setTranslateY(-50);
		usernameTF = new TextField();
		usernameTF.setText("Andrei98");
		usernameTF.setTranslateX(40);
		usernameTF.setTranslateY(-50);
		usernameTF.setMaxWidth(150);
		passwordLabel = new Label("Password");
		passwordLabel.setTranslateX(-100);
		passwordLabel.setTranslateY(-10);
		passwordTF = new PasswordField();
		passwordTF.setText("andrei18");
		passwordTF.setTranslateX(40);
		passwordTF.setTranslateY(-10);
		passwordTF.setMaxWidth(150);
		
		loginBtn=new Button("Login");
		loginBtn.setTranslateY(60);
		loginBtn.setTranslateX(-50);
		loginBtn.setOnAction(e -> {
			if(usernameTF.getText().length()>0 && passwordTF.getText().length()>0) {
				client.login(usernameTF.getText(),passwordTF.getText());
				this.close();
			}
			else {
					Alert failedLogin = new Alert(AlertType.ERROR);
					failedLogin.setHeaderText("Login failed!");
					failedLogin.setContentText("The username or password field is empty!");
					failedLogin.showAndWait();
			}
		});
		
		registerBtn=new Button("Register");
		registerBtn.setTranslateY(60);
		registerBtn.setTranslateX(50);
		registerBtn.setOnAction(e -> {
			if(usernameTF.getText().length()>0 && passwordTF.getText().length()>0) {
				boolean success = client.register(usernameTF.getText(),passwordTF.getText());
				if(success) {
					Alert failedLogin = new Alert(AlertType.INFORMATION);
					failedLogin.setHeaderText("Registration successful!");
					failedLogin.showAndWait();
					}
				else {
					Alert failedLogin = new Alert(AlertType.ERROR);
					failedLogin.setHeaderText("Registration failed!");
					failedLogin.setContentText("The user already exists!");
					failedLogin.showAndWait();
				}
			}
			else {
				Alert failedLogin = new Alert(AlertType.ERROR);
				failedLogin.setHeaderText("Registration failed!");
				failedLogin.setContentText("The username or password field is empty!");
				failedLogin.showAndWait();
			}
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().addAll(usernameLabel,usernameTF,passwordLabel,passwordTF,loginBtn,registerBtn);
		
		loginScene = new Scene(layout,300,200);
		this.setScene(loginScene);
		this.setTitle("WasteLess Login");
		this.show();
	}
	
	public void setClient(WLClient client) {
		this.client = client;
	}
	
}
