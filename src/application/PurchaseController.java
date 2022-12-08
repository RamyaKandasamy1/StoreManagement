package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PurchaseController implements Initializable{
	@FXML
	private TabPane ordertab;
	@FXML
	private TabPane display;
	@FXML
	private ChoiceBox<String> storeChoiceName; 
	@FXML
	private ChoiceBox<String> itemChoiceName; 
	@FXML
	private ChoiceBox<String> displayChoiceName; 
	@FXML
	private TableView<PurchaseModel>purchaseView1;
	@FXML
	private TableColumn<PurchaseModel,String>  storeName;
	@FXML
	private TableColumn<PurchaseModel,String>  orderName;
	@FXML
	private TableColumn<PurchaseModel,String>   itemName;
	@FXML
	private TableColumn<PurchaseModel,Integer>   itemQuantity;
	@FXML
	private TableView<PurchaseModel> displayView;
	@FXML
	private TableColumn<PurchaseModel,String> displaystoreName;
	@FXML
	private TableColumn<PurchaseModel,String>  displayorderName;
	@FXML
	private TableColumn<PurchaseModel,String>   displayitemName;
	@FXML
	private TableColumn<PurchaseModel,Integer>   displayitemQuantity;

	@FXML
	private Button GenerateButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button createStore;
	@FXML
	private Button displayStore;
	@FXML
	private Button addItemToStore;
	@FXML
	private TextField snameTextField;
	@FXML
	private TextField snumberTextField;
	@FXML
	private TextField inameTextField;
	@FXML
	private TextField inumberTextField;
	@FXML
	private TextField isnumberTextField;
	@FXML
	private TextField ordernameTextField;
	@FXML
	private TextField quantityTextField;
	@FXML
	int count=0;
	@FXML
	private SortedList<PurchaseModel> sorteddata;
	@FXML
	private Button clearSearch;
	@FXML
	private Button process;
	@FXML
	private TextField itemtablequantityTextField;
	@FXML
	private TextField itemtablepriceTextField;
	@FXML
	private TextField itemdetailsTextField;
	@FXML
	ObservableList sdata=FXCollections.observableArrayList();
	@FXML
	ObservableList idata=FXCollections.observableArrayList();
	@FXML
	ObservableList orderList=FXCollections.observableArrayList();
	@FXML
	ObservableList displaydata=FXCollections.observableArrayList();
	@FXML
	ObservableList displayList=FXCollections.observableArrayList();
@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		count++;
		// TODO Auto-generated method stub
		Load();
	
		
		
	}
	public void Load() {
		try {
			
			DatabaseConnection connectnow=new DatabaseConnection();
			Connection connectDB=connectnow.getConnection();
			Statement st=connectDB.createStatement();
			
			 String storequery="select distinct name_store from store";
			 ResultSet rs=st.executeQuery(storequery);
			while(rs.next()) {
				
				sdata.add(new String(rs.getString(1)));
				
			}
			
			storeChoiceName.setItems(sdata);
			
			
			//Statement st1=connectDB.createStatement();
			 String storequery1="select distinct name_item from item";
			 ResultSet rs1=st.executeQuery(storequery1);
			while(rs1.next()) {
				
				idata.add(new String(rs1.getString(1)));
				
			}
			
			itemChoiceName.setItems(idata);
			
			 String storequery3="select distinct name_store from store";
			 ResultSet rs2=st.executeQuery(storequery3);
			while(rs2.next()) {
				
				displaydata.add(new String(rs2.getString(1)));
				
			}
			
			displayChoiceName.setItems(displaydata);
			
		
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	public void CreateStoreOnAction(ActionEvent e) {
		String aname,anumber,adistrict,adetails;
		aname=snameTextField.getText();
		anumber=snumberTextField.getText();
		adistrict="default district";
		adetails="Store!";
		if(aname.isBlank()||anumber.isBlank()) {
			JOptionPane.showMessageDialog(null,"Values cannot be blank.. Please enter the details!!");
		}else {
		
		DatabaseConnection connectnow=new DatabaseConnection();
		Connection connectDB=connectnow.getConnection();
		String storequery="insert into store(name_store,number_store) values(?,?)";
		
		
		
		try {
			
			PreparedStatement st=connectDB.prepareStatement(storequery);
			
			
			st.setString(1,aname);
			st.setString(2,anumber);
			
			
			st.execute();
			//String storequery1="update store set details_store='"+adetails+"' and district_store='"+adistrict+"' where name_store='"+aname+"' ";
			//PreparedStatement st1=connectDB.prepareStatement(storequery1);
		//	st1.execute(storequery1);
			
		
			JOptionPane.showMessageDialog(null,"Store added successfully!!");
		
			st.close();
			connectDB.close();
		
		}catch(SQLException e1) {
			JOptionPane.showMessageDialog(null,"Store Number already exists\n");
			System.out.println(e1);
		}catch(Exception e2) {
			JOptionPane.showMessageDialog(null,e2);
		}
		

		}
		
	}
	
	public void DisplayStoreOnAction(ActionEvent e) {
		
		
		
		try {
			DatabaseConnection connectnow=new DatabaseConnection();
			Connection connectDB=connectnow.getConnection();
			Statement st=connectDB.createStatement();
			
			 String storequery="select name_store,number_store from store ORDER BY id_store desc limit 1";
			 ResultSet rs=st.executeQuery(storequery);
			 while(rs.next()) {
				 String qname=rs.getString(1);
					String qnumber=rs.getString(2);
				 JOptionPane.showMessageDialog(null, "Last Created the Store :"+qname+" store number :"+qnumber);
			 }
			

				st.close();
				connectDB.close();
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null,e1);
		}
		
	}
	

	public void additemToStoreOnAction(ActionEvent e) {
		
		String aname,anumber,asnumber,adetails,sid,aquantity,aprice;
		aname=inameTextField.getText();
		anumber=inumberTextField.getText();
		asnumber=isnumberTextField.getText();
		adetails=itemdetailsTextField.getText();
		aquantity=itemtablequantityTextField.getText();
		aprice=itemtablepriceTextField.getText();

	System.out.println(1);
		boolean a = asnumber.isBlank();
		System.out.println(a);
		System.out.println(!a);
		if(aname.isBlank()||anumber.isBlank()||asnumber.isBlank()||aquantity.isBlank()||aprice.isBlank()) {
			JOptionPane.showMessageDialog(null,"Values cannot be blank.. Please enter the details!!");
		}
		
		else if(!a){
			
			try {
			
				DatabaseConnection connectnow1=new DatabaseConnection();
				Connection connectDB1=connectnow1.getConnection();
				Statement st1=connectDB1.createStatement();
				String storequery1="select count(number_store) from store where number_store='"+asnumber+"'";
				
				ResultSet rs1=st1.executeQuery(storequery1);
				
				while(rs1.next()) {
					if(rs1.getInt(1)==1) {
						System.out.println(6);		
						DatabaseConnection connectnow=new DatabaseConnection();
						Connection connectDB=connectnow.getConnection();
						System.out.println(7);
						String storequery="INSERT INTO item (name_item,number_item,details_item,number_store,quantity_item,price_item)VALUES (?,?,?,?,?,?)";
						
						System.out.println(8);								
						try {
							
							PreparedStatement st=connectDB.prepareStatement(storequery);
							
							
							st.setString(1,aname);
							st.setString(2,anumber);
							st.setString(3,adetails);
							st.setString(4,asnumber);
							
							st.setString(5,aquantity);
							st.setString(6,aprice);
							st.execute();
							System.out.println(9);
						//	itemobslist.add(new ItemSearchModel(aname,anumber,adetails,asnumber));
							String storequery3="UPDATE item INNER JOIN store ON item.number_store=store.number_store SET item.id_store=store.id_store";
							System.out.println("Query executed");
										
							PreparedStatement st3=connectDB.prepareStatement(storequery3);
							st3.executeUpdate();
							
						
								System.out.println("st3 while");
							JOptionPane.showMessageDialog(null,"Item added successfully to the Store!!");
							
							
						}catch(SQLException e1) {
							JOptionPane.showMessageDialog(null,"Item Number already exists\n");
						}catch(Exception e2) {
							JOptionPane.showMessageDialog(null,e2);
						}
					}else {
						
						JOptionPane.showMessageDialog(null,"Store Number doesn't exist!! Create the store first!! ");
						
						}
					}
				}
				
			catch(Exception e1){
				System.out.println(e1);
			}
		}
					
	}
	
	public void ProcessOrderOnAction(ActionEvent e) {
		String psname,piname,poname;
		int pqty;
		psname=storeChoiceName.getValue();
		System.out.println(psname);
		piname=itemChoiceName.getValue();
		System.out.println(piname);
		poname=ordernameTextField.getText();
		System.out.println(poname);
		pqty=Integer.parseInt(quantityTextField.getText());
		System.out.println(pqty);
		DatabaseConnection connectnow=new DatabaseConnection();
		Connection connectDB=connectnow.getConnection();
		try {
			
			
				//Statement st=connectDB.createStatement();
				
				String storequery="Insert into orderitem(name_store,name_item,name_order,quantity)values(?,?,?,?)";
				
				
				

				PreparedStatement st=connectDB.prepareStatement(storequery);
				
				
				st.setString(1,psname);
				st.setString(2,piname);
				st.setString(3,poname);
				st.setInt(4,pqty);
				
				st.execute();
				
			
					
				orderList.add(new PurchaseModel(psname,piname,poname,pqty));
				
				storeName.setCellValueFactory(new PropertyValueFactory<>("Sname"));
				itemName.setCellValueFactory(new PropertyValueFactory<>("Iname"));
				orderName.setCellValueFactory(new PropertyValueFactory<>("Oname"));
				itemQuantity.setCellValueFactory(new PropertyValueFactory<>("Iqty"));
				
				purchaseView1.setItems(orderList);	//ItemView.s
				String storequery3="UPDATE orderitem o INNER JOIN store ON o.name_store=store.name_store SET o.id_store=store.id_store";
				
				String storequery4="UPDATE orderitem o INNER JOIN item ON o.name_item =item.name_item SET o.id_item=item.id_item  ";
				
				String storequery5="UPDATE item i INNER JOIN orderitem o ON i.id_item=o.id_item SET i.quantity_item=i.quantity_item- ' "+pqty+"' where i.name_item= '"+piname+"'";
					
				//String storequery6="UPDATE orderitem o INNER JOIN item ON o.name_item =item.name_item SET o.id_item=item.id_item  ";
				System.out.println("Query executed");
							
				PreparedStatement st3=connectDB.prepareStatement(storequery3);
				st3.executeUpdate();
				PreparedStatement st4=connectDB.prepareStatement(storequery4);
				st4.executeUpdate();
				PreparedStatement st5=connectDB.prepareStatement(storequery5);
				st5.executeUpdate();
			
			
			
		}catch(SQLException e3) {
			System.out.println(e3);
			JOptionPane.showMessageDialog(null, e);
		}
		catch(Exception e1) {
			System.out.println(e1);
		}
		
	}
	public void generateButtonOnAction(ActionEvent e) {
		try {
			String psname,piname,poname;
			int pqty;
			psname=displayChoiceName.getValue();
			System.out.println(psname);
			DatabaseConnection connectnow=new DatabaseConnection();
			Connection connectDB=connectnow.getConnection();
			Statement st=connectDB.createStatement();
			String storequery="select name_store,name_item,name_order,quantity from orderitem where name_store='"+psname+"'";
			 ResultSet rs=st.executeQuery(storequery);
			while(rs.next()) {
				String qname=rs.getString(1);
				String iname=rs.getString(2);
				String oname=rs.getString(3);
				int oquantity=rs.getInt(4);
				
				displayList.add(new PurchaseModel(qname,iname,oname,oquantity));
			}
			displaystoreName.setCellValueFactory(new PropertyValueFactory<>("Sname"));
			displayorderName.setCellValueFactory(new PropertyValueFactory<>("Oname"));
			displayitemName.setCellValueFactory(new PropertyValueFactory<>("Iname"));
			displayitemQuantity.setCellValueFactory(new PropertyValueFactory<>("Iqty"));
				
			displayView.setItems(displayList);	//storeView.s
			
			
		}catch(Exception e1) {
			System.out.println(e1);
		}
		
	}
	
	public void exitButtonOnAction(ActionEvent e) {
		Stage stage=(Stage)exitButton.getScene().getWindow();
		stage.close();
	}
	
	public void CLEAROnAction(ActionEvent e) {
		inameTextField.setText("");
		snumberTextField.setText("");
		inumberTextField.setText("");
		itemdetailsTextField.setText("");
		itemtablequantityTextField.setText("");
		itemtablepriceTextField.setText("");
	}


				
}
