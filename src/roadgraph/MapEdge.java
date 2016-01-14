/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadgraph;

import geography.GeographicPoint;

/**
 *
 * @author imdaz
 */
public class MapEdge {

    private final GeographicPoint start;
    private final GeographicPoint end;
    private final String streetName;
    private final String streetType;
    private final double distance;

    public MapEdge(GeographicPoint start, GeographicPoint end, 
            String streetName, String streetType, double distance) {
        this.start = start;
        this.end = end;
        this.streetName = streetName;
        this.streetType = streetType;
        this.distance = distance;
    }

    public GeographicPoint getStart() {
        return start;
    }

    public GeographicPoint getEnd() {
        return end;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetType() {
        return streetType;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "(" + start + ")->(" + end + ") " + streetName + ", " + streetType + ", " + distance + '}';
    }
    
    
}
