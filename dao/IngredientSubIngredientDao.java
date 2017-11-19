package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.IngredientSubIngredient;
import model.Ingredients;
import model.RecipeIngredients;



public class IngredientSubIngredientDao implements DaoInterface{
	IngredientSubIngredient ingred_sub = new IngredientSubIngredient();
	@Override
	public void insert(Object ob) {
		ingred_sub = (IngredientSubIngredient)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(ingred_sub);
        session.getTransaction().commit();
        session.close();
    	
		
	}

	@Override
	public List view() {
	    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM IngredientSubIngredient");   
        List<IngredientSubIngredient> subIngredientList = query.list();
        session.getTransaction().commit();
        session.close();
        
        return subIngredientList;
	}

	@Override
	public void delete(Object ob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object checkdao(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object ob) {
		// TODO Auto-generated method stub
		
	}

	public List<IngredientSubIngredient> showIngredients(int id) {
		
		SessionFactory sessionfactory;
		sessionfactory = HibernateUtil.getSessionFactory();
		Session session;
		session = sessionfactory.openSession();
		session.beginTransaction();
        Query query = session.createQuery("FROM IngredientSubIngredient I WHERE I.subIngredients.id = :id"); 
        query.setInteger("id", id);
        List<IngredientSubIngredient> subingredients = query.list();
        session.getTransaction().commit();
        session.close();
        
        System.out.println(subingredients.getClass());
        return subingredients;
	}

}
