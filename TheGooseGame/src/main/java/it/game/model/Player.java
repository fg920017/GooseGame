package it.game.model;

public class Player {
    private String nome;
    private int step;

    public Player(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
