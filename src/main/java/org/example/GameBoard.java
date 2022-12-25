package org.example;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GameBoard {
    private final Boolean[][] board;

    public GameBoard(Boolean[][] seed) {
        this.board = new Boolean[seed.length][seed[0].length];
        //initialize board from seed
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = seed[i][j];
            }
        }
    }

    public void step() {
        // create new empty state
        Boolean[][] boardCopy = new Boolean[board.length][board[0].length];

        // for each cell: get current neighbours, decide if cell dies or new cell spawns
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int neighbours = getCurrentNeighbours(board, i, j);
                boardCopy[i][j] = spawnOrDie(board[i][j], neighbours);
            }
        }

        // replace current board with next state
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(boardCopy[i], 0, board[i], 0, board[i].length);
        }
    }

    public boolean spawnOrDie(boolean current, int neighbours) {
        if (neighbours < 2 || neighbours > 3) {
            // if cell has less than 2 neighbours or more than 3 it dies
            return false;
        } else if (neighbours == 3) {
            // if current spot has exactly 3 neighbours new cell spawns
            return true;
        }
        return current;
    }

    public int getCurrentNeighbours(Boolean[][] board, int i, int j) {
        int neighbours = 0;
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if (isInBounds(i, j, row, col) && isNotCurrentCell(row, col)) {
                    if (board[i + row][j + col]) {
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }

    private boolean isInBounds(int i, int j, int row, int col) {
        return i + row >= 0 && i + row < board.length && j + col >= 0 && j + col < board[i].length;
    }

    private boolean isNotCurrentCell(int row, int col) {
        return !(row == 0 && col == 0);
    }
}
