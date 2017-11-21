package parking.server.utils;

import javafx.scene.control.*;

import java.util.ResourceBundle;

public class DialogsUtils {
    static ResourceBundle bundle = ResourceBundle.getBundle("parking.server.bundles.messages");

    public static void dialogAboutApplication() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle(bundle.getString("about.title"));
        aboutAlert.setHeaderText(bundle.getString("about.header"));
        aboutAlert.setContentText(bundle.getString("about.context"));
        aboutAlert.showAndWait();
    }


}
