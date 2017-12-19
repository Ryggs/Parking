package parking.parkingmeter;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parking.parkingmeter.utils.FXMLUtils;


import java.util.Locale;


public class Main extends Application{
    private final String ROOT_LAYOUT_FXML_PATH = "../view/RootLayout.fxml";



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(new Locale("en"));
        // Locale.setDefault(new Locale("pl"));

        Parent root = FXMLUtils.fxmlLoad(ROOT_LAYOUT_FXML_PATH);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();




    }

}
