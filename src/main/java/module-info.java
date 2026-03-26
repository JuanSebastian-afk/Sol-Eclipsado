module com.example.sol_eclipsado {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sol_eclipsado to javafx.fxml;
    opens com.example.sol_eclipsado.controller to javafx.fxml;

    exports com.example.sol_eclipsado;
}