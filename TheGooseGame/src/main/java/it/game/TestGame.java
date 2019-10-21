package it.game;

import it.game.model.Player;
import it.game.services.GameService;
import it.game.services.impl.GameServiceImpl;

public class TestGame {

    public static void main(String args[]) {
        GameService game = new GameServiceImpl();
        if (args.length != 0) {
            for(String arg:args){
                game.addPlayer(new Player(arg));
            }
        } else {
            game.addPlayer(new Player("Marco"));
            game.addPlayer(new Player("Francesco"));
            game.addPlayer(new Player("Antonio"));
            game.addPlayer(new Player("Marco"));
            game.addPlayer(new Player("Giuseppe"));
        }
        game.extractionPlayers();
        while (!game.isEnded()) {
            game.moveNextPlayer();
        }
    }


}
