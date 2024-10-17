package no.hvl.dat100ptc.oppgave1;

public class GPSPoint {
    // Objektvariabler
    private int time;
    private double latitude;
    private double longitude;
    private double elevation;

    // Konstruktør
    public GPSPoint(int time, double latitude, double longitude, double elevation) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    // Get/set metoder
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    // toString metode
    @Override
    public String toString() {
        return String.format("%d (%.1f,%.1f) %.1f\n", time, latitude, longitude, elevation);
    }
}