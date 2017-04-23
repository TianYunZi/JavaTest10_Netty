package chaptersecond.aio;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by XJX on 2017/4/23.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 9999;
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
        TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 10000);
        Future<Integer> execute = pool.execute(timeServerHandler);
        try {
            System.out.println(execute.get() == null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}
