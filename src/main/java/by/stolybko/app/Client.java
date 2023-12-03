package by.stolybko.app;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


@RequiredArgsConstructor
public class Client {

    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final List<Integer> data;
    private final Server server;
    private static final AtomicInteger accumulator = new AtomicInteger(0);
    //private int accumulator;


    public void sendData() {
        Random random = new Random();
        ExecutorService executor = Executors.newCachedThreadPool();
        while (!data.isEmpty()) {
            int value = data.remove(random.nextInt(data.size()));
            DataTransfer dataTransfer = new DataTransfer(value);
            executor.submit(() -> {
                LOGGER.info("Client отправил запрос: {}", value);
                int integer;
                try {
                    integer = server.process(dataTransfer).get().value();
                    LOGGER.info("Client получил ответ: {}", integer);
                    accumulator.addAndGet(value);
                    Thread.sleep(random.nextInt(100) + 100);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            // ждем завершения всех потоков
        }
    }

    public int getAccumulator() {
        return accumulator.get();
    }

//    private void addAccumulator(int value) {
//        LOCK.lock();
//        try {
//            accumulator.addAndGet(value);
//        } finally {
//            LOCK.unlock();
//        }
//    }
}
