package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ui.EscapeSequences.*;

import static ui.EscapeSequences.ERASE_SCREEN;
import static ui.EscapeSequences.SET_BG_COLOR_BLACK;

public class ChessBoard {
  public static PrintStream out=new PrintStream(System.out, true, StandardCharsets.UTF_8);
  public static chess.ChessBoard board=new chess.ChessBoard();
  static ArrayList<String> letters;

  static {
    board.resetBoard();
    letters=new ArrayList<>();
    letters.add("a");
    letters.add("b");
    letters.add("c");
    letters.add("d");
    letters.add("e");
    letters.add("f");
    letters.add("g");
    letters.add("h");
  }


  public static void main() {


    out.print(ERASE_SCREEN);

  }

  public static void drawBlack(PrintStream out, ChessPosition start, ArrayList<ChessPosition> end) {
    List<String> letters=Arrays.asList("h", "g", "f", "e", "d", "c", "b", "a");
    for (int i=0; i < 10; i++) {
      if (i == 0 || i == 9) {
        out(out, letters);
      }else {
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        out.print(i);
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        for (int k=8; k > 0; k--) {
          drawpiece(out, i, k, start, end);
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
    out.print(RESET_BG_COLOR);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  public static void drawWhite(PrintStream out, ChessPosition start, ArrayList<ChessPosition> end) {
    for (int i=9; i >= 0; i--) {
      if (i == 0 || i == 9) {
        out(out, letters);

      }else {
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        out.print(i);
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print("\u2006");
        for (int k=1; k < 9; k++) {
          drawpiece(out, i, k, start, end);
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
    out.print(RESET_BG_COLOR);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void out(PrintStream out, List<String> letters) {
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
  }


  public static void drawpiece(PrintStream out, int row, int col, ChessPosition start, ArrayList<ChessPosition> end) {
    var piece=board.getPiece(new ChessPosition(row, col));
    int doc=col + row;
    if (doc % 2 == 0) { //white color piece
      out.print(EscapeSequences.SET_BG_COLOR_MAGENTA);
      println(out, row, col, start, end, piece, SET_BG_COLOR_GREEN);
      out.print(EscapeSequences.SET_BG_COLOR_MAGENTA);
      out.print("\u2006");
    }else if (doc % 2 == 1) {
      out.print(SET_BG_COLOR_BLACK);
      println(out, row, col, start, end, piece, SET_TEXT_COLOR_GREEN);
      out.print(SET_BG_COLOR_BLACK);
      out.print("\u2006");
    }

  }

  private static void println(PrintStream out, int row, int col, ChessPosition start, ArrayList<ChessPosition> end, ChessPiece piece, String setBgColorGreen) {
    out.print("\u2006");
    if (start != null) {
    if (new ChessPosition(row, col).equals(start)) {
        out.print(setBgColorGreen);
      }else if (end.contains(new ChessPosition(row, col))) {
        out.print(SET_BG_COLOR_RED);
      }
    }
    out.print(selectpiece(piece, out));
  }

  public static String selectpiece(ChessPiece piece, PrintStream out) {
    if (piece == null) {
      return EMPTY;
    }
    if (piece.getPieceType().equals(ChessPiece.PieceType.KING)) {
      if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        out.print(EscapeSequences.SET_TEXT_COLOR_YELLOW);
        return WHITE_KING;
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
//        return "Q";
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

  public void updateboard(ChessGame game) {
    board=game.getBoard();
  }

  public void highlight(PrintStream out, ChessGame.TeamColor color, Collection<ChessMove> moves) {
    ChessPosition start_position=new ChessPosition(0, 0);
    ArrayList<ChessPosition> end_position=new ArrayList<>();
    for (ChessMove move : moves) {
      start_position=move.getStartPosition();
      end_position.add(move.getEndPosition());
    }
    if (color== null){
      drawWhite(out, start_position, end_position);
    }
    else if (color.equals(ChessGame.TeamColor.WHITE)) {
      drawWhite(out, start_position, end_position);
    }else {
      drawBlack(out, start_position, end_position);
    }

  }
}

