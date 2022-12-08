package application;

public class StoreSearchModel {
	String Name,Number,District,Details;

	public  StoreSearchModel(String name,String number,String district,String details){
		this.Name=name;
		this.Number=number;
		this.District=district;
		this.Details=details;
	}
	public String getName() {
		return Name;
	}
	public String getNumber() {
		return Number;
	}
	public String getDistrict() {
		return District;
	}
	public String getDetails() {
		return Details;
	}

	public void setname(String name) {
		this.Name=name;
	}
	public void setnumber(String number) {
		this.Number=number;
	}
	public void setdistrict(String district) {
		this.District=district;
	}
	public void setdetails(String details) {
		this.Details=details;
	}
}
