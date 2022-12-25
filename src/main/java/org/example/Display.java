package org.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Display {
    /**
     * Prints out the gameBoard to console using [] for empty cell and ██ for occupied cell
     *
     * @param gameBoard to be printed
     */
    void display(GameBoard gameBoard) {
        for (int i = 0; i < gameBoard.getBoard().length; i++) {
            for (int j = 0; j < gameBoard.getBoard()[i].length; j++) {
                if (gameBoard.getBoard()[i][j]) {
                    System.out.print("██"); // ASCII 219
                } else {
                    System.out.print("[]");
                }
            }
            System.out.println();
        }
    }
}