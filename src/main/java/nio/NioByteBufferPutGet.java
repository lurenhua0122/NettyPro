package nio;

import java.nio.ByteBuffer;

public class NioByteBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        allocate.putInt(100);
        allocate.putLong(9l);
        allocate.putChar('c');
        allocate.putShort((short)8);

        allocate.flip();

        System.out.println();
        System.out.println(allocate.getInt());
        System.out.println(allocate.getLong());
        System.out.println(allocate.getChar());
        System.out.println(allocate.getShort());
    }
}
