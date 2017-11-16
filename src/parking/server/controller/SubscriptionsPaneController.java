package parking.server.controller;

public class SubscriptionsPaneController {

    private RootLayoutController rootController;

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void backToMenu(){
        rootController.loadMainMenuScreen();
    }
}
