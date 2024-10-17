package no.hvl.dat100ptc.oppgave1;

public class Main {

    public static void main(String[] args) {
        // Oppretter et GPSData-objekt med plass til to Point-objekter
        GPSData gpsData = new GPSData(2);
        
        // Setter inn to Point-objekter i GPSData
        gpsData.insertGPS(new Point(60.3913, 5.3221, "Bergen Sentrum"));
        gpsData.insertGPS(new Point(63.4305, 10.3951, "Trondheim Sentrum"));
        
        // Skriver ut informasjon om Point-objektene
        gpsData.print();
    }

    static class Point {
        private double latitude;
        private double longitude;
        private String description;

        public Point(double latitude, double longitude, String description) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.description = description;
        }

        // Getters
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public String getDescription() { return description; }
    }

    static class GPSData {
        private Point[] gpsPoints;
        private int currentIndex = 0;

        public GPSData(int size) {
            gpsPoints = new Point[size];
        }

        public void insertGPS(Point point) {
            if (currentIndex < gpsPoints.length) {
                gpsPoints[currentIndex++] = point; // Inkrementer currentIndex etter innsetting
            } else {
                throw new ArrayIndexOutOfBoundsException("Array is full, cannot add more points!");
            }
        }

        public void print() {
            for (Point point : gpsPoints) {
                if (point != null) {
                    System.out.println("GPS Point: " + point.getDescription());
                    System.out.println("Latitude: " + point.getLatitude());
                    System.out.println("Longitude: " + point.getLongitude());
                    System.out.println();
                }
            }
        }
    }
}