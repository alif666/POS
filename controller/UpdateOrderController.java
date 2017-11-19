package controller;

import dao.SalesDetailsDao;
import dao.SalesInfoDao;
import dao.TableDao;
import java.text.DecimalFormat;
import java.util.List;
import javax.persistence.Table;
import model.Recipe;
import model.SalesDetails;
import model.SalesInfo;
import model.Tables;


public class UpdateOrderController {



  private final List<SalesDetails> salesDetails;
  private final SalesInfo salesInfo;
  private double exclusiveVat;


    UpdateOrderController(long sessionId){
          SalesDetailsDao salesDao = new SalesDetailsDao();    
          salesDetails = salesDao.getListFromSameSession(sessionId);
          SalesInfoDao salesInfoDao=new SalesInfoDao();
          salesInfo=(SalesInfo) salesInfoDao.checkDaoLong(sessionId);
          
    }
    
    public void deleteFromList(Recipe r){
        SalesDetails dummy = new SalesDetails();
        for(SalesDetails sd: getSalesDetails()){
            //System.out.println(sd.getRecipe().getId()+" is the existing list recipe======================================================== "+r.getId());
            if(sd.getRecipe().getId()==r.getId()){
                dummy = sd;
            
            }    
        }
        getSalesDetails().remove(dummy);
    }
    
    public boolean containList(Recipe r){
        
        for(SalesDetails sd: getSalesDetails()){
            //System.out.println(sd.getRecipe().getId()+" is the existing list recipe======================================================== "+r.getId());
            if(sd.getRecipe().getId()==r.getId()){
                return true;
            }
        }
       return false;
    }
    
    public void updateQuantity(Recipe r,String quantity){
        
        for(SalesDetails sd: getSalesDetails()){
            if(sd.getRecipe().getId()==r.getId()){
                sd.setQuantity(Long.parseLong(quantity));
            }
        }
    }
    
    
    public List<SalesDetails> getSalesDetails() {
        return salesDetails;
    }

    public double getSalesDetailsPrice(){
        double totalPrice=0;
        double vat = 0;
        exclusiveVat = 0;
        double subTotalSum =0;
        
        
        if(!salesDetails.isEmpty()){
                for(SalesDetails sd:getSalesDetails()){
                if(Double.parseDouble(sd.getRecipe().getNetPrice())>0){//inclusive
                                                       //if(sd.getRecipe().getId() == 667||sd.getRecipe().getId()== 669||sd.getRecipe().getId()== 653||sd.getRecipe().getId()== 651||sd.getRecipe().getId()== 656||sd.getRecipe().getId() == 662 || sd.getRecipe().getId() == 664|| sd.getRecipe().getId() == 658 ||sd.getRecipe().getId() == 660 )vat = 0; 
                                                       vat = vat+Math.round(Double.parseDouble(sd.getRecipe().getPrice())*sd.getQuantity()/1.15*.15*100.0)/100.0;
                    subTotalSum+=Double.parseDouble(sd.getRecipe().getPrice())*sd.getQuantity();

                }else{//exclusive
                    exclusiveVat+=Math.round(Double.parseDouble(sd.getRecipe().getPrice())*sd.getQuantity()*.4*100.0)/100.0;
                    //if(sd.getRecipe().getId() == 667||sd.getRecipe().getId()== 669||sd.getRecipe().getId()== 653||sd.getRecipe().getId()== 651||sd.getRecipe().getId()== 656||sd.getRecipe().getId() == 662 || sd.getRecipe().getId() == 664|| sd.getRecipe().getId() == 658 ||sd.getRecipe().getId() == 660 )vat = 0;
                    vat = vat+Math.round(Double.parseDouble(sd.getRecipe().getPrice())*sd.getQuantity()*.15*100.0)/100.0;
                    subTotalSum+=Double.parseDouble(sd.getRecipe().getPrice())*sd.getQuantity();
                    totalPrice+=vat;
                }

                totalPrice+=Double.parseDouble(sd.getRecipe().getPrice())*sd.getQuantity();
            }
            totalPrice -= Double.parseDouble(salesInfo.getDiscount());

            this.getSalesInfo().setSubTotal(subTotalSum);
            this.getSalesInfo().setVat(vat);


            //for deducting vat from discount
                    //        System.out.println("The vat is set to"+this.getSalesInfo().getVat());
                    //        System.out.println("The discount price is "+this.getSalesInfo().getDiscount());
                    //        
                    //        
                    //        System.out.println("Discount percentage is "+ ( Math.round(Double.parseDouble(this.getSalesInfo().getDiscount())) /this.getSalesInfo().getSubTotal()*100)  );
                           double discountPercentage = Math.round(Double.parseDouble(this.getSalesInfo().getDiscount())) /this.getSalesInfo().getSubTotal()*100;
                    //        
                    //        System.out.println("the vat to be deducted is "+ (this.getSalesInfo().getVat()*discountPercentage/100)  ); 
                    //        System.out.println("So now the vat is "+ (this.getSalesInfo().getVat() -(this.getSalesInfo().getVat()*discountPercentage/100))  );
                    //        
                           Double vatFloat =  Double.parseDouble((this.getSalesInfo().getVat() -(this.getSalesInfo().getVat()*discountPercentage/100))+"") ;
                           this.getSalesInfo().setVat(RoundTo2Decimals(vatFloat));
                    //        System.out.println("Finally the vat is set to"+this.getSalesInfo().getVat());
                            //for deducting vat from discount ends

            this.getSalesInfo().setOrderTotal( (totalPrice));
            
        }
        
        
        
        
        
        
        
        
        
        
        return totalPrice;
    }
    
