package dao;

import java.util.List;

import model.Unit;
import model.UserType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserTypeDao implements DaoInterface{
	 UserType userType = new UserType();
         	        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    @Override
	    public void insert(Object ob) {
	    	userType = (UserType)ob;

	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        session.save(userType);
	        session.getTransaction().commit();
	        session.close();
	        
	    }
	    
	   @Override
	    public Object checkdao(int id) {
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        
	        userType = (UserType)session.get(UserType.class, id);
	        session.getTransaction().commit();
	        
	        session.close();
	        return userType;
	    }
	    
	    @Override
	    public void delete(Object ob) {
	    	userType = (UserType)ob;
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        session.delete(userType);
	        session.getTransaction().commit();
	        session.close();

	    }
	    @Override
	    public List view(){
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        Query query = session.createQuery("FROM UserType");   
	        List<Unit> units = query.list();
	        session.getTransaction().commit();
	        session.close();
	        return units;
	    }

	    @Override
	    public void update(Object ob) {
	    	userType = (UserType)ob;
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        session.update(userType);
	        session.getTransaction().commit();
	        session.close();
	    }
	 
	}
