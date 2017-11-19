/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.IngredientsCategory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Tauhedul Amin
 */
public class IngredientsCategoryDao implements DaoInterface{
    IngredientsCategory ingredientsCategory = new IngredientsCategory();

    @Override
    public void insert(Object ob) {
        ingredientsCategory = (IngredientsCategory)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(ingredientsCategory);
        
        session.getTransaction().commit();
        session.close();
     
    }

    @Override
    public List view() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM IngredientsCategory");   
        List<IngredientsCategory> ingredientsCategories = query.list();
        session.getTransaction().commit();
        session.close();
        
        return ingredientsCategories;
        
   }

    @Override
    public void delete(Object ob) {
        ingredientsCategory = (IngredientsCategory)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(ingredientsCategory);
        session.getTransaction().commit();
        session.close();
   }

    @Override
    public Object checkdao(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        ingredientsCategory = (IngredientsCategory)session.get(IngredientsCategory.class, id);
        session.getTransaction().commit();
        
        session.close();
        return ingredientsCategory; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object ob) {
        ingredientsCategory = (IngredientsCategory)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(ingredientsCategory);
        session.getTransaction().commit();
        session.close();
        
    }
    
}
