package by.stolybko.app;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


class ServerTest {

    private final Server server = new Server();


    @Test
    void process() throws ExecutionException, InterruptedException {
        // given
        int count = 10;
        DataTransfer dataTransfer = new DataTransfer(3);

        // when
        for (int i = 0; i < count; i++) {
            server.process(dataTransfer).get();
        }

        // then
        assertThat(server.getRequestValues()).isNotEmpty();
        assertThat(server.getRequestValues().size()).isEqualTo(count);

    }
}