    public double getVat(){
        return getSalesInfo().getVat();
    }
    

    public void setDiscount(String discount){
        
        salesInfo.setDiscount(discount);
    } 
    public String getDiscount(){
        return salesInfo.getDiscount();
    } 
     
    public double getOrderTotal(){      
        return getSalesInfo().getOrderTotal();
    }


    public SalesInfo getSalesInfo() {
        return salesInfo;
    }
    
    
    public void updateSalesInDatabase(){
            TableDao tabledao = new TableDao();
    
        //for takeaway guest number 0
        if(getSalesInfo().getOrderType().equals("Take Away")){
            getSalesInfo().setGuestNumber("");
        }
        //for takeaway guest number 0 ends
        
        //table unoccupy kori
            SalesInfoDao salesInfoDao = new SalesInfoDao();
            SalesInfo tableSalesInfo = (SalesInfo)salesInfoDao.checkDaoLong(salesInfo.getSessionId());
            tableSalesInfo.getTable().setTableStatus("unoccupied");
            tabledao.update(tableSalesInfo.getTable());
            
        
        //table reserve kori
        if(!getSalesInfo().getTable().getTableName().equals("table 0")){
            getSalesInfo().getTable().setTableStatus("reserved");
            tabledao.update(getSalesInfo().getTable());
            
        }

  
        SalesDetailsDao salesDao = new SalesDetailsDao();
        List<SalesDetails> deleteSalesDetailsList = salesDao.getListFromSameSession(this.salesInfo.getSessionId());
        
        System.out.println("The table of salesIInfo is now "+salesInfo.getTable().getTableName());
        
        salesInfoDao.update(salesInfo);
        
        for(SalesDetails sd: deleteSalesDetailsList){
            salesDao.delete(sd);
        }
        
        for(SalesDetails sd: salesDetails){
            sd.setSalesInfo(salesInfo);
            salesDao.insert(sd);
        }
    }
    
    
    public double getExclusiveVat() {
        return exclusiveVat;
    }

    
    public void setExclusiveVat(double exclusiveVat) {
        this.exclusiveVat = exclusiveVat;
    }
    double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
          return Double.valueOf(df2.format(val));
  }
    
}

