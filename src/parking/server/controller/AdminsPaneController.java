package parking.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class AdminsPaneController {
    private RootLayoutController rootController;



    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void backToMenu(){
        rootController.loadMainMenuScreen();
    }
}
