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


@RequiredArgsConstructor
public class Client {

    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private final Server server;
    private final AtomicInteger accumulator = new AtomicInteger(0);


    public void sendData(List<Integer> data) {
        Random random = new Random();
        ExecutorService executor = Executors.newCachedThreadPool();
        while (!data.isEmpty()) {
            int value = data.remove(random.nextInt(data.size()));
            DataTransfer dataTransfer = new DataTransfer(value);
            executor.submit(() -> {
                LOGGER.info("Client отправил запрос: {}", value);
                int integer;
                try {
                    Thread.sleep(random.nextInt(100) + 100);
                    integer = server.process(dataTransfer).get().value();
                    LOGGER.info("Client получил ответ: {}", integer);
                    accumulator.addAndGet(integer);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    public int getAccumulator() {
        return accumulator.get();
    }
}
