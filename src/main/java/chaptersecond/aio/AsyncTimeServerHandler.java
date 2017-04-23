package chaptersecond.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by XJX on 2017/4/23.
 */
public class AsyncTimeServerHandler implements Callable<Integer> {

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel serverSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("服务启动的端口：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer call() throws Exception {
        latch = new CountDownLatch(1);
        doAccept();
        latch.await();
        return null;
    }

    private void doAccept() {
        serverSocketChannel.accept(this, new AcceptCompletionHandler());
    }
}
