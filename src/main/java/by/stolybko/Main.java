package by.stolybko;

import by.stolybko.app.Client;
import by.stolybko.app.Server;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> dataList = new ArrayList<>();
        int n = 100;
        int acc = 0;
        for (int i = 1; i <= n; i++) {
            dataList.add(i);
            acc += i;
        }
        System.out.println(acc);

        Server server = new Server();
        Client client = new Client(dataList, server);

        Thread clientThread = new Thread(client);
        clientThread.start();
        clientThread.join();

        server.shutdown();

        System.out.println(dataList);
        System.out.println(client.getAccumulator());
        System.out.println(server.getRequestValues().size());
    }
}