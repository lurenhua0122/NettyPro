package netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    //是个全局的事件执行器
    //Channel 组
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();

        channels.forEach(ch -> {
            if (ch != channel){
                ch.writeAndFlush("[客户]"+ channel.remoteAddress()+"发送了消息："+s+"\n");
            }else{
                ch.writeAndFlush("[自己]发送了消息："+s+"\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /*
        将客户端加入的消息推送给所有在线的客户端
        该方法会将 channels中所有的channel遍历一次，并发送信息
        不需要自己操作
         */
        channels.writeAndFlush("[客户端]"+ channel.remoteAddress()+" 加入聊天\n");
        channels.add(channel);
    }

    /**
     * Channel 处于活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("上线：    "+ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("离线： "+ ctx.channel().remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush(channel.remoteAddress()+"离开了");
        //channels.remove(channel);
        System.out.println("Channel Group Size:"+channels.size());
    }
}
