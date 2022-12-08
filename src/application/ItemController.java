package application;

import java.awt.Component;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ItemController implements Initializable {
@FXML
private TextField searchTextField;
@FXML
private TableView<ItemSearchModel> ItemView;
@FXML
private TableColumn<ItemSearchModel,String>  itemName;
@FXML
private TableColumn<ItemSearchModel,String>  storeNumber;
@FXML
private TableColumn<ItemSearchModel,String>   itemNumber;
@FXML
private TableColumn<ItemSearchModel,String>   itemDetails;
@FXML
private Button exit;
@FXML
private Button ADD;
@FXML
private Button CLEAR;
@FXML
private Button Display;
@FXML
private TextField inameTextField;
@FXML
private TextField snumberTextField;
@FXML
private TextField inumberTextField;
@FXML
private TextField idetailsTextField;
@FXML
private TextField iquantityTextField;
@FXML
private TextField ipriceTextField;
@FXML
int count=0;
@FXML
private SortedList<ItemSearchModel> sorteddata;
@FXML
private Button clearSearch;


ObservableList<ItemSearchModel> itemobslist=FXCollections.observableArrayList();
ObservableList<ItemSearchModel> itemobslistdisplay=FXCollections.observableArrayList();

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	searchTable();
	count++;
	// TODO Auto-generated method stub

}
public void searchTable() {
	if(count>=0) {
	itemobslist.clear();
	//System.out.println("Inside SearchTable");
	
	
	DatabaseConnection connectnow=new DatabaseConnection();
	Connection connectDB=connectnow.getConnection();
	String storequery="select name_item,number_item,details_item,number_store from item";
	//System.out.println("Query executed");
	try {
		//System.out.println("Inside TrY");
		
		Statement st=connectDB.createStatement();
		ResultSet rs=st.executeQuery(storequery);
		while(rs.next()) {
			//System.out.println("Inside while");
			String iname=rs.getString(1);
			String inumber=rs.getString(2);
			String idetails=rs.getString(3);
			String snumber=rs.getString(4);
			itemobslist.add(new ItemSearchModel(iname,inumber,idetails,snumber));
			//System.out.println("End while");
			
		}
		
		itemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		itemNumber.setCellValueFactory(new PropertyValueFactory<>("Inumber"));
		itemDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
		storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
		
		ItemView.setItems(itemobslist);
		
		FilteredList<ItemSearchModel> filteredData=new FilteredList<>(itemobslist,b->true);
		
		searchTextField.textProperty().addListener((observable,oldValue,newValue)->{
			filteredData.setPredicate(ItemSearchModel -> {
				
				if(newValue.isEmpty()||(newValue.isBlank())||(newValue == null)) {
					return true;
				}
			String searchkeywords=newValue.toLowerCase();
			 if(ItemSearchModel.getName().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}
			
			
			else if(ItemSearchModel.getNumber().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}
			else if(ItemSearchModel.getInumber().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}
			else if(ItemSearchModel.getDetails().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}else
				return false;
				
				
			});
		});
		
		
		SortedList<ItemSearchModel> sorteddata=new SortedList<>(filteredData);
		sorteddata.comparatorProperty().bind(ItemView.comparatorProperty());
		
		ItemView.setItems(sorteddata);
	
	}catch(Exception e) {
		Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE,null,e);
	      System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
	}
	}
}
public void ADDonAction(ActionEvent e) throws Exception {
	String aname,anumber,asnumber,adetails,sid,aquantity,aprice;
	aname=inameTextField.getText();
	anumber=inumberTextField.getText();
	asnumber=snumberTextField.getText();
	adetails=idetailsTextField.getText();
	aquantity=iquantityTextField.getText();
	aprice=ipriceTextField.getText();

//System.out.println(1);
	boolean a = asnumber.isBlank();
	
	if(aname.isBlank()||anumber.isBlank()||asnumber.isBlank()||adetails.isBlank()) {
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
						
					DatabaseConnection connectnow=new DatabaseConnection();
					Connection connectDB=connectnow.getConnection();
					
					String storequery="INSERT INTO item (name_item,number_item,details_item,number_store,quantity_item,price_item)VALUES (?,?,?,?,?,?)";
					
											
					try {
						
						PreparedStatement st=connectDB.prepareStatement(storequery);
						
						
						st.setString(1,aname);
						st.setString(2,anumber);
						st.setString(3,adetails);
						st.setString(4,asnumber);
						
						st.setString(5,aquantity);
						st.setString(6,aprice);
						st.execute();
						//System.out.println(9);
						itemobslist.add(new ItemSearchModel(aname,anumber,adetails,asnumber));
						String storequery3="UPDATE item INNER JOIN store ON item.number_store=store.number_store SET item.id_store=store.id_store";
						System.out.println("Query executed");
									
						PreparedStatement st3=connectDB.prepareStatement(storequery3);
						st3.executeUpdate();
						
					
							//System.out.println("st3 while");
						JOptionPane.showMessageDialog(null,"Store added successfully!!");
						searchTable();
						
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
	
public void DisplayOnAction(ActionEvent e) throws SQLException {
	itemobslist.clear();
	searchTextField.setText("");
	DatabaseConnection connectnow=new DatabaseConnection();
	Connection connectDB=connectnow.getConnection();
	String aname,anumber,asnumber,adetails;
	String storequery;
	 ResultSet rs = null;
	aname=inameTextField.getText();
	asnumber=snumberTextField.getText();
	anumber=inumberTextField.getText();
	adetails=idetailsTextField.getText();
	if(aname.isBlank()&& anumber.isBlank()&& asnumber.isBlank()&& adetails.isBlank()) {
		JOptionPane.showMessageDialog(null,"Displaying all the records");
		Statement st=connectDB.createStatement();
		
		 storequery="select name_item,number_item,details_item,number_store from item";
		 rs=st.executeQuery(storequery);
		while(rs.next()) {
			String qname=rs.getString(1);
			String qnumber=rs.getString(2);
			String qdetails=rs.getString(3);
			String qsnumber=rs.getString(4);
			
			itemobslist.add(new ItemSearchModel(qname,qnumber,qdetails,qsnumber));
		}
		itemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		itemNumber.setCellValueFactory(new PropertyValueFactory<>("Inumber"));
		itemDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
		storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
		
			ItemView.setItems(itemobslist);	//ItemView.s
		
		searchTable();
		
	}else {      
		try {
		JOptionPane.showMessageDialog(null,"Item Search with Criteria");
		 
		Statement st=connectDB.createStatement();
		switch ((aname.isBlank()? 0:1)+
				(anumber.isBlank()? 0:2)) {
		case 0:
			JOptionPane.showMessageDialog(null, "itemName and StoreNumber is empty!!");
			break;
		case 1:
			try {
			itemobslist.clear();
			 storequery="select name_item,number_item,details_item,number_store from item where name_item='"+aname+"'";
			  rs=st.executeQuery(storequery);
			
			  System.out.println(storequery);
			  if((!rs.isBeforeFirst() && rs.getRow() == 0) ){
				  JOptionPane.showMessageDialog(null,"Record not found!!");
			  }else {
			  while(rs.next()) {
				
					String qname=rs.getString(1);
					String qnumber=rs.getString(2);
					String qdetails=rs.getString(3);
					String qsnumber=rs.getString(4);
				
					itemobslist.add(new ItemSearchModel(qname,qnumber,qdetails,qsnumber));
				}
				//System.out.println("Inside while2");
				itemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
				itemNumber.setCellValueFactory(new PropertyValueFactory<>("Inumber"));
				itemDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
				storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
				
					//System.out.println("Inside while3");
					ItemView.setItems(itemobslist);	//ItemView.s
			}
			}catch(SQLException e1) {
				JOptionPane.showMessageDialog(null,"Record not found!!"+e1);
			}
			 break;
			
		case 2:
			itemobslist.clear();
			 storequery="select name_item,number_item,details_item,number_store  from item where number_item='"+anumber+"'";
			 System.out.println(storequery);
			  rs=st.executeQuery(storequery);
			 // System.out.println("case2");
			 if((!rs.isBeforeFirst() && rs.getRow() == 0) ){
				 JOptionPane.showMessageDialog(null,"Record not found!!");
			 }else {
				while(rs.next()) {
					
					String qname=rs.getString(1);
					String qnumber=rs.getString(2);
					
					String qdetails=rs.getString(3);
					String qsnumber=rs.getString(4);
					
					itemobslist.add(new ItemSearchModel(qname,qnumber,qdetails,qsnumber));
				}
				
				itemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
				itemNumber.setCellValueFactory(new PropertyValueFactory<>("Inumber"));
				itemDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
				storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
				
					
					ItemView.setItems(itemobslist);	//ItemView.s
				
			 }	
			 break;
		case 3:
			 storequery="select name_item,number_item,details_item,number_store from item where name_item='"+aname+"'and number_item='"+anumber+"'";
			 rs=st.executeQuery(storequery);
			 
			 if((!rs.isBeforeFirst() && rs.getRow() == 0) ){
				 JOptionPane.showMessageDialog(null,"Record not found!!");
			 }else {
				while(rs.next()) {
					
					String qname=rs.getString(1);
					String qnumber=rs.getString(2);
					
					String qdetails=rs.getString(3);
					String qsnumber=rs.getString(4);
					
					itemobslist.add(new ItemSearchModel(qname,qnumber,qdetails,qsnumber));
				}
				
				itemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
				itemNumber.setCellValueFactory(new PropertyValueFactory<>("Inumber"));
				itemDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
				storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
				
					//System.out.println("Inside while3");
					ItemView.setItems(itemobslist);	//ItemView.s
			 }
			break;
		}
		
			
		}
	catch(SQLException e1) {
		JOptionPane.showMessageDialog(null,"Record not found!!"+e1);
	}
	}
	
}
public void CLEAROnAction(ActionEvent e) {
	inameTextField.setText("");
	snumberTextField.setText("");
	inumberTextField.setText("");
	idetailsTextField.setText("");
	itemobslist.clear();
}

public void ClearSearchOnAction(ActionEvent e) {
	searchTextField.setText("");
}
public void exitButtonOnAction(ActionEvent e) {
	Stage stage=(Stage)exit.getScene().getWindow();
	stage.close();
}

}
