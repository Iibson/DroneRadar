package pl.edu.agh.DroneRadar.threadPool;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Scope("singleton")
@PropertySource("classpath:threadPool.properties")
public class AppThreadPool {

    private final ExecutorService executor;

    public AppThreadPool(Environment environment) {
        this.executor = initExecutor(environment);
    }
    public void submit(Runnable task) {
        executor.submit(task);
    }
    private ExecutorService initExecutor(Environment environment) {
        var threadNum = Integer.parseInt(environment.getProperty("threadPool.threadNum", "150"));

        return new ThreadPoolExecutor(threadNum,
                threadNum,
                15,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
