package dao;

import java.util.List;

import model.Tables;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TableDao implements DaoInterface{
	
	Tables tables = new Tables();
	@Override
	public void insert(Object ob) {
		   tables = (Tables)ob;
	        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        
	        session.save(tables);
	        
	        session.getTransaction().commit();
	        session.close();
		
	}

	@Override
	public List view() {
	 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM Tables order by(id)");   
        List<Tables> tableList = query.list();
        

        
        session.getTransaction().commit();
        session.close();
        
        return tableList;
		
	}

	@Override
	public void delete(Object ob) {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory;
		sessionFactory = HibernateUtil.getSessionFactory();
		
		Session session;
		session = sessionFactory.openSession();
		
		session.beginTransaction();
		String stringQuery = "DELETE FROM Tables";
		Query query = session.createQuery(stringQuery);
		
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	  
   @Override
    public Object checkdao(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        tables = (Tables)session.get(Tables.class, id);
        session.getTransaction().commit();
        
        session.close();
        return tables;
    }

	@Override
	public void update(Object ob) {
                tables = (Tables)ob;
                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.update(tables);
                session.getTransaction().commit();
                session.close();
		
	}
        
        
        public Tables getTable(String tableName){
            	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();

                Query query = session.createQuery("SELECT u FROM Tables u WHERE u.tableName ='"+tableName+"'");   
                List<Tables> tableList = query.list();



                session.getTransaction().commit();
                session.close();

                
                System.out.println(" ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttThe table we got is "+tables.getTableName());
                        
                
                return tableList.get(0);
            
         
        }

}
