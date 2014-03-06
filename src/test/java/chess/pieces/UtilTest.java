package chess.pieces;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import chess.GameState;
import chess.Player;
import chess.Position;

public class UtilTest {
    private GameState state;
    private Player player;

    @Before
    public void setUp() {
        state = new GameState();
        player = Player.Black;
    }

    @Test
    public void testCollectMovesNorth() {
        Collection<Position> moves = Util.collectMovesNorth(state, player, new Position("a1"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesNorth(state, player, new Position("a1"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("a2"), moves.toArray()[0]);

        moves = Util.collectMovesNorth(state, player, new Position("a8"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesNorth(state, player, new Position("a8"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesSouth() {
        Collection<Position> moves = Util.collectMovesSouth(state, player, new Position("a8"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesSouth(state, player, new Position("a8"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("a7"), moves.toArray()[0]);

        moves = Util.collectMovesSouth(state, player, new Position("a1"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesSouth(state, player, new Position("a1"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesEast() {
        Collection<Position> moves = Util.collectMovesEast(state, player, new Position("a1"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesEast(state, player, new Position("a1"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("b1"), moves.toArray()[0]);

        moves = Util.collectMovesEast(state, player, new Position("h1"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesEast(state, player, new Position("h1"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesWest() {
        Collection<Position> moves = Util.collectMovesWest(state, player, new Position("h1"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesWest(state, player, new Position("h1"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("g1"), moves.toArray()[0]);

        moves = Util.collectMovesWest(state, player, new Position("a1"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesWest(state, player, new Position("a1"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesNorthEast() {
        Collection<Position> moves = Util.collectMovesNorthEast(state, player, new Position("a1"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesNorthEast(state, player, new Position("a1"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("b2"), moves.toArray()[0]);

        moves = Util.collectMovesNorthEast(state, player, new Position("h8"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesNorthEast(state, player, new Position("h8"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesSouthWest() {
        Collection<Position> moves = Util.collectMovesSouthWest(state, player, new Position("h8"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesSouthWest(state, player, new Position("h8"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("g7"), moves.toArray()[0]);

        moves = Util.collectMovesSouthWest(state, player, new Position("a1"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesSouthWest(state, player, new Position("a1"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesNorthWest() {
        Collection<Position> moves = Util.collectMovesNorthWest(state, player, new Position("h1"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesNorthWest(state, player, new Position("h1"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("g2"), moves.toArray()[0]);

        moves = Util.collectMovesNorthWest(state, player, new Position("a8"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesNorthWest(state, player, new Position("a8"), 1);
        assertEquals(0, moves.size());
    }

    @Test
    public void testCollectMovesSouthEast() {
        Collection<Position> moves = Util.collectMovesSouthEast(state, player, new Position("a8"), 128);
        assertEquals(7, moves.size());

        moves = Util.collectMovesSouthEast(state, player, new Position("a8"), 1);
        assertEquals(1, moves.size());
        assertEquals(new Position("b7"), moves.toArray()[0]);

        moves = Util.collectMovesSouthEast(state, player, new Position("h1"), 128);
        assertEquals(0, moves.size());

        moves = Util.collectMovesSouthEast(state, player, new Position("h1"), 1);
        assertEquals(0, moves.size());
    }
}
