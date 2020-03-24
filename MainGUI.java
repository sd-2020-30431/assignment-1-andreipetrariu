package presentation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import business.WLClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.BoughtItem;
import model.ShopItem;

public class MainGUI extends Stage{
	
	private WLClient client;
	private List<BoughtItem> groceriesList = new ArrayList<BoughtItem>();
	private BorderPane gLayout,iLayout,rLayout,dLayout;
	private Scene groceriesScene;
	private Scene itemsScene;
	private Scene reportsScene;
	private Scene donationScene;
	private VBox[] items;
	private HBox[] menuBar;
	private String username,password;
	
	public MainGUI(WLClient client,String username,String password) {
		this.client = client;
		this.username = username;
		this.password = password;
		menuBar = new HBox[4];

		rLayout = new BorderPane();
		dLayout = new BorderPane();
		
		for(int i = 0; i < 4; i++) {
			Label menuTitle = new Label("WASTE LESS");
			menuTitle.setFont(new Font(17));
			Label userLabel = new Label(username+"\t\t");
			userLabel.setFont(new Font(15));
			Button gBtn = new Button("Groceries");
			Button iBtn = new Button("Bought Items");
			Button rBtn = new Button("Reports");
			Button dBtn = new Button("Donate");
			Button logoutBtn = new Button("Logout");
			
			gBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(groceriesScene);
				this.show();
				((HBox)gLayout.getChildren().get(0)).getChildren().get(2).requestFocus();
				});
			iBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(itemsScene);
				this.show();
				((HBox)iLayout.getChildren().get(0)).getChildren().get(3).requestFocus();
			});
			rBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(reportsScene);
				this.show();
				((HBox)rLayout.getChildren().get(0)).getChildren().get(4).requestFocus();
			});
			dBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setScene(donationScene);
				this.show();
				((HBox)dLayout.getChildren().get(0)).getChildren().get(5).requestFocus();
			});
			logoutBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, e-> {
				client.logout(username,password);
			});
			
			menuBar[i] = new HBox();
			menuBar[i].setTranslateX(36);
			menuBar[i].getChildren().add(menuTitle);
			menuBar[i].getChildren().add(new Label("\t\t\t\t\t\t\t"));
			menuBar[i].getChildren().add(gBtn);
			menuBar[i].getChildren().add(iBtn);
			menuBar[i].getChildren().add(rBtn);
			menuBar[i].getChildren().add(dBtn);
			menuBar[i].getChildren().add(new Label("\t\t\t\t\t"));
			menuBar[i].getChildren().add(userLabel);
			menuBar[i].getChildren().add(logoutBtn);
			menuBar[i].setStyle("-fx-padding: 25px;-fx-border-insets:25px");
		}
		
		rLayout.setTop(menuBar[2]);
		dLayout.setTop(menuBar[3]);
		
		reportsScene = new Scene(rLayout,900,590);
		donationScene = new Scene(dLayout,900,590);
		
		this.setScene(groceriesScene);
		this.show();
	}
	
	public void initGroceries(List<ShopItem> groceries) {
		items = new VBox[groceries.size()];
		HBox gBottomBox = new HBox();
		ScrollPane gSP = new ScrollPane();
		gLayout = new BorderPane();
		GridPane gLayout1 = new GridPane();
		Button gBtn = new Button("Purchase selected groceries");
		
		Date today = new Date();
		Date expirationDate;
		Calendar c = Calendar.getInstance();

		gLayout.setTop(menuBar[0]);
		gBottomBox.getChildren().addAll(gBtn,new Label("        \t"));
		gBottomBox.setStyle("-fx-padding: 20px;-fx-border-insets:20px");
		gBottomBox.setAlignment(Pos.BOTTOM_RIGHT);
		gBtn.setStyle("-fx-background-color: lightgreen");
		
		for(int i = 0; i < groceries.size(); i++) {
			c.setTime(today);
			c.add(Calendar.DATE, groceries.get(i).getDays());
			expirationDate = c.getTime();
			
			TextField quantityTF = new TextField();
			Button plus = new Button("+");
			Button minus = new Button("-");
			plus.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				int q;
				try{q = Integer.parseInt(quantityTF.getText());}
				catch(NumberFormatException ex) {
					q=0;
				}
				quantityTF.setText(String.valueOf(q+1));
			});
			minus.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				int q;
				try{q = Integer.parseInt(quantityTF.getText());}
				catch(NumberFormatException ex) {
					q = 0;
				}quantityTF.setText(String.valueOf(q - 1));
			});
			items[i] = new VBox(10);
			items[i].setTranslateX(15);
			//items[i].setTranslateY((i/3)*35 + 60);
			items[i].setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
			        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
			        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
			HBox hbox = new HBox(10);
			hbox.getChildren().addAll(new Label(groceries.get(i).getName()),quantityTF,plus,minus);
			items[i].getChildren().add(hbox);
			items[i].getChildren().add(new Label("Expiration date: "+expirationDate));
			gLayout1.add(items[i],(i%3),i/3,1,1);
		}
		
		gSP.setContent(gLayout1);
		gLayout.setCenter(gSP);
		gLayout.setBottom(gBottomBox);
		
		groceriesScene = new Scene(gLayout,940,590);
		this.setX(-5);
		this.setY(5);
		this.setScene(groceriesScene);
	}
	
	public void initItems(List<BoughtItem> items) {
		int i = 0, j = 0;
		Date lastDate, theDate;
		lastDate = new Date(0,0,0);
		GridPane iLayout1 = new GridPane();
		ScrollPane iSP = new ScrollPane();
		iLayout = new BorderPane();
		HBox iBottomBox = new HBox();
		Button iBtn = new Button("Remove selected items");
		
		iLayout1.setVgap(30);
		iLayout1.setHgap(10);
		iBottomBox.getChildren().addAll(iBtn,new Label("        \t"));
		iBottomBox.setStyle("-fx-padding: 20px;-fx-border-insets:20px");
		iBottomBox.setAlignment(Pos.BOTTOM_RIGHT);
		iLayout.setTop(menuBar[1]);
		Collections.sort(items, new Comparator<BoughtItem>() {
			@Override
			public int compare(BoughtItem i1, BoughtItem i2) {
					return i1.getPurchaseDate().compareTo(i2.getPurchaseDate());
			}
		});
		for(BoughtItem item : items) {
			theDate = item.getPurchaseDate();
			if(!(theDate.getYear() == lastDate.getYear() && theDate.getMonth() == lastDate.getMonth()
					&& theDate.getDay() == lastDate.getDay())) {
				i++;
				j = 0;
				iLayout1.add(new Label(item.getPurchaseDate().toLocaleString()),0,i,1,1);
			}
			iLayout1.add(new Label("\t" + item.getName() + " x " + item.getQuantity() + "  "),++j,i);
			iLayout1.add(new CheckBox(),++j,i);
			lastDate = theDate;
		}
		
		iLayout1.setStyle("-fx-padding:15px");
		iSP.setContent(iLayout1);
		iLayout.setCenter(iSP);
		iLayout.setBottom(iBottomBox);
		itemsScene = new Scene(iLayout,940,590);
	}
	
}
