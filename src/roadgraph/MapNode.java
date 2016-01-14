/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadgraph;

import geography.GeographicPoint;
import java.util.HashMap;

/**
 *
 * @author imdaz
 */
public class MapNode {

    private final GeographicPoint location;
    private final HashMap<GeographicPoint, MapEdge> neighbors;

    public MapNode(GeographicPoint location) {
        this.location = location;
        this.neighbors = new HashMap<>();
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public HashMap<GeographicPoint, MapEdge> getNeighbors() {
        return neighbors;
    }

    public boolean hasEdge(GeographicPoint neighbor) {
        return neighbors.get(neighbor) != null;
    }

    public boolean addNeighbor(GeographicPoint neighbor, String streetName,
            String streetType, double distance) {

        if (neighbor != null && !hasEdge(neighbor)
                && !this.location.equals(neighbor)) {

            this.neighbors.put(neighbor, new MapEdge(location, neighbor,
                    streetName, streetType, distance));
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "MapNode{" + "location=" + location + neighbors.size() + '}';
    }

    public static void main(String... args) {
        MapNode node = new MapNode(new GeographicPoint(4.0, 1.0));

//        System.out.println("Adding new neighbor (true)");
//        System.out.println(node.addNeighbor(new GeographicPoint(2.0, 2.0), "main", "road", 7));
//        System.out.println("Adding the same neighbor (fasle)");
//        System.out.println(node.addNeighbor(new GeographicPoint(2.0, 2.0), "main", "road", 7));
//        System.out.println("Check existed neighbor (true)");
//        System.out.println(node.hasEdge(new GeographicPoint(2.0, 2.0)));
//        System.out.println("Check non-existed neighbor (false)");
//        System.out.println(node.hasEdge(new GeographicPoint(2.0, 3.0)));
//        System.out.println("Self-edge (false)");
//        System.out.println(node.addNeighbor(new GeographicPoint(1.0, 1.0), "main", "road", 7));
        
        
        
    }

}
