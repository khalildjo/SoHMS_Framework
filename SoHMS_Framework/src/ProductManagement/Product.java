package ProductManagement;

import java.util.List;

public class Product {
	
	//Attributes
    private int id;
	private String name;
    private int x,y;
     
    //Constructors
    public Product() {}
    public Product(int id, String name, int x, int y) {
    	this.id=id;
    	this.name=name;
    	this.x=x;
    	this.y=y;
    }
 
    //getters and setters
	 public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
