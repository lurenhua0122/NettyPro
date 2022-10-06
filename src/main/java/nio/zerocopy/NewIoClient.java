package nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIoClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1",7001));
        String filename = "/Users/mike/IdeaProjects/NettyPro/Jietu20210928-194940-HD.mp4";
        FileChannel channel = new FileInputStream(filename).getChannel();

        long st = System.currentTimeMillis();
        //Linux 一次调用完成文件传输
        // Windows 一次只能发送8M文件，超过要分段传输文件
        // 而且要记住传输时的位置
        // transferTo 底层采用zeroCopy
        long count = channel.transferTo(0,channel.size(),sc);
        long et = System.currentTimeMillis();
        System.out.println("字节数："+count + "\n"+ (et-st));

    }
}
