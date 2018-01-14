package parking.parkingmeter.controller;

import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import parking.parkingmeter.model.Ticket;
import parking.parkingmeter.model.TicketDAO;
import parking.parkingmeter.model.TicketPay;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class PayTicketSubController {
    private RootLayoutController rootController;
    ResourceBundle bundle = FXMLUtils.getResourceBundle();
    private String ticketNo;
    private String username;
    private String password;
    private TicketPay ticketPay;

    @FXML
    private TextField ticketNoTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;
    @FXML
    final TextArea printingArea = new TextArea();

    @FXML
    private Label message;

    @FXML
    private TextArea detailsTextField;

    @FXML
    void backToMenu(ActionEvent event) {
        Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        rootController.loadRootLayout(currStage);
    }

    @FXML
    void confirm(ActionEvent event) throws SQLException {
        ticketNo = ticketNoTextField.getText();
        username = usernameTextField.getText();
        password = passwordTextField.getText();

        int retultFlag = TicketDAO.payTicketSub(ticketNo, username, password);

        if(retultFlag == -1) {
            message.setTextFill(Color.rgb(255, 0, 0));
            message.setText(bundle.getString("sub.err"));
            detailsTextField.setVisible(false);
        } else {
            message.setTextFill(Color.rgb(0, 255, 0));
            message.setText(bundle.getString("pay.ok"));

            detailsTextField.setVisible(true);
            // TODO: Wyświetlanie jakoś szczegółów płatności i drukowanie potwierdzenia DONE
            try {
                Ticket ticket = TicketDAO.getPaidTicket(ticketNo);

                detailsTextField.setText(bundle.getString("txt.username") + "\t" + username +"\n");
                detailsTextField.appendText(bundle.getString("txt.ticketNo") + "\t\t\t\t" + ticketNo +"\n");
                detailsTextField.appendText(bundle.getString("txt.entryTime") + "\t\t\t\t" +ticket.getEntryTime() +"\n");
                detailsTextField.appendText(bundle.getString("txt.paymentTime") + "\t\t\t\t" + ticket.getPaymentTime() +"\n");
                detailsTextField.appendText(bundle.getString("txt.paymentType") + "\t\t\t\t" + ticket.getPaymentType() +"\n");
                detailsTextField.appendText(bundle.getString("txt.charge") + "\t\t\t\t\t" +ticket.getCharge()/100.0 +"\n");
                detailsTextField.appendText(bundle.getString("txt.controlCode") + "\t\t\t\t" +ticket.getControlCode() +"\n");

                //set up ticket
                printingArea.setFont(Font.font(8));
                printingArea.setPrefColumnCount(20);
                printingArea.setPrefRowCount(12);  // While adding additional text remember to increase num of rows and columns
                //set ticket info
                printingArea.setText("********* Parking javaPark *********\n");
                printingArea.appendText("======================\n");
                printingArea.appendText(bundle.getString("txt.username") + "\t" + username +"\n");
                printingArea.appendText(bundle.getString("txt.ticketNo") + "\t" + ticketNo +"\n");
                printingArea.appendText(bundle.getString("txt.entryTime") + "\t" + ticket.getEntryTime() +"\n");
                printingArea.appendText(bundle.getString("txt.paymentTime") + "\t" + ticket.getPaymentTime() +"\n");
                printingArea.appendText(bundle.getString("txt.paymentType") + "\t" + ticket.getPaymentType() +"\n");
                printingArea.appendText(bundle.getString("txt.charge") + "\t" + ticket.getCharge()/100.0 +"\n");
                printingArea.appendText(bundle.getString("txt.controlCode") + "\t" + ticket.getControlCode() +"\n");
                printingArea.appendText("======================\n");

                //Print ticket
                if(print(printingArea)) {
                    detailsTextField.appendText(bundle.getString("txt.printSuccess") +"\n");
                }
                else {
                    detailsTextField.appendText(bundle.getString("txt.printFailed") + "\n");
                    detailsTextField.appendText(bundle.getString("txt.printFailed2") + "\n");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                detailsTextField.appendText(bundle.getString("err.dbError") + "\n");
            }
        }
    }

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    private boolean print(Node node)
    {
        // Create a printer job for the default printer
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null){
            // Get The Printer
            Printer printer = printerJob.getPrinter();

            // Create Paper
            Paper ticketPaper = PrintHelper.createPaper("Ticket100x100", 100, 100, Units.MM);

            // Create the Page Layout of the Printer
            // PageLayout pageLayout = printer.createPageLayout(ticketPaper, PageOrientation.LANDSCAPE,Printer.MarginType.EQUAL);
            PageLayout pageLayout = printer.createPageLayout(ticketPaper, PageOrientation.PORTRAIT,8.0,2.0,8.0,2.0);

            // Print the node using ticketPaper
            boolean printed = printerJob.printPage(pageLayout, node);;

            if (printed){
                // End the printer job
                printerJob.endJob();
                return true;
            }
            else{
                // Write Error Message
                System.out.println("\nPrinting failed.\nThe printer is damaged\nPlease contact with administrator");
                return false;
            }
        }
        else{
            // Write Error Message
            System.out.println("\nPrinting failed.\nThere is no printer");
            return false;
        }
    }
}
