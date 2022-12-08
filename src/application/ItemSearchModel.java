package application;

public class ItemSearchModel {
	String Name,Number,Inumber,Details;

	public  ItemSearchModel(String name,String inumber,String details,String number){
		
		this.Name=name;
		this.Inumber=inumber;
		this.Details=details;
		this.Number=number;
	}
	public String getName() {
		return Name;
	}
	public String getNumber() {
		return Number;
	}
	public String getInumber() {
		return Inumber;
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
	public void setinumber(String inumber) {
		this.Number=inumber;
	}
	public void setdetails(String details) {
		this.Details=details;
	}
}
