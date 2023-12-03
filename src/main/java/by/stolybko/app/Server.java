package by.stolybko.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {

    private final List<Integer> requestValues = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public Future<DataTransfer> process(DataTransfer dataTransfer) {
        return executor.submit(() -> {
            requestValues.add(dataTransfer.value());
            return new DataTransfer(requestValues.size());
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public List<Integer> getRequestValues() {
        return requestValues;
    }
}
