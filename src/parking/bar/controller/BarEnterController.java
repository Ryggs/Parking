package parking.bar.controller;
import parking.bar.model.Ticket;
import parking.bar.model.TicketDAO;
//javaFX
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
//printer
import javafx.print.PageOrientation;
import javafx.print.PageLayout;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
//sql
import java.sql.SQLException;

public class BarEnterController {

    @FXML
    private TextArea resultArea;

    @FXML
    final TextArea printingArea = new TextArea();

    //Set information to Text Area
    @FXML
    private void setInfoToTextArea (String infoText) {
        resultArea.setText(infoText);
    }

    //get new ticket from DataBase
    @FXML
    private void getTicket(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //TODO odliczanie czasu i wypiswywanie do pliku
        System.out.println("Pobieram nowy bilet");
        try {
            Ticket newTicket = TicketDAO.getNewTicket();
            String newInfo = "Twój nr biletu to " + newTicket.getTicketNo();
            String newInfo2 = "\nCzas wjazdu " + newTicket.getEntryTime().toString().substring(0,19);
            String newInfo3 = "\nMasz 30sek na wjazd";

            System.out.println(newInfo);
            System.out.println(newInfo2);
            System.out.println(newInfo3);
            setInfoToTextArea(newInfo + newInfo2 + newInfo3);

            //set up ticket
            printingArea.setFont(Font.font(8));
            printingArea.setPrefColumnCount(20);
            printingArea.setPrefRowCount(7);
            //set ticket info
            printingArea.setText("********* Parking javaPark *********");
            printingArea.appendText("\n======================");
            printingArea.appendText("\nTwój nr biletu to " + newTicket.getTicketNo());
            printingArea.appendText("\nCzas wjazdu " + newTicket.getEntryTime().toString().substring(0,19));
            printingArea.appendText("\n======================");
            printingArea.appendText("\nZachowaj ten bilet");
            printingArea.appendText("\nPrzed wyjazdem opłać w automacie");

            //Print ticket
            print(printingArea);

        } catch (SQLException e){
            System.out.println("Error occurred while getting new ticket from DB.\n" + e);
            throw e;
        }
    }

    private void print(Node node)
    {
        // Create a printer job for the default printer
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob != null){
            // Get The Printer
            Printer printer = printerJob.getPrinter();

            // Create Paper
            Paper ticketPaper = PrintHelper.createPaper("Ticket50x100", 50, 100, Units.MM);

            // Create the Page Layout of the Printer
            // PageLayout pageLayout = printer.createPageLayout(ticketPaper, PageOrientation.LANDSCAPE,Printer.MarginType.EQUAL);
            PageLayout pageLayout = printer.createPageLayout(ticketPaper,PageOrientation.PORTRAIT,2.0,2.0,2.0,2.0);

            // Print the node using ticketPaper
            boolean printed = printerJob.printPage(pageLayout, node);;

            if (printed){
                // End the printer job
                printerJob.endJob();
            }
            else{
                // Write Error Message
                resultArea.appendText("\nPrinting failed.\nThe printer is damaged\nPlease contact with administrator");
            }
        }
        else{
            // Write Error Message
            resultArea.appendText("\nPrinting failed.\nThere is no printer");
        }
    }
}
