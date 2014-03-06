package chess.pieces;

import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

class Util {
    static boolean collectPosition(GameState state, Player owner, List<Position> positions, Position p) {
        Player positionOwner = state.getOwnerAt(p);
        if (positionOwner != owner) {
            positions.add(p);
        }

        return positionOwner != null;
    }
}
