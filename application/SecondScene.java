package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class SecondScene extends Application {
	
	Stage stage=new Stage();
	
	@Override
	public void start(Stage primaryStage){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ThePhoto.fxml"));
			Parent root = (Parent)loader.load();
			Scene scene = new Scene(root,600,500);
			primaryStage.setTitle("展示图片");
			primaryStage.setScene(scene);
			primaryStage.show();
			ThePhotoController controller = (ThePhotoController)loader.getController();
			controller.setStage(primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void  showWindow() throws Exception {  
        start(stage);  
    }  
      

	public static void main(String[] args) {
		launch(args);
	}
}
