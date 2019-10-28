package it.game;

import it.game.cli.CommandExecutor;
import it.game.cli.exception.CommandNotFoundException;
import it.game.cli.exception.GameStoppedException;
import it.game.cli.exception.MalformedCommandException;
import it.game.model.Board;
import it.game.services.GameService;
import it.game.services.impl.GameServiceImpl;

import java.util.Scanner;

public class TestGame {

    public static void main(String args[]) {
        GameService game = new GameServiceImpl(new Board(63));
        CommandExecutor commandExecutor = new CommandExecutor(game);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueGame = true;
            do {
                System.out.print("Type command: ");
                String command = scanner.nextLine();
                try {
                    commandExecutor.executeGameCommand(command);
                    continueGame = !game.isEnded();
                } catch (CommandNotFoundException cnfe) {
                    System.out.println(cnfe.getMessage());
                } catch (GameStoppedException gse) {
                    continueGame = false;
                } catch (MalformedCommandException mce) {
                    System.out.println(mce.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continueGame = false;
                }

                System.out.println();
            } while (continueGame);
        }
    }


}
