/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.davidg95.queenspuzzle;

/**
 *
 * @author David
 */
public class Board {

    private final boolean board[][]; //The board, represented as a 2D array of booleans.

    public Board(int n) {
        board = new boolean[n][n];
    }

    /**
     * Starts the process.
     */
    public void start() {
        doRow(0);
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
                if (r == (board.length - 1)) { //If we are at the end of the row.
                    return true;
                }
                if (doRow(r + 1)) {
                    return true;
                }
                board[r][c] = false;
            } else {
                board[r][c] = false;
            }
        }
        return false;
    }

    private boolean checkConflicts(int r, int c) {
        for (int kr = 0; kr < board.length; kr++) {
            if (kr != r) {
                if (board[kr][c]) {
                    return false;
                }
            }
        }
        for (int kr = 0; kr < board.length; kr++) {
            int diff = Math.abs(kr - r);
            if (diff > 0) {
                if (c - diff >= 0) {
                    if (board[kr][c - diff]) {
                        return false;
                    }
                }
                if (c + diff < board.length) {
                    if (board[kr][c + diff]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void printState() {
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j] ? "Q" : "-"));
            }
            System.out.println("|");
        }
    }

    public boolean[][] getBoard() {
        return board;
    }

    public int getSize() {
        return board.length;
    }
}
