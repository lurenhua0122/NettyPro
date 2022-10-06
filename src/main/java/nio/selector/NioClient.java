package nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //设置服务器的IP和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7000);
        if (!socketChannel.connect(inetSocketAddress)){
            while(socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作");
            }
        }
        String str = "hello world";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(byteBuffer);
        System.in.read();
        socketChannel.close();
    }
}
