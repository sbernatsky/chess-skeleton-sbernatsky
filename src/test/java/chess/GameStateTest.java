package chess;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import chess.GameState.IllegalMoveException;
import chess.GameState.NoPieceFoundException;
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
        
        // put pieces into game state
        placePiece(state, whitePiece, whitePosition);
        placePiece(state, blackPiece, blackPosition);

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

    @Test
    public void testPositionsUnderAttack() throws Exception {
        Piece whitePiece = mock(Piece.class);
        Piece blackPiece = mock(Piece.class);
        Position whitePosition = new Position("a1");
        Position blackPosition = new Position("d8");
        
        // put pieces into game state
        placePiece(state, whitePiece, whitePosition);
        placePiece(state, blackPiece, blackPosition);

        when(whitePiece.getOwner()).thenReturn(Player.White);
        when(blackPiece.getOwner()).thenReturn(Player.Black);
        when(whitePiece.getPositionsUnderAtack(state, whitePosition)).thenReturn(Collections.singleton(new Position("h8")));
        when(blackPiece.getPositionsUnderAtack(state, blackPosition)).thenReturn(Collections.singleton(new Position("h1")));

        Collection<Position> moves = state.getPositionsUnderAttack(Player.Black);
        assertNotNull(moves);
        assertEquals(1, moves.size());
        assertEquals(new Position("h1"), moves.toArray()[0]);

        moves = state.getPositionsUnderAttack(Player.White);
        assertNotNull(moves);
        assertEquals(1, moves.size());
        assertEquals(new Position("h8"), moves.toArray()[0]);
    }

    //@Test(expected = GameState.NoPieceFoundException.class)
    @Test
    public void testMoveInvalidFrom1() throws Exception {
        Position from = new Position("a1");

        try {
            state.move(from, null);
            fail("should throw NoPieceFoundException");
        } catch (NoPieceFoundException e) {
            assertNotNull(e.getMessage());
        }

        assertEquals(Player.White, state.getCurrentPlayer());
    }

    @Test
    public void testMoveInvalidFrom2() throws Exception {
        Position position = new Position("a1");
        Piece piece = mock(Piece.class);
        
        // put pieces into game state
        placePiece(state, piece, position);

        when(piece.getOwner()).thenReturn(Player.Black);

        try {
            state.move(position, null);
            fail("should throw NoPieceFoundException");
        } catch (NoPieceFoundException e) {
            assertNotNull(e.getMessage());
        }

        assertEquals(Player.White, state.getCurrentPlayer());
        assertEquals(state.getPieceAt(position), piece);
    }

    @Test
    public void testMoveInvalidTo() throws Exception {
        Position from = new Position("a1");
        Piece piece = mock(Piece.class);
        
        // put pieces into game state
        placePiece(state, piece, from);

        when(piece.getOwner()).thenReturn(Player.White);
        when(piece.getMoves(state, from)).thenReturn(Collections.singleton(new Position("d8")));

        try {
            state.move(from, new Position("a2"));
            fail("should throw IllegalMoveException");
        } catch (IllegalMoveException e) {
            assertNotNull(e.getMessage());
        }

        assertEquals(Player.White, state.getCurrentPlayer());
        assertEquals(state.getPieceAt(from), piece);
    }

    @Test
    public void testMove1() throws Exception {
        Position from = new Position("a1");
        Position to = new Position("d8");
        Piece piece = mock(Piece.class);
        
        // put pieces into game state
        placePiece(state, piece, from);

        when(piece.getOwner()).thenReturn(Player.White);
        when(piece.getMoves(state, from)).thenReturn(Collections.singleton(to));

        state.move(from, to);

        assertEquals(Player.Black, state.getCurrentPlayer());
        assertNull(state.getPieceAt(from));
        assertEquals(state.getPieceAt(to), piece);
    }

    @Test
    public void testMove2() throws Exception {
        Position from = new Position("a1");
        Position to = new Position("d8");
        Piece piece = mock(Piece.class);
        Piece another = mock(Piece.class);
        
        // put pieces into game state
        placePiece(state, piece, from);
        placePiece(state, another, to);

        when(piece.getOwner()).thenReturn(Player.White);
        when(piece.getMoves(state, from)).thenReturn(Collections.singleton(to));

        state.move(from, to);

        assertEquals(Player.Black, state.getCurrentPlayer());
        assertNull(state.getPieceAt(from));
        assertEquals(state.getPieceAt(to), piece);
    }

    /* put piece into game state via reflection */
    private static void placePiece(GameState state, Piece piece, Position position) throws Exception {
        Method placePieceMethod = GameState.class.getDeclaredMethod("placePiece", Piece.class, Position.class);
        placePieceMethod.setAccessible(true);
        placePieceMethod.invoke(state, piece, position);
    }
}
