package netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

public class NettyByteBuf {
    public static void main(String[] args) {
        //创建一个ByteBuf对象
        //1。对象包含一个数组，是一个byte[10]
        //2。在Netty的Buffer中，不需要flip();
        // 底层维护了 readerindex 和 writerindex
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println("capacity="+buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }


        ByteBuf buf = Unpooled.copiedBuffer("hello,world", CharsetUtil.UTF_8);
        if (buf.hasArray()){
            byte[] arr = buf.array();

            System.out.println(new String(arr,CharsetUtil.UTF_8));
        }
        System.out.println(buf.readableBytes());
        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());
        System.out.println(buf.getByte(0));
        for (int i = 0; i < buf.capacity(); i++) {
            System.out.println((char)buf.getByte(i));
        }
        System.out.println(buf.getCharSequence(0,4,CharsetUtil.UTF_8));
    }
}
