package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

/**
 * The Knight class
 */
public class Knight extends Piece {
    public Knight(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'n';
    }

    public Collection<Position> getMoves(GameState state, Position position) {
        List<Position> tmp = new ArrayList<Position>();
        tmp.add(new Position((char) (position.getColumn() + 1), position.getRow() + 2));
        tmp.add(new Position((char) (position.getColumn() + 1), position.getRow() - 2));
        tmp.add(new Position((char) (position.getColumn() - 1), position.getRow() + 2));
        tmp.add(new Position((char) (position.getColumn() - 1), position.getRow() - 2));
        tmp.add(new Position((char) (position.getColumn() + 2), position.getRow() + 1));
        tmp.add(new Position((char) (position.getColumn() + 2), position.getRow() - 1));
        tmp.add(new Position((char) (position.getColumn() - 2), position.getRow() + 1));
        tmp.add(new Position((char) (position.getColumn() - 2), position.getRow() - 1));

        List<Position> result = new ArrayList<Position>();
        for (Position p : tmp) {
            if (p.isValid() && state.getOwnerAt(p) != getOwner()) {
                result.add(p);
            }
        }
        return result;
    }
}
