
import java.io.*;
import java.net.*;
import java.util.*;

public class user {
    private  Socket myclient;
    private String filename;
    public static void main(String[] ads) throws ClassNotFoundException {
        new user();
    }

    user() {
        run();
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.print("Port  Connect : ");
            int port = scan.nextInt();
            myclient = new Socket("localhost", port);
            System.out.println("User name ");
            String name = scan.next();
            System.out.println("-----Connecting to Server-----");
            DataInputStream datain = new DataInputStream(myclient.getInputStream());
            DataOutputStream dataout = new DataOutputStream(myclient.getOutputStream());
            dataout.writeUTF(name);
            int n = datain.readInt();
            System.out.println(n+"FIle in server");
            for (int j = 0; j < n; j++) {
                String x = datain.readUTF();
                System.out.println("Select : "+(j + 1) + " |" + x + "|");
            }
            System.out.println();
            while (true) {
                try {
                    System.out.print("Select Filename (exit :'n'): ");
                     filename = scan.next();
                    dataout.writeUTF(filename);
                    dataout.flush();
                    if(filename.charAt(0)=='n')
                    {
                        System.exit(1);
                    }
                } catch (Exception e) {
                    System.out.println("input index : 1  - " + n);
                    continue;
                }
                if(datain.readBoolean())
                {
                    read(myclient,filename);
                }else
                {
                 System.out.println("Invalid file name");   
                 continue;
                }
                

            }
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public void read(Socket myclie,String  filename) {
        try {
            DataInputStream datain = new DataInputStream(myclient.getInputStream());
                    System.out.println(filename + "\n---on Server---");
                    int len = datain.readInt();
                    if (len > 0) {
                        byte BF[] = new byte[len];
                        datain.readFully(BF, 0, len);
                        File dowload = new File(filename);
                        try {
                            FileOutputStream Fos = new FileOutputStream(dowload);
                            Fos.write(BF);
                            Fos.close();
                            
                        } catch (Exception e) {
                            System.out.println("OUT");
                            System.exit(1);
                        }
                    }
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    

    }

}
