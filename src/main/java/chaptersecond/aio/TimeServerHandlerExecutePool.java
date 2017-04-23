package chaptersecond.aio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by XJX on 2017/4/20.
 * 构建线程池
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit
                .SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    public Future<Integer> execute(Callable<Integer> task) {
        return executorService.submit(task);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}