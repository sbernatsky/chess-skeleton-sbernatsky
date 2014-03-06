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

    // FIXME: must be abstract method, implementations are piece-specific
    public Collection<Position> getMoves(GameState state, Position position) {
        return Collections.emptySet();
    }

}
