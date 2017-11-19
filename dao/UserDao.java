package dao;

import java.util.List;
import model.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;




public class UserDao implements DaoInterface {
	private boolean flag=false;
	//HttpSession httpsession;
	SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
	
	Users user = new Users();
        @Override
	public void insert(Object ob) {
		user = (Users)ob;
		Session session = sessionFactory.openSession();
		session.beginTransaction();     
		session.save(user);	      
		session.getTransaction().commit();
		session.close();
		//sessionFactory.close();
	}
        @Override
	public Object checkdao(int id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
		user = (Users) session.get(Users.class,id);
		session.getTransaction().commit();
		//session.close();
		return user;
	}
   @Override
    public void delete(Object ob)
    {
        
    	user = (Users)ob;
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        //sessionFactory.close();
        
    }
    
        @Override
	public List view() {
	 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        org.hibernate.Query query = session.createQuery("FROM Users");   
        List<Users> userList = query.list();
        

        
        session.getTransaction().commit();
        session.close();
        
        return userList;
		
	}
	@Override
	public void update(Object ob) {
        user = (Users)ob;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
	}

        


	public boolean validate(String name,String password){

		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
                EncryptPassword ep=new EncryptPassword();
                password=ep.encrypt(password);
		Query query= (Query) session.createQuery("SELECT u FROM Users u WHERE u.userName='"+name+"'and u.userPassword='"+password+"' and u.userType.typeName='Cashier'");
		List<Users> users = query.list();
		if(!users.isEmpty()){
			flag=true;
		}
		session.getTransaction().commit();
		session.close();
		return flag;		 
	}
        
        public int getId(String name){
                int id = 0;
                SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query= (Query) session.createQuery("SELECT u FROM Users u WHERE u.userName='"+name+"'");
                List<Users> users = query.list();
                for(Users u:users){
                    System.out.println(u.getUserName());
                    id = u.getId();
                }
		session.getTransaction().commit();
		session.close();
            
            
            return id;
        }


}
