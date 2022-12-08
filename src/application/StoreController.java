package application;

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

public class StoreController implements Initializable {
@FXML
private TextField searchTextField;
@FXML
private TableView<StoreSearchModel> storeView;
@FXML
private TableColumn<StoreSearchModel,String>  storeName;
@FXML
private TableColumn<StoreSearchModel,String>  storeNumber;
@FXML
private TableColumn<StoreSearchModel,String>   storeDistrict;
@FXML
private TableColumn<StoreSearchModel,String>   storeDetails;
@FXML
private Button exitButtonOnAction;
@FXML
private Button ADD;
@FXML
private Button CLEAR;
@FXML
private Button Display;
@FXML
private Button exit;
@FXML
private TextField snameTextField;
@FXML
private TextField snumberTextField;
@FXML
private TextField sdistrictTextField;
@FXML
private TextField sdetailsTextField;
@FXML
int count=0;
@FXML
private SortedList<StoreSearchModel> sorteddata;
@FXML
private Button clearSearch;


ObservableList<StoreSearchModel> storeobslist=FXCollections.observableArrayList();
ObservableList<StoreSearchModel> storeobslistdisplay=FXCollections.observableArrayList();

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	searchTable();
	count++;
	// TODO Auto-generated method stub

}
public void searchTable() {
	if(count>=0) {
	storeobslist.clear();
	
	
	
	DatabaseConnection connectnow=new DatabaseConnection();
	Connection connectDB=connectnow.getConnection();
	String storequery="select name_store,number_store,district_store,details_store from store";
	
	try {
		
		Statement st=connectDB.createStatement();
		ResultSet rs=st.executeQuery(storequery);
		while(rs.next()) {
			String qname=rs.getString(1);
			String qnumber=rs.getString(2);
			String qdistrict=rs.getString(3);
			String qdetails=rs.getString(4);
			
			storeobslist.add(new StoreSearchModel(qname,qnumber,qdistrict,qdetails));
		
		}
		storeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
		storeDistrict.setCellValueFactory(new PropertyValueFactory<>("District"));
		storeDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
		
		storeView.setItems(storeobslist);
		
		FilteredList<StoreSearchModel> filteredData=new FilteredList<>(storeobslist,b->true);
		
		searchTextField.textProperty().addListener((observable,oldValue,newValue)->{
			filteredData.setPredicate(StoreSearchModel -> {
				
				if(newValue.isEmpty()||(newValue.isBlank())||(newValue == null)) {
					return true;
				}
			String searchkeywords=newValue.toLowerCase();
			 if(StoreSearchModel.getName().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}
			
			
			else if(StoreSearchModel.getNumber().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}
			else if(StoreSearchModel.getDistrict().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}
			else if(StoreSearchModel.getDetails().toLowerCase().indexOf(searchkeywords) > -1) {
				return true;
				
			}else
				return false;
				
				
			});
		});
		
		
		SortedList<StoreSearchModel> sorteddata=new SortedList<>(filteredData);
		sorteddata.comparatorProperty().bind(storeView.comparatorProperty());
		
		storeView.setItems(sorteddata);
	
	}catch(Exception e) {
		Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE,null,e);
	      System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
	}
	}
}
public void ADDonAction(ActionEvent e) {
	String aname,anumber,adistrict,adetails;
	aname=snameTextField.getText();
	anumber=snumberTextField.getText();
	adistrict=sdistrictTextField.getText();
	adetails=sdetailsTextField.getText();
	if(aname.isBlank()||anumber.isBlank()||adistrict.isBlank()||adetails.isBlank()) {
		JOptionPane.showMessageDialog(null,"Values cannot be blank.. Please enter the details!!");
	}else {
	
	DatabaseConnection connectnow=new DatabaseConnection();
	Connection connectDB=connectnow.getConnection();
	String storequery="insert into store(name_store,number_store,district_store,details_store) values(?,?,?,?)";
	
	
	
	try {
		
		PreparedStatement st=connectDB.prepareStatement(storequery);
		
		
		st.setString(1,aname);
		st.setString(2,anumber);
		st.setString(3,adistrict);
		st.setString(4,adetails);
		
		st.execute();
		storeobslist.add(new StoreSearchModel(aname,anumber,adistrict,adetails));
		JOptionPane.showMessageDialog(null,"Store added successfully!!");
		searchTable();
		
	}catch(SQLException e1) {
		JOptionPane.showMessageDialog(null,"Store Number already exists!! Try different Number\n");
		System.out.println(e1);
	}catch(Exception e2) {
		JOptionPane.showMessageDialog(null,e2);
	}
	
	
	}
}
public void DisplayOnAction(ActionEvent e) throws SQLException {
	storeobslist.clear();
	searchTextField.setText("");
	DatabaseConnection connectnow=new DatabaseConnection();
	Connection connectDB=connectnow.getConnection();
	String aname,anumber,adistrict,adetails;
	String storequery;
	 ResultSet rs = null;
	aname=snameTextField.getText();
	anumber=snumberTextField.getText();
	adistrict=sdistrictTextField.getText();
	adetails=sdetailsTextField.getText();
	if(aname.isBlank()&& anumber.isBlank()&& adistrict.isBlank()&& adetails.isBlank()) {
		JOptionPane.showMessageDialog(null,"Displaying all the records");
		Statement st=connectDB.createStatement();
		
		 storequery="select name_store,number_store,district_store,details_store from store";
		 rs=st.executeQuery(storequery);
		while(rs.next()) {
			String qname=rs.getString(1);
			String qnumber=rs.getString(2);
			String qdistrict=rs.getString(3);
			String qdetails=rs.getString(4);
			
			storeobslist.add(new StoreSearchModel(qname,qnumber,qdistrict,qdetails));
		}
			storeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
			storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
			storeDistrict.setCellValueFactory(new PropertyValueFactory<>("District"));
			storeDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
			
			storeView.setItems(storeobslist);	//storeView.s
		
		searchTable();
		
	}else {      
		try {
		JOptionPane.showMessageDialog(null,"Store Search with Criteria");
		 
		Statement st=connectDB.createStatement();
		switch ((aname.isBlank()? 0:1)+
				(anumber.isBlank()? 0:2)) {
		case 0:
			JOptionPane.showMessageDialog(null, "StoreName and StoreNumber is empty!!");
			break;
		case 1:
			try {
			storeobslist.clear();
			 storequery="select name_store,number_store,district_store,details_store from store where name_store='"+aname+"'";
			  rs=st.executeQuery(storequery);
			  System.out.println("case1");
			  System.out.println(storequery);
			  if((!rs.isBeforeFirst() && rs.getRow() == 0) ){
				  JOptionPane.showMessageDialog(null,"Record not found!!");
			  }else {
			  while(rs.next()) {
					System.out.println("Inside while");
					String qname=rs.getString(1);
					String qnumber=rs.getString(2);
					String qdistrict=rs.getString(3);
					String qdetails=rs.getString(4);
					System.out.println("Inside while1");
					storeobslist.add(new StoreSearchModel(qname,qnumber,qdistrict,qdetails));
				}
				System.out.println("Inside while2");
					storeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
					storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
					storeDistrict.setCellValueFactory(new PropertyValueFactory<>("District"));
					storeDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
					System.out.println("Inside while3");
					storeView.setItems(storeobslist);	//storeView.s
			}
			}catch(SQLException e1) {
				JOptionPane.showMessageDialog(null,"Record not found!!"+e1);
			}
			 break;
			
		case 2:
			storeobslist.clear();
			 storequery="select name_store,number_store,district_store,details_store from store where number_store='"+anumber+"'";
			
			  rs=st.executeQuery(storequery);
		
			 if((!rs.isBeforeFirst() && rs.getRow() == 0) ){
				 JOptionPane.showMessageDialog(null,"Record not found!!");
			 }else {
				while(rs.next()) {
					String qname=rs.getString(1);
					String qnumber=rs.getString(2);
					String qdistrict=rs.getString(3);
					String qdetails=rs.getString(4);
					System.out.println("Inside while1");
					storeobslist.add(new StoreSearchModel(qname,qnumber,qdistrict,qdetails));
				}
				
					storeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
					storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
					storeDistrict.setCellValueFactory(new PropertyValueFactory<>("District"));
					storeDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
					System.out.println("Inside while3");
					storeView.setItems(storeobslist);	//storeView.s
				
			 }	
			 break;
		case 3:
			 storequery="select name_store,number_store,district_store,details_store from store where name_store='"+aname+"'and number_store='"+anumber+"'";
			 rs=st.executeQuery(storequery);
		
			 if((!rs.isBeforeFirst() && rs.getRow() == 0) ){
				 JOptionPane.showMessageDialog(null,"Record not found!!");
			 }else {
				while(rs.next()) {
					
					String qname=rs.getString(1);
					String qnumber=rs.getString(2);
					String qdistrict=rs.getString(3);
					String qdetails=rs.getString(4);
					
					storeobslist.add(new StoreSearchModel(qname,qnumber,qdistrict,qdetails));
				}
				
					storeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
					storeNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
					storeDistrict.setCellValueFactory(new PropertyValueFactory<>("District"));
					storeDetails.setCellValueFactory(new PropertyValueFactory<>("Details"));
					
					storeView.setItems(storeobslist);	//storeView.s
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
	snameTextField.setText("");
	snumberTextField.setText("");
	sdistrictTextField.setText("");
	sdetailsTextField.setText("");
	storeobslist.clear();
	
}

public void ClearSearchOnAction(ActionEvent e) {
	searchTextField.setText("");
}
public void exitButtonOnActionPerformed(ActionEvent e) {
	Stage stage=(Stage)exitButtonOnAction.getScene().getWindow();
	stage.close();
}

}
