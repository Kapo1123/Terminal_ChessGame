package Moves;

import chess.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class Pawncalculator {
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
    ArrayList moves=new ArrayList<>();
    int row=myPosition.getRow();
    if (color.equals(ChessGame.TeamColor.WHITE)) {
      moves.addAll(helper(board, myPosition, 1, 0));
      moves.addAll(helper2(board, myPosition, color, 1, 1));
      moves.addAll(helper2(board, myPosition, color, 1, -1));

      if (row == 2) {
        moves.addAll(helper3(board, myPosition, 2, 0));
      }
    }else {
      moves.addAll(helper(board, myPosition, -1, 0));
      moves.addAll(helper2(board, myPosition, color, -1, 1));
      moves.addAll(helper2(board, myPosition, color, -1, -1));

      if (row == 7) {
        moves.addAll(helper3(board, myPosition, -2, 0));
      }
    }
    return moves;
  }


  public Collection<ChessMove> helper(ChessBoard board, ChessPosition myPosition, int first, int second) {
    ArrayList moves=new ArrayList<>();
    int row=myPosition.getRow() + first;
    int col=myPosition.getColumn() + second;
    if (row <= 8 && col <= 8 && col >= 1 && row >= 1) {
      ChessPiece chess=board.getPiece(new ChessPosition(row, col));
      if (chess == null) {
        if (row == 8 || row == 1) {
          for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
            if (type.equals(ChessPiece.PieceType.PAWN) || type.equals(ChessPiece.PieceType.KING)) {
              continue;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), type));
          }
        }else {
          moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
        }
      }

    }
    return moves;
  }

  public Collection<ChessMove> helper2(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int first, int second) {
    ArrayList moves=new ArrayList<>();
    int row=myPosition.getRow() + first;
    int col=myPosition.getColumn() + second;
    if (row <= 8 && col <= 8 && col >= 1 && row >= 1) {
      ChessPiece chess=board.getPiece(new ChessPosition(row, col));
      if (chess != null && !chess.getTeamColor().equals(color)) {
        if (row == 8 || row == 1) {
          for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
            if ((type.equals(ChessPiece.PieceType.PAWN) || type.equals(ChessPiece.PieceType.KING))) {
              continue;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(row, col), type));

          }
        }else {
          moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
        }
      }

    }
    return moves;
  }

  public Collection<ChessMove> helper3(ChessBoard board, ChessPosition myPosition, int first, int second) {
    ArrayList moves=new ArrayList<>();
    int row=myPosition.getRow() + first;
    int col=myPosition.getColumn() + second;
    ChessPiece chess1;
    if (first == -2) {
      chess1=board.getPiece(new ChessPosition(row + 1, col ));
    }else {
      chess1=board.getPiece(new ChessPosition(row - 1, col ));
    }
    if (row <= 8 && col <= 8 && col >= 1 && row >= 1) {
      if (chess1 != null) {
        return moves;
      }
      ChessPiece chess=board.getPiece(new ChessPosition(row, col));
      if (chess == null) {
          moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
        }

      }
    return moves;
    }

  }
