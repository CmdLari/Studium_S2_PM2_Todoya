module todoyaf.todoyaf {
    requires javafx.controls;
    requires javafx.fxml;


    opens todoyaf.todoyaf to javafx.fxml;
    exports todoyaf.todoyaf;
}