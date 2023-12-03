package by.stolybko.app;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class Client implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private final List<Integer> data;
    private final Server server;
    private int accumulator;


    @Override
    public void run() {
        Random random = new Random();

        while (!data.isEmpty())  {
            int value = data.remove(random.nextInt(data.size()));
            DataTransfer dataTransfer = new DataTransfer(value);
            LOGGER.info("Client отправил запрос: {}", value);
            Integer integer = null;
            try {
                integer = server.process(dataTransfer).get().value();
                LOGGER.info("Client получил ответ: {}", integer);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            accumulator += integer;
        }
    }

    public int getAccumulator() {
        return accumulator;
    }
}
