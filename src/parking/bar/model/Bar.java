package parking.bar.model;

public class Bar extends Thread{
    // IN FILE BAR CLOSED = 1, BAR OPENED = 0;
    static public boolean isBarClosed = true;
    static public long barOpenTime = 10000; //in milisec if time is longer than 30sec
    private long startTime;
    private long elapsedTime;

    private void checkIfBarIsClosed(){
        //TODO sprawdź czy w pliku zmieniła się wartość na 1
        // System.out.println("Checking if bar is closed");
    }

    public static void openBar(){
        if(isBarClosed) {
            isBarClosed = false;
            //TODO zapisz do pliku stan 0- szlaban otawrty
            System.out.println("Bar is opened");
        }
    }

    private void closeBar(){
        if(!isBarClosed) {
            isBarClosed = true;
            //TODO zapisz do pliku stan 1-szlaban zamknięty
            System.out.println("Bar is closed");
        }
    }

    // run thread to check if bar isClosed
    public void run() {
        System.out.println("Thread started " + System.currentTimeMillis());
        startTime = System.currentTimeMillis();
        while(!isBarClosed) {
            // Get elapsed time in milliseconds
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime > barOpenTime)
                closeBar();
            checkIfBarIsClosed();
            try{
                Thread.sleep(500);
            }
            catch (InterruptedException e){
                System.out.println(e);
            }
        }
        System.out.println("Thread Ended " + System.currentTimeMillis());
    }

}
