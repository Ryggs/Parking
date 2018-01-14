package parking.parkingmeter.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

//import static parking.server.utils.FXMLUtils.loader;

public class FXMLUtils {

    // Class to use if no controller instance needed
    public static Pane fxmlLoad(String path){
        FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(path));
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

    // if controller instance needed, use this
    public static FXMLLoader getLoader(String path) {
        FXMLLoader loader = new FXMLLoader(FXMLUtils.class.getResource(path));
        loader.setResources(getResourceBundle());
        return loader;
    }


}
