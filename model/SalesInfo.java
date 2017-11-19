/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SalesInfo implements Serializable {

    @Id
    private long sessionId;
    private String date;
    @ManyToOne
    private Tables table;
    @ManyToOne
    private Users waiterId;
    private String guestNumber;
    private String orderType;
    private String discount;
    private double vat; 
    private double subTotal;
    private String orderStatus;
    private String paymentType;
    private String cashAmount;
    private String cardAmount;
    private String paidAmount;
    private String returnedAmount;
    private String dueAmount;
    private String time;
    private String bankName;
    private String serviceCharge;
    private double orderTotal;
    private String kitchenOrderStatus;
    private boolean guestBillRequest;
    
    public String getDate() {
    return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
     public boolean isGuestBillRequest() {
        return guestBillRequest;
    }

    /**
     * @param guestBillRequest the guestBillRequest to set
     */
    public void setGuestBillRequest(boolean guestBillRequest) {
        this.guestBillRequest = guestBillRequest;
    }
    public String getTableName() {
      return table.getTableName();
   }
    public void setOrderTotal(double orderTotal){
        this.orderTotal = orderTotal;
    }
     public double getOrderTotal() {
         return orderTotal;
     }

    /**
     * @return the table
     */
    public Tables getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(Tables table) {
        this.table = table;
    }


    /**
     * @return the guestNumber
     */
    public String getGuestNumber() {
        return guestNumber;
    }

    /**
     * @param guestNumber the guestNumber to set
     */
    public void setGuestNumber(String guestNumber) {
        this.guestNumber = guestNumber;
    }

    /**
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     * @return the vat
     */
    public double getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(double vat) {
        this.vat = vat;
    }

    /**
     * @return the subTotal
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the subTotal to set
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return the orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the cashAmount
     */
    public String getCashAmount() {
        return cashAmount;
    }

    /**
     * @param cashAmount the cashAmount to set
     */
    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
     * @return the cardAmount
     */
    public String getCardAmount() {
        return cardAmount;
    }

    /**
     * @param cardAmount the cardAmount to set
     */
    public void setCardAmount(String cardAmount) {
        this.cardAmount = cardAmount;
    }

    /**
     * @return the paidAmount
     */
    public String getPaidAmount() {
        return paidAmount;
    }

    /**
     * @param paidAmount the paidAmount to set
     */
    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * @return the returnedAmount
     */
    public String getReturnedAmount() {
        return returnedAmount;
    }

    /**
     * @param returnedAmount the returnedAmount to set
     */
    public void setReturnedAmount(String returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    /**
     * @return the dueAmount
     */
    public String getDueAmount() {
        return dueAmount;
    }

    /**
     * @param dueAmount the dueAmount to set
     */
    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the serviceCharge
     */
    public String getServiceCharge() {
        return serviceCharge;
    }

    /**
     * @param serviceCharge the serviceCharge to set
     */
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * @return the waiterId
     */
    public Users getWaiterId() {
        return waiterId;
    }

    /**
     * @param waiterId the waiterId to set
     */
    public void setWaiterId(Users waiterId) {
        this.waiterId = waiterId;
    }

    /**
     * @return the sessionId
     */
    public long getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the kitchenOrderStatus
     */
    public String getKitchenOrderStatus() {
        return kitchenOrderStatus;
    }

    /**
     * @param kitchenOrderStatus the kitchenOrderStatus to set
     */
    public void setKitchenOrderStatus(String kitchenOrderStatus) {
        this.kitchenOrderStatus = kitchenOrderStatus;
    }
}
