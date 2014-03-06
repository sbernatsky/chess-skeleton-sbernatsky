package chess.pieces;

import java.util.Collections;
import java.util.Set;

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
    public Set<Position> getMoves(GameState state, Position position) {
        int newRow = (Player.White == owner) ? position.getRow() + 1 : position.getRow() - 1;

        if (newRow < Position.MIN_ROW || newRow > Position.MAX_ROW) {
            return Collections.emptySet();
        }

        Position newPosition = new Position(position.getColumn(), newRow);
        if (state.getPieceAt(newPosition) != null && state.getPieceAt(newPosition).getOwner() == owner) {
            return Collections.emptySet();
        }

        return Collections.singleton(newPosition);
    }

}
