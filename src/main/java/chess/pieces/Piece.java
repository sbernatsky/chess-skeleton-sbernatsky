package chess.pieces;

import java.util.Collection;
import java.util.Collections;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * A base class for chess pieces
 */
public abstract class Piece {
    private final Player owner;

    protected Piece(Player owner) {
        this.owner = owner;
    }

    public char getIdentifier() {
        char id = getIdentifyingCharacter();
        if (Player.White == owner) {
            return Character.toLowerCase(id);
        } else {
            return Character.toUpperCase(id);
        }
    }

    public Player getOwner() {
        return owner;
    }

    protected abstract char getIdentifyingCharacter();

    /** Returns positions under attack by this piece on specified position. */
    public Collection<Position> getPositionsUnderAtack(GameState state, Position position) {
        // in most cases they are the same as possible move positions (except for pawn)
        return getMoves(state, position);
    }

    // FIXME: must be abstract method, implementations are piece-specific
    public Collection<Position> getMoves(GameState state, Position position) {
        return Collections.emptySet();
    }

}
