package parking.bar.controller;
import parking.bar.model.Ticket;
import parking.bar.model.TicketDAO;
import parking.bar.model.Bar;
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

public class BarEnterController{
    private boolean canGetTicket = true;
    Bar barChecker;

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
        setInfoToTextArea("Zaczekaj aż szlaban opadnie");
        resultArea.appendText("\nWait until the bar is closed");
        if(Bar.isBarClosed)
            canGetTicket = true;
        if(canGetTicket) {
            canGetTicket = false;
            System.out.println("Pobieram nowy bilet");
            try {
                Ticket newTicket = TicketDAO.getNewTicket();

                //set up ticket
                printingArea.setFont(Font.font(6));
                printingArea.setPrefColumnCount(25);
                printingArea.setPrefRowCount(12);
                //set ticket info
                printingArea.setText("********* Parking javaPark *********");
                printingArea.appendText("\n======================");
                printingArea.appendText("\nNr biletu / TicketNo " + newTicket.getTicketNo());
                printingArea.appendText("\nCzas wjazdu / Entry Time" + newTicket.getEntryTime().toString().substring(0, 19));
                printingArea.appendText("\n======================");
                printingArea.appendText("\nZachowaj ten bilet");
                printingArea.appendText("\nPrzed wyjazdem opłać w automacie");
                printingArea.appendText("\nKeep this ticket");
                printingArea.appendText("\nPay in ticket machine, before leaving");
                printingArea.appendText(("\n"));

                //Print ticket
                if(print(printingArea)) {
                    //open Bar
                    Bar.openBar();

                    //run thread to check for bar status
                    barChecker = new Bar();
                    barChecker.start();

                    String newInfo = "Twój nr biletu to " + newTicket.getTicketNo();
                    String newInfo2 = "\nCzas wjazdu " + newTicket.getEntryTime().toString().substring(0, 19);
                    String newInfo3 = "\nMasz 60sek na wjazd";

                    System.out.print(newInfo);
                    System.out.print(newInfo2);
                    System.out.print(newInfo3);
                    System.out.println("");
                    setInfoToTextArea(newInfo + newInfo2 + newInfo3);
                    resultArea.appendText("\n==============\n");
                    resultArea.appendText("Your ticketNo is " + newTicket.getTicketNo() +"\n");
                    resultArea.appendText("Entry time " + newTicket.getEntryTime().toString().substring(0, 19) + "\n");
                    resultArea.appendText("You have 60sec for entry\n");
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while getting new ticket from DB.\n" + e);
                throw e;
            }
        }
    }

    private boolean print(Node node)
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
            PageLayout pageLayout = printer.createPageLayout(ticketPaper,PageOrientation.PORTRAIT,8.0,2.0,8.0,2.0);

            // Print the node using ticketPaper
            boolean printed = printerJob.printPage(pageLayout, node);;

            if (printed){
                // End the printer job
                printerJob.endJob();
                return true;
            }
            else{
                // Write Error Message
                resultArea.setText("Awaria drukarki\nSkontaktuj się z administratorem");
                resultArea.appendText("\n======================");
                resultArea.appendText("\nPrinting failed.\nThe printer is damaged\nPlease contact with administrator");
                return false;
            }
        }
        else{
            // Write Error Message
            resultArea.setText("Drukowanie nieudane.\nBrak drukarki.\nSkontaktuj się z administratorem.");
            resultArea.appendText("\n======================");
            resultArea.appendText("\nPrinting failed.\nThere is no printer");
            return false;
        }
    }

}
