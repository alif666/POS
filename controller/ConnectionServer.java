/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import dao.IngredientsDao;
import dao.SalesDetailsDao;
import dao.SalesInfoDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Ingredients;
import model.SalesDetails;
import model.SalesInfo;
/**
 *
 * @author aliflodi
 */
public class ConnectionServer {
    String paymentType;
    
    public void connectServer(){
        List sessionIds = new ArrayList();
          Connection c = null;
          Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://45.64.135.244:5432/honeycom_db_new",
            "honeycom_user_new", "T4]WIqK?6-pv");
         System.out.println("Opened database successfully");
         
         stmt = c.createStatement();
         //query's are given here
         ResultSet rs = stmt.executeQuery("select * from salesinfo");

         
         
         
         
         SalesInfoDao salesInfoDao = new SalesInfoDao();
         while ( rs.next() ) {
            long id = rs.getLong("sessionid");
            System.out.println( "ID = " + id );
            //get all the sessionId's 
                  
                  sessionIds.add(id+"");
                
            //get all the sessionId's ends
         }
         List<SalesInfo> salesList = salesInfoDao.view();
         for(SalesInfo s : salesList){
             if((sessionIds.contains(s.getSessionId()+""))){
                    
                 //do nothing if exists in salesInfo
             }else{
              //System.out.println("The date is now "+s.getDate());
            String dateString = s.getDate();
            String sql = "INSERT INTO salesinfo (waiterid_id,table_id,paidamount,sessionid,guestbillrequest,ordertotal,subtotal,vat,bankname,cardamount,cashamount,date,discount,kitchenorderstatus,orderstatus,dueamount) VALUES ("+s.getWaiterId().getId()+","+s.getTable().getId()+","+s.getPaidAmount()+","+s.getSessionId()+",'FALSE',"+s.getOrderTotal()+","+s.getSubTotal()+","+s.getVat()+","+s.getBankName()+","+s.getCardAmount()+","+s.getCashAmount()+",TO_DATE('"+dateString+"','YYYY/MM/DD'),"+s.getDiscount()+","+s.getKitchenOrderStatus()+",'Paid',"+s.getDueAmount()+")";
            stmt.executeUpdate(sql);
            SalesDetailsDao salesDetailsDao = new SalesDetailsDao();
            List<SalesDetails> salesDetailsList = salesDetailsDao.getListFromSameSession(s.getSessionId());
            for(SalesDetails sd : salesDetailsList){                
                 try {
                            stmt.executeUpdate("INSERT INTO public.salesdetails(\n" +
"	id, quantity, recipe_id, salesinfo_sessionid)\n" +
"	VALUES ( "+sd.getId()+", "+sd.getQuantity()+", "+sd.getRecipe().getId()+", "+sd.getSalesInfo().getSessionId()+");"); 
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                 }
            }
        }
        
        
        //update inventory into the server
         try{
            stmt = c.createStatement();
            rs = stmt.executeQuery("select * from ingredients");
                 //get ingredients from local database 
                        IngredientsDao ingredientsDao = new IngredientsDao();
                        List<Ingredients> ingredientsList = ingredientsDao.view();
                               while (rs.next()){
                                  long id = rs.getInt("id");
                                  double liveStock = rs.getDouble("livestock");
                                  System.out.println( "ID = " + id +" LIVESTOCK IS = "+liveStock);
                                       for(Ingredients i : ingredientsList ){
                                           if(id==i.getId()){
                                               //System.out.println("MATCH");
                                               String sql = "UPDATE ingredients set livestock = "+i.getLivestock()+" where id="+id+";";
                                                    stmt = c.createStatement();
                                                    stmt.executeUpdate(sql);
                                               }
                                           }
                                       }
                       //get all the sessionId's ends
                       
            }
            catch(SQLException ex){
                ex.printStackTrace();
             }
        
        
             
            
        
        
        stmt.close();
        c.close();
        
        //update inventory into the server ends
      }catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      
        
    }
    
}
