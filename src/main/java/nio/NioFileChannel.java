package nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NioFileChannel {
    public static void main(String[] args) throws IOException {
        String str = "Helooworld";

//        创建一个输出流
       FileOutputStream fos =  new FileOutputStream("/Users/mike/IdeaProjects/NettyPro/file.txt");

       FileChannel fc = fos.getChannel();

//        创建一个缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);
//        缓冲区中PUT数据进入
        bb.put(str.getBytes(StandardCharsets.UTF_8));
//        反转
        bb.flip();
//        写入到通道
        fc.write(bb);
//        关闭所有资料，不需要一个个关闭
        fos.close();

    }
}
