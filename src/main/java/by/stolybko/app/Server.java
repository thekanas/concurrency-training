package by.stolybko.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;


public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final Random random = new Random();
    private final List<Integer> requestValues = new ArrayList<>();


    public Future<DataTransfer> process(DataTransfer dataTransfer) {
        return executor.submit(() -> {
            try {
                Thread.sleep(random.nextInt(100) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOCK.lock();
            try {
                requestValues.add(dataTransfer.value());
                LOGGER.info("Server сохранил входящие данные: {}", dataTransfer.value());
                return new DataTransfer(requestValues.size());
            } finally {
                LOCK.unlock();
            }
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public List<Integer> getRequestValues() {
        return requestValues;
    }
}
