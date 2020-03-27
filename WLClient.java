package business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import data_access.WLDataAccess;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.BoughtItem;
import presentation.*;

public class WLClient {
	private MainGUI mainGUI;
	private WLDataAccess dataAccess;
	private final File weeklyReports,monthlyReports;
	
	public WLClient() {
		weeklyReports = new File("C:\\Users\\Andrei\\Desktop\\Facultate\\Software_Design\\WasteLess\\weeklyReports.txt");
		monthlyReports = new File("C:\\Users\\Andrei\\Desktop\\Facultate\\Software_Design\\WasteLess\\monthlyReports.txt");
		dataAccess = new WLDataAccess();
	}
	
	public void login(String username, String password) {
		if(dataAccess.login(username, password)==true) {
			mainGUI = new MainGUI(this,username,password);
			mainGUI.initGroceries(dataAccess.readAllGroceries());
			List<BoughtItem> wastedItems = dataAccess.initWastedItems();
			List<BoughtItem> allItems = dataAccess.readAllItems(username);
			updateReports(wastedItems);
			computeRates(allItems);
			mainGUI.initItems(dataAccess.readUnusedItems(username));
		}
		else {
			Alert failedLogin = new Alert(AlertType.ERROR);
			failedLogin.setHeaderText("Login failed!");
			failedLogin.setContentText("Username and password combination not found.");
			failedLogin.showAndWait();
		}
	}
	private void updateReports(List<BoughtItem> wastedItems) {
		Report report = AbstractReportFactory.getFactory("weekly").getReport();
		String weeklyReport = report.createFile(wastedItems,weeklyReports.getAbsolutePath());
		report = AbstractReportFactory.getFactory("monthly").getReport();
		String monthlyReport = report.createFile(wastedItems,monthlyReports.getAbsolutePath());
		
		//reading the reports and sending them to the GUI
		try {
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(weeklyReports.getAbsolutePath()));
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(monthlyReports.getAbsolutePath()));
			bw1.append(weeklyReport);
			bw2.append(monthlyReport);
			bw1.close();
			bw2.close();
			
			mainGUI.initReports(weeklyReport,monthlyReport);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void computeRates(List<BoughtItem> allItems) {
		int idealRate = 0, userRate = 0,  userSum = 0, userN = 0;
		long diffDays = 1;
		Date currentDate, lastDate;

		//calculating the burndown rates
			Collections.sort(allItems, new Comparator<BoughtItem>() {
				@Override
				public int compare(BoughtItem i1, BoughtItem i2) {
					if(i1.getConsumptionDate() == null)
						return -1;
					if(i2.getConsumptionDate() == null)
						return 1;
					if((i1.getConsumptionDate() == null) && (i2.getConsumptionDate() == null))
						return i1.getPurchaseDate().compareTo(i2.getPurchaseDate());
					return i1.getConsumptionDate().compareTo(i2.getConsumptionDate());
				}
			});
			lastDate = new Date(0,0,0);
			for(BoughtItem item : allItems) {
				if(item.getConsumptionDate() == null) {
					diffDays = item.getExpirationDate().getTime() - item.getPurchaseDate().getTime();
					diffDays /= 1000*60*60*24;
					if(diffDays == 0)
						diffDays = 1;
					idealRate += (int) (item.getQuantity() *item.getCalories() / diffDays);
					}
				else {
					currentDate = item.getConsumptionDate();
					if(currentDate.getDay() != lastDate.getDay())
						userN ++;
					diffDays = item.getConsumptionDate().getTime() - item.getPurchaseDate().getTime();
					diffDays /= 1000*60*60*24; 
					if(diffDays == 0)
						diffDays = 1;
					userSum += item.getQuantity() * item.getCalories() / diffDays;
					lastDate = currentDate;
					}
			}
			
			if(userN == 0)
				userN = 1;
			userRate = userSum / userN;
			mainGUI.setRates(idealRate, userRate);
	}
	
	public void logout(String username,String password) {
		dataAccess.logout(username,password);
		mainGUI.close();
	}

	public void createItems(List<BoughtItem> boughtItems,String username) {
		dataAccess.createItems(boughtItems);
		mainGUI.initItems(dataAccess.readUnusedItems(username));
		List<BoughtItem> allItems = dataAccess.readAllItems(username);
		computeRates(allItems);
	}
	
	public void consumeItems(List<String> removedItems,String username) {
		dataAccess.updateItems(removedItems);
		mainGUI.initItems(dataAccess.readUnusedItems(username));
		List<BoughtItem> allItems = dataAccess.readAllItems(username);
		computeRates(allItems);
	}
	
}
