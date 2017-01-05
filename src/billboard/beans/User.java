package billboard.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String login_id;
	private String password;
	private String name;
	private int branch_id;
	private int assign_type_id;
	private int isBan;	//1=true 0=false
	private Date insertDate;
	private Date updateDate;


	//ゲッターとセッターの定義
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginId() {
		return login_id;
	}
	public void setLoginId(String loginId) {
		this.login_id = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBranchId() {
		return branch_id;
	}
	public void setBranchId(int branchId) {
		this.branch_id = branchId;
	}
	public int getAssignTypeId() {
		return assign_type_id;
	}
	public void setAssignTypeId(int assignTypeId) {
		this.assign_type_id = assignTypeId;
	}
	public int getIsBan() {
		return isBan;
	}
	public void setIsBan(int isBan) {
		this.isBan = isBan;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
