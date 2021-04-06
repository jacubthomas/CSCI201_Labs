import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerThread extends Thread{
	static BufferedReader br;
	static PrintStream ps;
	InputStream is;
	static Socket s;
//	private static Server server;
	public ServerThread(Socket s, Server serv) {
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			ps = new PrintStream(new BufferedOutputStream(s.getOutputStream()));
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	public void run() {
		try {
			String line = "";
			while(true) {
				line = br.readLine();
				if(!line.equalsIgnoreCase(""))
					break;
			}
			// top-tier parsing
			String filename = "";
			System.out.println(line);
			if(line != null && line != "") {
				filename = line.substring(line.indexOf("GET /") + 5, line.indexOf("HTTP"));
			}
			System.out.println(filename);
			
			String response =
					"HTTP /1.1 200 OK\r\n"
					+ "Content-type: text/html\r\n\r\n";
			String filePath = new File("").getAbsolutePath();
			filePath += "/src";
			System.out.println(filePath);
			is = new FileInputStream(filePath + "/" + filename.trim());
			System.out.println(filePath + "/" + filename.trim());
			
			Path path =  Paths.get(filePath + "/" + filename.trim());
			ps.print(response);
			ps.write(Files.readAllBytes(path));
			ps.flush();
		} catch (FileNotFoundException fnfe) {
			String response =
					"HTTP /1.1 200 OK\r\n"
					+ "Content-type: text/html\r\n\r\n";
			String filePath = new File("").getAbsolutePath();
			filePath += "/src/404.html";
			Path path = Paths.get(filePath);
			ps.print(response);
			try {
				ps.write(Files.readAllBytes(path));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ps.flush();
		} catch (IOException ioe) {
			
			ioe.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(s != null)
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
