package parking.server.controller;

public class PricesPaneController {
    private RootLayoutController rootController;

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void backToMenu(){
        rootController.loadMainMenuScreen();
    }
}
