package chaptersecond.nio;

/**
 * Created by XJX on 2017/4/22.
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 9999;
        new Thread(new TimeClientHandle("127.0.0.1", port)).start();
    }
}
