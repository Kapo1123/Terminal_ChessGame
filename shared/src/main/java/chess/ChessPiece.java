package chess;

import Moves.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
  public ChessGame.TeamColor pieceColor;
  private ChessPiece.PieceType type;

  public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
    this.pieceColor=pieceColor;
    this.type=type;
  }

  /**
   * The various different chess piece options
   */
  public enum PieceType {
    KING,
    QUEEN,
    BISHOP,
    KNIGHT,
    ROOK,
    PAWN
  }

  /**
   * @return Which team this chess piece belongs to
   */
  public ChessGame.TeamColor getTeamColor() {

    return pieceColor;
  }

  /**
   * @return which type of chess piece this piece is
   */
  public PieceType getPieceType() {

    return type;
  }

  @Override
  public String toString() {
    return "ChessPiece{" +
            "pieceColor=" + pieceColor +
            ", type=" + type +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessPiece that=(ChessPiece) o;
    return pieceColor == that.pieceColor && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pieceColor, type);
  }

  /**
   * Calculates all the positions a chess piece can move to
   * Does not take into account moves that are illegal due to leaving the king in
   * danger
   *
   * @return Collection of valid moves
   */
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    ArrayList<ChessMove> moves=new ArrayList<>();
    if (this.getPieceType() == PieceType.BISHOP) {
      Bishopcalculator cal=new Bishopcalculator();
      moves.addAll(cal.pieceMoves(board, myPosition, this.getTeamColor()));
    }else if (this.getPieceType() == PieceType.ROOK) {
      RookCalculator cal=new RookCalculator();
      moves.addAll(cal.pieceMoves(board, myPosition, this.getTeamColor()));
    }else if (this.getPieceType() == PieceType.QUEEN) {
      Bishopcalculator cal=new Bishopcalculator();
      moves.addAll(cal.pieceMoves(board, myPosition, this.getTeamColor()));
      RookCalculator cal1=new RookCalculator();
      moves.addAll(cal1.pieceMoves(board, myPosition, this.getTeamColor()));
    }else if (this.getPieceType() == PieceType.KNIGHT) {
      Knightcalculator cal=new Knightcalculator();
      moves.addAll(cal.pieceMoves(board, myPosition, this.getTeamColor()));
    }else if (this.getPieceType() == PieceType.KING) {
      Kingcalculator cal=new Kingcalculator();
      moves.addAll(cal.pieceMoves(board, myPosition, this.getTeamColor()));
    }else if (this.getPieceType() == PieceType.PAWN) {
      Pawncalculator cal=new Pawncalculator();
      moves.addAll(cal.pieceMoves(board, myPosition, this.getTeamColor()));
    }

    return moves;


  }
}
