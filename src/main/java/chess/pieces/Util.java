package chess.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

class Util {
    static Collection<Position> collectMovesNorth(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        for (int i = position.getRow() + 1; i <= Math.min(Position.MAX_ROW, position.getRow() + maxMoves); i++) {
            Position p = new Position(position.getColumn(), i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if(shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesSouth(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        for (int i = position.getRow() - 1; i >= Math.max(Position.MIN_ROW, position.getRow() - maxMoves); i--) {
            Position p = new Position(position.getColumn(), i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if(shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesEast(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        for (char c = (char) (position.getColumn() + 1); c <= Math.min(Position.MAX_COLUMN, position.getColumn() + maxMoves); c++) {
            Position p = new Position(c, position.getRow());
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if(shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesWest(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        for (char c = (char) (position.getColumn() - 1); c >= Math.max(Position.MIN_COLUMN, position.getColumn() - maxMoves); c--) {
            Position p = new Position(c, position.getRow());
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static boolean collectPosition(GameState state, Player owner, List<Position> positions, Position p) {
        Player positionOwner = state.getOwnerAt(p);
        if (positionOwner != owner) {
            positions.add(p);
        }

        return positionOwner != null;
    }
}
