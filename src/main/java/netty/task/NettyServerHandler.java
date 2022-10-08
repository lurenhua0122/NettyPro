package netty.task;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

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
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送的消息："+buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址："+ctx.channel().remoteAddress().toString());

        //比如这里有一个耗时操作 -》 异步执行 -》 提交 到对应Channel的 NIOEventLoop的taskQuenue中
        //解决方案一：用户自定义普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("妙1",CharsetUtil.UTF_8));
            }
        });
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("妙3",CharsetUtil.UTF_8));
            }
        });

        //用户自定义定时任务 -》 提交到ScheduleTaskQuenue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("妙4",CharsetUtil.UTF_8));
            }
        },5, TimeUnit.SECONDS);
        System.out.println("go on...");
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
        ctx.writeAndFlush(Unpooled.copiedBuffer("妙2",CharsetUtil.UTF_8));
    }
}
