package by.stolybko.app;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Client {

    private final List<Integer> data;
    private int accumulator;

}
