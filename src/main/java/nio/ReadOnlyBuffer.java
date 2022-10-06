package nio;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer bu = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            bu.put((byte)i);
        }

        bu.flip();

        ByteBuffer readOnlyBuffer = bu.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        while(readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        readOnlyBuffer.put((byte) 100);   //.ReadOnlyBufferException
    }
}
