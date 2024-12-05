module com.example.make_square_proj {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.make_square_proj to javafx.fxml;

    exports com.example.make_square_proj;
}
