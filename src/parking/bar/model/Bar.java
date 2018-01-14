package parking.bar.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bar extends Thread{
    static public boolean isBarClosed = true;
    static public long barOpenTime = 60000; //in milisec if time is longer than 30sec
    private long startTime;
    private long elapsedTime;
    // IN FILE BAR CLOSED = 1, BAR OPENED = 0;
    static Path fileInOut = FileSystems.getDefault().getPath("connection", "bar.log");

    private void checkIfBarIsClosed(){
        if(!fileInOut.toFile().exists()){
            File theDir = new File(fileInOut.getParent().toString());
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                System.out.println("Creating directory: " + theDir.getName());
                try {
                    theDir.mkdir();
                } catch (SecurityException e) {
                    System.out.println("You do not have enough rights to create this folder");
                }
            }
            try (BufferedWriter writer = Files.newBufferedWriter(fileInOut, StandardCharsets.UTF_8)) {
                System.out.println("Creating file: " + fileInOut.toString());
                writer.write("", 0, 0);
            } catch (java.io.IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }
        try (BufferedReader reader = Files.newBufferedReader(fileInOut, StandardCharsets.UTF_8)) {
            String line = null;
            if ((line = reader.readLine()) != null) {
                //System.out.println(line); // only for tests
                if(line.startsWith("1")){
                    isBarClosed = true;
                }
            }
        }
        catch(java.nio.file.NoSuchFileException e){
            System.err.format("IOException: %s%n", e);
        }
        catch (java.io.IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public static void openBar(){
        if(!fileInOut.toFile().exists()){
            File theDir = new File(fileInOut.getParent().toString());
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                System.out.println("Creating directory: " + theDir.getName());
                try {
                    theDir.mkdir();
                } catch (SecurityException e) {
                    System.out.println("You do not have enough rights to create this folder");
                }
            }
        }
        if(isBarClosed) {
            System.out.println("Bar is opened");
            String s = "0";
            try (BufferedWriter writer = Files.newBufferedWriter(fileInOut, StandardCharsets.UTF_8)) {
                writer.write(s, 0, s.length());
                isBarClosed = false;
            } catch (java.io.IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }
    }

    private void closeBar(){
        if(!fileInOut.toFile().exists()){
            File theDir = new File(fileInOut.getParent().toString());
            // if the directory does not exist, create it
            if (!theDir.exists()) {
                System.out.println("Creating directory: " + theDir.getName());
                try {
                    theDir.mkdir();
                } catch (SecurityException e) {
                    System.out.println("You do not have enough rights to create this folder");
                }
            }
        }
        if(!isBarClosed) {
            System.out.println("Bar is closed");
            String s = "1";
            try (BufferedWriter writer = Files.newBufferedWriter(fileInOut, StandardCharsets.UTF_8)) {
                writer.write(s, 0, s.length());
                isBarClosed = true;
            } catch (java.io.IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        }
    }

    // run thread to check if bar isClosed
    public void run() {
        System.out.println("Thread: Started -> " + System.currentTimeMillis());
        startTime = System.currentTimeMillis();
        while(!isBarClosed) {
            // Get elapsed time in milliseconds
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime > barOpenTime)
                closeBar();
            checkIfBarIsClosed();
            try{
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                System.out.println(e);
            }
        }
        System.out.println("Thread: Bar is closed");
        System.out.println("Thread: Ended -> " + System.currentTimeMillis());
    }
}
