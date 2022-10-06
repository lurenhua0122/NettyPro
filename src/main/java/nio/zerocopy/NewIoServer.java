package nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIoServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(inetSocketAddress);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while(true){
            SocketChannel so = serverSocketChannel.accept();
            int count = 0;
            while(-1 != count){
                try {
                    count = so.read(buffer);
                }catch (Exception e){
//                    e.printStackTrace();
                    break;
                }
                buffer.rewind();
            }
        }
    }
}
