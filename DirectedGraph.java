package com.example.java;
import java.util.*;

/**
 * Created by nayanks on 3/9/17.
 */

/**
 * Instantiate Class DirectedGraph to create a Directed Graph object.
 * This has an Inner Static class Edge which is used to define an edge.
 * An instance of InnerClass-Edge can exist only within an instance of OuterClass-Directed Graph.
 * Edge has vertex v and cost c(initialized to 1).
 */

public class DirectedGraph{

    public static class Edge{
        private Node node;
        private float cost;

        public Edge(Node node){
            this.node= node;
            cost = 1;
        }

        public Node getNode() {
            return node;
        }

        public float getCost() {
            return cost;
        }
        /**
         * Overriding the toString function to Give the output of the Edge instantiated with "To vertex" and the cost of the Edge.
         */
        @Override
        public String toString() {
            return "Edge [To vertex=" + node.toString() + ", cost=" + cost + "]";
        }

    }

    /**
     *THRESHOLD is a constant and can be updated based on requirement. This is used to converge the pagerank on n iterations.
     * A HashMap neighbour_vertices is used to map each vertex to its list of adjacent vertices.
     */

    private static final double THRESHOLD =0.0005;
    private static final float DAMPING_FACTOR = 0.85f; /* DAMPING_FACTOR can be set to any value between 0-1 */
    public HashMap<Node, List<Edge>> neighbourVertices = new HashMap<Node, List<Edge>>();
    public int nr_edges;

    /**
     * String representation of graph.
     */

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (Node node : neighbourVertices.keySet())
            s.append("\n    " + node.toString() + " -> " + neighbourVertices.get(node));
        return s.toString();
    }

    /**
     * Adds a vertex to the graph. Nothing happens if vertex is already in graph.
     * Edges to the vertex,which is the key in the Hashmap neighbourVertices has List as its value.
     * The list contains the vertices the key vertex is connected to.
     */

    public void add(Node node) {
        if (neighbourVertices.containsKey(node)){
            return;}
        neighbourVertices.put(node, new ArrayList<Edge>());
    }

    /**
     * This implementation allows the creation of multi-edges and self-loops.
     * also constantly updating the indegree and outdegree of a node
     */

    public void addEdge(Node from, Node to) {
        this.add(from);
        this.add(to);
        neighbourVertices.get(from).add(new Edge(to));
        from.outdegree++;
        to.indegree++;
        from.outlist.add(to);                           /* adds node-to to the outdegreelist of the from-node */
        to.inlist.add(from);                            /* adds node-to to the outdegreelist of the from-node */
        nr_edges++;                                     /* Global variable number of edges is incremented as an edge is added*/
    }


    /**
     * Method contains Returns True iff graph contains vertex.
     * Time complexity is O(1)
     */

    public boolean contains(Node node) {
        return neighbourVertices.containsKey(node);
    }

    /**
     * Method returns true if it is an edge.
     * Time complexity is the total number of edges for that vertex which is O(m).
     */

    public boolean isEdge(Node from, Node to) {
        for(Edge e :  neighbourVertices.get(from)){
            if(e.node.equals(to))
                return true;
        }
        return false;
    }

    /**
     * Method instantiateCostEdge Initilaizes cost of each outdegree edge of the vertex to 1/outdegree of the edge.
     * Time complexity is O(mn), which is traversing all vertices and edge.
     */

    public void instantiateCostEdge(){
        for (Node v : neighbourVertices.keySet()) {
            for (Edge e : neighbourVertices.get(v)) {
                e.cost = (float)1/v.outdegree;
            }
        }
    }

    /**
     * Method returns the cost of an Edge.
     * Time complexity is the total number of edges for that vertex which is O(m).
     */

    public float getCost(Node from, Node to) {
        for(Edge e :  neighbourVertices.get(from)){
            if(e.node.equals(to))
                return e.cost;
        }
        return -1;
    }

    /**
     * Method calculatePageRank calculates pageRank of each vertex.
     * We assume page A has pages T1...Tn which point to it.
     * The parameter d is a damping factor which can be set between 0 and 1. I have set DAMPING_FACTOR to 0.85.
     * PR(A) = (1-d) + d (PR(T1)/C(T1) + ... + PR(Tn)/C(Tn))
     */

    public void calculatePageRank(){
        boolean set_pagerank_flag = false;
        /* until equilibrium is reached */
        while(!set_pagerank_flag) {
            //iteration++;
            float newCost=0f;
            int count=0;
            int i = 0;
            for (Node v : neighbourVertices.keySet()) {
                /* if no inbound neighbours found for that vertex then PageRank is defaulted to 0.15 */
                if (v.indegree == 0) {
                    newCost = (float) (1 - DAMPING_FACTOR);
                    if(newCost==v.pageRank){
                        count++;
                    }
                    v.pageRank = newCost;
                    i++;
                } else {
                    newCost = 0f;
                    for (Node invertex : v.inlist) {
                        newCost += (DAMPING_FACTOR * invertex.pageRank / invertex.outdegree);
                    }
                    newCost += (1 - DAMPING_FACTOR);
                    if(newCost==v.pageRank){
                        count++;
                    }
                    v.pageRank = newCost;
                    i++;
                }
            }
            /* if count is equal to the number of nodes, we have reached equilibrium
            */
            if(count == neighbourVertices.size()){
                set_pagerank_flag=true;                 /* set_pagerank_flag set to true if equilibrium is reached */
            }
        }
    }

    /**
     * Method printPageRank Prints the Page Rank of each Vertex after computation.
     */

    public void printPageRank(){
        for (Node v : neighbourVertices.keySet()) {
            System.out.println("Page Rank of Vertex " +v.nodeNumber+" is : " +v.pageRank);
        }
    }

}
