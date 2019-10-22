package it.game.model;

import java.util.function.Function;

public class GooseBox extends Box {
    public GooseBox(String name, int index) {
        super(name, index);
    }

    @Override
    public Function<Integer, Integer> getRole() {
        return (i) -> getIndex() + i;
    }
}
