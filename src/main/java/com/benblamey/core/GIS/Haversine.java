package com.benblamey.core.GIS;

/**
 * Utility method for computing the Haversine distance.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class Haversine {

    // http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java
    public static double distInMiles(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }

}
