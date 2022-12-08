package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.chart.PieChart.Data;

public class DashboardController implements Initializable {

	@FXML 
	private BarChart<?,?> barchart;
	@FXML
	private PieChart piechart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private Label msg;
	@FXML
	private Button exit;
	@FXML

ObservableList data=FXCollections.observableArrayList();
@FXML
ObservableList<PieChart.Data> pie=FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//barchartfunction();
		//double sceneWidth = scene.getWidth();
	    //double msgWidth = msg.getLayoutBounds().getWidth();
	  //
	
		
		
		voidanimation1();
		chartat();
		piechart();
	}
	private void voidanimation1() {
		// TODO Auto-generated method stub
		
	String str="Order Information - Generates Report with Stores having more orders!!\n Store Information - Genarates Report with Store having more items !! ";
		  final IntegerProperty i = new SimpleIntegerProperty(0);
	        Timeline timeline = new Timeline();
	        KeyFrame keyFrame = new KeyFrame(
	                Duration.seconds(.05),
	                event -> {
	                    if (i.get() > str.length()) {
	                        timeline.stop();
	                    } else {
	                        msg.setText(str.substring(0, i.get()));
	                        i.set(i.get() + 1);
	                    }
	                });
	        timeline.getKeyFrames().add(keyFrame);
	        timeline.setCycleCount(Animation.INDEFINITE);
	        timeline.play();
		
	}
	@FXML
	
	public void chartat() {
		DatabaseConnection connectnow=new DatabaseConnection();
		Connection connectDB=connectnow.getConnection();
		String storequery="select number_store,count(name_item) from item group by number_store";
		try {
			PreparedStatement st=connectDB.prepareStatement(storequery);
			ResultSet rs=st.executeQuery();
			
			
			XYChart.Series chartData=new XYChart.Series<>();
			while(rs.next()) {
				chartData.getData().add(new XYChart.Data(rs.getString(1),rs.getInt(2)));
			}
			barchart.getData().add(chartData);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public void piechart() {
		
		DatabaseConnection connectnow=new DatabaseConnection();
		Connection connectDB=connectnow.getConnection();

		
		
		String storequery="select name_store,count(name_item) from orderitem group by name_item";
		try {
			PreparedStatement st=connectDB.prepareStatement(storequery);
			ResultSet rs=st.executeQuery();
	
			while(rs.next()) {
				
				data.add(new PieChart.Data(rs.getString(1),rs.getInt(2)));
			}
			
	        piechart.getData().addAll(data);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void ExitButtonOnAction(ActionEvent e) {
		Stage stage=(Stage)exit.getScene().getWindow();
		stage.close();
	}

}
