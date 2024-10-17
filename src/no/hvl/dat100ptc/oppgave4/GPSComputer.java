package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
    
    private GPSPoint[] gpspoints;
    
    public GPSComputer(String filename) {
        GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
        gpspoints = gpsdata.getGPSPoints();
    }

    public GPSComputer(GPSPoint[] gpspoints) {
        this.gpspoints = gpspoints;
    }
    
    public GPSPoint[] getGPSPoints() {
        return this.gpspoints;
    }
    
    public double totalDistance() {
        double distance = 0;
        for (int i = 0; i < gpspoints.length - 1; i++) {
            distance += GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
        }
        return distance;
    }

    public double totalElevation() {
        double elevation = 0;
        for (int i = 0; i < gpspoints.length - 1; i++) {
            double diff = gpspoints[i+1].getElevation() - gpspoints[i].getElevation();
            if (diff > 0) {
                elevation += diff;
            }
        }
        return elevation;
    }

    public int totalTime() {
        return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
    }
        
    public double[] speeds() {
        double[] speeds = new double[gpspoints.length-1];
        for (int i = 0; i < gpspoints.length - 1; i++) {
            speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
        }
        return speeds;
    }
    
    public double maxSpeed() {
        double[] speeds = speeds();
        return GPSUtils.findMax(speeds);
    }

    public double averageSpeed() {
        return totalDistance() / totalTime();
    }

    public static final double MS = 2.23;

    public double kcal(double weight, int secs, double speed) {
        double met;
        double speedmph = speed * MS;
        
        if (speedmph < 10) met = 4.0;
        else if (speedmph < 12) met = 6.0;
        else if (speedmph < 14) met = 8.0;
        else if (speedmph < 16) met = 10.0;
        else if (speedmph < 20) met = 12.0;
        else met = 16.0;
        
        return met * weight * (secs / 3600.0);
    }

    public double totalKcal(double weight) {
        double totalkcal = 0;
        for (int i = 0; i < gpspoints.length - 1; i++) {
            int secs = gpspoints[i+1].getTime() - gpspoints[i].getTime();
            double speed = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
            totalkcal += kcal(weight, secs, speed);
        }
        return totalkcal;
    }
    
    private static double WEIGHT = 80.0;
    
    public void displayStatistics() {
        System.out.println("==============================================");
        System.out.println("Total Time     : " + GPSUtils.formatTime(totalTime()));
        System.out.println("Total distance : " + String.format("%10.2f", totalDistance()/1000) + " km");
        System.out.println("Total elevation: " + String.format("%10.2f", totalElevation()) + " m");
        System.out.println("Max speed      : " + String.format("%10.2f", maxSpeed()*3.6) + " km/t");
        System.out.println("Average speed  : " + String.format("%10.2f", averageSpeed()*3.6) + " km/t");
        System.out.println("Energy         : " + String.format("%10.2f", totalKcal(WEIGHT)) + " kcal");
        System.out.println("==============================================");
    }
}