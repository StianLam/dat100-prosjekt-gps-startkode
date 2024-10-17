package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowSpeed extends EasyGraphics {
    private static int MARGIN = 50;
    private static int BARHEIGHT = 100;
    private GPSComputer gpscomputer;
    
    public ShowSpeed() {
        String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
        gpscomputer = new GPSComputer(filename);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void run() {
        makeWindow("Speed profile", 
                2 * MARGIN + 2 * gpscomputer.speeds().length, 
                2 * MARGIN + BARHEIGHT);
        
        showSpeedProfile(MARGIN + BARHEIGHT);
    }
    
    public void showSpeedProfile(int ybase) {
        setColor(0, 0, 255);
        
        double[] speeds = gpscomputer.speeds();
        double maxSpeed = gpscomputer.maxSpeed();
        double averageSpeed = gpscomputer.averageSpeed();
        
        for (int i = 0; i < speeds.length; i++) {
            int height = (int) ((speeds[i] / maxSpeed) * BARHEIGHT);
            drawLine(MARGIN + 2*i, ybase, MARGIN + 2*i, ybase - height);
        }
        
        setColor(0, 255, 0);
        int averageHeight = (int) ((averageSpeed / maxSpeed) * BARHEIGHT);
        drawLine(MARGIN, ybase - averageHeight, MARGIN + 2*speeds.length, ybase - averageHeight);
    }
}