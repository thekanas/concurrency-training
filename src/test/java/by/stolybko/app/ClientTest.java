package by.stolybko.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClientTest {

    @Mock
    private Server server;

    @Mock
    private Future<DataTransfer> future;

    @InjectMocks
    private Client client;


    @Test
    void sendDataTest() throws ExecutionException, InterruptedException {
        // given
        int count = 3;
        DataTransfer dataTransfer = new DataTransfer(3);
        when(server.process(any())).thenReturn(future);
        when(future.get()).thenReturn(dataTransfer);

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(10);
        }

        // when
        client.sendData(data);

        // then
        assertThat(data).isEmpty();
        assertThat(client.getAccumulator()).isEqualTo(count * dataTransfer.value());
    }
}