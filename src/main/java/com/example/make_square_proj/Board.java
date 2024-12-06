package com.example.make_square_proj;

public class Board {
    private final Piece[][] board; // 2D array representing the board
    private final int size = 4; // Example: 4x4 board

    public Board() {
        board = new Piece[size][size];
    }

    public boolean placePiece(Piece piece, int x, int y) {
        // Try to place the piece at position (x, y) or its rotated versions
        for (int i = 0; i < 4; i++) { // Try rotating up to 3 times (90, 180, 270 degrees)
            if (canPlacePiece(piece, x, y)) {
                board[x][y] = piece;
                return true;
            }
            piece.rotate(); // Rotate piece 90 degrees
        }
        return false;
    }

    private boolean canPlacePiece(Piece piece, int x, int y) {
        // Check if the piece fits into the board at position (x, y)
        int[][] shape = piece.getShape();
        if (x + shape.length > size || y + shape[0].length > size) {
            return false; // The piece doesn't fit in this position
        }

        // Check if any cells are already occupied
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0 && board[x + i][y + j] != null) {
                    return false; // The position is already occupied
                }
            }
        }
        return true;
    }

    // Displaying the board (for debugging purposes)
    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].getId() + "\t");
                } else {
                    System.out.print("X\t"); // Empty space
                }
            }
            System.out.println();
        }
    }

    public boolean isSolved() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

}
