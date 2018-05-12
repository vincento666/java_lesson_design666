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
	
	 public static  ArrayList<Image> imageList;//测试
 	public static  String pasted="null";//记录上次点击的图片的id
 	double x_start ;//多选时记录拖动的初始位置x
    double y_start;//多选时记录拖动的初始位置y
  double x_end;//多选时记录拖动的结束位置x
    double y_end;//多选时记录拖动的结束位置y
    int IfDrag=0;//判断是否是拖动操作，用在flowpane的点击事件的绑定上
    Boolean hasImage=false;//初始设置为没有图片
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();//主面板
			Scene scene = new Scene(root,800,800); 
			root.prefHeightProperty().bind(scene.heightProperty());//主面板size绑定到窗口大小
	        root.prefWidthProperty().bind(scene.widthProperty());
		  // FirstStage firststage=new FirstStage();
		   //BorderPane borderpane=firststage.creatPane(root); 
	    		Pane  pane_bottom=new Pane();//面板底部
	    		//System.out.println(pane_bottom);
	    		root.setBottom(pane_bottom);
	    		pane_bottom.prefHeightProperty().bind(root.heightProperty().divide(15));//绑定底部面板大小
	    		pane_bottom.prefWidthProperty().bind(root.widthProperty());
	    		pane_bottom.setStyle("-fx-background-color:black");
	    		HBox vbox = new HBox();//放目录树
	    		root.setLeft(vbox);
	    		initView(root,vbox);
	    		vbox.prefWidthProperty().bind(root.widthProperty().divide(4.5));//绑定显示目录树的面板大小
	    		vbox.prefHeightProperty().bind(root.heightProperty().subtract(pane_bottom.prefHeightProperty()));
	    	  	
	    	
	    		
	    		Pane  pane_right=new Pane();//右边面板
	    		root.setRight(pane_right);
	    		//pane_right.setPrefHeight(root.getHeight()-100-50-menu.getHeight()-36);
	    		pane_right.prefHeightProperty().bind(root.heightProperty().subtract(pane_bottom.prefHeightProperty()));
	    		pane_right.prefWidthProperty().bind(root.widthProperty().subtract(vbox.prefWidthProperty()));
	    		//System.out.println(root.heightProperty());
	    		HBox  hbox=new HBox();//显示所点击的目录有多少张图片
	    		hbox.prefHeightProperty().bind(root.heightProperty().divide(15));
	    		hbox.prefWidthProperty().bind(pane_right.prefWidthProperty());
	    		hbox.setStyle("-fx-background-color:white;-fx-padding:5");
	    		Image image2 = new Image("image/imageicon.png");
	    		ImageView imageviewicon=new ImageView(image2);
	    		imageviewicon.setFitHeight(50);
	    		imageviewicon.setFitWidth(50);
	    		String total;
	    		if(imageList==null) {
	    			 total="文件名共0张";//此处文件名要从多点击的目录获得
	    			System.out.println("没有图片！");
	    		}else {
	    			  hasImage=true;//如果有图片只设置为true
	    			System.out.println("有图片！");
	    			total="文件名"+"共"+imageList.size()+"张";
	    		}
	    	    Label imageicon=new Label(total,imageviewicon);//显示多少张图片
	    		//Label imageicon=new Label();
	    		imageicon.prefHeightProperty().bind(hbox.prefHeightProperty().divide(1.2));
	    		imageicon.prefWidthProperty().bind(hbox.prefWidthProperty().divide(5.0));
	    		//imageicon.layoutXProperty().bind(vbox.layoutXProperty().add(20));
	    		imageicon.setStyle("-fx-border-color:black;-fx-font-size:14");
	    		imageicon.setWrapText(true);
	    		hbox.getChildren().add(imageicon);
	    		//显示爱心图片
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
	    		ScrollPane scrollpane=new ScrollPane();//显示图片部分的思路是scrollpane<-borderpane<-flowpane<-label
	    		pane_right.getChildren().add(scrollpane);
	    		scrollpane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);//设置需要是显示滚动条
	    		scrollpane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
	    		//scrollpane.layoutXProperty().bind(vbox.prefWidthProperty());
	    		scrollpane.layoutYProperty().bind(hbox.prefHeightProperty().add(10));//面板定位
	    		//scrollpane.prefHeightProperty().bind(pane_right.heightProperty());
	    		//scrollpane.prefWidthProperty().bind(pane_right.widthProperty());
	    		
	    		//System.out.println(scrollpane);
	    		BorderPane border=new BorderPane();
	    		border.prefHeightProperty().bind(pane_right.prefHeightProperty().subtract(hbox.prefHeightProperty()).subtract(20));//面板大小绑定
	    		border.prefWidthProperty().bind(pane_right.prefWidthProperty());
	    		scrollpane.setContent(border);
	    		FlowPane flowpane=new FlowPane();
	    		flowpane.maxHeight(780);
	    		
	    		flowpane.prefHeightProperty().bind(pane_right.prefHeightProperty().subtract(hbox.prefHeightProperty()).subtract(20));
	    		flowpane.prefWidthProperty().bind(pane_right.prefWidthProperty());
	    		flowpane.setStyle("-fx-border-color:green;-fx-padding:10;");
	    		flowpane.setHgap(20);
	    		flowpane.setVgap(10);
	    		
	    		ContextMenu pop=new ContextMenu();//pop菜单，右击时才弹出
	    		MenuItem copy=new MenuItem("copy");
	    		MenuItem del=new MenuItem("del");
	    		MenuItem paste=new MenuItem("paste");
	    		MenuItem rName=new MenuItem("rName");
	    		pop.getItems().addAll(copy,new SeparatorMenuItem(),del,new SeparatorMenuItem(),paste,new SeparatorMenuItem(),rName);
	    		pop.setId("popMenu");
	    		System.out.println("hasImage:"+hasImage);
	    		if(hasImage==true) {//没图片时，不生成图片的label，但是这里只会执行一次，第二次就进不来了
	    			ImageLabel[] imageLabelArray=new ImageLabel[imageList.size()] ;//存放页面图片的ImageLabel数组
	    		for(int i=0;i<imageList.size();i++) {
	    			Image image = imageList.get(i);
	    			ImageLabel   newone=new ImageLabel(image,i);
	    			Label button=newone.creatLabel();
	    			Conter contername=new Conter();//实例化计数器，计算点击的次数
	    			button.setOnMouseClicked(new EventHandler<MouseEvent>(){
	    	        	@Override
	    	        	public void handle(MouseEvent e){
	    	        		if(e.getButton()==MouseButton.PRIMARY) {//图片上左击
	    	        			if(!pasted.equals(button.getId())) {//如果上次点击的不是本张图片，那计算器的值清0
		    	        			contername.setValue(0);
		    	        			
		    	        		}
		    	        		 contername.add();//计算点击次数
		    	        		 pasted=button.getId();//记录本次点击的东西的id
		    	        		 System.out.println(contername.getValue());
		    	        		 for(ImageLabel nowButton:imageLabelArray){//增加选中样式前，全部图片变为未选中状态
		    		       	        	  nowButton.NOTsetHover();
		    		       	        	 
		    	     			}
		    	        		 if(contername.getValue()==1) {//点击一次，图片变为选中样式
		    	        			 newone.setHover();
		    	        		 }
		    	        		 if(contername.getValue()>=2) {//点击大于两次，生成新stage显示图片的详细信息
		    	        			newone.newStage();
		    	        		 }
	    	        		}
	    	        		System.out.println(newone.getChose());
	    	        		if(newone.getChose()) {
	    	        			 if(e.getButton()==MouseButton.SECONDARY) {//图片上右击，弹出功能菜单
				    				  pop.show(button, e.getSceneX(),e.getSceneY());
				    				  
				    				  copy.setOnAction(new EventHandler<ActionEvent>() {//测试：给每个copy的menuItem绑定一个复制的功能
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
	    		
	    		Rectangle r = new Rectangle();//多选时的选择框
	    		border.getChildren().add(r);
	    		flowpane.setOnMouseClicked(new EventHandler<MouseEvent>() {//点击图片区域面板
	    			@Override
	    			public void handle(MouseEvent e) {
	    				//System.out.println(IfDrag);
	    				if(e.getButton()==MouseButton.PRIMARY) {
	    					 //左击
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
		    				if(IfDrag==0) {//如果不是拖动才做，将全部图片上的状态取消
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
	    				 if(e.getButton()==MouseButton.SECONDARY) {//图片区域右击弹出选择菜单
		    				  pop.show(primaryStage, e.getSceneX(),e.getSceneY());
		    			  }else {
		    				  pop.hide();
		    			  }
	    			}
	    		});
	    		 flowpane.setOnMousePressed(new EventHandler<MouseEvent>() {//图片区域按动鼠标
	    			@Override
	    			public void handle(MouseEvent e) {
	    				IfDrag=0;
	    				x_start=e.getSceneX();//记录拖动初始位置
	    				y_start=e.getSceneY();
	    				//System.out.println("x_start:"+x_start);
	    				//System.out.println("y_start:"+y_start);
	    				
	    			}
	    			});
	    		flowpane.setOnMouseDragged(new EventHandler<MouseEvent>() {//图片区域拖动事件
	    			@Override
	    			public void handle(MouseEvent e) {
	    				//System.out.println("x_start:"+x_start);
	    				//System.out.println("y_start:"+y_start);
	    				IfDrag=1;//记录拖动操作
	    				x_end=e.getSceneX();//记录结束的位置
	    				y_end=e.getSceneY();
	    				r.setHeight(y_end-y_start);
	    				r.setWidth(x_end-x_start);
	    				r.setLayoutX(x_start-vbox.getWidth());
	    				r.setLayoutY(y_start-hbox.getHeight());
	    				 for(ImageLabel nowLabel:imageLabelArray) {//判断图片是否在拖动区域内，如果在则图片边为被选中状态
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
	    			     r.setStroke(Color.BISQUE);//选择矩形框的样式
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
							//递归构建目录树
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
		   
     	TreeItem<File> treeRoot = new TreeItem<>(new File("我的电脑"));
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
			    		 hasImage=true;//每次点击是，如果那个目录有图片，则改变值
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