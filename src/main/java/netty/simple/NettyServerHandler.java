package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * 自己定义一个Handler 需要继承 Netty 规定好的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据实际处理的方面
     *
     * @param ctx 上下文对象 ，包含pipeline 和 channel  ，地址
     * @param msg 客户端发送的数据，默认是Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx:"+ctx);
        //将MSG转成ByteBuf
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress().toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().closeFuture();
    }

    /**
     * 数据读取完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //数据写入到缓冲区，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("妙",CharsetUtil.UTF_8));
    }
}
