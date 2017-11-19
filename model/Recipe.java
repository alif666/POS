package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Recipe {
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private
	MenuCategory category;
	private String name;
	private String price;
        private String netPrice;
	private String recipeColor;
	private String recipeImage;

	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuCategory getCategory() {
		return category;
	}

	public void setCategory(MenuCategory category) {
		this.category = category;
	}

	public String getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(String recipeImage) {
		this.recipeImage = recipeImage;
	}

	public String getRecipeColor() {
		return recipeColor;
	}

	public void setRecipeColor(String recipeColor) {
		this.recipeColor = recipeColor;
	}

    /**
     * @return the netPrice
     */
    public String getNetPrice() {
        return netPrice;
    }

    /**
     * @param netPrice the netPrice to set
     */
    public void setNetPrice(String netPrice) {
        this.netPrice = netPrice;
    }


}
