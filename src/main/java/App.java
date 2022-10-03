import java.nio.Buffer;
import java.nio.IntBuffer;

public class App {
    public static void main(String[] args) {
        System.err.println("hello");
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //intBuffer.put(1);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        intBuffer.flip();
//        for (int c :
//                intBuffer.array()) {
//            System.out.println("int :  "+c);
//        }
        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
