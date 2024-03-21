package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.ERASE_SCREEN;

public class chess_board {
  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

//    drawHeaders(out);

//    drawTicTacToeBoard(out);
//
//    out.print(SET_BG_COLOR_BLACK);
//    out.print(SET_TEXT_COLOR_WHITE);
  }

}
