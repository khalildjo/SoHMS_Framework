package ResourceManagement;

import java.util.ArrayList;

import ProductManagement.ProductHolon;

public abstract class Resource {
	
	//Attributes
	protected int resourceId;
	protected String name;
	protected String technology;
	protected String category;
	protected String textDescription;
	protected ArrayList<String> inputPorts;
	protected ArrayList<String> outputPorts;
//	protected ProductHolon associated_PH;
	
	
	public Resource() {
	}
	public Resource(Resource r) {
		this.name= r.getName();
		this.technology=r.getTechnology();
		this.category=r.getCategory();
		this.textDescription=r.getTextDescription();
	}
	public Resource(int resourceId, String name, String technology, String category, String textDescription,
			ArrayList<String> inputPorts, ArrayList<String> outputPorts) {
		this.resourceId = resourceId;
		this.name = name;
		this.technology = technology;
		this.category = category;
		this.textDescription = textDescription;
		this.inputPorts = inputPorts;
		this.outputPorts = outputPorts;
	}
	public Resource(String name, String technology, String category, String textDescription,
			ArrayList<String> inputPorts, ArrayList<String> outputPorts) {
		this.name = name;
		this.technology = technology;
		this.category = category;
		this.textDescription = textDescription;
		this.inputPorts = inputPorts;
		this.outputPorts = outputPorts;
	}
	

	//Getters
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getName() {
		return name;
	}
	public String getTechnology() {
		return technology;
	}
	
	public String getCategory() {
		return category;
	}
	public String getTextDescription() {
		return textDescription;
	}
	public ArrayList<String> getInputPorts() {
		return inputPorts;
	}
	public ArrayList<String> getOutputPorts() {
		return outputPorts;
	}
    //Setters
	
	public void setName(String name) {
		this.name = name;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}
	public void setInputPorts(ArrayList<String> inputPorts) {
		this.inputPorts = inputPorts;
	}
	public void setOutputPorts(ArrayList<String> outputPorts) {
		this.outputPorts = outputPorts;
	}
	
	//methods
	public String toString() {
		return "Resource [resourceId=" + this.resourceId + ", name=" + this.name
				+ ", technology=" + this.technology + ", category=" + this.category
				+ ", textDescription=" +this.textDescription + ", inputPorts="
				+ inputPorts + ", outputPorts=" + outputPorts + "]";
	}
	
}