package chaptersecond.aio;

/**
 * Created by XJX on 2017/4/23.
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", 9999)).start();
    }
}
