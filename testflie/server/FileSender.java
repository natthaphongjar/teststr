//6 December 2013 Sirak
//This class is a class object to send a file from client to the server
//It will be used rather than using scp command which is required ssh configuration


import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.ByteBuffer;


public class FileSender {
	
	final int serverPort =  9999; 
	SocketChannel sc;	
	
	public FileSender(){
		
	}
	//This method uses file descriptor as parameter
	public void sendFile(String ipAddress, File file) throws IOException {
		
		SocketAddress sad = new InetSocketAddress(ipAddress,serverPort);
		try{
			sc = SocketChannel.open();
			
			sc.connect(sad);
			sc.configureBlocking(true);
		}catch(IOException e){
			System.err.println("Error in open socketChannel");
			e.printStackTrace();
		}
		
		long fileSize = file.length();
		
		String filePath = file.getPath();
		String fileHeader = filePath+"\n";
		System.out.print("send " + filePath);
		byte[] stuff = fileHeader.getBytes();
		
		ByteBuffer buf = ByteBuffer.wrap(stuff);
		sc.write(buf);
		buf.clear();
		
		FileChannel fc = new FileInputStream(filePath).getChannel();
		fc.transferTo(0, fileSize, sc);
		fc.close();
		sc.close();
		
		
	}
	// This method uses fileName in string as parameter
	public void sendFile(String ipAddress, String fileName) throws IOException {
		
		SocketAddress sad = new InetSocketAddress(ipAddress,serverPort);
		try{
			sc = SocketChannel.open();
			sc.connect(sad); 
			sc.configureBlocking(true);
		}catch(IOException e){
			System.err.println("Error in open socketChannel");
			e.printStackTrace();
		}
		
	    File file = new File(fileName);
		long fileSize = file.length();
		String filePath = file.getPath();
		String fileHeader = filePath+"\n";
		System.out.print("send " + filePath);
		byte[] stuff = fileHeader.getBytes();
		
		ByteBuffer buf = ByteBuffer.wrap(stuff);
		sc.write(buf);
		
		buf.clear();
		
		FileChannel fc = new FileInputStream(filePath).getChannel();
		fc.transferTo(0, fileSize, sc);
		fc.close();
		sc.close();
		
		
		
	}
	
}
