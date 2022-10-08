package netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestHttpInitialize extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个netty 提供一个http server codec
        //netty 提供的一个http编码和解码器
        pipeline.addLast("MYHttpServerCodec",new HttpServerCodec());
        // 增加一个自定义的处理器
        pipeline.addLast("MyHttp",new TestHttpServerHandler());
    }
}
