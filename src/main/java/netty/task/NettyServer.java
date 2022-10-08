package netty.task;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup 和WorkGroup
        //创建两个线程组，分别是BossGroup和WorkGroup
//        BossGroup只处理连接请求
//        WorkGroup处理业务
//        两个都是无限循环
        NioEventLoopGroup bossgroup = new NioEventLoopGroup();
        NioEventLoopGroup workgroup = new NioEventLoopGroup();
        try {
            //        创建服务端启动对接，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程
            serverBootstrap.group(bossgroup, workgroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道 对象
                        //向通道pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //可以使用一个集合管理SocketChannel ，再推送消息时，可以将业务加入到各个Channel，对应用的NIOEventLoop 的TaskQuenue 或ScheduleTaskQuenue
                            System.out.println("客户端的HashCode："+socketChannel.hashCode());
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的WorkGroup的EventLoop对应的管道设置处理器
            System.out.println("服务器 启动成功......");
            //绑定一个端口并同步，生成一个ChannelFuture对象
            //启动服务器
            ChannelFuture sync = serverBootstrap.bind(6668).sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("监听端口：6668成功");
                    }else {
                        System.out.println("监听端口：6668失败");
                    }
                }
            });
            //关闭通道进行监听
            sync.channel().closeFuture().sync();
        } finally {
            bossgroup.shutdownGracefully();
            workgroup.shutdownGracefully();
        }

    }
}
