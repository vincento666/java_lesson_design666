package application;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TREE {
	private TreeItem<File> t;
	private final File ft;
	private HBox vbox;
	private  ArrayList<Image> images;
	private Boolean hasImage=false;
	public Boolean getHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public TREE(final File ft,HBox vbox) {
		this.ft=ft;
		
		this.vbox=vbox;
		t=createNode(ft);
		initView();
	}
	
	 TreeItem<File> createNode(final File f) {
		
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
	public void initView() {
 		   File [] diskRoot = File.listRoots();
        	for(File file : diskRoot) 
			{ 
				TreeItem<File> diskNode =createNode(file);
				diskNode.setGraphic(createIcon("../image/disk.png"));
				t.getChildren().add(diskNode);
			} 
			
			t.setGraphic(createIcon("../image/computer.png"));
           
			TreeView treeView = new TreeView<File>(t);
			vbox.getChildren().add(treeView);
			vbox.setHgrow(treeView, Priority.ALWAYS);
			treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<File>>() {
			      @Override
			      public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue,
			          TreeItem<File> newValue) {
                          
			    	 String Val=newValue.getValue().toString();
			    	 Img img=new Img(Val);
			    	 images=img.getImageList();
			    	 if(images!=null&&!images.isEmpty()) {
			    		 System.out.println(img.getImgListLength());
			    		 hasImage=true;
			    		 System.out.println(hasImage);
			    	 }else {
			    		 System.out.println(img.getImgListLength());
			    		 hasImage=false;
			    		 System.out.println(hasImage);
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
}
