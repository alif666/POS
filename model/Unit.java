package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Unit {
@Id
@GeneratedValue
private int id;
private String unitName;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUnitName() {
	return unitName;
}
public void setUnitName(String unitName) {
	this.unitName = unitName;
}

}
