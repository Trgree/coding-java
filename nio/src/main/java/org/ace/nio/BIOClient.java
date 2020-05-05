package org.ace.nio;

import java.io.*;
import java.net.Socket;

/**
 * @author L
 * @date 2018/5/5
 */
public class BIOClient {
    private static int port = 8000;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", port);
        System.out.println("客户端已启动");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null) {
            pw.println(line);
            pw.flush();
            System.out.println(in.readLine());
        }
        pw.close();
        br.close();
        in.close();
    }
}
