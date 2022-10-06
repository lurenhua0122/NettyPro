package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering : 写入到buffer时，可以采用buffer数组，依次写入到【】
 * Gathering : 读取buffer时，可以采用buffer数组，依次读取
 */
public class ScatteringAndGathering {
    public static void main(String[] args) throws IOException {
//        ServerSocketChannel 和 SocketChannel
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        open.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffer = new ByteBuffer[2];
        byteBuffer[0] = ByteBuffer.allocate(5);
        byteBuffer[1] = ByteBuffer.allocate(3);

        SocketChannel accept = open.accept();

        int msgLen = 8;
        while (true) {
            int byteread = 0;
            while (byteread < msgLen) {
                long l = accept.read(byteBuffer);
                byteread += l;
                System.out.println("byte read = " + byteread);
                Arrays.asList(byteBuffer).stream().map(byteBuffer1 -> "position:" + byteBuffer1.position()
                        + "limit:" + byteBuffer1.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffer).forEach(byteBuffer1 -> byteBuffer1.flip());

            long byteWrite = 0;
            while(byteWrite < msgLen){
                long l = accept.write(byteBuffer);
                byteWrite += l;
            }

            Arrays.asList(byteBuffer).forEach(byteBuffer1 -> {

                byteBuffer1.clear();
            });

            System.out.println("byteread="+ byteread + " byte write:"+byteWrite + " msgLen: "+msgLen);
        }

    }
}
