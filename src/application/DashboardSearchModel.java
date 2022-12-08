package application;

public class DashboardSearchModel {

	String Name;
	int Number;
	public  DashboardSearchModel(String name,int number){
		
		this.Name=name;
		this.Number= number;
	}
	public  DashboardSearchModel(String name){
		
		this.Name=name;
		
	}
	public  DashboardSearchModel(int number){
		
		
		this.Number= number;
		
	}
	public String getName() {
		return Name;
	}
	public int getNumber() {
		return Number;
	}
	

	public void setname(String name) {
		this.Name=name;
	}
	public void setnumber(int number) {
		this.Number=number;
	}
	
	
	
}
