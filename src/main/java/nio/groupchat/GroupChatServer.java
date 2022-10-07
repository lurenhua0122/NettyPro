package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class GroupChatServer {
    //    定义相关属性
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int Port = 7000;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(Port));
            //设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        System.out.println("线程："+Thread.currentThread().getName());
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    //有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            //服务器监听
                            SocketChannel sc = serverSocketChannel.accept();
                            sc.configureBlocking(false);
//                            将selector注册到socketchannel
                            sc.register(selector, SelectionKey.OP_READ);
                            //服务端提示
                            System.out.println("已经上线：" + sc.getRemoteAddress());
                        } else if (key.isReadable()) {
                            //读取事件
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
//                    System.out.println("服务端等待中.....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    private void readData(SelectionKey key){
        SocketChannel socketChannel = null;
        try {
            socketChannel =  (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0){
                String msg = new String(buffer.array());
                System.out.println("客服端的消息："+msg);
                //向其它客户转发消息
                sendInfoToOtherClients(msg,socketChannel);
            }
        }catch (Exception e){
            try {
                System.out.println(socketChannel.getRemoteAddress() + " 离线了！");
                key.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }finally {

        }
    }
    private void sendInfoToOtherClients(String msg , SocketChannel self) throws IOException {
        System.out.println("服务器转发消息线程："+Thread.currentThread().getName());
        System.out.println("服务器转发消息");
        //遍历所有 注册到selector上的所有 socketchannel 并排除self
        for (SelectionKey key :
                selector.keys()) {
            Channel target = key.channel();
            if (target instanceof SocketChannel && target != self){
                SocketChannel socketChannel = (SocketChannel) target;
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }
    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
