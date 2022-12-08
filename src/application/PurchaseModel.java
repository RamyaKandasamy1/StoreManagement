package application;

public class PurchaseModel {
	String Sname,Oname,Iname;
	int Iqty;

	public  PurchaseModel(String sname,String oname,String iname,int quantity){
		this.Sname=sname;
		this.Oname=oname;
		this.Iname=iname;
		this.Iqty=quantity;
	}
	public String getSname() {
		return Sname;
	}
	public String getOname() {
		return Oname;
	}
	public String getIname() {
		return Iname;
	}
	public int getIqty() {
		return Iqty;
	}

	public void setSname(String sname) {
		this.Sname=sname;
	}
	public void setOname(String oname) {
		this.Oname=oname;
	}
	public void setIname(String iname) {
		this.Iname=iname;
	}
	public void setIqty(int quantity) {
		this.Iqty=quantity;
	}
}
