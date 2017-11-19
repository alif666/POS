/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import model.Ingredients;
import model.Unit;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Tauhedul Amin
 */
public class IngredientsDao implements DaoInterface {
    Ingredients ingredients = new Ingredients();
    
    @Override
    public void insert(Object ob) {
    	
        ingredients = (Ingredients)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(ingredients);
        
        session.getTransaction().commit();
        session.close();
    	
    	
       }

    @Override
    public List view() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Ingredients");   
        List<Ingredients> ingredientsList = query.list();
        session.getTransaction().commit();
        session.close();
        
        return ingredientsList;
    }

    @Override
    public void delete(Object ob) {
        ingredients = (Ingredients)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(ingredients);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Object checkdao(int id) {
    
    	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    	Session session = sessionFactory.openSession();
    	
    	session.beginTransaction();
    	ingredients = (Ingredients)session.get(Ingredients.class, id);
    	
    	session.getTransaction().commit();
    	session.close();
    	
    	
      return ingredients;
    }

    @Override
    public void update(Object ob) {
    	
        ingredients = (Ingredients)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(ingredients);
        session.getTransaction().commit();
        session.close();
    
    }
    
}
