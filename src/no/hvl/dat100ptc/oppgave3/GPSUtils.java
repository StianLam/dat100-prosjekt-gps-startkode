package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

    public static double findMax(double[] da) {
        double max; 
        
        max = da[0];
        
        for (double d : da) {
            if (d > max) {
                max = d;
            }
        }
        
        return max;
    }

    public static double findMin(double[] da) {
        double min = da[0];
        
        for (double d : da) {
            if (d < min) {
                min = d;
            }
        }
        
        return min;
    }

    public static double[] getLatitudes(GPSPoint[] gpspoints) {
        double[] latitudes = new double[gpspoints.length];
        
        for (int i = 0; i < gpspoints.length; i++) {
            latitudes[i] = gpspoints[i].getLatitude();
        }
        
        return latitudes;
    }

    public static double[] getLongitudes(GPSPoint[] gpspoints) {
        double[] longitudes = new double[gpspoints.length];
        
        for (int i = 0; i < gpspoints.length; i++) {
            longitudes[i] = gpspoints[i].getLongitude();
        }
        
        return longitudes;
    }

    private static final int R = 6371000; // jordens radius

    public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
        double d;
        double latitude1, longitude1, latitude2, longitude2;

        latitude1 = toRadians(gpspoint1.getLatitude());
        longitude1 = toRadians(gpspoint1.getLongitude());
        latitude2 = toRadians(gpspoint2.getLatitude());
        longitude2 = toRadians(gpspoint2.getLongitude());

        double deltaLatitude = latitude2 - latitude1;
        double deltaLongitude = longitude2 - longitude1;

        double a = compute_a(latitude1, latitude2, deltaLatitude, deltaLongitude);
        double c = compute_c(a);

        d = R * c;

        return d;
    }
    
    private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {
        return pow(sin(deltaphi / 2), 2) + cos(phi1) * cos(phi2) * pow(sin(deltadelta / 2), 2);
    }

    private static double compute_c(double a) {
        return 2 * atan2(sqrt(a), sqrt(1 - a));
    }
    
    public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
        int secs;
        double speed;
        
        double distance = distance(gpspoint1, gpspoint2);
        secs = gpspoint2.getTime() - gpspoint1.getTime();
        speed = distance / secs;
        
        return speed;
    }

    public static String formatTime(int secs) {
        String TIMESEP = ":";

        int hours = secs / 3600;
        int minutes = (secs % 3600) / 60;
        int seconds = secs % 60;

       
        String timestr = String.format("%1$2s%2$02d%3$s%4$02d%3$s%5$02d", "  ", hours, TIMESEP, minutes, seconds);

        System.out.println("formatTime(" + secs + ") returnerer: '" + timestr + "'");
        return timestr;
    }
    
    private static int TEXTWIDTH = 10;

    public static String formatDouble(double d) {
        String str;
        
        str = String.format("%" + TEXTWIDTH + ".2f", d);
        
        return str;
    }
}