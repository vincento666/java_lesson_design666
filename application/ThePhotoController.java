package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser.ExtensionFilter;

public class ThePhotoController {
	@FXML
	private VBox vboxImage;
	@FXML
	private ImageView viewPhoto;
	@FXML
	private HBox hboxButton;
	@FXML
	private Button lastPhoto;
	@FXML
	private Button nextPhoto;
	@FXML
	private Button oppositeCol;
	@FXML
	private Button grayCol;
	@FXML
	private Button rePhoto;
	@FXML
	private Button outPhoto;
	@FXML
	private Button enlargePhoto;
	@FXML
	private Button narrowPhoto;
	@FXML
	private Button playPhoto;
	
	private Image image;
	private WritableImage wImage;
	private FileChooser fileChooser;
	Stage stage;
	
	


	// Event Listener on Button[#lastPhoto].onAction
	@FXML
	public void lastPhotoButton(ActionEvent event) {
		showThePhoto();
	}
	// Event Listener on Button[#nextPhoto].onAction
	@FXML
	public void nextPhotoButton(ActionEvent event) {
		showThePhoto();
	}
	// Event Listener on Button[#oppositeCol].onAction
	@FXML
	public void oppositeColButton(ActionEvent event) {
		image = new Image("file:" + "C:\\Users\\Li\\Desktop\\HW\\20180427_113951.jpg");
		pixWithImage(1);
	}
	// Event Listener on Button[#grayCol].onAction
	@FXML
	public void grayColButton(ActionEvent event) {
		image = new Image("file:" + "C:\\Users\\Li\\Desktop\\HW\\20180427_113951.jpg");
		pixWithImage(2);
	}
	// Event Listener on Button[#rePhoto].onAction
	@FXML
	public void rePhotoButton(ActionEvent event) {
		image = new Image("file:" + "C:\\Users\\Li\\Desktop\\HW\\20180427_113951.jpg");
		viewPhoto.setImage(image);
	}
	// Event Listener on Button[#outPhoto].onAction
	@FXML
	public void outPhotoButton(ActionEvent event) {
		fileChooser = new FileChooser();  
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("图片文件", "*.png","*.jpg", "*.bmp", "*.gif"),
        		new ExtensionFilter("JPG","*.jpg"),
        		new ExtensionFilter("PNG", "*.png"),
        		new ExtensionFilter("BMP", "*.bmp"),
        		new ExtensionFilter("GIF", "*.gif")); 
		File file = fileChooser.showSaveDialog(stage.getOwner());
//		System.out.println("err");
		if(file!=null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", file);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	// Event Listener on Button[#enlargePhoto].onAction
	@FXML
	public void enlargePhotoButton(ActionEvent event) {
		zoomWithAnima(1);
	}
	// Event Listener on Button[#narrowPhoto].onAction
	@FXML
	public void narrowPhotoButton(ActionEvent event) {
		zoomWithAnima(2);
	}
	// Event Listener on Button[#playPhoto].onAction
	@FXML
	public void playPhotoButton(ActionEvent event) {
		showThePhoto();
	}
	
	public void setStage(Stage nowStage) {
		stage=nowStage;
	}
	//图片处理方法
	private void pixWithImage (int type) {  
        PixelReader pixelReader = viewPhoto.getImage().getPixelReader();  
        // Create WritableImage  
        wImage = new WritableImage(  
                (int)image.getWidth(),  
                (int)image.getHeight());  
        PixelWriter pixelWriter = wImage.getPixelWriter();  
          
        for(int y = 0; y < image.getHeight(); y++){  
            for(int x = 0; x < image.getWidth(); x++){  
                Color color = pixelReader.getColor(x, y);  
                switch (type) {  
                case 1:  
                    color = color.invert();  
                    break;  
                case 2:  
                    color = color.grayscale();  
                    break;  
                 
                default:  
                    break;  
                }  
                pixelWriter.setColor(x, y, color);  
            }  
        }  
        viewPhoto.setImage(wImage);  
    }
	
	public void showThePhoto(Image image){
//		Image image = new Image("file:" + "C:\\Users\\Li\\Desktop\\HW\\20180427_113951.jpg");
		viewPhoto.setImage(image);
		viewPhoto.setPreserveRatio(true);
		viewPhoto.setSmooth(true);
	}
	
//	public void zoom(int type) {
//		switch(type) {
//		case 1:
//			double fitw = viewPhoto.getFitWidth()*1.2;
//			double fith = viewPhoto.getFitWidth()*1.2;
//			viewPhoto.setFitWidth(fitw);
//			viewPhoto.setFitHeight(fith);
//			break;
//		case 2:
//			double fitw2 = viewPhoto.getFitWidth()*0.8;
//			double fith2 = viewPhoto.getFitWidth()*0.8;
//			viewPhoto.setFitWidth(fitw2);
//			viewPhoto.setFitHeight(fith2);
//			break;
//		default:
//			break;
//		}
//		
//	}
	
	public void zoomWithAnima(int type) {
		Timeline timeline = new Timeline();
		Duration duration = new Duration(300);
		double changew = 100D;
		double changeh = 100D;
		double fitw = viewPhoto.getFitWidth() < 200D ? 200D : viewPhoto.getFitWidth();
		double fith = viewPhoto.getFitHeight() < 200D ? 200D : viewPhoto.getFitHeight();
		switch(type) {
		case 1:
			timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(viewPhoto.fitWidthProperty(), fitw)),
										new KeyFrame(Duration.ZERO, new KeyValue(viewPhoto.fitHeightProperty(), fith)),
										new KeyFrame(duration, new KeyValue(viewPhoto.fitWidthProperty(), fitw+changeh)),
										new KeyFrame(duration, new KeyValue(viewPhoto.fitHeightProperty(), fith+changew)));
			
			break;
		case 2:
			timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(viewPhoto.fitWidthProperty(), fitw)),
										new KeyFrame(Duration.ZERO, new KeyValue(viewPhoto.fitHeightProperty(), fith)),
										new KeyFrame(duration, new KeyValue(viewPhoto.fitWidthProperty(), fitw-changeh)),
										new KeyFrame(duration, new KeyValue(viewPhoto.fitHeightProperty(), fith-changew)));
			
			break;
		default:
			break;
		}
		timeline.play();
		
	}
	
}
