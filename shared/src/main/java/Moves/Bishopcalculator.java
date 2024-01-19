package Moves;
import chess.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class Bishopcalculator implements calculator {
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
    ArrayList moves = new ArrayList<>();
    moves.addAll(helper(board,myPosition,color,1,1));
    moves.addAll(helper(board,myPosition,color,1,-1));
    moves.addAll(helper(board,myPosition,color,-1,1));
    moves.addAll(helper(board,myPosition,color,-1,-1));
    return moves;





  }
  public Collection<ChessMove> helper(ChessBoard board, ChessPosition myPosition,ChessGame.TeamColor color,int first, int second){
    ArrayList moves = new ArrayList<ChessMove>();
    int col = myPosition.getColumn();
    int row = myPosition.getRow();
    while (col <8 && row<8 && col+second>=1 && row+first>=1){
      if (board.getPiece(new ChessPosition(row+first,col+second)) != null){
        ChessPiece chess = board.getPiece(new ChessPosition(row+first,col+second));
        if (chess.getTeamColor().equals(color)){
            break;
        }
        else {
          moves.add( new ChessMove(myPosition,new ChessPosition(row+first,col+second), null));
          break;
        }

      }
      moves.add( new ChessMove(myPosition,new ChessPosition(row+first,col+second), null));
      row += first;
      col+= second;

    }
    return moves;
  }
}


