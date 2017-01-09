package billboard.beans;

import java.io.Serializable;

public class AssignType implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String type_name;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}


}
