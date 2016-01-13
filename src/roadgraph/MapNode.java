/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roadgraph;

import geography.GeographicPoint;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author imdaz
 */
public class MapNode {

    private GeographicPoint location;
    private List<MapEdge> neighbors;

    public MapNode(GeographicPoint location, List<MapEdge> neighbors) {
        this.location = location;
        this.neighbors = neighbors;
    }

    public MapNode(GeographicPoint location) {
        this.location = location;
        this.neighbors = new LinkedList<>();
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public List<MapEdge> getNeighbors() {
        return neighbors;
    }

    public void setLocation(GeographicPoint location) {
        this.location = location;
    }

    public void addNeighbor(MapEdge neighbor) {
        if (neighbor != null)
        this.neighbors.add(neighbor);
    }

    @Override
    public String toString() {

        return "MapNode{" + "location=" + location + neighbors.size() +'}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapNode other = (MapNode) obj;
        return Objects.equals(this.location, other.location);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.location);
        return hash;
    }
    
    
}
