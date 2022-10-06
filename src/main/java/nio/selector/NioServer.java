package nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel =  ServerSocketChannel.open();

        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(7000));
//        非阻塞
        serverSocketChannel.configureBlocking(false);
//        把通道注册到selector 关心的 事件 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("注册后的selection Keys size:"+selector.keys().size());
//        循环等待客户端连接
        while(true){
            //等待1秒，如果没有事件，返回继续
            if (selector.select(1000) ==0){
                System.out.println("服务器等待了一秒，无连接");
                continue;
            }
//            如果返回的不是0,返回有事件发生的
//            关注事件的集合
//            通过selectionkey取得Selector
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //取得相应selectionkey
                SelectionKey key = iterator.next();
                //根据Key对应通道发生的事件做相应的处理
                if (key.isAcceptable()){
                    //客户端生成SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    /**
                     * 设置非阻塞模式
                     * Exception in thread "main" java.nio.channels.IllegalBlockingModeException
                     * 	at java.base/java.nio.channels.spi.AbstractSelectableChannel.register(AbstractSelectableChannel.java:225)
                     * 	at nio.selector.NioServer.main(NioServer.java:42)
                     *
                     * Process finished with exit code 1
                     */
                    socketChannel.configureBlocking(false);
                    System.out.println("连接正常："+socketChannel.hashCode());
                    //将当前的SocketChannel注册到Selector中去,
                    //关注 SelectionKey.OP_READ
                    //关联一个Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("客户端连接后，注册到selection Keys size:"+selector.keys().size());
                }else if (key.isReadable()){  //发生读取事件
                    //通过Key反向取得Channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    channel.read(byteBuffer);
                    System.out.println("客户端发送的信息："+ new String(byteBuffer.array()));
                }
                //手动从集合中移除当前的selectionkey ，防止重复操作。
                iterator.remove();
            }
        }


    }
}
