package Protocols;

import commHMS.SocketMessage;
import Crosscutting.*;
public class Protocol_FieldNotifications {

	//HANDLER--------------------------------------------------------------------
	public static void handleMessage(SocketMessage message, OutBoxSender outBoxSender){
			
			String orderXMLString = message.content;
		
		switch (message.performative) {
			
			case "Pallet Location":
				
				break;
			case "Service Ack":
				//TODO
				
				break;

			default:
				break;
			}
			
		}
		
}
