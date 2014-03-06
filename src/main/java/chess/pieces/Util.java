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
        int movesNorth = Math.min(Position.MAX_ROW - position.getRow(), maxMoves);
        for (int i = 1; i <= movesNorth; i++) {
            Position p = new Position(position.getColumn(), position.getRow() + i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesSouth(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesSouth = Math.min(position.getRow() - Position.MIN_ROW, maxMoves);
        for (int i = 1; i <= movesSouth; i++) {
            Position p = new Position(position.getColumn(), position.getRow() - i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesEast(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesEast = Math.min(Position.MAX_COLUMN - position.getColumn(), maxMoves);
        for (int i = 1; i <= movesEast; i++) {
            Position p = new Position((char) (position.getColumn() + i), position.getRow());
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesWest(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesWest = Math.min(position.getColumn() - Position.MIN_COLUMN, maxMoves);
        for (int i = 1; i <= movesWest; i++) {
            Position p = new Position((char) (position.getColumn() - i), position.getRow());
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesNorthEast(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesNorth = Math.min(Position.MAX_ROW - position.getRow(), maxMoves);
        int movesEast = Math.min(Position.MAX_COLUMN - position.getColumn(), maxMoves);
        for (int i = 1; i <= Math.min(movesNorth, movesEast); i++) {
            Position p = new Position((char) (position.getColumn() + i), position.getRow() + i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesNorthWest(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesNorth = Math.min(Position.MAX_ROW - position.getRow(), maxMoves);
        int movesWest = Math.min(position.getColumn() - Position.MIN_COLUMN, maxMoves);
        for (int i = 1; i <= Math.min(movesNorth, movesWest); i++) {
            Position p = new Position((char) (position.getColumn() - i), position.getRow() + i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesSouthEast(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesSouth = Math.min(position.getRow() - Position.MIN_ROW, maxMoves);
        int movesEast = Math.min(Position.MAX_COLUMN - position.getColumn(), maxMoves);
        for (int i = 1; i <= Math.min(movesSouth, movesEast); i++) {
            Position p = new Position((char) (position.getColumn() + i), position.getRow() - i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    static Collection<Position> collectMovesSouthWest(GameState state, Player owner, Position position, int maxMoves) {
        List<Position> positions = new ArrayList<Position>();
        int movesSouth = Math.min(position.getRow() - Position.MIN_ROW, maxMoves);
        int movesWest = Math.min(position.getColumn() - Position.MIN_COLUMN, maxMoves);
        for (int i = 1; i <= Math.min(movesSouth, movesWest); i++) {
            Position p = new Position((char) (position.getColumn() - i), position.getRow() - i);
            boolean shouldBreak = Util.collectPosition(state, owner, positions, p);
            if (shouldBreak) {
                break;
            }
        }
        return positions;
    }

    private static boolean collectPosition(GameState state, Player owner, List<Position> positions, Position p) {
        Player positionOwner = state.getOwnerAt(p);
        if (positionOwner != owner) {
            positions.add(p);
        }

        return positionOwner != null;
    }
}
