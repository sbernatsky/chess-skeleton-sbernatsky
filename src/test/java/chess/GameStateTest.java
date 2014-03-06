package chess;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

/**
 * Basic unit tests for the GameState class
 */
public class GameStateTest {

    private GameState state;

    @Before
    public void setUp() {
        state = new GameState();
    }

    @Test
    public void testStartsEmpty() {
        // Make sure all the positions are empty
        for (char col = Position.MIN_COLUMN; col <= Position.MAX_COLUMN; col++) {
            for (int row = Position.MIN_ROW; row <= Position.MAX_ROW; row++) {
                assertNull("All pieces should be empty", state.getPieceAt(String.valueOf(col) + row));
            }
        }
    }

    @Test
    public void testInitialGame() {
        // Start the game
        state.reset();

        // White should be the first player
        Player current = state.getCurrentPlayer();
        assertEquals("The initial player should be White", Player.White, current);

        // Spot check a few pieces
        Piece whiteRook = state.getPieceAt("a1");
        assertTrue("A rook should be at a1", whiteRook instanceof Rook);
        assertEquals("The rook at a1 should be owned by White", Player.White, whiteRook.getOwner());


        Piece blackQueen = state.getPieceAt("d8");
        assertTrue("A queen should be at d8", blackQueen instanceof Queen);
        assertEquals("The queen at d8 should be owned by Black", Player.Black, blackQueen.getOwner());
    }

    @Test
    public void testMoves() throws Exception {
        Piece whitePiece = mock(Piece.class);
        Piece blackPiece = mock(Piece.class);
        Position whitePosition = new Position("a1");
        Position blackPosition = new Position("d8");
        
        // put pieces into game state via reflection
        Method placePieceMethod = GameState.class.getDeclaredMethod("placePiece", Piece.class, Position.class);
        placePieceMethod.setAccessible(true);
        placePieceMethod.invoke(state, whitePiece, whitePosition);
        placePieceMethod.invoke(state, blackPiece, blackPosition);

        when(whitePiece.getOwner()).thenReturn(Player.White);
        when(blackPiece.getOwner()).thenReturn(Player.Black);
        when(whitePiece.getMoves(state, whitePosition)).thenReturn(Collections.singleton(new Position("h8")));
        when(blackPiece.getMoves(state, blackPosition)).thenReturn(Collections.singleton(new Position("h1")));

        // should be one move for white player
        Collection<Move> moves = state.getMoves();
        assertNotNull(moves);
        assertEquals(1, moves.size());
        Move move = moves.toArray(new Move[1])[0];
        assertEquals(whitePosition, move.getFrom());
        assertEquals(new Position("h8"), move.getTo());
    }
}
