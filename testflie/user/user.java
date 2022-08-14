import java.io.*;
import java.net.*;
import java.util.Scanner;
class user {
    public static void main(String[]ads) throws Exception{
        Socket sc = new Socket("localhost",9000);
        DataInputStream dit = new DataInputStream(sc.getInputStream());
        DataOutputStream Dos = new DataOutputStream(sc.getOutputStream());
        Scanner scan = new Scanner(System.in);
        System.out.print("pls file name : ");
        String name = scan.next();
        Dos.writeUTF(name);
        int n = dit.readInt();
        byte b[] = new byte[n];
        InputStream is = sc.getInputStream();
        FileOutputStream fos = new FileOutputStream(name);
        while((n = dit.read(b)) != -1){
            fos.write(b,0,n);
            fos.flush();
        }
        fos.close();
        }


    }
    

