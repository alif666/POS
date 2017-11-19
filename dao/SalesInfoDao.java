/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.SalesInfo;
import model.Unit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Enamul Karim
 */
public class SalesInfoDao implements DaoInterface{
  SalesInfo salesInfo=new SalesInfo();
  SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void insert(Object ob) {
        salesInfo = (SalesInfo)ob;
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<SalesInfo> result = session.createQuery("from SalesInfo ORDER BY id DESC")
                          .setMaxResults(1)
                          .list();
        if(!result.isEmpty()){
        String seq=String.valueOf(result.get(0).getSessionId()).substring(12);
        System.out.println(seq);
        
          salesInfo.setSessionId( Long.parseLong((salesInfo.getSessionId())+ String.valueOf(Long.parseLong(seq)+1)));
        }
        else{
           salesInfo.setSessionId(Long.parseLong(salesInfo.getSessionId()+"1"));
        }
        
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        salesInfo.setTime(time);
    

        System.out.println("========================Sales info values are =-=====================================");
        System.out.println(salesInfo.getDate()+" is the date");
        System.out.println(salesInfo.getOrderStatus()+" is the sales status");
        System.out.println(salesInfo.getOrderType()+ " is the order type ");
        System.out.println(salesInfo.getSubTotal()+" is the subtotal");
        System.out.println(salesInfo.getTable().getId()+" is the id");
        System.out.println(salesInfo.getWaiterId().getUserName()+" is the waiter user name");
        System.out.println(salesInfo.getTime()+" is the time of insertion");
        
        session.save(salesInfo);
        
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List view() {
       
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM SalesInfo");   
        List<SalesInfo> salesInfoLists = query.list();
        session.getTransaction().commit();
        session.close();
        
        return salesInfoLists;
       
    }

    @Override
    public void delete(Object ob) {
        salesInfo = (SalesInfo)ob;
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(salesInfo);
        session.getTransaction().commit();
        session.close();
      
    }

    
    public Object checkDaoLong(long id) {
                System.out.println("the id that entered is "+id);
        	//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction(); 
                Query query= (Query) session.createQuery("SELECT u FROM SalesInfo u WHERE u.sessionId='"+id+"'");
		List<SalesInfo> salesInfoList = query.list();
                salesInfoList = query.list(); 

                salesInfo =  salesInfoList.get(0);
         
                System.out.println(salesInfo.getSessionId()+"is the sales info id");
		session.getTransaction().commit();
		session.close();
		return salesInfo;
     
    }

    @Override
    public void update(Object ob) {
        salesInfo = (SalesInfo)ob;
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(salesInfo);
        session.getTransaction().commit();
        session.close();
    
       
    }

    @Override
    public Object checkdao(int id) {
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        salesInfo = (SalesInfo)session.get(Unit.class, id);
        session.getTransaction().commit();
        
        session.close();
        return salesInfo;
    }


}
