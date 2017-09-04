/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.davidg95.queenspuzzle;

/**
 * Models the board. Also has methods for working out queen position.
 *
 * @author David
 */
public class Board {

    private final boolean board[][]; //The board, represented as a 2D array of booleans.

    /**
     * Create a new board with given size.
     *
     * @param n the board size.
     */
    public Board(int n) {
        board = new boolean[n][n];
    }

    /**
     * Starts the process.
     *
     * @return true if a solution was found, false if none as found.
     */
    public boolean start() {
        return doRow(0);
    }

    /**
     * Check where the queen can go on the given row.
     *
     * @param r the row to check.
     * @return true if a valid place was found, false if it was not.
     */
    public boolean doRow(int r) {
        for (int c = 0; c < board[r].length; c++) { //Loop through every column.
            board[r][c] = true; //Set the current position to true.
            boolean valid = checkConflicts(r, c); //Check if there are any conflicts in the current position.
            if (valid) { //If there are no conflicts.
                if (r == (board.length - 1)) { //If we are on the last row.
                    return true; //If this line is reached, a solution has been found.
                }
                if (doRow(r + 1)) { //Check the next row.
                    return true;
                }
                board[r][c] = false; //When this line is reached, no children rows have valid position, so a new position must be tried.
            } else {
                board[r][c] = false; //Position is not valid.
            }
        }
        return false;
    }

    /**
     * Check if the current position is valid.
     *
     * @param r the row to check.
     * @param c the column to check.
     * @return true if it is valid, false if it is not.
     */
    private boolean checkConflicts(int r, int c) {
        //Check column.
        for (int kr = 0; kr < board.length; kr++) { //Loop throw every row and check if the current column has a queen.
            if (kr != r) {
                if (board[kr][c]) {
                    return false; //There is a queen already in this column.
                }
            }
        }
        //Check diagonals
        for (int kr = 0; kr < board.length; kr++) { //Loop throuw every row.
            int diff = Math.abs(kr - r);
            if (diff > 0) {
                //Check diagonally up.
                if (c - diff >= 0) {
                    if (board[kr][c - diff]) {
                        return false; //Not valid.
                    }
                }
                //Check diagonally down.
                if (c + diff < board.length) {
                    if (board[kr][c + diff]) {
                        return false; //Not valid.
                    }
                }
            }
        }
        return true; //Valid.
    }

    /**
     * Print the current state in console.
     */
    public void printState() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j] ? "Q" : "-"));
            }
            System.out.println("|");
        }
    }

    /**
     * Get the board.
     *
     * @return the board.
     */
    public boolean[][] getBoard() {
        return board;
    }

    /**
     * Get the size of the board.
     *
     * @return the size.
     */
    public int getSize() {
        return board.length;
    }
}
