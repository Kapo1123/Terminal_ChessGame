package chess;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    private ChessPosition White_king;
    private ChessPosition Black_king;
    private ArrayList<ChessPosition> Black_pieces;
    private ArrayList<ChessPosition> White_pieces;


    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
        if(piece.getPieceType() == ChessPiece.PieceType.KING){
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                White_king = position;
            }
            else{
                Black_king = position;
            }
        }
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            White_pieces.add(position);
        }
        else{
            Black_pieces.add(position);
        }

    }
    public ArrayList<ChessPosition> get_list(ChessGame.TeamColor teamColor){
        if(teamColor == ChessGame.TeamColor.WHITE){
            return White_pieces;
        }
        return Black_pieces;
    }
    public ChessPosition get_king(ChessGame.TeamColor teamColor){
        if(teamColor == ChessGame.TeamColor.WHITE){
            return White_king;
        }
        return Black_king;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int i =0; i<8;i++){
            for (int j =0; i<8;i++){
                squares[i][j] = null;
            }
        }
        //Pawn for both teams
        for (int i =0; i<8;i++){
            squares[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN);
            squares[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN);
        }
        //King for both teams
        squares[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KING);
        squares[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KING);

        //  Queen for both team
        squares[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.QUEEN);
        squares[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.QUEEN);

        //Knight for both team
        squares[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        squares[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        squares[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        squares[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);

        //ROOK for both team
        squares[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        squares[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        squares[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);
        squares[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);

        //Bishop for both team
        squares[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        squares[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        squares[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);
        squares[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(squares);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }
}
