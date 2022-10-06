package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapedByteBuffer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/mike/IdeaProjects/NettyPro/1.txt","rw");
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 0: 可修改的起始位置
         * 5： 可修改的数量
         * 可以直接修改的0-5个
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(3,(byte) 'b');
        map.put(2,(byte) 'A');
        randomAccessFile.close();
        System.out.println("修改成功");


    }
}
