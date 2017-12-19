package parking.parkingmeter.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class FXMLUtils {
    public static FXMLLoader loader;

    public static Pane fxmlLoad(String path){
        loader = new FXMLLoader(FXMLUtils.class.getResource(path));
        loader.setResources(getResourceBundle());
        try {
            System.out.println(FXMLUtils.class.getResource(path));
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Cant return");
        return null;
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("parking.parkingmeter.bundles.messages");
    }

    public static FXMLLoader getLoader() {
        return loader;
    }


}
