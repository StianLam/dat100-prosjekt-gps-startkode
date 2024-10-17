package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import javax.swing.JOptionPane;

public class ShowRoute extends EasyGraphics {
    private static int MARGIN = 50;
    private static int MAPXSIZE = 800;
    private static int MAPYSIZE = 800;

    private GPSPoint[] gpspoints;
    private GPSComputer gpscomputer;
    
    private double minlon, minlat, maxlon, maxlat;
    private double xstep, ystep;

    public ShowRoute() {
        String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
        gpscomputer = new GPSComputer(filename);
        gpspoints = gpscomputer.getGPSPoints();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run() {
        makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);
        
        minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
        minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
        maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
        maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
        
        xstep = scale(MAPXSIZE, minlon, maxlon);
        ystep = scale(MAPYSIZE, minlat, maxlat);
        
        showRouteMap(MARGIN + MAPYSIZE);
        showStatistics();
        replayRoute(MARGIN + MAPYSIZE);
    }

    public double scale(int maxsize, double minvalue, double maxvalue) {
        double scale = maxsize / (Math.abs(maxvalue - minvalue));
        return scale;
    }

    public void showRouteMap(int ybase) {
        setColor(0, 255, 0);
        
        for (GPSPoint gpspoint : gpspoints) {
            int x = MARGIN + (int) ((gpspoint.getLongitude() - minlon) * xstep);
            int y = ybase - (int) ((gpspoint.getLatitude() - minlat) * ystep);
            fillCircle(x, y, 3);
        }
    }

    public void showStatistics() {
        int TEXTDISTANCE = 20;
        setColor(0, 0, 0);
        setFont("Courier", 12);
        
        drawString("Total Time     : " + GPSUtils.formatTime(gpscomputer.totalTime()), 10, 20);
        drawString("Total distance : " + String.format("%.2f", gpscomputer.totalDistance() / 1000) + " km", 10, 40);
        drawString("Total elevation: " + String.format("%.2f", gpscomputer.totalElevation()) + " m", 10, 60);
        drawString("Max speed      : " + String.format("%.2f", gpscomputer.maxSpeed() * 3.6) + " km/t", 10, 80);
        drawString("Average speed  : " + String.format("%.2f", gpscomputer.averageSpeed() * 3.6) + " km/t", 10, 100);
        drawString("Energy         : " + String.format("%.2f", gpscomputer.totalKcal(80)) + " kcal", 10, 120);
    }

    public void replayRoute(int ybase) {
        setColor(0, 0, 255);
        
        for (GPSPoint gpspoint : gpspoints) {
            int x = MARGIN + (int) ((gpspoint.getLongitude() - minlon) * xstep);
            int y = ybase - (int) ((gpspoint.getLatitude() - minlat) * ystep);
            
            fillCircle(x, y, 5);
            pause(50);
        }
    }
}