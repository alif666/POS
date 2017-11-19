package dao;

import java.util.List;
import model.MenuCategory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class MenuCategoryDao implements DaoInterface {
    MenuCategory category=new MenuCategory();
    @Override
    public void insert(Object ob) {
        category = (MenuCategory)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(category);
        
        session.getTransaction().commit();
        session.close();
        
    }
    
   @Override
    public Object checkdao(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        category = (MenuCategory)session.get(MenuCategory.class, id);
        session.getTransaction().commit();
        
        session.close();
        return category;
    }
    
    @Override
    public void delete(Object ob) {
        category = (MenuCategory)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(category);
        session.getTransaction().commit();
        session.close();

    }
    @Override
    public List view(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM MenuCategory");   
        List<MenuCategory> categories = query.list();
        session.getTransaction().commit();
        session.close();
        
        return categories;
    }

    @Override
    public void update(Object ob) {
        category = (MenuCategory)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(category);
        session.getTransaction().commit();
        session.close();
    }
 
}
