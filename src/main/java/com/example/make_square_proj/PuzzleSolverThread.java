package com.example.make_square_proj;

import java.util.concurrent.CountDownLatch;

/**
 * This class represents a thread responsible for solving a part of the puzzle.
 * It attempts to place puzzle pieces on the board and uses backtracking to find a solution.
 */
public class PuzzleSolverThread extends Thread {
    private final Board board;
    private final int threadId;  // Unique ID for the thread
    private final CountDownLatch latch;  // Used to notify the main manager that the thread has finished
    private boolean solved = false; // Flag to track if the puzzle has been solved
    private volatile boolean isInterrupted = false;  // Flag to interrupt the solving process if needed

    /**
     * Constructor to initialize the PuzzleSolverThread.
     * @param board The game board where the pieces are placed.
     * @param threadId The ID of this thread.
     * @param latch The latch used to wait for the completion of all threads.
     */
    public PuzzleSolverThread(Board board, int threadId, CountDownLatch latch) {
        this.board = board;
        this.threadId = threadId;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            solvePuzzle();
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadId + " was interrupted.");
        } finally {
            latch.countDown(); // Notify the manager that the thread has finished
        }
    }

    /**
     * Attempts to solve the puzzle using a backtracking approach.
     */
    private void solvePuzzle() throws InterruptedException {
        // Try placing pieces on the board using backtracking
        backtrackSolve(0, 0);  // Starting from the top-left corner of the board
    }

    /**
     * Backtracking method to attempt placing pieces on the board.
     * @param x The row index to place the piece.
     * @param y The column index to place the piece.
     * @return true if the puzzle is solved, false otherwise.
     */
    private boolean backtrackSolve(int x, int y) throws InterruptedException {
        // If we reach the last cell, check if the puzzle is solved
        if (x == board.getSize()) {
            if (board.isSolved()) {
                solved = true; // Puzzle solved
                return true;
            }
            return false;
        }

        // Try placing a piece at this position (x, y)
        for (int i = 0; i < board.getNumberOfPieces(); i++) {
            // If the current piece can be placed at (x, y)
            if (board.placePiece(new Piece(i), x, y)) {
                // Move to the next cell (rightwards)
                int nextX = (y + 1) == board.getSize() ? x + 1 : x;
                int nextY = (y + 1) == board.getSize() ? 0 : y + 1;

                if (backtrackSolve(nextX, nextY)) {
                    return true;
                }

                // If placing the piece didn't lead to a solution, remove it (backtrack)
                board.removePiece(i);
            }

            // Check if the thread was interrupted before continuing the loop
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }

        // If no piece could be placed, backtrack
        return false;
    }

    /**
     * Interrupt the solving process if needed.
     */
    public void interruptSolving() {
        isInterrupted = true;
        interrupt();
    }

    /**
     * Check if the puzzle has been solved by this thread.
     * @return true if the puzzle is solved, false otherwise.
     */
    public boolean isSolved() {
        return solved;
    }
}
