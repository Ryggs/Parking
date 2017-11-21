package parking.server.controller;

public class LogPaneController {
    private RootLayoutController rootController;

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public void backToMenu(){
        System.out.println("root w log: " + rootController.toString());
        rootController.loadMainMenuScreen();
    }
}
