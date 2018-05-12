package application;
	

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;



import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	
	 public static  ArrayList<Image> imageList;//����
 	public static  String pasted="null";//��¼�ϴε����ͼƬ��id
 	double x_start ;//��ѡʱ��¼�϶��ĳ�ʼλ��x
    double y_start;//��ѡʱ��¼�϶��ĳ�ʼλ��y
  double x_end;//��ѡʱ��¼�϶��Ľ���λ��x
    double y_end;//��ѡʱ��¼�϶��Ľ���λ��y
    int IfDrag=0;//�ж��Ƿ����϶�����������flowpane�ĵ���¼��İ���
    Boolean hasImage=false;//��ʼ����Ϊû��ͼƬ
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();//�����
			Scene scene = new Scene(root,800,800); 
			root.prefHeightProperty().bind(scene.heightProperty());//�����size�󶨵����ڴ�С
	        root.prefWidthProperty().bind(scene.widthProperty());
		  // FirstStage firststage=new FirstStage();
		   //BorderPane borderpane=firststage.creatPane(root); 
	    		Pane  pane_bottom=new Pane();//���ײ�
	    		//System.out.println(pane_bottom);
	    		root.setBottom(pane_bottom);
	    		pane_bottom.prefHeightProperty().bind(root.heightProperty().divide(15));//�󶨵ײ�����С
	    		pane_bottom.prefWidthProperty().bind(root.widthProperty());
	    		pane_bottom.setStyle("-fx-background-color:black");
	    		HBox vbox = new HBox();//��Ŀ¼��
	    		root.setLeft(vbox);
	    		initView(root,vbox);
	    		vbox.prefWidthProperty().bind(root.widthProperty().divide(4.5));//����ʾĿ¼��������С
	    		vbox.prefHeightProperty().bind(root.heightProperty().subtract(pane_bottom.prefHeightProperty()));
	    	  	
	    	
	    		
	    		Pane  pane_right=new Pane();//�ұ����
	    		root.setRight(pane_right);
	    		//pane_right.setPrefHeight(root.getHeight()-100-50-menu.getHeight()-36);
	    		pane_right.prefHeightProperty().bind(root.heightProperty().subtract(pane_bottom.prefHeightProperty()));
	    		pane_right.prefWidthProperty().bind(root.widthProperty().subtract(vbox.prefWidthProperty()));
	    		//System.out.println(root.heightProperty());
	    		HBox  hbox=new HBox();//��ʾ�������Ŀ¼�ж�����ͼƬ
	    		hbox.prefHeightProperty().bind(root.heightProperty().divide(15));
	    		hbox.prefWidthProperty().bind(pane_right.prefWidthProperty());
	    		hbox.setStyle("-fx-background-color:white;-fx-padding:5");
	    		Image image2 = new Image("image/imageicon.png");
	    		ImageView imageviewicon=new ImageView(image2);
	    		imageviewicon.setFitHeight(50);
	    		imageviewicon.setFitWidth(50);
	    		String total;
	    		if(imageList==null) {
	    			 total="�ļ�����0��";//�˴��ļ���Ҫ�Ӷ�����Ŀ¼���
	    			System.out.println("û��ͼƬ��");
	    		}else {
	    			  hasImage=true;//�����ͼƬֻ����Ϊtrue
	    			System.out.println("��ͼƬ��");
	    			total="�ļ���"+"��"+imageList.size()+"��";
	    		}
	    	    Label imageicon=new Label(total,imageviewicon);//��ʾ������ͼƬ
	    		//Label imageicon=new Label();
	    		imageicon.prefHeightProperty().bind(hbox.prefHeightProperty().divide(1.2));
	    		imageicon.prefWidthProperty().bind(hbox.prefWidthProperty().divide(5.0));
	    		//imageicon.layoutXProperty().bind(vbox.layoutXProperty().add(20));
	    		imageicon.setStyle("-fx-border-color:black;-fx-font-size:14");
	    		imageicon.setWrapText(true);
	    		hbox.getChildren().add(imageicon);
	    		//��ʾ����ͼƬ
	    		Image image3 = new Image("image/image2icon.png");
	    		ImageView imageviewicon2=new ImageView(image3);
	    		imageviewicon2.setFitHeight(30);
	    		imageviewicon2.setFitWidth(30);
	    		Label imageicon2=new Label();
	    		imageicon2.setGraphic(imageviewicon2);
	    		imageicon2.prefHeightProperty().bind(hbox.prefHeightProperty().divide(1.6));
	    		imageicon2.prefWidthProperty().bind(hbox.prefWidthProperty().divide(16.0));
	    		imageicon2.setStyle("-fx-border-color:red;-fx-padding:2;maxWidth:50;");
	    		//imageicon2.layoutXProperty().bind(imageicon.layoutXProperty().add(5));
	    		//imageicon2.layoutYProperty().bind(imageicon.layoutYProperty().add(2));
	    		hbox.getChildren().add(imageicon2);
	    		
	    		
	    		
	    		pane_right.getChildren().add(hbox);
	    		ScrollPane scrollpane=new ScrollPane();//��ʾͼƬ���ֵ�˼·��scrollpane<-borderpane<-flowpane<-label
	    		pane_right.getChildren().add(scrollpane);
	    		scrollpane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);//������Ҫ����ʾ������
	    		scrollpane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    		//scrollpane.layoutXProperty().bind(vbox.prefWidthProperty());
	    		scrollpane.layoutYProperty().bind(hbox.prefHeightProperty().add(10));//��嶨λ
	    		//scrollpane.prefHeightProperty().bind(pane_right.heightProperty());
	    		//scrollpane.prefWidthProperty().bind(pane_right.widthProperty());
	    		
	    		//System.out.println(scrollpane);
	    		BorderPane border=new BorderPane();
	    		border.prefHeightProperty().bind(pane_right.prefHeightProperty().subtract(hbox.prefHeightProperty()).subtract(20));//����С��
	    		border.prefWidthProperty().bind(pane_right.prefWidthProperty());
	    		scrollpane.setContent(border);
	    		FlowPane flowpane=new FlowPane();
	    		flowpane.maxHeight(780);
	    		
	    		flowpane.prefHeightProperty().bind(pane_right.prefHeightProperty().subtract(hbox.prefHeightProperty()).subtract(20));
	    		flowpane.prefWidthProperty().bind(pane_right.prefWidthProperty());
	    		flowpane.setStyle("-fx-border-color:green;-fx-padding:10;");
	    		flowpane.setHgap(20);
	    		flowpane.setVgap(10);
	    		
	    		ContextMenu pop=new ContextMenu();//pop�˵����һ�ʱ�ŵ���
	    		MenuItem copy=new MenuItem("copy");
	    		MenuItem del=new MenuItem("del");
	    		MenuItem paste=new MenuItem("paste");
	    		MenuItem rName=new MenuItem("rName");
	    		pop.getItems().addAll(copy,new SeparatorMenuItem(),del,new SeparatorMenuItem(),paste,new SeparatorMenuItem(),rName);
	    		pop.setId("popMenu");
	    		System.out.println("hasImage:"+hasImage);
	    		if(hasImage==true) {//ûͼƬʱ��������ͼƬ��label����������ֻ��ִ��һ�Σ��ڶ��ξͽ�������
	    			ImageLabel[] imageLabelArray=new ImageLabel[imageList.size()] ;//���ҳ��ͼƬ��ImageLabel����
	    		for(int i=0;i<imageList.size();i++) {
	    			Image image = imageList.get(i);
	    			ImageLabel   newone=new ImageLabel(image,i);
	    			Label button=newone.creatLabel();
	    			Conter contername=new Conter();//ʵ�������������������Ĵ���
	    			button.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	        	@Override
	    	        	public void handle(MouseEvent e){
	    	        		if(e.getButton()==MouseButton.PRIMARY) {//ͼƬ�����
	    	        			if(!pasted.equals(button.getId())) {//����ϴε���Ĳ��Ǳ���ͼƬ���Ǽ�������ֵ��0
		    	        			contername.setValue(0);
		    	        			
		    	        		}
		    	        		 contername.add();//����������
		    	        		 pasted=button.getId();//��¼���ε���Ķ�����id
		    	        		 System.out.println(contername.getValue());
		    	        		 for(ImageLabel nowButton:imageLabelArray){//����ѡ����ʽǰ��ȫ��ͼƬ��Ϊδѡ��״̬
		    		       	        	  nowButton.NOTsetHover();
		    		       	        	 
		    	     			}
		    	        		 if(contername.getValue()==1) {//���һ�Σ�ͼƬ��Ϊѡ����ʽ
		    	        			 newone.setHover();
		    	        		 }
		    	        		 if(contername.getValue()>=2) {//����������Σ�������stage��ʾͼƬ����ϸ��Ϣ
		    	        			newone.newStage();
		    	        		 }
	    	        		}
	    	        		System.out.println(newone.getChose());
	    	        		if(newone.getChose()) {
	    	        			 if(e.getButton()==MouseButton.SECONDARY) {//ͼƬ���һ����������ܲ˵�
				    				  pop.show(button, e.getSceneX(),e.getSceneY());
				    				  
				    				  copy.setOnAction(new EventHandler<ActionEvent>() {//���ԣ���ÿ��copy��menuItem��һ�����ƵĹ���
				    		    		    public void handle(ActionEvent e) {
				    		    		        Clipboard clipboard = Clipboard.getSystemClipboard();
				    		    		        ClipboardContent content = new ClipboardContent();
				    		    		        content.putImage(newone.getImage());
				    		    		        clipboard.setContent(content);
				    		    		    }
				    		    		});
				    			  }else {
				    				  pop.hide();
				    			  }
	    	        		}
	    	        	}
	    	        });
	    			imageLabelArray[i]=newone;
	    			flowpane.getChildren().add(button);
	    		}
	    		
	    		Rectangle r = new Rectangle();//��ѡʱ��ѡ���
	    		border.getChildren().add(r);
	    		flowpane.setOnMouseClicked(new EventHandler<MouseEvent>() {//���ͼƬ�������
	    			@Override
	    			public void handle(MouseEvent e) {
	    				//System.out.println(IfDrag);
	    				if(e.getButton()==MouseButton.PRIMARY) {
	    					 //���
	    					Double  mouseX=e.getX()-flowpane.getLayoutX();
		    				Double  mouseY=e.getY()-flowpane.getLayoutY();
		    				//System.out.println("x_end:"+x_end);
		    				//System.out.println("y_end:"+y_end);
		    				//System.out.println(vbox.getPrefWidth());
		    				//System.out.println(hbox.getPrefHeight());
		    				//System.out.println("e.getX():"+(e.getX()+vbox.getPrefWidth()+2));
		    				//System.out.println("e.getY():"+(e.getY()+hbox.getPrefHeight()+12));
		    				/*
		    		    if( (e.getX()+vbox.getPrefWidth()+2)-x_end>1&&(e.getY()+hbox.getPrefHeight()+12)-y_end>1) {
		    					r.setHeight(0);
			          	    	r.setWidth(0);
			          	    	
		    					 for(ImageLabel nowLabel:imageLabelArray){ 
			    					 Double  LabelX= nowLabel.getButton().getPrefWidth();
			    						Double  LabelY= nowLabel.getButton().getPrefHeight();
			    					
			          	       if(mouseX<nowLabel.getButton().getLayoutX()||mouseY<nowLabel.getButton().getLayoutY()||
			          	    		   mouseX>(nowLabel.getButton().getLayoutX()+LabelX)||mouseY>(nowLabel.getButton().getLayoutY()+LabelY)) {
			          	    	 //System.out.println(nowLabel);
			          	    	     nowLabel.NOTsetHover();
			    			         }
			    				
			    			     }
		    				}*/
		    				if(IfDrag==0) {//��������϶���������ȫ��ͼƬ�ϵ�״̬ȡ��
		    					r.setHeight(0);
			          	    	r.setWidth(0);
			          	    	
		    					 for(ImageLabel nowLabel:imageLabelArray){ 
			    					 Double  LabelX= nowLabel.getButton().getPrefWidth();
			    						Double  LabelY= nowLabel.getButton().getPrefHeight();
			    					
			          	       if(mouseX<nowLabel.getButton().getLayoutX()||mouseY<nowLabel.getButton().getLayoutY()||
			          	    		   mouseX>(nowLabel.getButton().getLayoutX()+LabelX)||mouseY>(nowLabel.getButton().getLayoutY()+LabelY)) {
			          	    	 //System.out.println(nowLabel);
			          	    	     nowLabel.NOTsetHover();
			    			         }
			    				
			    			     }
		    				}
		    				
	    				}
	    				 if(e.getButton()==MouseButton.SECONDARY) {//ͼƬ�����һ�����ѡ��˵�
		    				  pop.show(primaryStage, e.getSceneX(),e.getSceneY());
		    			  }else {
		    				  pop.hide();
		    			  }
	    			}
	    		});
	    		 flowpane.setOnMousePressed(new EventHandler<MouseEvent>() {//ͼƬ���򰴶����
	    			@Override
	    			public void handle(MouseEvent e) {
	    				IfDrag=0;
	    				x_start=e.getSceneX();//��¼�϶���ʼλ��
	    				y_start=e.getSceneY();
	    				//System.out.println("x_start:"+x_start);
	    				//System.out.println("y_start:"+y_start);
	    				
	    			}
	    			});
	    		flowpane.setOnMouseDragged(new EventHandler<MouseEvent>() {//ͼƬ�����϶��¼�
	    			@Override
	    			public void handle(MouseEvent e) {
	    				//System.out.println("x_start:"+x_start);
	    				//System.out.println("y_start:"+y_start);
	    				IfDrag=1;//��¼�϶�����
	    				x_end=e.getSceneX();//��¼������λ��
	    				y_end=e.getSceneY();
	    				r.setHeight(y_end-y_start);
	    				r.setWidth(x_end-x_start);
	    				r.setLayoutX(x_start-vbox.getWidth());
	    				r.setLayoutY(y_start-hbox.getHeight());
	    				 for(ImageLabel nowLabel:imageLabelArray) {//�ж�ͼƬ�Ƿ����϶������ڣ��������ͼƬ��Ϊ��ѡ��״̬
	    					 nowLabel.NOTsetHover();
	    					 if(nowLabel.getButton().getLayoutX()>r.getLayoutX()&&nowLabel.getButton().getLayoutY()>r.getLayoutY()
	    							 &&nowLabel.getButton().getLayoutX()<(r.getLayoutX()+r.getWidth())&&nowLabel.getButton().getLayoutY()<(r.getLayoutY()+r.getHeight())) {
	    						 nowLabel.setHover();
	    					 }
	    					 if((nowLabel.getButton().getLayoutX()+nowLabel.getButton().getWidth())>r.getLayoutX()&&(nowLabel.getButton().getLayoutY()+nowLabel.getButton().getHeight())>r.getLayoutY()
	    							 &&(nowLabel.getButton().getLayoutX()+nowLabel.getButton().getWidth())<(r.getLayoutX()+r.getWidth())&&(nowLabel.getButton().getLayoutY()+nowLabel.getButton().getHeight())<(r.getLayoutY()+r.getHeight())) {
	    						 nowLabel.setHover();
	    					 }
	    				 }
	    			     r.setStroke(Color.BISQUE);//ѡ����ο����ʽ
	    				r.setStyle("-fx-border-radius:8px;-fx-opacity: 0.4;");
	    				//System.out.println("x_end:"+x_end);
	    				//System.out.println("y_end:"+y_end);
	    			}
	    			
	    			});
	    	}
	    		border.setCenter(flowpane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
   private TreeItem<File> createNode(final File f) {
		
	   return new TreeItem<File>(f) {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;
			@Override
			public ObservableList<TreeItem<File>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}
			
			
			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					File f = (File) getValue();
					isLeaf = f.isFile();
				}
				return isLeaf;
			}
			
			private ObservableList<TreeItem<File>> buildChildren(
			TreeItem<File> TreeItem) {
				
				File f = TreeItem.getValue();
				
				if (f == null) {
					return FXCollections.emptyObservableList();
				}
				
				if (f.isFile()) {
//					return FXCollections.emptyObservableList();
					return null;
				}
				
				File[] files = f.listFiles();
				
				if (files != null) {
					ObservableList<TreeItem<File>> children = FXCollections
					.observableArrayList();
					for (File childFile : files) {
						if(childFile.isDirectory()) {
							//�ݹ鹹��Ŀ¼��
							TreeItem<File> child = createNode(childFile);
							child.setGraphic(createIcon("../image/folder.png"));
							children.add(child);
						}
					}
					return children;
				}
				
				return FXCollections.emptyObservableList();
			}
		};
	}
	private void initView(Parent root,HBox vbox) {
		   
     	TreeItem<File> treeRoot = new TreeItem<>(new File("�ҵĵ���"));
    	File [] diskRoot = File.listRoots(); 
    	for(File file : diskRoot) 
		{ 
			TreeItem<File> diskNode =createNode(file);
			diskNode.setGraphic(createIcon("../image/disk.png"));
			treeRoot.getChildren().add(diskNode);
		} 
    	treeRoot.setGraphic(createIcon("../image/computer.png"));
        
    	TreeView treeView = new TreeView<File>(treeRoot);
    	vbox.getChildren().add(treeView);
			vbox.setHgrow(treeView, Priority.ALWAYS);
			treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<File>>() {
			      @Override
			      public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue,
			          TreeItem<File> newValue) {
                       
			    	 String Val=newValue.getValue().toString();
			    	 Img img=new Img(Val);
			    	 imageList=img.getImageList();
			    	 if(imageList!=null&&!imageList.isEmpty()) {
			    		 System.out.println(img.getImgListLength());
			    		 hasImage=true;//ÿ�ε���ǣ�����Ǹ�Ŀ¼��ͼƬ����ı�ֵ
			    		 //System.out.println(hasImage);
			    	 }else {
			    		 System.out.println(img.getImgListLength());
			    		 hasImage=false;
			    		 //System.out.println(hasImage);
			    	 }
			      }
			    });
		}
		  
		  public ImageView createIcon(String path) {
			  
			  ImageView icon = new ImageView();
			  Image Image = new Image(getClass().getResourceAsStream(path));
			  icon.setImage(Image);
			  icon.setFitWidth(16);
			  icon.setFitHeight(16);
			  return icon;
			  
		  }
	 
	public static void main(String[] args) {
		launch(args);
	}
}