package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeController implements Initializable{
	
	@FXML
	private Label welcomelabel;
	@FXML
	private Button analyButton;
	@FXML
	private Button storeButton;
	@FXML
	private Button itemButton;
	@FXML
	private Button purchaseButton;
	@FXML
	private Button exitButton;
	@FXML
	private BorderPane borderpane;
	@FXML
	private Label msg;
	@FXML
	private Label msg1;
	@FXML

	private HBox hbox1;
	
	
	@FXML
	private void animation1() {
		// TODO Auto-generated method stub
		
			String str="Welcome Admin!!\n  ";
			String str2="\t * STORE : You can view store informations by clicking on STORE\n "+
			"\t * ITEM : You can view Items of the store informations by clicking on ITEM\n"+
			"\t * PURCHASE : You can view purchase order informations by clicking on PURCHASE\n"+
			"\t * DASHBOARD : You can view generated report informations by clicking on DASHBOARD\n";
				
		  final IntegerProperty i = new SimpleIntegerProperty(0);
	        Timeline timeline = new Timeline();
	        KeyFrame keyFrame = new KeyFrame(
	                Duration.seconds(.05),
	                event -> {
	                    if (i.get() > str.length()) {
	                        timeline.stop();
	                    } else {
	                        msg.setText(str.substring(0, i.get()));
	                       // msg1.setText(str2.substring(0, i.get()));
	                        i.set(i.get() + 1);
	                    }
	                });
	        timeline.getKeyFrames().add(keyFrame);
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.play();
		
	}
	private void animation2() {
		// TODO Auto-generated method stub
		
			//String str="Welcome Admin!!\n  ";
			String str="\t * STORE : You can view store informations by clicking on STORE\n "+
			"\t * ITEM : You can view Items of the store informations by clicking on ITEM\n"+
			"\t * PURCHASE : You can view purchase order informations by clicking on PURCHASE\n"+
			"\t * DASHBOARD : You can view generated report  by clicking on DASHBOARD\n"+
			"\t * EXIT : You can exit the application anytime by clicking on EXIT \n"+
			"\t\t Thank you for checking in!!\t Thank You!";
				
		  final IntegerProperty i = new SimpleIntegerProperty(0);
	        Timeline timeline = new Timeline();
	        KeyFrame keyFrame = new KeyFrame(
	                Duration.seconds(.05),
	                event -> {
	                    if (i.get() > str.length()) {
	                        timeline.stop();
	                    } else {
	                        msg1.setText(str.substring(0, i.get()));
	                       // msg1.setText(str2.substring(0, i.get()));
	                        i.set(i.get() + 1);
	                    }
	                });
	        timeline.getKeyFrames().add(keyFrame);
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.play();
		
	}
	@FXML
	public void storeButtonOnAction(ActionEvent e) {
	try {
		AnchorPane view=FXMLLoader.load(getClass().getResource("Store.fxml"));
		borderpane.setCenter(view);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}
	public void itemButtonOnAction(ActionEvent e) {
		try {
			AnchorPane view=FXMLLoader.load(getClass().getResource("itemfxml.fxml"));
			borderpane.setCenter(view);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
public void purchaseButtonOnAction(ActionEvent e) {
	try {
		AnchorPane view=FXMLLoader.load(getClass().getResource("purchasefxml.fxml"));
		borderpane.setCenter(view);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}
public void analyButtonOnAction(ActionEvent e) {
	try {
		AnchorPane view=FXMLLoader.load(getClass().getResource("dashboardfxml.fxml"));
		borderpane.setCenter(view);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}

public void exitButtonOnAction(ActionEvent e) {
	System.exit(0);
	}
@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	animation1();
	animation2();
}



}
