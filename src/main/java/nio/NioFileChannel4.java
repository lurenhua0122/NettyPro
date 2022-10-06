package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NioFileChannel4 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("panzi.jpeg");
        FileOutputStream fileOutputStream = new FileOutputStream("pz.jpeg");

        FileChannel channel = fileOutputStream.getChannel();
        FileChannel channel1 = fileInputStream.getChannel();

        channel.transferFrom(channel1,0,channel1.size());

        channel1.close();
        channel.close();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
