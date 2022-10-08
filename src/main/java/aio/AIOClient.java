package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class AIOClient {
    AsynchronousSocketChannel channel;

    public AIOClient() throws IOException {
        init();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        System.out.println("向服务器端发送数据");
        Scanner s = new Scanner(System.in);
        String line = s.nextLine();
        AIOClient client = new AIOClient();
        client.write(line);
        client.read();
        client.doDestory();
    }
    private void init() throws IOException {
        channel = AsynchronousSocketChannel.open();
        channel.connect(new InetSocketAddress("localhost",9999));
    }
    private void write(String s) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(s.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
    }
    private void read() throws IOException, ExecutionException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer).get();
        System.out.println(new String(buffer.array()));
    }
    private  void doDestory() throws IOException {
        if (null != channel){
            channel.close();
        }
    }
}
