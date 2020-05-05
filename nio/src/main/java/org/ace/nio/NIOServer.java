package org.ace.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author L
 * @date 2018/5/5
 */
public class NIOServer {
    private static int port = 8000;
    private static InetSocketAddress address = null;
    private static Selector selector;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        address = new InetSocketAddress(port);
        server.bind(address);
        // 设置成非阻塞
        server.configureBlocking(false);
        // 初始化selector
        selector = Selector.open();
        // 把路和selector关联上
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已启动");
        // 轮循操作
        while(true){
            System.out.println("Waiting for select...");
            int noOfKeys = selector.select();
            System.out.println("Number of selected keys: " + noOfKeys);
            if(noOfKeys == 0){continue;}
            Set<SelectionKey> selectionKeys =  selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while(it.hasNext()){
                SelectionKey selectionKey = it.next();
                process(selectionKey);
                it.remove();
            }

        }
    }

    private static void process(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()){
            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
            SocketChannel client = server.accept();
            if (client == null) {
                return;
            }
            // 设置成非阻塞
            client.configureBlocking(false);
            // 要对这个key进行注册
            client.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端注册进来");
        } else if(selectionKey.isConnectable()) {
            System.out.println("is connectalbe");
        }

        else if(selectionKey.isReadable()){
            SocketChannel client = (SocketChannel) selectionKey.channel();
            int len = client.read(buffer);
            if(len > 0) {
                buffer.flip(); // 锁定 把客户端数据读取buffer中
                String text = new String(buffer.array(), 0 , len);
                System.out.println(text);
                System.out.println("Message read from client: " + text);
                if (text.equals("Bye.")) {
                    client.close();
                    System.out.println("Client messages are complete; close.");
                }
            }
            // 读取完成后，需要告诉selector
          //  client.register(selector, SelectionKey.OP_WRITE);
            buffer.clear();
            System.out.println("读取客户端完成");
        } else if(selectionKey.isWritable()){
            SocketChannel client = (SocketChannel) selectionKey.channel();
            client.write(buffer.wrap("恭喜你，取到钱了".getBytes()));
            // client.close();
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
           // client.register(selector, SelectionKey.OP_READ);
            System.out.println("写客户端完成");
            buffer.clear();
        }
    }
}
