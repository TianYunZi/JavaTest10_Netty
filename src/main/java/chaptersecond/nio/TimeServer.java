package chaptersecond.nio;

import org.junit.Test;

/**
 * Created by XJX on 2017/4/22.
 */
public class TimeServer {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        final int port = 9999;
        TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        executePool.execute(timeServer);
        executePool.shutdown();
    }
}
