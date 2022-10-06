package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NioFileChannel2 {
    public static void main(String[] args) throws IOException {
//        String str = "Helooworld";

//        创建一个输出流
        File file = new File("/Users/mike/IdeaProjects/NettyPro/file.txt");
       FileInputStream fos =  new FileInputStream(file);

       FileChannel fc = fos.getChannel();

//        创建一个缓冲区
        ByteBuffer bb = ByteBuffer.allocate((int)file.length());
//        缓冲区中PUT数据进入
//        bb.put(str.getBytes(StandardCharsets.UTF_8));
//        反转

//        bb.flip();
//        写入到通道
        fc.read(bb);
//        关闭所有资料，不需要一个个关闭
        bb.flip();

        System.out.println(new String(bb.array()));
        fos.close();

    }
}
