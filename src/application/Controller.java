package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller {
@FXML
private Button exitButton;
@FXML
private Button cancelButton;
@FXML
private Button loginButton;
@FXML
private Label loginMessageLabel;
@FXML
private TextField usernameTextField;
@FXML
private PasswordField passwordField;
@FXML
Stage stage;
public void loginButtonOnAction(ActionEvent e) {
	//loginMessageLabel.setText("You try to login!");
	
	
	
	
	if(usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank()== false ) {
		validateLogin();
	
		
	}else {
		loginMessageLabel.setText("Please enter username and password");
		
	}
	
	}
@FXML
private void validateLogin() {
	// 
	DatabaseConnection connectnow=new DatabaseConnection();
	Connection connectDB=connectnow.getConnection();
	String verifyLogin= "select count(1) from userLogin where username='"+usernameTextField.getText()+"' and password='"+passwordField.getText()+"'";
	
	try {
		Statement statement=connectDB.createStatement();
		ResultSet rs=statement.executeQuery(verifyLogin);
		while(rs.next()) {
			if(rs.getInt(1)==1) {
				loginMessageLabel.setText("Welcome!");
				
				//primaryStage.dispose();	
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Welcome.fxml"));
			Parent root=loader.load();
		
			
			
			Stage stage=new Stage();
			stage.setScene(new Scene(root));
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		
			Stage stage1=(Stage)loginButton.getScene().getWindow();
			stage1.close();
			
			
			
				
			}else {
				loginMessageLabel.setText("Invalid Login.. Please try again!!");
			}
			
		}
		
	}catch(Exception e) {
		
	}
	
}

public void cancelButtonOnAction(ActionEvent e) {
	usernameTextField.setText(" ");
	passwordField.setText("");
	loginMessageLabel.setText(" ");
	}

public void exitButtonOnAction(ActionEvent e) {
	Stage stage=(Stage)exitButton.getScene().getWindow();
	stage.close();
	}
}
