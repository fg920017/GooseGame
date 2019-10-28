package it.game.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private List<Box> listBox;

    public Board(int max) {
        listBox = IntStream.rangeClosed(0, max).mapToObj(i -> {
            switch (i) {
                case 0:
                    return new DefaultBox("Start", 0);
                case 6:
                    return new BridgeBox("The Bridge", i);
                case 5:
                case 9:
                case 14:
                case 18:
                case 23:
                case 27:
                    return new GooseBox(i + ", The Goose", i);
                default:
                    return new DefaultBox(Integer.toString(i), i);
            }
        }).collect(Collectors.toList());
        //.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    public Box getBox(Integer i) {
        return listBox.get(i);
    }
}
