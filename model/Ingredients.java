/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;


@Entity
public class Ingredients {
    
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private double livestock;
    private double perDayConsumption;
    private double minimumStock;
    private double equivalentRatio;
    private double equivalentQuantity;
    private double wastage;
    

    
    @ManyToOne
    private Unit equivalentUnit;
    
    @ManyToOne   
    private Unit unitId;
    
    @ManyToOne
    private IngredientsCategory categoryId;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }




    /**
     * @return the unitId
     */
    public Unit getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the categoryId
     */
    public IngredientsCategory getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(IngredientsCategory categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

	public Unit getEquivalentUnit() {
		return equivalentUnit;
	}

	public void setEquivalentUnit(Unit equivalentUnit) {
		this.equivalentUnit = equivalentUnit;
	}

	public double getLivestock() {
		return livestock;
	}

	public void setLivestock(double livestock) {
		this.livestock = livestock;
	}

	public double getPerDayConsumption() {
		return perDayConsumption;
	}

	public void setPerDayConsumption(double perDayConsumption) {
		this.perDayConsumption = perDayConsumption;
	}

	public double getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(double minimumStock) {
		this.minimumStock = minimumStock;
	}

	public double getEquivalentRatio() {
		return equivalentRatio;
	}

	public void setEquivalentRatio(double equivalentRatio) {
		this.equivalentRatio = equivalentRatio;
	}

	public double getEquivalentQuantity() {
		return equivalentQuantity;
	}

	public void setEquivalentQuantity(double equivalentQuantity) {
		this.equivalentQuantity = equivalentQuantity;
	}

	public double getWastage() {
		return wastage;
	}

	public void setWastage(double wastage) {
		this.wastage = wastage;
	}



   
    
}
