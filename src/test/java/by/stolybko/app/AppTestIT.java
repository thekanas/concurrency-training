package by.stolybko.app;

import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTestIT {

    private final Server server = new Server();
    private final Client client = new Client(server);


    @RepeatedTest(3)
    void sendDataTest() {
        // given
        List<Integer> dataList = new ArrayList<>();
        int count = 100;
        int acc = 0;
        for (int i = 1; i <= count; i++) {
            dataList.add(i);
            acc += i;
        }

        // when
        client.sendData(dataList);
        server.shutdown();

        // then
        assertThat(dataList).isEmpty();
        assertThat(server.getRequestValues().size()).isEqualTo(count);
        assertThat(client.getAccumulator()).isEqualTo(acc);

    }
}
