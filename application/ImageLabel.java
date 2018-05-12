package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ImageLabel {
	private Boolean chose=false;//是否被选中
      private int i;//第几个Label
      private  Image image;//Label的图像
      private Label button;
   
	public ImageLabel(Image image,int i) {
    	  this.i=i;//
    	  this.image=image;
      }
      public Label creatLabel() {//生成显示图片的label
    	  String s="image"+i;
    	  ImageView imageView = new ImageView(image);
    	  imageView.setId("imageView"+i);
  		 imageView.setFitHeight(100);
  		 imageView.setFitWidth(180);
  		 button=new Label(s,imageView);
  		 button.setStyle("-fx-border-color:black;-fx-border-width:2;-fx-padding:2");
		 button.setContentDisplay(ContentDisplay.TOP);
		 button.setId("image"+i);
		 button.setPrefHeight(145);
		 button.setPrefWidth(187);
		 return button;
      }
     
	  public void setHover() {//被选中状态
		  chose=true;
		 button.setStyle("-fx-background-color:gray;-fx-border-color:black;-fx-border-width:4;");
	  }
	  public void NOTsetHover() {//没有被选中状态
       		chose=false;
       		button.setStyle("-fx-border-color:gray;-fx-border-width:2;");
	  }
	  public void newStage() {//生成新的stage
		  chose=true;
		  button.setScaleX(1.5);
     		button.setScaleY(1.5);
	  }
	  //setter  and  getter
	    public Boolean getChose() {
			return chose;
		}
		public void setChose(Boolean chose) {
			this.chose = chose;
		}
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}
		  public Label getButton() {
				return button;
			}
			public void setButton(Label button) {
				this.button = button;
			}
}
