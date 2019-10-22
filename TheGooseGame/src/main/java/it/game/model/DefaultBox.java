package it.game.model;

import java.util.function.Function;

public class DefaultBox extends Box {
    public DefaultBox(String start, int i) {
        super(start, i);
    }

    @Override
    public Function<Integer, Integer> getRole() {
        return (i) -> getIndex();
    }
}
