package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MenuCategory implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    
    private String menuCategoryName;
    private String menuCategoryColor;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }

	public String getMenuCategoryColor() {
		return menuCategoryColor;
	}

	public void setMenuCategoryColor(String menuCategoryColor) {
		this.menuCategoryColor = menuCategoryColor;
	}
}
