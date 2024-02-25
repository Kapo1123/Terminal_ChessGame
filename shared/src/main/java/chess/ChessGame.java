package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
  private TeamColor turn;
  private ChessBoard squares;
  private chess.InvalidMoveException invalidMoveException=new InvalidMoveException();

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
    turn=team;
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
    ArrayList<ChessMove> moves=new ArrayList<>();
    ArrayList<ChessMove> validMoves=new ArrayList<>();
    ChessPiece piece=squares.getPiece(startPosition);
    if (piece == null) {
      return null;
    }
    moves.addAll(piece.pieceMoves(getBoard(), startPosition));
    for (ChessMove move : moves) {
      ChessPiece endpiece=squares.getPiece(move.getEndPosition());
      squares.addPiece(startPosition, null);
      squares.addPiece(move.getEndPosition(), piece);
      if (isInCheck(piece.getTeamColor()) == false) {
        validMoves.add(move);
      }
      squares.addPiece(move.getEndPosition(), endpiece);
      squares.addPiece(startPosition, piece);
    }
    return validMoves;
  }

  /**
   * Makes a move in a chess game
   *
   * @param move chess move to preform
   * @throws InvalidMoveException if move is invalid
   */
  public void makeMove(ChessMove move) throws InvalidMoveException {
    Collection<ChessMove> validMoves=validMoves(move.getStartPosition());
    ChessPiece piece=squares.getPiece(move.getStartPosition());
    if (piece.getTeamColor() != turn) {
      throw invalidMoveException;
    }
    if (validMoves == null) {
      throw invalidMoveException;
    }else if (!validMoves.contains(move)) {
      throw invalidMoveException;
    }else {
      if (move.getPromotionPiece() != null) {
        piece=new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
      }
      squares.addPiece(move.getStartPosition(), null);
      squares.addPiece(move.getEndPosition(), piece);
      if (getTeamTurn() == TeamColor.WHITE) {
        setTeamTurn(TeamColor.BLACK);
      }else {
        setTeamTurn(TeamColor.WHITE);
      }

    }
  }

  /**
   * Determines if the given team is in check
   *
   * @param teamColor which team to check for check
   * @return True if the specified team is in check
   */
  public boolean isInCheck(TeamColor teamColor) {
    ArrayList<ChessPosition> piecesPosition=squares.getList(teamColor);
    Collection<ChessMove> moves;
    ChessPosition king=squares.getKing(teamColor);
    for (ChessPosition position : piecesPosition) {
      ChessPiece piece=squares.getPiece(position);
      if (piece != null && piece.getTeamColor() != teamColor) {
        moves=piece.pieceMoves(squares, position);
        for (ChessMove move : moves) {
          if (move.getEndPosition().equals(king)) {
            return true;
          }
        }
      }

    }
    return false;
  }

  /**
   * Determines if the given team is in checkmate
   *
   * @param teamColor which team to check for checkmate
   * @return True if the specified team is in checkmate
   */
  public boolean isInCheckmate(TeamColor teamColor) {
    if (isInCheck(teamColor) == true && isInStalemate(teamColor) == true) {
      return true;
    }
    return false;
  }

  /**
   * Determines if the given team is in stalemate, which here is defined as having
   * no valid moves
   *
   * @param teamColor which team to check for stalemate
   * @return True if the specified team is in stalemate, otherwise false
   */
  public boolean isInStalemate(TeamColor teamColor) {
    ArrayList<ChessPosition> thisMoves;
    ArrayList<ChessMove> valid=new ArrayList<>();
    TeamColor listWanted;
    if (teamColor == TeamColor.WHITE) {
      listWanted=TeamColor.BLACK;
    }else {
      listWanted=TeamColor.WHITE;
    }
    thisMoves=new ArrayList<>(squares.getList(listWanted));

    for (ChessPosition moves : thisMoves) {
      ChessPiece piece=squares.getPiece(moves);
      if (piece != null && piece.getTeamColor() == teamColor) {
        Collection<ChessMove> cheese=validMoves(moves);
        valid.addAll(cheese);
      }
    }

    if (valid.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Sets this game's chessboard with a given board
   *
   * @param board the new board to use
   */
  public void setBoard(ChessBoard board) {
    this.squares=board;
  }

  /**
   * Gets the current chessboard
   *
   * @return the chessboard
   */
  public ChessBoard getBoard() {
    return squares;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessGame chessGame=(ChessGame) o;
    return turn == chessGame.turn && Objects.equals(squares, chessGame.squares) && Objects.equals(invalidMoveException, chessGame.invalidMoveException);
  }

  @Override
  public int hashCode() {
    return Objects.hash(turn, squares, invalidMoveException);
  }

  @Override
  public String toString() {
    return "ChessGame{" +
            "turn=" + turn +
            ", squares=" + squares +
            ", InvalidMoveException=" + invalidMoveException +
            '}';
  }
}
