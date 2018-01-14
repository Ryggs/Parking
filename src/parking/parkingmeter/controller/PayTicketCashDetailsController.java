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
import parking.parkingmeter.model.Ticket;
import parking.parkingmeter.model.TicketDAO;
import parking.parkingmeter.utils.FXMLUtils;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class PayTicketCashDetailsController {
    private String ticketNo;
    Ticket ticket;
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
            ticket = TicketDAO.getPaidTicket(ticketNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO: Ladniejsze wyswietlanie / drukowanie DONE-Klos
        resultTextArea.appendText(bundle.getString("txt.ticketNo") + "\t\t\t\t" + ticketNo +"\n");
        resultTextArea.appendText(bundle.getString("txt.entryTime") + "\t\t\t\t" +ticket.getEntryTime() +"\n");
        resultTextArea.appendText(bundle.getString("txt.paymentTime") + "\t\t\t\t" + ticket.getPaymentTime() +"\n");
        resultTextArea.appendText(bundle.getString("txt.paymentType") + "\t\t\t\t" + ticket.getPaymentType() +"\n");
        resultTextArea.appendText(bundle.getString("txt.charge") + "\t\t\t\t\t" +ticket.getCharge()/100.0 +"\n");
        resultTextArea.appendText(bundle.getString("txt.controlCode") + "\t\t\t\t" +ticket.getControlCode() +"\n");

        //set up ticket
        printingArea.setFont(Font.font(8));
        printingArea.setPrefColumnCount(18);
        printingArea.setPrefRowCount(12);  // While adding additional text remember to increase num of rows and columns
        //set ticket info
        printingArea.setText("********* Parking javaPark *********\n");
        printingArea.appendText("======================\n");
        printingArea.appendText(bundle.getString("txt.ticketNo") + "\t" + ticketNo +"\n");
        printingArea.appendText(bundle.getString("txt.entryTime") + "\t" +ticket.getEntryTime() +"\n");
        printingArea.appendText(bundle.getString("txt.paymentTime") + "\t" + ticket.getPaymentTime() +"\n");
        printingArea.appendText(bundle.getString("txt.paymentType") + "\t" + ticket.getPaymentType() +"\n");
        printingArea.appendText(bundle.getString("txt.charge") + "\t" +ticket.getCharge()/100.0 +"\n");
        printingArea.appendText(bundle.getString("txt.controlCode") + "\t" +ticket.getControlCode() +"\n");
        printingArea.appendText("======================\n");

        //Print ticket
        if(print(printingArea)) {
            resultTextArea.appendText(bundle.getString("txt.printSuccess") +"\n");
        }
        else {
            resultTextArea.appendText(bundle.getString("txt.printFailed") + "\n");
            resultTextArea.appendText(bundle.getString("txt.printFailed2") + "\n");
        }
    }


    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }


    private boolean print(Node node)
    {
        // Create a printer job for the default printer
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null){
            // Get The Printer
            Printer printer = printerJob.getPrinter();

            // Create Paper
            Paper ticketPaper = PrintHelper.createPaper("Ticket200x200", 200, 200, Units.MM);

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
