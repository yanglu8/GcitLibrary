package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Branch implements Serializable{
	private static final long serialVersionUID = 6171253982444712448L;
	private int branchId;
	private String branchName;
	private String branchAddress;
	private Map<Book, Integer> copiesMap = new HashMap<Book, Integer>();
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public Map<Book, Integer> getLoansMap() {
		return copiesMap;
	}
	public void setLoansMap(Map<Book, Integer> loansMap) {
		this.copiesMap = loansMap;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchAddress == null) ? 0 : branchAddress.hashCode());
		result = prime * result + branchId;
		result = prime * result
				+ ((branchName == null) ? 0 : branchName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		if (branchAddress == null) {
			if (other.branchAddress != null)
				return false;
		} else if (!branchAddress.equals(other.branchAddress))
			return false;
		if (branchId != other.branchId)
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		return true;
	}
	
}
