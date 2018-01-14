package parking.parkingmeter.controller;

import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import parking.parkingmeter.model.Sub;
import parking.parkingmeter.model.SubDAO;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class BuySubDetailsController {
    private String username;
    private Sub sub;
    private ResourceBundle bundle = FXMLUtils.getResourceBundle();


    @FXML
    private TextArea resultTextArea;

    @FXML
    void close(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    final TextArea printingArea = new TextArea();

    public void fillTextField() {
        try {
            sub = SubDAO.getLatestSub(username);
            // TODO: Ladniejsze wyswietlanie / drukowanie   DONE
            resultTextArea.appendText(bundle.getString("txt.username") + "\t\t\t\t\t" + username +"\n");
            resultTextArea.appendText(bundle.getString("txt.name") + "\t\t\t\t" +sub.getName() +"\n");
            resultTextArea.appendText(bundle.getString("txt.surname") + "\t\t\t\t" + sub.getSurname() +"\n");
            resultTextArea.appendText(bundle.getString("txt.start") + "\t\t\t\t" +sub.getStartTime() +"\n");
            resultTextArea.appendText(bundle.getString("txt.end") + "\t\t\t\t\t" +sub.getEndTime() +"\n");

            //set up ticket
            printingArea.setFont(Font.font(8));
            printingArea.setPrefColumnCount(20);
            printingArea.setPrefRowCount(10);  // While adding additional text remember to increase num of rows and columns
            //set ticket info
            printingArea.setText("********* Parking javaPark *********\n");
            printingArea.appendText("======================\n");
            printingArea.appendText(bundle.getString("txt.username") + "\t" + username +"\n");
            printingArea.appendText(bundle.getString("txt.name") + "\t" +sub.getName() +"\n");
            printingArea.appendText(bundle.getString("txt.surname") + "\t" + sub.getSurname() +"\n");
            printingArea.appendText(bundle.getString("txt.start") + "\t" +sub.getStartTime() +"\n");
            printingArea.appendText(bundle.getString("txt.end") + "\t" +sub.getEndTime() +"\n");
            printingArea.appendText("======================\n");

            //Print ticket
            if(print(printingArea)) {
                resultTextArea.appendText(bundle.getString("txt.printSuccess") +"\n");
            }
            else {
                resultTextArea.appendText(bundle.getString("txt.printFailed") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultTextArea.appendText(bundle.getString("err.dbError") + "\n");
        }
    }

    public void setUser(String username) {
        this.username = username;
        System.out.println(this.username);
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
