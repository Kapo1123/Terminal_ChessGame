package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ui.EscapeSequences.*;

import static ui.EscapeSequences.ERASE_SCREEN;
import static ui.EscapeSequences.SET_BG_COLOR_BLACK;

public class chess_board {
  public static ChessBoard board=new ChessBoard();

  static {
    board.resetBoard();
  }

  static ArrayList<String> letters;

  public static void main() {

    var out=new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    letters=new ArrayList<>();
    letters.add("a");
    letters.add("b");
    letters.add("c");
    letters.add("d");
    letters.add("e");
    letters.add("f");
    letters.add("g");
    letters.add("h");

    drawWhite(out);
    drawBlack(out);
    out.print(RESET_BG_COLOR);
    out.print(SET_TEXT_COLOR_WHITE);

//    drawTicTacToeBoard(out);
//
//    out.print(SET_BG_COLOR_BLACK);
//    out.print(SET_TEXT_COLOR_WHITE);
  }

  public static void drawWhite(PrintStream out) {
    for (int i=0; i < 10; i++) {
      if (i == 0 || i == 9) {
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.EMPTY);
        for (String letter : letters) {
          out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
          out.print("\u2003");
          out.print("\u2003");
          out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
          out.print(letter);
          out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
          out.print("\u2003");
          out.print("\u2003");
        }
        out.print(EscapeSequences.EMPTY);
      }else {
          out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
          out.print("\u2006");
          out.print(i);
          out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
          out.print("\u2006");
          for (int k=1; k < 9; k++) {
            drawpiece(out, i, k);
          }
            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            out.print("\u2006");
            out.print(i);
            out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
            out.print("\u2006");
          }
        out.println();
        }
        out.println();
      }
  public static void drawBlack(PrintStream out) {
    List<String> letters = Arrays.asList("h", "g", "f", "e", "d", "c", "b", "a");
    for (int i=9; i >= 0; i--) {
      if (i == 0 || i == 9) {
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.EMPTY);
        for (String letter : letters){
          out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
          out.print("\u2003");
          out.print("\u2003");
          out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
          out.print(letter);
          out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
          out.print("\u2003");
          out.print("\u2003");
        }
        out.print(EscapeSequences.EMPTY);

      }else {
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        out.print(i);
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        for (int k=8; k >0; k--) {
          drawpiece(out, i, k);
        }
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        out.print(i);
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
      }
      out.println();
    }
    out.println();
  }




  public static void drawpiece(PrintStream out, int row, int col) {
    var piece=board.getPiece(new ChessPosition(row, col));
    int doc = col + row;
    if (doc % 2 == 0) { //white color piece
      out.print(EscapeSequences.SET_BG_COLOR_MAGENTA);
      out.print("\u2006");
      out.print(selectpiece(piece,out));
      out.print(EscapeSequences.SET_BG_COLOR_MAGENTA);
      out.print("\u2006");
    }else if (doc % 2 == 1) {
      out.print(SET_BG_COLOR_BLACK);
      out.print("\u2006");
      out.print(selectpiece(piece,out));
      out.print(SET_BG_COLOR_BLACK);
      out.print("\u2006");
    }

  }

  public static String selectpiece(ChessPiece piece,PrintStream out) {
    if (piece == null){
      return EMPTY;
    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.KING)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return  WHITE_KING;
      }else {
        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        return BLACK_KING;
      }

    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return WHITE_QUEEN;
      }else {
        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        return BLACK_QUEEN;
      }
    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.BISHOP)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return WHITE_BISHOP;
      }else {
        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        return BLACK_BISHOP;
      }
    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.KNIGHT)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return WHITE_KNIGHT;
      }else {
        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        return BLACK_KNIGHT;
      }
    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.ROOK)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return WHITE_ROOK;
      }else {
        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        return BLACK_ROOK;
      }
    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.PAWN)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return WHITE_PAWN;
      }else {
        out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        return BLACK_PAWN;
      }
    }
    return null;
  }
}

