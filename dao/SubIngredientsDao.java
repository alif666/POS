package dao;

import java.util.List;

import model.MenuCategory;
import model.SubIngredients;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SubIngredientsDao implements DaoInterface{
	SubIngredients sub=new SubIngredients();
	@Override
	public void insert(Object ob) {
		sub = (SubIngredients)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(sub);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public List view() {
		 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        
	        Query query = session.createQuery("FROM SubIngredients");   
	        List<SubIngredients> subIngredientList = query.list();
	        session.getTransaction().commit();
	        session.close();
	        
	        return subIngredientList;
	}

	@Override
	public void delete(Object ob) {
		    sub = (SubIngredients)ob;
	        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        session.delete(sub);
	        session.getTransaction().commit();
	        session.close();
		

	}

	@Override
	public Object checkdao(int id) {
		   SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        sub = (SubIngredients)session.get(SubIngredients.class, id);
	        session.getTransaction().commit();
	        session.close();
	        return sub;
	}

	@Override
	public void update(Object ob) {
		// TODO Auto-generated method stub
		
	}

}
