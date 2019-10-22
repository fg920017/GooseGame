package it.game.model;

import java.util.function.Function;

public abstract class Box {

    private String name;
    private int index;

    public Box(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public abstract Function<Integer, Integer> getRole();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
