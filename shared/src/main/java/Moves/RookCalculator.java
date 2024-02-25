package Moves;
import chess.*;

import java.util.ArrayList;
import java.util.Collection;

import static Moves.Bishopcalculator.getChessMoves;

public class RookCalculator implements Calculator {
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
    ArrayList<ChessMove> moves = new ArrayList<>();
    moves.addAll(helper(board,myPosition,color,1,0));
    moves.addAll(helper(board,myPosition,color,-1,0));
    moves.addAll(helper(board,myPosition,color,0,1));
    moves.addAll(helper(board,myPosition,color,0,-1));

    return moves;
  }
  public Collection<ChessMove> helper(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,int first, int second){
    return getChessMoves(board, myPosition, color, first, second);
  }

}

