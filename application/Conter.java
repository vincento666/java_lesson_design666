package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Conter {

	
	 private IntegerProperty value = new SimpleIntegerProperty();
	 
	
		public IntegerProperty valueProperty() {
	        return this.value;
	    }
	    public int getValue() {
	        return this.value.get();
	    }
	    public void setValue(int value) {
	        this.value.set(value);
	    }
	    public void add() {
	        this.value.set(this.value.get() + 1);
	    }
}
