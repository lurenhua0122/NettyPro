package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel3 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/mike/IdeaProjects/NettyPro/1.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/mike/IdeaProjects/NettyPro/2.txt");
        FileChannel channel1 = fileOutputStream.getChannel();
        ByteBuffer byteBuffer =  ByteBuffer.allocate(512);
        while(true){
            byteBuffer.clear();
            int read = channel.read(byteBuffer);
            if (read ==-1){
                break;
            }
            byteBuffer.flip();
            channel1.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();

    }
}
