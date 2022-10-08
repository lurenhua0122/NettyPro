package aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class AIOServerHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {


    @Override
    public void completed(AsynchronousSocketChannel result, AIOServer attachment) {
        attachment.getServerSocketChannel().accept(attachment,this);
        doRead(result);
    }

    private void doRead(AsynchronousSocketChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println(attachment.capacity());
                attachment.flip();
                System.out.println("from client:"+new String(attachment.array()));
                try {
                    doWrite(channel);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }
    private void doWrite(AsynchronousSocketChannel result) throws ExecutionException, InterruptedException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("向客户端发送数据");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        buffer.put(s.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        result.write(buffer).get();
//        同步的写法
//        result.write(buffer).get();
    }
    @Override
    public void failed(Throwable exc, AIOServer attachment) {
        exc.printStackTrace();
    }
}
