import java.io.*;
import java.net.*;
class server{
    
        public static void main(String[]asd) throws Exception{


                ServerSocket serv = new ServerSocket(9000);

                while (true){
                    try {
                        Socket user = serv.accept();
                        new server_re(user);
                    } catch (Exception e) {
                    }
                
                }
              
        }

    


}

 class server_re extends Thread{
            Socket user;
        server_re(Socket u){
            user = u;
            System.out.println("---connecting---");
            start();
        }
    public void run() {

        try {
            DataInputStream Dis = new DataInputStream(user.getInputStream());
            DataOutputStream dot = new DataOutputStream(user.getOutputStream());
            String Filename = Dis.readUTF();
            File filea = new File(Filename);
            dot.writeInt((int)filea.length());
            dot.flush();
          
            FileInputStream File = new FileInputStream(Filename);
            BufferedInputStream Bos = new BufferedInputStream(File);
            OutputStream os = user.getOutputStream();
            byte b[] = new byte[(int)filea.length()];
            int n;
            while((n =File.read(b)) != -1){
                dot.write(b,0,n);
                dot.flush();
            }
            dot.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
     
    }

}