package dad.bibliotecafx.db;

import org.hibernate.Session;

import dad.bibliotecafx.utils.HibernateUtil;

public class DataBase {

	private static Session sesion;

	public static void connect() {
		sesion = HibernateUtil.getSessionFactory().openSession();
	}

	public static Session getSession() {
		return sesion;
	}
	
	public static void begin() {
		getSession().beginTransaction();		
	}
	
	public static void commit() {
		getSession().flush();
		getSession().getTransaction().commit();
		getSession().clear();
	}
	
}
