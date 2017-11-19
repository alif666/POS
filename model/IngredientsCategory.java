/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Tauhedul Amin
 */
@Entity
public class IngredientsCategory {
    
    @Id
    @GeneratedValue
    private int id;
    
    private String ingredientsCategoryName;

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
     * @return the ingredientsCategoryName
     */
    public String getIngredientsCategoryName() {
        return ingredientsCategoryName;
    }

    /**
     * @param ingredientsCategoryName the ingredientsCategoryName to set
     */
    public void setIngredientsCategoryName(String ingredientsCategoryName) {
        this.ingredientsCategoryName = ingredientsCategoryName;
    }
    
}
