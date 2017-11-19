package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class IngredientSubIngredient {
   @Id
   @GeneratedValue
   private int id;
   @ManyToOne
   private 
   Ingredients ingredients;
   @ManyToOne(cascade = CascadeType.PERSIST)
   private SubIngredients subIngredients;
   
   private int quantity;
   
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Ingredients getIngredients() {
	return ingredients;
}
public void setIngredients(Ingredients ingredients) {
	this.ingredients = ingredients;
}
public SubIngredients getSubIngredients() {
	return subIngredients;
}
public void setSubIngredients(SubIngredients subIngredients) {
	this.subIngredients = subIngredients;
}
   
}
