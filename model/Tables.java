package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tables {

    /**
     * @return the tableStatus
     */
    public String getTableStatus() {
        return tableStatus;
    }

    /**
     * @param tableStatus the tableStatus to set
     */
    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

	@Id
	@GeneratedValue
	private int id;
	
	private String tableName;
        private String tableStatus;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
