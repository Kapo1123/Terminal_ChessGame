package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
private TeamColor turn;
private ChessBoard squares;
    private chess.InvalidMoveException InvalidMoveException;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
       return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessMove> valid_moves = new ArrayList<>();
        ChessPiece piece = squares.getPiece(startPosition);
        if(piece ==null){
            return null;
        }
        moves.addAll(piece.pieceMoves(getBoard(),startPosition));
        for(ChessMove move : moves){
            squares.addPiece(startPosition,null);
            squares.addPiece(move.getEndPosition(),piece);
            isInCheck(piece.getTeamColor());
        }

        return moves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
       if(validMoves(move.getStartPosition()) == null){
           throw InvalidMoveException;
       }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
       ArrayList<ChessPosition> pieces_position = squares.get_list(teamColor);
       Collection<ChessMove> moves;
       for (ChessPosition position : pieces_position){
           ChessPiece piece = squares.getPiece(position);
           moves = piece.pieceMoves(squares,position);

       }

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.squares = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
       return squares;
    }
}
