package aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AIOServer {
    public ExecutorService getService() {
        return service;
    }

    public void setService(ExecutorService service) {
        this.service = service;
    }

    public AsynchronousServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void setServerSocketChannel(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    //线程池
    private ExecutorService service;
    //服务器端通道
    private AsynchronousServerSocketChannel serverSocketChannel;

    public AIOServer() {
        init(9999);
    }

    private void init(int port){
        try {
            System.out.println("server is starting :"+port);

            service = Executors.newFixedThreadPool(4);
            serverSocketChannel = AsynchronousServerSocketChannel.open();

            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println( "server started ");
            serverSocketChannel.accept(this,new AIOServerHandler());
            try{
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            }catch (Exception e){

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AIOServer();
    }
}
