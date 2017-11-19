package dao;

import java.util.List;
import model.Unit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class UnitDao implements DaoInterface {
    Unit unit = new Unit();
    @Override
    public void insert(Object ob) {
        unit = (Unit)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.save(unit);
        
        session.getTransaction().commit();
        session.close();
        
    }
    
   @Override
    public Object checkdao(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        unit = (Unit)session.get(Unit.class, id);
        session.getTransaction().commit();
        
        session.close();
        return unit;
    }
    
    @Override
    public void delete(Object ob) {
        unit = (Unit)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(unit);
        session.getTransaction().commit();
        session.close();

    }
    @Override
    public List view(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Unit");   
        List<Unit> units = query.list();
        session.getTransaction().commit();
        session.close();
        
        return units;
    }

    @Override
    public void update(Object ob) {
        unit = (Unit)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(unit);
        session.getTransaction().commit();
        session.close();
    }
 
}
