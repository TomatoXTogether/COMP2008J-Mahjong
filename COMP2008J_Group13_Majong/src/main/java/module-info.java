module com.example.comp2008j_group13_majong {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.comp2008j_group13_majong to javafx.fxml;
    exports com.example.comp2008j_group13_majong;
}