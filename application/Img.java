package application;

import java.io.File;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;
import javax.activation.MimetypesFileTypeMap;
public class Img {
	private ArrayList<Image> imageList;
	private File dir;

	
	public Img(String dirname) {
		loadImage(dirname);
	}
	
	
	private void loadImage(String dirname) {
		this.dir = new File(dirname);
		this.imageList = new ArrayList<Image>();
		File[] files = this.dir.listFiles();

		if(files.length != 0 && files != null) {
			for(File file : files) {
				if(isImage(file)) {
					this.imageList.add( new Image( "file:/" + file.getPath()  ) );
				}
			}
		}
	}
	
	
    public  boolean isImage(File file){
    	return isImgFileName(file);
    }
	
    
    public String getImagePathByIndex(int index) {
    	String url = this.imageList.get(index).impl_getUrl();
    	return url.substring(0, 6);
    }
    
    
	private boolean isImgFileName(File file) {
		Pattern pattern = Pattern.compile(".*\\.(jpg|png|gif)$");
		Matcher matcher = pattern.matcher(file.getPath());
		return matcher.matches();
	}
	
	public String getDirName() {
		return this.dir.getPath();
	}
	
	public void setDir(String dirname) {
		loadImage(dirname);
	}
	
	public int getImgListLength() {
		return this.imageList.size();
	}
	
	
	public Image getImgByIndex(int index) {
		if(index >= 0 && index + 1 <= this.imageList.size()) {
			return this.imageList.get(index);
		} else {
			return null;
		}
	}
	
	public ArrayList<Image> getImageList() {
		return this.imageList;
	}

}
