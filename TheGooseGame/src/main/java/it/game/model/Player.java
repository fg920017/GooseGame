package it.game.model;

public class Player {
    private String name;
    private int step;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setName(String name) {
        this.name = name;
    }


}
