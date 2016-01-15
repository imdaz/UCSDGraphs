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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
        List<MapNode> q = new LinkedList();

        MapNode curr = mapNodes.get(start);
        q.add(curr);
        trace.put(curr, null);
        boolean found = false;
        MapNode tmp;

        while (!q.isEmpty()) {
            curr = q.remove(0);

            // Hook for visualization.  See writeup.
            nodeSearched.accept(curr.getLocation());

            if (curr.getLocation().equals(goal)) {
                found = true;
                break;
            }

            if (!visited.contains(curr)) {

                for (MapEdge edge : curr.getNeighbors().values()) {
                    tmp = mapNodes.get(edge.getEnd());
                    if (!visited.contains(tmp)) {

                        /* adds a neighbor only if it has not been added before
                         // to trace. This preventing adding self-loops and 
                         // longer paths to q.
                         */
                        if (!trace.containsKey(tmp)) {
                            q.add(tmp);
                            trace.put(tmp, curr);
                        }
                    }
                }
                visited.add(curr);
            }
        }

        if (found) {
            result = buildPath(trace, goal);
        } 

        return result;
    }

    /**
     * Create a path from trace HashMap. returns a path - list of points from
     * start to goal
     */
    private List<GeographicPoint> buildPath(HashMap<MapNode, MapNode> trace,
            GeographicPoint goal) {

        MapNode curr = mapNodes.get(goal);

        List<GeographicPoint> result = new LinkedList<>();
        while (curr != null) {
            result.add(curr.getLocation());
            curr = trace.get(curr);
        }

        Collections.reverse(result);
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
        l = theMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(6.5, 0.0));

        System.out.println("BFS");
        if (l == null) {
            System.out.println("no way");
        } else {
            for (int i = 0; i < l.size(); i++) {
                System.out.println(l.get(i));
            }

        }

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
