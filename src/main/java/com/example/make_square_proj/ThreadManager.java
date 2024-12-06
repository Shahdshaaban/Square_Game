package com.example.make_square_proj;

import java.util.*;
import java.util.concurrent.*;

/**
 * This class manages the threads responsible for solving the puzzle.
 * It ensures all threads are properly executed and handles any issues that arise.
 */
public class ThreadManager {
    private final ExecutorService executorService;
    private final List<PuzzleSolverThread> threads;
    private final Board board;
    private final int numThreads;
    private volatile boolean isSolving = false; // Flag to track if the puzzle is being solved
    private CountDownLatch latch;  // Used to track the completion of threads
    private boolean isCancelled = false; // Flag to track if solving is cancelled

    /**
     * Constructor initializes the thread manager with a board and number of threads.
     *
     * @param board The game board where the puzzle pieces are placed.
     * @param numThreads The number of threads to be used for solving the puzzle.
     */
    public ThreadManager(Board board, int numThreads) {
        this.board = board;
        this.numThreads = numThreads;
        this.threads = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(numThreads); // Fixed pool for thread management
    }

    /**
     * Starts the puzzle-solving process by creating and executing threads.
     * Each thread will attempt to solve a portion of the puzzle.
     */
    public synchronized void startSolving() {
        if (isSolving) {
            System.out.println("Puzzle solving is already in progress.");
            return; // Prevent multiple solving attempts at once
        }

        isSolving = true;
        isCancelled = false;
        threads.clear(); // Clear the old threads if any
        latch = new CountDownLatch(numThreads); // Initialize latch to the number of threads

        // Create and start the threads
        for (int i = 0; i < numThreads; i++) {
            PuzzleSolverThread solverThread = new PuzzleSolverThread(board, i, latch);
            threads.add(solverThread);
            executorService.submit(solverThread); // Submit each thread to the executor
        }
    }

    /**
     * Stops the puzzle-solving process.
     * Interrupts all active threads.
     */
    public synchronized void stopSolving() {
        if (!isSolving) {
            System.out.println("No puzzle solving process is currently running.");
            return;
        }

        isSolving = false;
        isCancelled = true;

        for (PuzzleSolverThread thread : threads) {
            thread.interrupt(); // Interrupt each thread to stop it
        }

        // Wait for all threads to finish gracefully
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread manager interrupted while waiting for threads to finish.");
        }

        executorService.shutdownNow(); // Shut down the executor service immediately
        System.out.println("Puzzle solving stopped.");
    }

    /**
     * Monitors the puzzle-solving process and handles any issues like deadlocks or thread failure.
     * It provides a callback to the main game logic to handle the result.
     */
    public void monitorSolving() {
        try {
            latch.await();  // Wait for all threads to finish
            if (!isCancelled && board.isSolved()) {
                System.out.println("Puzzle solved successfully!");
            } else if (isCancelled) {
                System.out.println("Puzzle solving was cancelled.");
            } else {
                System.out.println("Puzzle solving failed.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption
            System.out.println("Thread manager interrupted while monitoring.");
        }
    }

    /**
     * Cleans up the resources used by the thread manager and shuts down the executor service.
     */
    public void cleanup() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
    }

    public boolean isSolving() {
        return isSolving;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
