package it.game.model;

import java.util.function.Function;

public class BridgeBox extends Box {
    public BridgeBox(String name, int index) {
        super(name, index);
    }

    @Override
    public Function<Integer, Integer> getRole() {
        return (i) -> 12;
    }
}
