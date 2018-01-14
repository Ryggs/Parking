package parking.server;

import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import parking.server.controller.LoginPopupController;
import parking.server.model.AdminDAO;
import parking.util.DBUtil;

import java.util.Locale;


public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) throws Exception {
//        Locale.setDefault(new Locale("en"));
        Locale.setDefault(new Locale("pl"));
        LoginPopupController loginInstance = new LoginPopupController();

        loginInstance.showLoginWindow(loginStage);

        //AdminDAO.addNewUser("test1233","test","admin","test","test",123,"t@t");
     //   DBUtil.dbConnect();

    }


}



























