package Communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ListenerField{

    Socket socketArena;
	
	public ListenerField(Socket s) {
		this.socketArena = s;
	}
	
	public void run(){
		String message = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socketArena.getInputStream()));

			while(true) {
				message = reader.readLine();		
				File log = new File("logsFlexsim.txt");
				PrintWriter out = new PrintWriter(new FileWriter(log, true));
				out.write(message + '\n');
				out.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
