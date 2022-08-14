
import java.io.*;
import java.net.*;


class host {
    private static ServerSocket server;
    private static int port = 9999;

    public static void main(String[] ads) throws IOException {
        server = new ServerSocket(port);
        System.out.println("-----Waiting for client-----");
        for (;;) {
            try {
                Socket client = server.accept();
                Thread thread_Server = new Thread(new hostRequest(client));
                thread_Server.start();
                System.out.println("Client Connected  Port :" + client.getPort());
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

}

class hostRequest implements Runnable {

    private static Socket socket;
    private String Username;
    DataInputStream datain;
    DataOutputStream dataout;
    File[] fList;

    public hostRequest(Socket client) {
        socket = client;
    }

    public void run() {

        try {

            datain = new DataInputStream(socket.getInputStream());
            dataout = new DataOutputStream(socket.getOutputStream());
            File directory = new File("C:/Users/taza3/Desktop/teststr/server"); // set path server
            fList = directory.listFiles();
            Username = datain.readUTF();
            System.out.println(Username + " running ");
            String[] book = new String[1000];
            int n = 0;
            for (File file : fList) {
                book[n] = file.getName();
                n++;
            }
            dataout.writeInt(n);
            for (int j = 0; j < n; j++) {
                dataout.writeUTF(book[j]);
            }
            while (true) {
                dataout.flush();
                String filename = datain.readUTF();
                if(filename.charAt(0)=='n')
                {
                       break;
                }
                sendFile(filename);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void sendFile(String Fname) throws IOException {
        System.out.println("----ready to send----");

        try {
            String path = "C:/Users/taza3/Desktop/teststr/server" + "/".concat(Fname);
            path.concat(Fname);
            System.out.println(path);
            File Fp = new File(path);
            System.out.println("Path File in server : " + Fp.getPath());
           
            DataOutputStream dataoutputstream = new DataOutputStream(dataout);
            FileInputStream filein = new FileInputStream(Fp);
            dataoutputstream.writeBoolean(true);
            byte BF[] = new byte[(int) Fp.length()];
            filein.read(BF);
            //fliein.flush();
            dataoutputstream.writeInt(BF.length);
            dataoutputstream.write(BF);
            System.out.println("Send  File " + Fname +" to " + Username + " ");
        } catch (Exception e) {
            dataout.writeBoolean(false);
            e.printStackTrace();
            System.out.println("File not found");
        }

    }

}