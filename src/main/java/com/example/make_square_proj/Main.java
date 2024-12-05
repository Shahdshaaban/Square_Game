package com.example.make_square_proj;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane for the 4x4 square grid
        GridPane grid = new GridPane();
        int cellSize = 80;
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(100, 100, 100, 100));
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize, Color.WHITE);
                cell.setStroke(Color.CHOCOLATE);
                grid.add(cell, col, row);
            }
        }

        // Create VBox for inputs and labels
        VBox inputBox = new VBox(20);
        inputBox.setPadding(new Insets(20));
        inputBox.setAlignment(Pos.CENTER_LEFT);

        // Add labels and text fields for the input
        String[] labels = {"I", "J", "L", "O", "S", "Z", "T"};
        TextField[] textFields = new TextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            HBox inputRow = new HBox(10);
            inputRow.setAlignment(Pos.CENTER_LEFT);

            Text label = new Text(labels[i]);
            label.setFont(Font.font(20));
            TextField textField = new TextField("0");
            textField.setPrefWidth(50);
            textFields[i] = textField;

            inputRow.getChildren().addAll(label, textField);
            inputBox.getChildren().add(inputRow);
        }

        // Add a Solve button at the bottom
        Button solveButton = new Button("Solve");
        solveButton.setFont(Font.font(16));
        solveButton.setOnAction(event -> {
            // Handle solving logic here
            System.out.println("Solve button clicked!");
        });
        inputBox.getChildren().add(solveButton);

        // Create an HBox to hold the input section and the grid
        HBox root = new HBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(inputBox, grid);

        // Create the scene and display it
        Scene scene = new Scene(root, 850, 500);
        primaryStage.setTitle("Make A Square Game");
        primaryStage.setScene(scene); // Set the scene only once
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
