package Communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationField{

	private final int port = 1235;
	private final String FleximAddresse = "localhost";
	private Socket socket;
	private BufferedReader in;
	private PrintStream out;
	private BufferedWriter writer;
	private ServerSocket connection;
	//Constructor
	public CommunicationField() throws IOException {
		connection = new ServerSocket(port);
		/*
			try {
				this.socket = new Socket(InetAddress.getByName(FleximAddresse), port);
				this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            this.out = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		 */
		while(true)
		{
			System.out.println("new connection from Flexim");
			this.socket = connection.accept();
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write("MOVE SOURCE M1\r\n");
			writer.flush();
			String clientmsg;
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));

			writer.write("PROCESS M1 S10\r\n");
			writer.flush();
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));

			writer.write("MOVE M1 M2\r\n");
			writer.flush();
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));

			writer.write("PROCESS M2 S5\r\n");
			writer.flush();
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));

			writer.write("MOVE M2 M3\r\n");
			writer.flush();
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));

			writer.write("PROCESS M3 S14\n");
			writer.flush();
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));

			writer.write("MOVE M3 SINK\r\n");
			writer.flush();
			do {
				clientmsg = in.readLine();
				System.out.println(clientmsg);
			}while(!clientmsg.startsWith("END"));
		}    
	}

	private void execute(String message) throws IOException {
		this.out.println(message);
		writer.write(message +" \r\n");
		writer.flush();
	}

	public void move(String origin, String destination) throws IOException {
		String msg = "MOVE "+origin+" "+destination;
		execute(msg);
	}
}
