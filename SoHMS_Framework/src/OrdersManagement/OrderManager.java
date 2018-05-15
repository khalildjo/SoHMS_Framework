package OrdersManagement;

import java.io.PrintWriter;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import Crosscutting.OutBoxSender;
import ProductManagement.ProductHolon;
import ProductManagement.ProductionOrder;

 
  /* 
   * A generic Order Manager that :
   *    1-launches the PH to treat the products.
   *    2-Registers the progress on the products production process.
   */

public abstract class OrderManager {
	
	//Attributes
	protected ProductionOrder order;
	protected OutBoxSender outBoxSender;
	protected Set<ProductHolon> finishedPHs;
	protected Set<ProductHolon> activePHs;
	
	//Constructors
	public OrderManager() {
		finishedPHs = Collections.synchronizedSet(new HashSet<ProductHolon>()); // Synchronizes the acces to  this ArrayList. Must synchronize if iterated
		activePHs = Collections.synchronizedSet(new HashSet<ProductHolon>()); // Synchronizes the acces to  this ArrayList. Must synchronize if iterated
	}
	public OrderManager(ProductionOrder order, OutBoxSender outBoxSender) {
		System.out.println("order");
		this.order= order;
		this.outBoxSender= outBoxSender;
		finishedPHs = Collections.synchronizedSet(new HashSet<ProductHolon>()); // Synchronizes the acces to  this ArrayList. Must synchronize if iterated
		activePHs = Collections.synchronizedSet(new HashSet<ProductHolon>()); // Synchronizes the acces to  this ArrayList. Must synchronize if iterated
	}
	
	//a method that launchs the execution of an order. each order is a psecific to targer domain
	abstract void  launchOrder(
	  /*1-Ask number of needed resources. (maximum unit specifi� dans l'ordre !!!).
	    2-Launch all resources
	      2-1 Create a product Holon
		  2-2 Associate a behavior to the PH.
		  2-3 launch PH
		      2-3-1 Select a free resource. (la premi�re pallet que arrive au port initial et qui est libre)!!
		      2-3-2 Associate this resource to the PH
		      2-3-3 launch the behavior
		  2-4 Add the PH to the active PHs list.
		  2-5 Once Finished 
		      2-5-1 Send a notification
		      2-5-2 Add the PH to the active PHs list.
		      2-5-3 launch the next order
		      ..
		  2-6 Product Finished     
	  */
			
	);
	
    public synchronized void  phIsFinised(ProductHolon ph){	
		finishedPHs.add(ph);
		activePHs.remove(ph);	
		//another speicific behavior can be added here
	    //ph.liberateResource();
		// Launch new Products
		launchOrder();
     }
	
    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		return result;
	}
	
    public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderManager other = (OrderManager) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}	
	
    public String getOrderManagerName(){
		return ("OrderManager("+order.getProductionOrderID()+")");
	}

}
