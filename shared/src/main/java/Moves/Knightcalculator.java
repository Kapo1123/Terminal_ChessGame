package Moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

import static Moves.Kingcalculator.getChessMoves;

public class Knightcalculator implements Calculator {
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
    ArrayList moves=new ArrayList<>();
    moves.addAll(helper(board, myPosition, color, 2, 1));
    moves.addAll(helper(board, myPosition, color, 2, -1));
    moves.addAll(helper(board, myPosition, color, -2, -1));
    moves.addAll(helper(board, myPosition, color, -2, 1));
    moves.addAll(helper(board, myPosition, color, 1, 2));
    moves.addAll(helper(board, myPosition, color, -1, 2));
    moves.addAll(helper(board, myPosition, color, 1, -2));
    moves.addAll(helper(board, myPosition, color, -1, -2));


    return moves;
  }

  public Collection<ChessMove> helper(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int first, int second) {
    ArrayList moves=new ArrayList<ChessMove>();
    return getChessMoves(board, myPosition, color, first, second, moves);
  }

}

