package parking.server;

import javafx.application.Application;

import javafx.stage.Stage;
import parking.server.controller.LoginPopupController;
import parking.util.DBUtil;


public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) throws Exception {

        LoginPopupController loginInstance = new LoginPopupController();

        loginInstance.showLoginWindow(loginStage);

        DBUtil.dbConnect();

    }


}



























