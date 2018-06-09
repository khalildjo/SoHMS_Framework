package commHMS;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Crosscutting.OutBoxSender;
import Protocols.*;

/*
 * This class launches all conversations and directs the messages to the conversation Threads
 * @author gamboa-f
 *
 */

public class InBoxReader extends Thread{

	//ATTRIBUTS-------------------------------
	public List<SocketMessage> inBoxBuffer = Collections.synchronizedList(new ArrayList<SocketMessage>()); // Synchronizes the acces to  this ArrayList. Must synchronize if iterated

	PrintWriter output = null;
	BufferedReader input = null;
	public boolean init = false;
	public Boolean _connexion ; 
	public OutBoxSender outBoxSender;

	public InBoxReader(){}
	//--------------------------------------
	public InBoxReader(Boolean _connexion, BufferedReader input, OutBoxSender outBoxSender){
		this._connexion= _connexion ;
		this.input= input;
		this.outBoxSender= outBoxSender;
	}
	//PUBLIC METHODS-------------------------------
	public void run(){
		init = true;
		while(_connexion) {
			synchronized (inBoxBuffer) {
				try {
					if (inBoxBuffer.isEmpty()) {
						inBoxBuffer.wait();//Wait for notification of new message
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//NOTIFICATION RECIEVED THAT THERE ARE NEW MESSAGES
			//Read and handle all messages
			while (!inBoxBuffer.isEmpty()){ // While there are Messages in the Inbox  //TODO this can be optimized
				SocketMessage message= null;
				//Read the Message
				synchronized (inBoxBuffer) {
					message = new SocketMessage(inBoxBuffer.get(0));
					inBoxBuffer.remove(0); //Remove the message from the list. It  has been "handled"
				}
				if (message.reciever.equals("HMI")){
					// Que fais-je?
				}
				if (message.reciever.equals("HMS")){

					//Launch the Action according to the Message's Protocol
					if(message.protocol.equalsIgnoreCase("OrderLaunching")){
						Protocol_OrderLaunching.handleMessage(message,outBoxSender);
					}
					else if ( message.protocol.equalsIgnoreCase("Field Notifications")){
						Protocol_FieldNotifications.handleMessage(message, outBoxSender);
					}

					else if ( message.protocol.equalsIgnoreCase("RH modify")){
						Protocol_RHmodify.handleMessage(message, outBoxSender);
					}
				}
			}
			Thread.yield(); // no messages then yield to other threads
		}
	}
}
