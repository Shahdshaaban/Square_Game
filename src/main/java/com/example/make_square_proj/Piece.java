package com.example.make_square_proj;

public class Piece {
    private final int id; // Unique identifier for the piece
    private int[][] shape; // Represents the piece as a 2D array
    private final int size; // Size of the piece (e.g., 2x2, 3x3)

    public Piece(int id, int size) {
        this.id = id;
        this.size = size;
        this.shape = new int[size][size];
        // Initialize shape based on id (example: set a specific pattern or piece)
        initializeShape();
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int[][] getShape() {
        return shape;
    }

    // Initializes the shape for the piece, example could vary
    private void initializeShape() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                shape[i][j] = (i + j) % 2; // Example pattern (could be any shape)
            }
        }
    }

    /**
     * Rotates the piece 90 degrees clockwise.
     */
    public void rotate() {
        int newSize = shape.length;
        int[][] rotatedShape = new int[newSize][newSize];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                rotatedShape[i][j] = shape[newSize - 1 - j][i];
            }
        }
        shape = rotatedShape;
    }
}
