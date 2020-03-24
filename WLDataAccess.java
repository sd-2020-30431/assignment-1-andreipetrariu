package data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.BoughtItem;
import model.ShopItem;
import model.User;

public class WLDataAccess {

	private static SessionFactory factory;
	private Session session;
	private int id;
	
	public WLDataAccess() {
		factory = new Configuration().configure().addAnnotatedClass(BoughtItem.class).addAnnotatedClass(ShopItem.class).addAnnotatedClass(User.class).buildSessionFactory();
	}
	
	public boolean login(String username, String password) {
			session = factory.getCurrentSession();
			session.beginTransaction();
			//Query query = session.createQuery("from User w where w.username = :username and w.password = :password and w.status=0")
			//	    .setParameter("username", username)
			//	    .setParameter("password", password);
			Query query = session.createSQLQuery("select id_users from users where username = :username and password = :password and status=0")
					    .setParameter("username", username)
					    .setParameter("password", password);
			if(query.list().isEmpty()) {
				session.close();
				return false;
			}
			id = (int) query.getResultList().get(0);
			session.get(User.class,id).setStatus(1);
			query = session.createSQLQuery(
				    "update users set status = 0" + " where username = :username ");
			query.setParameter("username", username);
			query.executeUpdate();
			session.getTransaction().commit();
			session.close();
			return true;
	}
	public void logout(String username,String password){
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(
				    "update users set status = 0" + " where username = :username");
			query.setParameter("username", username);
			query.executeUpdate();
			session.getTransaction().commit();
		}
 	    finally {
 	    	session.close();
 		    factory.close();}
    }
	
	public void createItem(BoughtItem item) {
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.save(item);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			Alert failedLogin = new Alert(AlertType.ERROR);
			failedLogin.setHeaderText("Create Item failed!");
			failedLogin.setContentText(e.toString());
			failedLogin.showAndWait();
		}
		finally {
			session.close();
		}
	}
	
	public List<ShopItem> readAllGroceries() {
		List<ShopItem> groceries = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("from ShopItem");
			groceries = query.list();
			session.getTransaction().commit();
		}
		finally{
			session.close();
		}
		return groceries;
	}
	
	public List<BoughtItem> readAllItems(String username) {
		List<BoughtItem> results = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			results = session.createQuery("from BoughtItem w where w.fk_user = :id").setParameter("id",id).list();
			session.getTransaction().commit();
		}
		finally {
			session.close();
			return null;
		}
	}
	
	//todo: initItems - called at the beginning of the application
		//checks the item database and moves items that have been wasted to the report
	

}
