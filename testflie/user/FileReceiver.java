//6 December 2013 Sirak
//FileReceiver is a class object  to receive file from remote client, check the exiting of the directory, 
//and create file or directory if necessary. 


import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.io.*;

public class FileReceiver {
	ServerSocketChannel listener;
	final int serverPort = 9999;
	
	public FileReceiver(){
		InetSocketAddress listenAddr = new InetSocketAddress(serverPort);
		try{
			listener = ServerSocketChannel.open();
			ServerSocket ss = listener.socket();
			//DataInputStream din = new DataInputStream(ss);
			ss.setReuseAddress(true);
			ss.bind(listenAddr);
			
		}catch(IOException e){
			System.out.println("Failed to bind port:" + listenAddr.toString());
			e.printStackTrace();
		}
		//this.receiveData();
	}
	
	public void receiveData(){
		ByteBuffer dst = ByteBuffer.allocate(4096);
		ByteBuffer tmp = ByteBuffer.allocate(4096);
		try{
			while(true){
				SocketChannel conn = listener.accept();
				conn.configureBlocking(true);
				int nread =0;
				int fileSize = 0;
	
				File file;
				FileChannel channel = null;
				int index = 0;
				while(nread != -1){
					try{
						
						nread = conn.read(dst);
						// checking index = 0 for the first block that contains file name
						if(index == 0){ 
							
							int num = conn.read(dst);
							byte[] name = dst.array();
											
							
							// put file name
							String fileFullPath = new String(name, "UTF-8");
							
							String [] n = fileFullPath.split("\n");
							fileFullPath = n[0];
							
							//System.out.println("Long file name  is " + n[0]);
							//System.out.println("FileName.length = " +fileFullPath.length() );
							//System.out.println("fileName = " + fileFullPath);
							
							String[] nameSplit = fileFullPath.split("/");
							String path = "/";
							for(int i = 1; i < (nameSplit.length)-1; i++){
							
								path+=nameSplit[i];
								File f = new File(path);
								if(f.exists()){
									path+="/";
								}else{
									f.mkdir();
									path+="/";
								}
							}
							

							
							file = new File(fileFullPath);
							if(file.exists()){
								file.delete(); // to make sure getting the new file
							}
							
							
							// getting file content
							boolean append = true;
							channel = new FileOutputStream(file, append).getChannel();
							dst.flip();
							dst.position(fileFullPath.length()+1);
							channel.write(dst);
							dst.clear();
							dst.put(new byte[4096]);
							dst.clear();
							
							
						}// end if
						//System.out.println("read " + nread + " bytes");
						fileSize+=nread;
						dst.flip();
						channel.write(dst);
						dst.clear();
						dst.put(new byte[4096]);
						dst.clear();
					}catch(IOException e){
						System.out.println("FileReceiver object got an error in writing file");
						e.printStackTrace();
						nread = -1;
					}
					dst.rewind();
					index++;
				}
				dst.clear();
				channel.close();
				
				
			}// end while
		}catch(IOException e){
			System.out.println("Error in FileReceiver object");
			e.printStackTrace();
		}
	}
}
