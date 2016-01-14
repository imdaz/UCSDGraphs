/**
 * @author UCSD MOOC development team and YOU
 *
 * A class which represents a graph of geographic locations Nodes in the graph
 * are intersections between
 *
 */
package roadgraph;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 *
 * A class which represents a graph of geographic locations Nodes in the graph
 * are intersections between
 *
 */
public class MapGraph {

    private HashMap<GeographicPoint, MapNode> mapNodes;
    private int numEdges;
    private int numVertices;

    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
        mapNodes = new HashMap<>();
        numEdges = 0;
        numVertices = 0;
    }

    /**
     * Get the number of vertices (road intersections) in the graph
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     *
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        return mapNodes.keySet();
    }

    /**
     * Get the number of road segments in the graph
     *
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        return numEdges;
    }

    /**
     * Add a node corresponding to an intersection at a Geographic Point If the
     * location is already in the graph or null, this method does not change the
     * graph.
     *
     * @param location The location of the intersection
     * @return true if a node was added, false if it was not (the node was
     * already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location) {
        if (location == null || mapNodes.containsKey(location)) {
            return false;
        }

        mapNodes.put(location, new MapNode(location));
        numVertices++;
        return true;
    }

    /**
     * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
     * GeographicPoints have already been added to the graph
     *
     * @param from The starting point of the edge
     * @param to The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been
     * added as nodes to the graph, if any of the arguments is null, or if the
     * length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to,
            String roadName, String roadType, double length)
            throws IllegalArgumentException {

        if (from == null || to == null || roadName == null
                || roadType == null || length < 0 || !mapNodes.containsKey(from)
                || !mapNodes.containsKey(to)) {
            throw new IllegalArgumentException();
        }

        if (!mapNodes.get(from).hasEdge(to)) {
            mapNodes.get(from).addNeighbor(to, roadName, roadType, length);
            numEdges++;
        }
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return bfs(start, goal, temp);
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization. See assignment instructions
     * for how to use it.
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start,
            GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {

        if (start == null || goal == null) {
            throw new IllegalArgumentException();
        }

        List<GeographicPoint> result = null;
        if (start.equals(goal)) {
            result = new LinkedList<>();
            result.add(goal);
            return result;
        }

        //HashMap for tracing path <child, parent>
        HashMap<MapNode, MapNode> trace = new HashMap<>();
        HashSet<MapNode> visited = new HashSet<>();
        // queue for BFS
        List<MapNode> q = new LinkedList();

        MapNode curr = mapNodes.get(start);
        q.add(curr);
        trace.put(curr, null);
        boolean found = false;
        MapNode tmp;

        while (!q.isEmpty()) {
            curr = q.remove(0);
            nodeSearched.accept(curr.getLocation());
            
            System.out.println("**************************"+curr);
            
            
            
            
            
            
            if (!visited.contains(curr)) {
                
                
                System.out.println("=====================");
                for (MapEdge edge : curr.getNeighbors().values()) {
                    tmp = mapNodes.get(edge.getEnd());
//                    System.out.println("vis "+ visited);
//                    System.out.println("tmp "+ tmp);
//                    System.out.println("con "+ visited.contains(tmp));
                    if (!visited.contains(tmp)) {
                        
                        
                        q.add(tmp);
                        trace.put(tmp, curr);
                        System.out.println("trace " + tmp + "->" + curr);
                        
                        
                    }

                }
                visited.add(curr);
                System.out.println("=====================");
            }
            
            if (curr.getLocation().equals(goal)) {
                found = true;
                break;
            }
            
        }

        if (found) {
            result = buildPath(trace, goal);
        }
        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return result;
    }

    private List<GeographicPoint> buildPath(HashMap<MapNode, MapNode> trace,
            GeographicPoint goal) {

        System.out.println("building");
        System.out.println(trace);
        MapNode curr = mapNodes.get(goal);
        
        Stack<GeographicPoint> result = new Stack<>();
        while (curr != null) {
//            System.out.println(curr.getLocation());
            
            result.push(curr.getLocation());
            
            curr = trace.get(curr);
        }
        System.out.println("end --- ");
        return result;
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest path from start
     * to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        // You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return dijkstra(start, goal, temp);
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization. See assignment instructions
     * for how to use it.
     * @return The list of intersections that form the shortest path from start
     * to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start,
            GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());
        return null;
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal The goal location
     * @return The list of intersections that form the shortest path from start
     * to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return aStarSearch(start, goal, temp);
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal The goal location
     * @param nodeSearched A hook for visualization. See assignment instructions
     * for how to use it.
     * @return The list of intersections that form the shortest path from start
     * to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start,
            GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		// TODO: Implement this method in WEEK 3

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());
        return null;
    }

    @Override
    public String toString() {

        return "MapGraph{" + "mapNodes=" + mapNodes + '}';
    }

    public static void main(String[] args) {
        System.out.print("Making a new map...");
        MapGraph theMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
        System.out.println("DONE.");
        int c = 1;

        System.out.println(theMap.numEdges + " " + theMap.numVertices);
        for (GeographicPoint gp : theMap.getVertices()) {
            System.out.println("" + c++ + ". " + gp);
        }

//        System.out.println("=============================");
//        c = 1;
//            for (MapNode mp : theMap.mapNodes.values()){
//                for(MapEdge me : mp.getNeighbors().values()){
//                    System.out.println("" + c++ + ". "+ me);
//                }
//            }
        List<GeographicPoint> l;
        l = theMap.bfs(new GeographicPoint(4.0, 1.0), new GeographicPoint(5.0, 1.0));
        
        MapNode m1 = theMap.mapNodes.get(new GeographicPoint(4.0, 1.0));
        
        for (MapEdge e : m1.getNeighbors().values()){
            System.out.println("edges:" + e);
        }
        
        if (l == null) {
            System.out.println("no way");
        } else {
            for (GeographicPoint g : l) {
                System.out.println(g);
            }
        }
//        List<GeographicPoint> l = theMap.bfs((GeographicPoint) s.toArray()[0], (GeographicPoint) s.toArray()[1]);
//        System.out.println("***** path ");
//        System.out.println(l);
        // You can use this method for testing.  
        /* Use this code in Week 3 End of Week Quiz
         MapGraph theMap = new MapGraph();
         System.out.print("DONE. \nLoading the map...");
         GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
         System.out.println("DONE.");

         GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
         GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
         List<GeographicPoint> route = theMap.dijkstra(start,end);
         List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

         */
    }

}
