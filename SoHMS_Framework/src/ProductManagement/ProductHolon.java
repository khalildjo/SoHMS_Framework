package ProductManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import Crosscutting.*;
import DirectoryFacilitator.DirectoryFacilitator;
import MService.MService;
import OrdersManagement.OrderManager;
import ResourceManagement.Resource;
import ResourceManagement.ResourceHolon;
import ResourceManagement.Transporter;
import Workshop.LayoutMap;

public  class ProductHolon{
  
	//Attributes 
	public  static int phCount= 0;
	public  static int phListSize= 1000;
	
	protected int id;
	protected OrderManager ordermanager;
	protected LayoutMap productionPlan;
	protected ConcurrentHashMap<Integer,State> actionsPlan;
	protected ProductionProcess recipe;
	protected PH_Behavior_Planner exploreBehavior;
	protected Transporter associatedResource; //Palette for example for transport

	//Constructor
	public ProductHolon(){
		this.id= (phCount % phListSize) +1;
		phCount=this.id;
		productionPlan= new LayoutMap();
		actionsPlan= new ConcurrentHashMap<>(); //A terminer
	}
	public ProductHolon(OrderManager orderManager, ProductionProcess recipe){
		this.ordermanager= orderManager;
		this.recipe= recipe;
	}
	
	//methods
	public void launch() {
		//1-Associate this resource to the PH after selecting a free resource
		associateResourceToPH();
		//2-launch the behavior
		Thread phExploreThread = new Thread(exploreBehavior);
		phExploreThread.start();
		
	}
	public void associateResourceToPH() {
		/**
		 * Selectioner la premire resource que arrive au port initial et qui est libre
		 * Returns an associated Ressource to the PH.
		 */
		//1-Select a free resource.
		Transporter selectedResource= null;
		// Do until succesful association
		do{
			// Update List of Free Transporters
			ArrayList<Transporter> listOFResources= null;
			for (Transporter r : listOFResources) {
				//if(r.getp== PortPositionStatus.Blocked &&  // La Pallet est stable dans une position
				if(r.associatedPH==null){ // La Palette n'a pas de PH associ�
					selectedResource= r;
					break;
				}	
				//	}
			}
		}while(selectedResource==null);
		//2-Associate this resource to the PH
		this.associatedResource= selectedResource;
	}
	public void associateResource(Transporter s) {
     //Associate PH to resource
	  s.setAssociatedPH(this);
	//Associate resource to PH
	  this.associatedResource = s;
	};
	public void liberateResource() {
	  //1-liberate Resource from PH
	  this.associatedResource.liberate();
	  //2- liberate PH from Resource
	  this.associatedResource=null;
	};
	public void productTerminated() {
		ordermanager.phIsFinised(this);
		this.associatedResource=null;
	}
	
	public void addPathArcToExecutablePlans(ArrayList<PathArc> nextStepPlans) {}


	//Setters and Getters
	public static int getPhCount() {
		return phCount;
	}
	public static void setPhCount(int phCount) {
		ProductHolon.phCount = phCount;
	}
	public static int getPhListSize() {
		return phListSize;
	}
	public static void setPhListSize(int phListSize) {
		ProductHolon.phListSize = phListSize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OrderManager getOrdermanager() {
		return ordermanager;
	}
	public void setOrdermanager(OrderManager ordermanager) {
		this.ordermanager = ordermanager;
	}
	public LayoutMap getProductionPlan() {
		return productionPlan;
	}
	public void setProductionPlan(LayoutMap productionPlan) {
		this.productionPlan = productionPlan;
	}
	public ConcurrentHashMap<Integer, State> getActionsPlan() {
		return actionsPlan;
	}
	public void setActionsPlan(ConcurrentHashMap<Integer, State> actionsPlan) {
		this.actionsPlan = actionsPlan;
	}
	public ProductionProcess getRecipe() {
		return recipe;
	}
	public void setRecipe(ProductionProcess recipe) {
		this.recipe = recipe;
	}
	public PH_Behavior_Planner getExploreBehavior() {
		return exploreBehavior;
	}
	public void setExploreBehavior(PH_Behavior_Planner exploreBehavior) {
		this.exploreBehavior = exploreBehavior;
	}
	public Transporter getAssociatedResource() {
		return associatedResource;
	}
	public void setAssociatedResource(Transporter associatedResource) {
		this.associatedResource = associatedResource;
	}
}
