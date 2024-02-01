package Moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class Bishopcalculator implements calculator{
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
    ArrayList<ChessMove> moves = new ArrayList<>();
    moves.addAll(helper(board,myPosition,color,1,1));
    moves.addAll(helper(board,myPosition,color,1,-1));
    moves.addAll(helper(board,myPosition,color,-1,1));
    moves.addAll(helper(board,myPosition,color,-1,-1));

    return moves;
  }
  public Collection<ChessMove> helper(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color,int first, int second){
    ArrayList<ChessMove> moves = new ArrayList<>();
    int row = myPosition.getRow()+first;
    int col =myPosition.getColumn() +second;
    while(row<=8&&col<=8&&row>=1&&col>=1){
      ChessPiece piece = board.getPiece(new ChessPosition(row,col));
      if(piece != null){
        if (piece.getTeamColor() == color){
          return moves;
        }
        moves.add(new ChessMove(myPosition,new ChessPosition(row,col),null));
        return moves;
      }
      moves.add(new ChessMove(myPosition,new ChessPosition(row,col),null));
      row+=first;
      col+=second;
    }
    return moves;
  }

}
