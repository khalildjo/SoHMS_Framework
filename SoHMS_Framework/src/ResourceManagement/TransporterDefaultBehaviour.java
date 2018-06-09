package ResourceManagement;

import java.util.HashSet;

import DirectoryFacilitator.DirectoryFacilitator;


public class TransporterDefaultBehaviour extends Thread {
	
	//Attributes
	private Transporter t;	

	//Getters and Setters
    public Transporter getPallet() {
		return t;
	}


	public void setPallet(Transporter t) {
		this.t = t;
	}


	//CONSTRUCTOR
	public TransporterDefaultBehaviour(Transporter t) {
		this.t= t;
	}
   
	
	// METHODS
	@Override
	public void run() {
		while(t!= null){  // condition to eliminate thread
			//Take charge of Pallet trajectory
			while(t.associatedPH==null){
				//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " BIP 4")
				//Request Actions while Blocked
				if(t.portStatus== "Blocked"){
					//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " BIP 5");
					requestDefaultAction(t.actualPort);
				}
				else {
					synchronized (t) {
						try {
							//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " waiting");
							t.wait();//Wait for changes in the Pallet 
							//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " ended waiting");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " BIP 1");
					}
					//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " BIP 2");
				}
				//System.out.println("== PalletDefaultBehavior Pallet "+ pallet._RFID+ " BIP 3");
			}// There is a PH associated
			t.waitPalletLiberation();	// will wait to re take control	
			System.out.println("Pallet "+t._RFID+" Liberated!");
	}//end While
 }//end run
//---------------------------------------------------------	
	private synchronized void requestDefaultAction(String port) {
		// REQUEST DEFAULT ACTION TO PORT OWNER
		HashSet<ResourceHolon>portOwners=DirectoryFacilitator.getPortOwners(port);
		String newPort= null;
						
		for (ResourceHolon rh : portOwners) {
			//System.out.println("PalletDefaultBehaviour: rh "+rh.getName()+" NbExec="+rh.roh.numOfCurrentExecutions);
			// Check that there is still no PH assigned
			if(t.associatedPH!= null) break; 
			  	// Request Transfer
				 newPort = rh.roh.requestDefaultTransfer(t, port);
				// If successful transfer
				 if(newPort!= null){
					t.actualPort= newPort;
					break; // if null then ask another port owner for its default action.
			 	}
		}//end for
	
	}

}
