import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	// to do --> data structure to hold server threads 
	private HashMap<Integer, ServerThread> threads = new HashMap<Integer, ServerThread>();
	private static HashMap<Integer, BufferedReader> readers = new HashMap<Integer, BufferedReader>();
	private static HashMap<Integer, PrintWriter> writers = new HashMap<Integer, PrintWriter>();
	private static int count = 0;
	public Server(int port) {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(port);
			while(true) {
				Socket s = ss.accept();
				ServerThread st = new ServerThread(s, this);
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				threads.put(++count, st);
				readers.put(++count, br);
				writers.put(++count, pw);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			for (HashMap.Entry<Integer,PrintWriter> entry : writers.entrySet()) {
				try {
					entry.getValue().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (HashMap.Entry<Integer,BufferedReader> entry : readers.entrySet()) {
				try {
					entry.getValue().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String [] args) {
		new Server(6789);
	}
}
