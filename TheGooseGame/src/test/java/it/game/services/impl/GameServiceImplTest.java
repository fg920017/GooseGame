package it.game.services.impl;

import it.game.exception.PlayerAlreadyExistsException;
import it.game.exception.PlayerNotFoundException;
import it.game.model.Board;
import it.game.model.Player;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GameServiceImplTest {
    public GameServiceImplTest() {
    }

    private GameServiceImpl game;
    private Board board;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void conf() {
        board = new Board(63);
        game = new GameServiceImpl(board);
    }

    @Test
    public void testAddPlayer() throws Exception {
        String name = "Marco";
        game.addPlayer(name);
        assertThat(game.getAllPlayers(), CoreMatchers.containsString(name));
    }

    @Test(expected = PlayerAlreadyExistsException.class)
    public void testAddAlreadyExistingPlayer() throws PlayerAlreadyExistsException {
        String name = "Marco";
        game.addPlayer(name);
        game.addPlayer(name);
    }

    @Test(expected = PlayerNotFoundException.class )
    public void testMoveNotExistingPlayer() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name = "Marco";
        game.addPlayer(name);
        game.movePlayer("Gianluca", 2, 5);
    }

    @Test
    public void testDefaultMovePlayer() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name = "Marco";
        game.addPlayer(name);

        game.movePlayer(name, 2, 5);
        Player player = game.getPlayers().stream().filter(p -> p.getName() == name).findFirst().orElse(null);
        assertEquals(player.getStep(), 7 );

        game.movePlayer(name, 1, 2);
        player = game.getPlayers().stream().filter(p -> p.getName() == name).findFirst().orElse(null);
        assertEquals(player.getStep(), 10 );
    }

    @Test
    public void testGooseMovePlayer() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name = "Marco";
        game.addPlayer(name);
        game.movePlayer(name, 4, 5); // The Goose 9 -> 18 -> 27 -> 36
        Player player = game.getPlayers().stream().filter(p -> p.getName() == name).findFirst().orElse(null);
        assertEquals(player.getStep(), 36 );
    }

    @Test
    public void testBridgeMovePlayer() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name = "Marco";
        game.addPlayer(name);

        game.movePlayer(name, 2, 4);
        Player player = game.getPlayers().stream().filter(p -> p.getName() == name).findFirst().orElse(null);
        assertEquals(player.getStep(), 12 );
    }

    @Test
    public void testBounces() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name1 = "Carlo";
        game.addPlayer(name1);
        game.movePlayer(name1, 2, 5);
        game.movePlayer(name1, 4, 4);
        game.movePlayer(name1, 1, 6);
        game.movePlayer(name1, 6, 6);
        game.movePlayer(name1, 5, 6);
        game.movePlayer(name1, 6, 2);
        game.movePlayer(name1, 6, 6);
        Player player = game.getPlayers().stream().filter(p -> p.getName() == name1).findFirst().orElse(null);
        assertEquals(player.getStep(), 61 );
    }

    @Test
    public void testMovePlayerSomeBoxAnotherPlayer() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name1 = "Carlo";
        String name2 = "Franco";
        game.addPlayer(name1);
        game.addPlayer(name2);
        game.movePlayer(name1, 2, 5);
        game.movePlayer(name2, 3, 3); // --> jump to 12
        game.movePlayer(name1, 1, 4);

        Player player1 = game.getPlayers().stream().filter(p -> p.getName() == name1).findFirst().orElse(null);
        Player player2 = game.getPlayers().stream().filter(p -> p.getName() == name2).findFirst().orElse(null);
        assertEquals(player1.getStep(), 12 );
        assertEquals(player2.getStep(), 7 );
    }

    @Test
    public void testEndOfGame() throws PlayerAlreadyExistsException, PlayerNotFoundException {
        String name1 = "Carlo";
        game.addPlayer(name1);
        game.movePlayer(name1, 2, 5);
        assertFalse(game.isEnded());
        game.movePlayer(name1, 1, 4);
        assertFalse(game.isEnded());
        game.movePlayer(name1, 6, 6);
        assertFalse(game.isEnded());
        game.movePlayer(name1, 6, 6);
        assertFalse(game.isEnded());
        game.movePlayer(name1, 6, 6);
        assertFalse(game.isEnded());
        game.movePlayer(name1, 6, 6);
        assertFalse(game.isEnded());
        game.movePlayer(name1, 1, 2);
        assertTrue(game.isEnded());

    }







}